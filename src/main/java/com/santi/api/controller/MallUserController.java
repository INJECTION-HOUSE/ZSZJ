package com.santi.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.UserEntity;
import com.santi.core.global.common.IPUtil;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.LoginService;
import com.santi.core.service.MemberService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "/api/malluser", description = "这是会员登录API") // Swagger annotation
@RestController
@RequestMapping("/api/malluser")
public class MallUserController {
	private static final Logger log = LoggerFactory.getLogger("login");
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "会员登录 - 传入参数是用户手机号码作为登录名、密码")
	@RequestMapping(value="signin", method = RequestMethod.GET)
	@ResponseBody
	public List<RespJson> login(HttpServletRequest request, String userAccount, String password){
		List<RespJson> results = new ArrayList<>();
		RespJson result = null;
		UserEntity user = new UserEntity();
		user.setUserAccount(userAccount);
		user.setPassword(password);
		
		try{
			result = loginService.login(request, user);
			MemberEntity member = memberService.getMemeberByCellPhone(userAccount);
			user.setId(member.getId());
			user.setUsername(member.getNickName());
			result.setData(user);
			
		}catch(Throwable e){
			log.error("用户{}尝试登陆，系统出错：{},ip为{}",user.getUserAccount(),e.getMessage(),IPUtil.getIp(request));
			throw e;
		}
		log.info("用户{}尝试登陆，结果：{},ip为{}",user.getUserAccount(),result.getMsg(),IPUtil.getIp(request));
		results.add(result);
		return results;
	}
	
	@ApiOperation(value = "会员查询 - 传入参数-cellphone - 会员手机号码， 返回会员信息")
	@RequestMapping(value = "queryMember",  method = RequestMethod.GET)
	@ResponseBody
	public List<MemberEntity> getUserInfo(HttpServletRequest request, String cellphone) {
		List<MemberEntity> results = new ArrayList<>();
		MemberEntity member = memberService.getMemeberByCellPhone(cellphone);
		results.add(member);
		return results;
	}
	
	@ApiOperation(value = "会员查询 - 传入参数 用户Id-member id - 用户id， 返回会员信息")
	@RequestMapping(value = "queryMemberById",  method = RequestMethod.GET)
	@ResponseBody
	public List<MemberEntity> getUserInfoById(HttpServletRequest request, String memberId) {
		List<MemberEntity> results = new ArrayList<>();
		MemberEntity member = memberService.getMemberById(Long.parseLong(memberId));
		results.add(member);
		return results;
	}
	
	
	@ApiOperation(value = "会员信息更新 - 传入参数-会员信息JSON - 必须包含全部查询得到信息，以及修改以后的信息")
	@RequestMapping(value = "updateMember",  method = RequestMethod.POST)
	@ResponseBody
	public List<RespJson> updateUserInfo(HttpServletRequest request, MemberEntity member) {
		List<RespJson> results = new ArrayList<>();
		memberService.editMember(member);
		results.add(RespJsonFactory.buildSuccess());
		return results;
	}
	
	@ApiOperation(value = "会员注销 - 传入参数userAccount:是用户手机号码作为登录名、password:密码")
	@RequestMapping(value = "signout",  method = RequestMethod.GET)
	@ResponseBody
	public List<RespJson> signout(HttpServletRequest request, String userAccount, String password){
		// fix issue by ZHIGANG on 2016-0913
		List<RespJson> results = new ArrayList<>();
		LoginInfoUtil.signOut(request);
		results.add(RespJsonFactory.buildSuccess());
		return results;
	}
	
	@ApiOperation(value = "查询手机号码是否已经注册 - 返回code = 0表示没有注册， code = 1 表示用户已经存在")
	@RequestMapping(value = "queryExist",  method = RequestMethod.GET)
	@ResponseBody
	public List<RespJson> isExist(HttpServletRequest request, String cellphone, String password){
		// fix issue by ZHIGANG on 2016-0913
		List<RespJson> results = new ArrayList<>();
		MemberEntity member = memberService.getMemeberByCellPhone(cellphone);
		if(member != null && cellphone.equals(member.getCellphone())) {
			RespJson result = RespJsonFactory.buildSuccess();
			result.setCode(1);
			results.add(result);	
			log.info("尝试查询用户{}已经存在，结果：{},ip为{}",member.getNickName(), member.getCellphone(), IPUtil.getIp(request));
		} else {
			RespJson result = RespJsonFactory.buildSuccess();
			result.setData(null);
			result.setCode(0);
			results.add(result);
		}
		return results;
	}
	
	@ApiOperation(value = "查询用户昵称是否重复 - 返回code = 0表示没有重复， code = 1 表示用户昵称已经存在")
	@RequestMapping(value = "checkDuplicateName",  method = RequestMethod.GET)
	@ResponseBody
	public List<RespJson> isDuplicateName(HttpServletRequest request, String nickname){
		// fix issue by ZHIGANG on 2016-0913
		List<RespJson> results = new ArrayList<>();
		MemberEntity member = memberService.getMemeberByNickName(nickname);
		if(member != null && nickname.equals(member.getNickName())) {
			RespJson result = RespJsonFactory.buildSuccess();
			result.setCode(1);
			results.add(result);	
			log.info("尝试查询用户{}已经存在，结果：{},ip为{}",member.getNickName(), member.getCellphone(), IPUtil.getIp(request));
		} else {
			RespJson result = RespJsonFactory.buildSuccess();
			result.setData(null);
			result.setCode(0);
			results.add(result);
		}
		return results;
	}
}
