package com.santi.api.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.util.XMLUtil;
import com.santi.core.datamodel.dto.AccessTokenOpenIdInfoBean;
import com.santi.core.datamodel.dto.WeiXinConstants;
import com.santi.core.datamodel.dto.WeiXinUserInfoBean;
import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.entity.WeiXinUserEntity;
import com.santi.core.entity.WeiXinUserInfoEntity;
import com.santi.core.global.common.ApplicationConstants;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.ApplyMemberService;
import com.santi.core.service.MemberService;
import com.santi.core.service.WeiXinUserInfoService;
import com.wordnik.swagger.annotations.Api;

@Api(value = "/wechat/", description = "微信接口对接API") // Swagger annotation
@RestController
@RequestMapping("/api/wechat")
public class WeiXinClientController {

	private static final Logger logger = Logger.getLogger(WeiXinClientController.class);
	
	@Autowired
	private WeiXinUserInfoService wxUserInfoService;
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
    private ApplyMemberService applyCertificationService;
	
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("queryRole")
	@ResponseBody
	public Map<String, Object> queryMemberRight(HttpServletRequest request){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String openId = loginInfo.getLoginUser().getOpenId();
		logger.info("query member role by openid : " + openId);
		WeiXinUserEntity entity = wxUserInfoService.findUserByOpenid(openId);
		Map<String, Object> myMap = new HashMap<String, Object>();
		if(!entity.isValid()) {
			myMap.put("role", "unknown");
		} else {
			MemberEntity member = memberService.getMemberByOpenId(openId);
			if(member.getRole().equals(DataEntityConstants.ROLE_UNKNOWN_TYPE)) {
				MemberAuditEntity auditInfo = applyCertificationService.queryCertification(member.getId());
				if(auditInfo == null || auditInfo.getStatus() == 2) {
					myMap.put("role", "unknown");
				}
				else {
					myMap.put("role", "processing");
				}
			}
			else if(member.getRole().equals(DataEntityConstants.ROLE_PERSON_TYPE)) {
				myMap.put("role", "member");
			}
			else if(member.getRole().equals(DataEntityConstants.ROLE_ENTERPRISE_TYPE)) {
				myMap.put("role", "enterprise");
			}
			myMap.put("result", new Integer(1));
		}
		logger.info("weixin client role : " + myMap.get("role"));
		myMap.put("result", new Integer(1));
		return myMap;
	}
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("bindWeixinAccount")
	public void bindWeiXinAccount(String openId, String cellphone, String password){
		logger.info("try to bind the member info with openid : " + openId);
		wxUserInfoService.bindMemberInfo(openId, cellphone, password);
		logger.info("binding weixin account with cellphone : " + cellphone);
	}
	
	@RequestMapping(value = "/requestAccessCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> requestOauth2(HttpServletRequest request, HttpServletResponse response) {
		// code=CODE&state=STATE
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		logger.info("accept Oauth2 right by state : " + state);
		if(state.equals(WeiXinConstants.APP_OAUTH_STATE) && code != null) {
			if(code.length() > 0) {
				try {
					// 通过code获取access token
					HttpClient httpClient = HttpClientBuilder.create().build(); 
					HttpGet get = new HttpGet(WeiXinConstants.REQUEST_ACCESS_TOKEN_URL.replace("{CODE}", code));
					logger.info("[[requestOauth2]]->> current request code : " + code);
					HttpResponse httpResponse = httpClient.execute(get);
					String responseJSON = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
					
					logger.info("response json content : " + responseJSON);
					// parse CODE_URL CONTENT
					ObjectMapper mapper = new ObjectMapper();
					AccessTokenOpenIdInfoBean acc = mapper.readValue(responseJSON, AccessTokenOpenIdInfoBean.class);
					logger.info("response error message : " + acc.getErrmsg());
					logger.info("response access token : " + acc.getAccess_token());
					String accesstoken = acc.getAccess_token();
					String openid = acc.getOpenid();
					get.releaseConnection();
					// 通过access token与openid获取用户信息
					if(accesstoken != null && !"".equals(accesstoken) && openid != null && !"".equals(openid)) {
						String requestUserInfoURL = WeiXinConstants.REQUEST_USERINFO_URL.replace("{ACCESS_TOKEN}", accesstoken).replace("{OPENID}", openid);
						get = new HttpGet(requestUserInfoURL);
						httpResponse = httpClient.execute(get);
						responseJSON = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
						logger.info("response json content : " + responseJSON);
						// parse CODE_URL CONTENT
						WeiXinUserInfoBean wxUserInfo = mapper.readValue(responseJSON, WeiXinUserInfoBean.class);
						logger.info("response nickname : " + wxUserInfo.getNickname());
						get.releaseConnection();
						WeiXinUserEntity entity = loginSystem(request, wxUserInfo);
						resultMap.put("data", entity);
						resultMap.put("errcode", 0);
					} else {
						resultMap.put("errcode", 4000);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultMap;
		
	}
	
	private WeiXinUserEntity loginSystem(HttpServletRequest request, WeiXinUserInfoBean wxUserInfo) {
		boolean isExist = wxUserInfoService.isExistUser(wxUserInfo.getOpenid());
		if(!isExist) {
			wxUserInfoService.createWXUserInfo(wxUserInfo);
		} 
		WeiXinUserEntity userInfo = wxUserInfoService.findUserByOpenid(wxUserInfo.getOpenid());
		UserEntity user = new UserEntity();
		if(userInfo.isValid()) {
			MemberEntity member = memberService.getMemberByOpenId(userInfo.getOpenid());
			user.setId(member.getId());
			user.setUserAccount(member.getCellphone());
			userInfo.setId(member.getId());
		} else {
			user.setUserAccount("13000001111");
		}
		user.setOpenId(userInfo.getOpenid());
		user.setPicture(userInfo.getHeadimgurl());
		user.setUsername(userInfo.getNickname());
		LoginInfoUtil.setLoginInfoToSession(request, user, "");
		return userInfo;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void confirmBindingMsg(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		
		// 首次绑定 ， 需要验证签名
		logger.info("signature from weixin : " + signature);
		logger.info("timestamp from weixin : " + timestamp);
		try {
			OutputStream os = response.getOutputStream();
			os.write("true".getBytes());
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void receiveMessage(HttpServletRequest request, HttpServletResponse response) {
		logger.info("\t [[receiveMessage]] ->> start to auto-reply message callback method...");
		try {
			Map<String, String> resultMap = new HashMap<String, String>();
			InputStream inputStream = request.getInputStream();
			// 读取输入流
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List list = root.getChildren();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if (children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = XMLUtil.getChildrenText(children);
				}
				resultMap.put(k, v);
			}
			inputStream.close();
			inputStream = null;
			String openId = resultMap.get("FromUserName");
			String ownerAccount = resultMap.get("ToUserName");
			String msgType = resultMap.get("MsgType");
			if("text".equals(msgType)) {
				logger.info("message from weixin : " + resultMap.get("Content"));
				logger.info("openid from weixin user : " + openId);
			}
			
			response.getWriter().print(XMLUtil.responseMessageXML(ApplicationConstants.WEIXIN_WELCOME_MSG));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/requestUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public WeiXinUserInfoEntity getUserInfo(String openId) {
		WeiXinUserInfoEntity entity = wxUserInfoService.getUserInfo(openId);
		return entity;
		
	}

}
