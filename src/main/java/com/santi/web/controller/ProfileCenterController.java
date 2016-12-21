package com.santi.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.messages.Global;
import com.santi.core.common.util.CodeTypeConstants;
import com.santi.core.datamodel.dto.PersonDetailInfoDto;
import com.santi.core.entity.MemberEntity;
import com.santi.core.global.common.ApplicationConstants;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.MemberDetailInfoService;
import com.santi.core.service.MemberService;

@Controller
@RequestMapping("profile")
public class ProfileCenterController {
	private static final Logger logger = Logger.getLogger(ProfileCenterController.class);
	
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
    private MemberDetailInfoService memberDetailService;
	
	@RequestMapping("home")
	public String goPersonalCenter(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		logger.info("go to member profile center with name : " + loginInfo.getLoginName());
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		model.addAttribute("nickname", memberInfo.getNickName());
		model.addAttribute("myscore", memberInfo.getScore() == null ? "0" : String.valueOf(memberInfo.getScore()));
		model.addAttribute("myincome", memberInfo.getTotalCash());
		return "personinfo/profilecenter";
	}
	
	@RequestMapping("editicon")
	public String goEditIcon(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		model.addAttribute("myiconpath", memberInfo.getAvatarName());
		return "personinfo/updateicon";
	}
	
	@RequestMapping("editprofile")
	public String goEditProfile(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		PersonDetailInfoDto detailInfoDto = memberDetailService.getMemberDetailInfoByMemberId(loginInfo.getLoginUser().getId());
		model.addAttribute("genderItems", CodeTypeConstants.getGenderMap());
		model.addAttribute("detailInfoForm", detailInfoDto);
		model.addAttribute(ApplicationConstants.SUCCESS_MSG_KEY, "");
		return "personinfo/updateprofile";
	}
	
	@RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
	public String applyCertification(@Valid PersonDetailInfoDto memberDetailInfo, BindingResult result, Model model) {
		logger.info("update member detail information from client console...");
		memberDetailService.updateDetailInfo(memberDetailInfo);
		model.addAttribute("genderItems", CodeTypeConstants.getGenderMap());
		model.addAttribute("detailInfoForm", memberDetailInfo);
		model.addAttribute(ApplicationConstants.SUCCESS_MSG_KEY, Global.getMessage("common.update.success"));
		return "personinfo/updateprofile";
	}
	
	@RequestMapping("viewprofile")
	public String goViewProfile(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		PersonDetailInfoDto detailInfoDto = memberDetailService.getMemberDetailInfoByMemberId(loginInfo.getLoginUser().getId());
		model.addAttribute("memberDetailInfo", detailInfoDto);
		return "personinfo/viewprofile";
	}
	
	@RequestMapping("saveAvatar")
	@ResponseBody
	public RespJson updateAvatarImage(HttpServletRequest request, String avatarImage){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		memberInfo.setAvatarName(avatarImage);
		memberService.editMember(memberInfo);
		return RespJsonFactory.buildSuccess();
	}
	
}
