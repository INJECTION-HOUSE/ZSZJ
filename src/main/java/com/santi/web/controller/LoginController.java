package com.santi.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.util.SMSGateWayProxy;
import com.santi.core.entity.UserEntity;
import com.santi.core.global.common.IPUtil;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.LoginService;
import com.santi.core.service.MemberService;



@Controller
@RequestMapping("login")
public class LoginController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger("login");
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("admin")
	public String goAdminLogin(){
		return "login/login";
	}
	
	@RequestMapping("index")
	public String goLogin(){
		return "pclogin/login";
	}
	
	@RequestMapping("register")
	public String goMemberRegister(){
		return "pclogin/register";
	}
	
	@RequestMapping("getSMSCode")
	@ResponseBody
	public RespJson getSMSCode(HttpServletRequest request, @RequestParam String cellphone){
		String lastTime = (String)request.getSession().getAttribute("sms-code-cellphone");
		if(lastTime == null || "".equals(lastTime)) {
			request.getSession().setAttribute("sms-code-cellphone", ""+System.currentTimeMillis());
		}
		else {
			long currentTime = System.currentTimeMillis();
			long duration = (currentTime - Long.parseLong(lastTime)) / 60000;
			if(duration < 5) {
				return RespJsonFactory.buildFailure();
			}
			request.getSession().setAttribute("sms-code-cellphone", ""+System.currentTimeMillis());
		}
		RespJson result = RespJsonFactory.buildSuccess();
		String code = SMSGateWayProxy.getRadomCode();
		result.setData(code);
		List<String> contactList = new ArrayList<String>();
		contactList.add(cellphone);
		SMSGateWayProxy.sendMessage(contactList, "验证码:" + code);
		return result;
	}
	
	@RequestMapping(value="createNewUser", method = RequestMethod.POST)
	@ResponseBody
	public RespJson createNewUser(HttpServletRequest request, UserEntity user){
		return memberService.createMember(user);
	}
	
	@RequestMapping("passwordUpdateInit")
	public String passwordUpdateInit(HttpServletRequest request, UserEntity user){
		return "login/passwordUpdate";
	}
	
	@RequestMapping("passwordUpdate")
	@ResponseBody
	public RespJson passwordUpdate(HttpServletRequest request, String password, String newpassword){
		return loginService.passwordUpdate(request, password, newpassword);
	}
	
	@RequestMapping("signinAdmin")
	@ResponseBody
	public RespJson loginAdmin(HttpServletRequest request, UserEntity user){
		RespJson result = null;
		try{
			result = loginService.loginAdmin(request, user);
		}catch(Throwable e){
			log.error("用户{}尝试登陆，系统出错：{},ip为{}",user.getUserAccount(),e.getMessage(),IPUtil.getIp(request));
			throw e;
		}
		log.info("用户{}尝试登陆，结果：{},ip为{}",user.getUserAccount(),result.getMsg(),IPUtil.getIp(request));
		return result;
	}
	
	@RequestMapping("signin")
	@ResponseBody
	public RespJson login(HttpServletRequest request, UserEntity user){
		RespJson result = null;
		try{
			result = loginService.login(request, user);
		}catch(Throwable e){
			log.error("用户{}尝试登陆，系统出错：{},ip为{}",user.getUserAccount(),e.getMessage(),IPUtil.getIp(request));
			throw e;
		}
		log.info("用户{}尝试登陆，结果：{},ip为{}",user.getUserAccount(),result.getMsg(),IPUtil.getIp(request));
		return result;
	}
	
	@RequestMapping("signout")
	@ResponseBody
	public RespJson signout(HttpServletRequest request, UserEntity user){
		// fix issue by ZHIGANG on 2016-0913
		LoginInfoUtil.signOut(request);
		return RespJsonFactory.buildSuccess();
	}
}
