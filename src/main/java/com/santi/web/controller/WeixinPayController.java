package com.santi.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.santi.core.common.util.UUIDUtil;
import com.santi.core.common.util.WeiXinPaymentUtil;
import com.santi.core.common.util.XMLUtil;
import com.santi.core.datamodel.dto.UnifiedorderDto;
import com.santi.core.datamodel.dto.WeiXinConstants;
import com.santi.core.entity.MemberEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.MemberService;

@Controller
@RequestMapping("payment")
public class WeixinPayController {
	private static final Logger logger = Logger.getLogger(WeixinPayController.class);
	
	@Value("#{configProperties['upload.location.path']}")
	private String basePath;
	
	@Autowired
    private MemberService memberService;

	@RequestMapping(value = "/unifiedorder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> startUnifiedorder(HttpServletRequest request, HttpServletResponse response, @RequestParam String cash) {
		Map<String,Object> result=new HashMap<String,Object>();
		UnifiedorderDto dto = new UnifiedorderDto();
		if(cash == null || "".equals(cash)) {
			result.put("error", "cash could not be zero");
			return result;
		}
		int totalfee = 100*Integer.parseInt(cash);
		logger.info("total recharge cash : " + totalfee);
		dto.setProduct_id(String.valueOf(System.currentTimeMillis()));
		dto.setBody("repair");
		dto.setNonce_str(String.valueOf(System.nanoTime()));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		// 通过手机号作为身份识别标志
		dto.setOut_trade_no(loginInfo.getLoginUser().getUserAccount() + "_" + String.valueOf(System.currentTimeMillis()));
		dto.setTotal_fee(totalfee);
		dto.setSpbill_create_ip("127.0.0.1");
		// generate signature
		dto.setSign(dto.makeSign());
		logger.info("sign : " + dto.makeSign());
		logger.info("xml content : " + dto.generateXMLContent());
		try {
			HttpClient httpClient = HttpClientBuilder.create().build(); 
			HttpPost post = new HttpPost(WeiXinConstants.UNIFIEDORDER_URL);
			post.addHeader("Content-Type", "text/xml; charset=UTF-8");
			StringEntity xmlEntity = new StringEntity(dto.generateXMLContent(), ContentType.TEXT_XML);
			post.setEntity(xmlEntity);
			HttpResponse httpResponse = httpClient.execute(post);
			String responseXML = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			logger.info("response xml content : " + responseXML);
			// parse CODE_URL CONTENT
			Map<String, String> resultMap = (Map<String, String>)XMLUtil.doXMLParse(responseXML);
			logger.info("response code_url : " + resultMap.get("code_url"));
			String codeurl = resultMap.get("code_url");
			if(codeurl != null && !"".equals(codeurl)) {
				String imageurl = generateQrcode(codeurl);
				result.put("QRIMAGE", imageurl);
			}
			post.releaseConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		result.put("success", "1");
		return result;
	}
	
	private String generateQrcode(String codeurl) {
		File foldler = new File(basePath + "qrcode");
		if(!foldler.exists()) {
			foldler.mkdirs();
		}
		
		String f_name = UUIDUtil.uuid() + ".png";
        try {
        	File f = new File(basePath + "qrcode", f_name);
        	FileOutputStream fio = new FileOutputStream(f);
        	MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        	Map hints = new HashMap();
        	hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
        	BitMatrix bitMatrix = null;
            bitMatrix = multiFormatWriter.encode(codeurl, BarcodeFormat.QR_CODE, 300, 300,hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            //输出二维码图片流
            ImageIO.write(image, "png", fio);
            return ("qrcode/" + f_name);
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }     
	}
	
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
          for (int y = 0; y < height; y++) {
            image.setRGB(x, y, matrix.get(x, y) ? WeiXinConstants.BLACK : WeiXinConstants.WHITE);
          }
        }
        return image;
   }  
	
	@RequestMapping(value = "/notifycallback", method = RequestMethod.POST)
	@ResponseBody
	public void finishPayment(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("start to callback from weixin server: " + request.getRemoteHost());
			Map<String, String> resultMap = new HashMap<String, String>();
			InputStream inputStream = request.getInputStream();
		    // 读取输入流
			SAXBuilder saxBuilder= new SAXBuilder();
		    Document document = saxBuilder.build(inputStream);
		    // 得到xml根元素
		    Element root = document.getRootElement();
		    // 得到根元素的所有子节点
		    List list = root.getChildren();
			Iterator it = list.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List children = e.getChildren();
				if(children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = XMLUtil.getChildrenText(children);
				}
				resultMap.put(k, v);
			}
			
			// 验证签名！！！
			/*
			String[] keys = resultMap.keySet().toArray(new String[0]);
			Arrays.sort(keys);
			String kvparams = "";
			for(int i=0; i<keys.length; i++) {
				if(keys[i].equals("esign")) {
					continue;
				}
				// 签名算法
				if(i == 0) {
					kvparams += (keys[i] + "=" + resultMap.get(keys[i]));
				} else {
					kvparams += ("&" + keys[i] + "=" + resultMap.get(keys[i]));
				}
			}
			String esign = kvparams + "&key=" + WeiXinConstants.MD5_API_KEY;
			String md5esign = WeiXinPaymentUtil.MD5Encode(esign, "UTF-8");
			if(!md5esign.equals(resultMap.get("sign"))) {
				return;
			}*/
			
			//关闭流
		    // 释放资源
		    inputStream.close();
		    inputStream = null;
		    String returnCode = resultMap.get("return_code");
		    String outtradeno = resultMap.get("out_trade_no");
		    // 以分为单位
		    int nfee = Integer.parseInt(resultMap.get("total_fee"));
		    logger.info("out trade no : " + outtradeno);
		    logger.info("total_fee : " + nfee);
		    // 业务处理流程
		    if("SUCCESS".equals(returnCode)) {		    	
		    	String cellphone = outtradeno.substring(0, 11);
		    	logger.info("user cellphone : " + cellphone);
		    	MemberEntity member = memberService.getMemeberByCellPhone(cellphone);
		    	int newTotalCash = member.getTotalCash() + nfee/100;
		    	logger.info("new total cash : " + newTotalCash);
		    	member.setTotalCash(newTotalCash);
		    	memberService.editMember(member);
		    	response.getWriter().print(XMLUtil.getRetResultXML(resultMap.get("return_code"), resultMap.get("return_code")));
		    } else {
		    	response.getWriter().print(XMLUtil.getRetResultXML(resultMap.get("return_code"), resultMap.get("return_msg")));
		    }
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		} catch (JDOMException e1) {
			e1.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/appUnifiedorder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> appUnifiedOrder(HttpServletRequest request, HttpServletResponse response, @RequestParam int cash, @RequestParam String openId) {
		Map<String,Object> result=new HashMap<String,Object>();
		UnifiedorderDto dto = new UnifiedorderDto(true);
		int totalfee = 100*cash;
		logger.info("total recharge cash : " + totalfee);
		dto.setBody("app_repair");
		dto.setNonce_str(String.valueOf(System.nanoTime()));
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		// 通过手机号作为身份识别标志
		dto.setOut_trade_no("wechatorder_" + String.valueOf(System.currentTimeMillis()));
		dto.setTotal_fee(totalfee);
		dto.setSpbill_create_ip("127.0.0.1");
		// generate signature
		dto.setOpenId(openId);
		dto.setSign(dto.makeAppSign());
		logger.info("sign : " + dto.makeAppSign());
		logger.info("xml content : " + dto.generateAppXMLContent());
		try {
			HttpClient httpClient = HttpClientBuilder.create().build(); 
			HttpPost post = new HttpPost(WeiXinConstants.UNIFIEDORDER_URL);
			post.addHeader("Content-Type", "text/xml; charset=UTF-8");
			StringEntity xmlEntity = new StringEntity(dto.generateAppXMLContent(), ContentType.TEXT_XML);
			post.setEntity(xmlEntity);
			HttpResponse httpResponse = httpClient.execute(post);
			String responseXML = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			logger.info("response xml content : " + responseXML);
			// parse CODE_URL CONTENT
			Map<String, String> resultMap = (Map<String, String>)XMLUtil.doXMLParse(responseXML);
			String return_code = resultMap.get("return_code");
			if(return_code.equals("SUCCESS")){
				String appid = resultMap.get("appid");
				String nonce_str = resultMap.get("nonce_str");
//				String sign = resultMap.get("sign");
				String prepay_id = resultMap.get("prepay_id");
				String packageStr = "prepay_id=" + prepay_id;
				String timeStamp = String.valueOf(Math.round(new Date().getTime() / 1000));
				String signStr = "appId=" + appid + "&nonceStr=" + nonce_str + "&package=" + packageStr + "&signType=MD5&timeStamp=" + timeStamp + "&key=" + WeiXinConstants.MD5_API_KEY;
				String finalSign = WeiXinPaymentUtil.MD5Encode(signStr, "utf-8").toUpperCase();
				result.put("success", "1");
				result.put("appid", appid);
				result.put("timeStamp", timeStamp);
				result.put("nonce_str", nonce_str);
				result.put("sign", finalSign);
				result.put("package", packageStr);
			}else{
				result.put("success", "0");
				result.put("message", resultMap.get("return_msg"));
			}
			post.releaseConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/appnotifycallback", method = RequestMethod.POST)
	@ResponseBody
	public void appFinishPayment(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("app unified order callback");
	}

}
