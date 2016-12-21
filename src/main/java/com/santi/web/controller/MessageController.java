package com.santi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.entity.MessageEntity;
import com.santi.core.entity.NoticeEntity;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.service.MessageService;

@Controller
@RequestMapping("messages")
public class MessageController {
	private static final Logger logger = Logger.getLogger(MessageController.class);
	
	
	@Autowired
    private MessageService messageService;
	
	@RequestMapping(value = "systemNotify", method = RequestMethod.GET)
	public String goNotifyView(Locale locale, Model model) {
		logger.info("go to system notification view...");
		return "messages/notification";
	}
	
	@RequestMapping(value = "privacy", method = RequestMethod.GET)
	public String goPrivacyMsgView(Locale locale, Model model) {
		logger.info("go to privacy message view...");
		return "messages/privacy";
	}
	
	@RequestMapping("/listInbox")
	@ResponseBody
	public List<MessageEntity> listMsgInbox(HttpServletRequest request) {
		logger.info("try to retrieve list inbox messages...");
		String nickname = LoginInfoUtil.getUserName(request);
		return messageService.listInBoxMsg(nickname);
	}
	
	@RequestMapping("/countInBox")
	@ResponseBody
	public Map<String, Object> countMsgInbox(HttpServletRequest request) {
		logger.info("try to retrieve list inbox messages...");
		String nickname = LoginInfoUtil.getUserName(request);
		int count = messageService.countInBoxMsg(nickname);
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("count", new Integer(count));
		myMap.put("result", new Integer(1));
		return myMap;
	}
	
	@RequestMapping("/listOutbox")
	@ResponseBody
	public List<MessageEntity> listMsgOutbox(HttpServletRequest request) {
		logger.info("try to retrieve list outbox messages...");
		String nickname = LoginInfoUtil.getUserName(request);
		return messageService.listOutBoxMsg(nickname);
	}
	
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("readMessage")
	@ResponseBody
	public MessageEntity readMessage(Integer id){
		logger.info("try to load the message content by id : " + id);
		return messageService.getMessageById(id);
	}
	
	/**
	 * 通过ajax保存
	 * @param user
	 * @param result 
	 * @return
	 */
	@RequestMapping(value="savePrivacyMsg", method={RequestMethod.POST})
	@ResponseBody
	public RespJson saveMessage(@Valid MessageEntity entity,BindingResult result ){
		if(result.hasErrors()){
			return RespJsonFactory.buildFailure(result.getAllErrors().get(0).getDefaultMessage(), "module.codetype.exist.error");
		}
		if(entity.getId() == null) {
			logger.info("create new message...");
			messageService.createMessage(entity);
		}
		else {
			messageService.updateReadStatus(entity);
			logger.info("mark message as read one with id : " + entity.getId());
		}
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value="/deletePrivacy", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deletePrivacyMsg(@RequestBody MessageEntity entity) {
		if(entity != null)
		{
			messageService.deleteMessage(entity);
			logger.info("delete Code type data from db with id : " + entity.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}
	
	@RequestMapping(value="/markAsReadMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> markAsReadMsg(@RequestBody MessageEntity entity) {
		if(entity != null)
		{
			messageService.updateReadStatus(entity);
			logger.info("mark message as read one with id : " + entity.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "标记成功...");
		return map;
	} 
	
	
	@RequestMapping("/listNotices")
	@ResponseBody
	public List<NoticeEntity> listNotices() {
		logger.info("try to retrieve notice entities...");
		return messageService.listNotices();
	}
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("editNotice")
	@ResponseBody
	public NoticeEntity editNotice(Integer id){
		logger.info("try to load the code type data entity by id : " + id);
		return messageService.findNoticeById(id);
	}
	
	/**
	 * 通过ajax保存
	 * @param user
	 * @param result 
	 * @return
	 */
	@RequestMapping(value="saveNotice", method={RequestMethod.POST})
	@ResponseBody
	public RespJson save(@Valid NoticeEntity entity,BindingResult result ){
		if(result.hasErrors()){
			return RespJsonFactory.buildFailure(result.getAllErrors().get(0).getDefaultMessage(), "module.codetype.exist.error");
		}
		if(entity.getId() == null) {
			logger.info("create new code type...");
			messageService.createNotice(entity);
		}
		else {
			logger.info("update code type by id : " + entity.getId());
			messageService.updateNotices(entity);		
		}
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value="/deleteNotice", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteTypeEntity(@RequestBody NoticeEntity entity) {
		if(entity != null)
		{
			messageService.deleteNotice(entity);
			logger.info("delete Code type data from db with id : " + entity.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String goMessageView(Locale locale, Model model) {
		logger.info("go to message view...");
		return "messages/messagelist";
	}

}
