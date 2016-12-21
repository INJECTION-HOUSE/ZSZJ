package com.santi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.datamodel.dto.MyNoticeDto;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.MessageEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.MemberService;
import com.santi.core.service.MessageService;
import com.santi.core.service.MyMessageService;

@Controller
@RequestMapping("mymessage")
public class MyMessageController {
	private static final Logger logger = Logger.getLogger(MyMessageController.class);
	
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
    private MyMessageService mymessageService;
	
	@Autowired
    private MessageService messageService;
	
	@RequestMapping("home")
	public String goPersonalCenter(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		logger.info("go to member profile center with name : " + loginInfo.getLoginName());
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		model.addAttribute("nickname", memberInfo.getNickName());
		model.addAttribute("myscore", memberInfo.getScore() == null ? "0" : String.valueOf(memberInfo.getScore()));
		model.addAttribute("myincome", memberInfo.getTotalCash());
		return "personmessage/lettercenter";
	}
	
	
	@RequestMapping("privacyletters")
	public String goPrivacyLetters(){
		return "personmessage/privacyletters";
	}
	
	@RequestMapping("activities")
	public String gomarketactivity(){
		return "personmessage/marketactivity";
	}
	
	@RequestMapping("sysnotices")
	public String goSystemNotices(){
		return "personmessage/systemnotices";
	}
	
	
	@RequestMapping("/listMyNotices")
	@ResponseBody
	public List<MyNoticeDto> listNotices() {
		logger.info("try to retrieve notice entities...");
		return mymessageService.listMyNotices();
	}
	
	@RequestMapping("/listOutbox")
	@ResponseBody
	public List<MessageEntity> listMsgOutbox(HttpServletRequest request) {
		logger.info("try to retrieve list outbox messages...");
		String nickname = LoginInfoUtil.getUserName(request);
		return messageService.listOutBoxMsg(nickname);
	}
	
	@RequestMapping("/listInbox")
	@ResponseBody
	public List<MessageEntity> listMsgInbox(HttpServletRequest request) {
		logger.info("try to retrieve list inbox messages...");
		String nickname = LoginInfoUtil.getUserName(request);
		return messageService.listInBoxMsg(nickname);
	}
	
	@RequestMapping(value="/deletePrivacy", method = RequestMethod.DELETE)
	@ResponseBody
	public RespJson deletePrivacyMsg(@RequestBody MessageEntity entity) {
		if(entity != null)
		{
			messageService.deleteMessage(entity);
			logger.info("delete Code type data from db with id : " + entity.getId());
		}
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value="/clearInbox", method = RequestMethod.DELETE)
	@ResponseBody
	public RespJson clearInbox() {
		mymessageService.clearInbox();
		return RespJsonFactory.buildSuccess();
	}

}
