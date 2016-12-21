package com.santi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.messages.Global;
import com.santi.core.common.util.CodeTypeConstants;
import com.santi.core.datamodel.dto.BidCommentsDto;
import com.santi.core.datamodel.dto.PageListResultDto;
import com.santi.core.datamodel.dto.PersonDetailInfoDto;
import com.santi.core.entity.CodeItemEntity;
import com.santi.core.entity.IncomeRecordEntity;
import com.santi.core.entity.MemberAuditEntity;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.MessageEntity;
import com.santi.core.entity.TaskBidEntity;
import com.santi.core.entity.TaskEntity;
import com.santi.core.global.common.ApplicationConstants;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.SearchBidRecordParam;
import com.santi.core.param.SearchIncomeParam;
import com.santi.core.param.SearchTaskParam;
import com.santi.core.param.UpdateTaskBidParam;
import com.santi.core.service.ApplyMemberService;
import com.santi.core.service.CodeService;
import com.santi.core.service.IncomeRecordService;
import com.santi.core.service.MemberDetailInfoService;
import com.santi.core.service.MemberService;
import com.santi.core.service.MessageService;
import com.santi.core.service.TaskService;
import com.santi.core.service.WithDrawService;

@Controller
@RequestMapping("personCenter")

public class PersonCenterController extends BaseController {

	@Autowired
    private ApplyMemberService applyCertificationService;
	
	@Autowired
    private WithDrawService withDrawService;
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
	private MemberDetailInfoService memberDetailService;
	
	@Autowired
    private CodeService codeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private IncomeRecordService incomeService;
	
	@Autowired
    private MessageService messageService;
	
	
	@RequestMapping("home")
	public String goPersonalCenter(HttpServletRequest request, Model model) {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		logger.info("go to member profile center with name : " + loginInfo.getLoginName());
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		model.addAttribute("nickname", memberInfo.getNickName());
		model.addAttribute("myscore", memberInfo.getScore() == null ? "0" : String.valueOf(memberInfo.getScore()));
		model.addAttribute("myincome", memberInfo.getTotalCash());
		return "personcenter/personalcenter";
	}
	
	@RequestMapping("tasklist")
	public String goTaskList(Model model){
		return "personcenter/tasklist";
	}
	
	@RequestMapping("newtask")
	public String goCreateTask(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		logger.info("go to issue new task page for member : " + loginInfo.getLoginName());
		TaskEntity ent = new TaskEntity();
		model.addAttribute("taskForm", ent);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("typeName", CodeTypeConstants.CODE_ISSUE_TYPE);
		List<CodeItemEntity> codes = codeService.listCodeItems(paramMap);
		Map<Integer, String> codeMap = new HashMap<Integer, String>();
		for(CodeItemEntity entity : codes) {
			codeMap.put(entity.getId(), entity.getName());
		}
		model.addAttribute("issueType", codeMap);
		return "personcenter/newtask";
	}
	
	@RequestMapping(value = "/savetask", method = RequestMethod.POST)
	public String saveTask(@Valid @ModelAttribute("taskForm") TaskEntity taskEntity,
			BindingResult result, Model model){
		if(result.hasErrors()){
			return "personcenter/newtask";
		}
		taskService.save(taskEntity);
		return "personcenter/tasklist";
	}
	
	@RequestMapping("bidlist")
	public String goBidList(){
		return "personcenter/bidlist";
	}
	
	@RequestMapping("incomerecords")
	public String goIncomeRecords(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		logger.info("go to member profile center with name : " + loginInfo.getLoginName());
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		Integer avacash = memberInfo.getAvaCash();
		model.addAttribute("availcash", avacash);
		return "personcenter/incomerecords";
	}
	
	
	@RequestMapping("orderlist")
	public String goMyOrderList(){
		return "personcenter/orderlist";
	}
	
	
	@RequestMapping("myskillset")
	public String goMySkillSet(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		logger.info("go to member profile center with name : " + loginInfo.getLoginUser().getUsername());
		PersonDetailInfoDto detailInfo = memberDetailService.getMemberDetailInfoByMemberId(loginInfo.getLoginUser().getId());
		model.addAttribute("skillDesc", detailInfo.getSkills());
		return "personcenter/myskillset";
	}
	
	@RequestMapping("/avatar")
	@ResponseBody
	public Map<String, Object> getAvatarImage(HttpServletRequest request) {
		logger.info("get avatar image from server side...");
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("avatarPath", memberInfo.getAvatarName());
		myMap.put("result", new Integer(1));
		return myMap;
	}
	
	@RequestMapping("/queryRole")
	@ResponseBody
	public Map<String, Object> queryMemberRole(HttpServletRequest request) {
		logger.info("query member role...");
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		String cellphone = loginInfo.getLoginUser().getUserAccount();
		MemberEntity memberInfo = memberService.getMemeberByCellPhone(cellphone);
		Map<String, Object> myMap = new HashMap<String, Object>();
		if(memberInfo.getRole().equals(DataEntityConstants.ROLE_UNKNOWN_TYPE)) {
			MemberAuditEntity auditInfo = applyCertificationService.queryCertification(memberInfo.getId());
			if(auditInfo == null || auditInfo.getStatus() == 2) {
				myMap.put("role", "unknown");
			}
			else {
				myMap.put("role", "processing");
			}
		}
		else if(memberInfo.getRole().equals(DataEntityConstants.ROLE_PERSON_TYPE)) {
			myMap.put("role", "member");
		}
		else if(memberInfo.getRole().equals(DataEntityConstants.ROLE_ENTERPRISE_TYPE)) {
			myMap.put("role", "enterprise");
		}
		myMap.put("result", new Integer(1));
		return myMap;
	}
	
	@RequestMapping("goCertification")
	public String goApplyCertification(HttpServletRequest request, Model model){
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		MemberEntity memberForm = memberService.getMemeberByCellPhone(loginInfo.getLoginUser().getUserAccount());
		model.addAttribute("memberForm", memberForm);
		model.addAttribute("genderItems", CodeTypeConstants.getGenderMap());
		model.addAttribute("lisenceTypeList", CodeTypeConstants.getCertTypeMap());
		return "personcenter/applycertification";
	}
	
	@RequestMapping(value = "/applyCertif", method = RequestMethod.POST)
	public String applyCertification(@Valid MemberEntity member, BindingResult result, Model model) {
		logger.info("apply member certification detail information from client console...");
		applyCertificationService.applyCertification(member);
		model.addAttribute(ApplicationConstants.SUCCESS_MSG_KEY, Global.getMessage("common.update.success"));
		return "personcenter/success";
	}
	
	@RequestMapping(value = "/applyWithdraw", method = RequestMethod.GET)
	@ResponseBody
	public RespJson applyWithDraw(HttpServletRequest request) {
		logger.info("apply income withdraw from client console...");
		return withDrawService.applyWithDraw();
	}
	
	@RequestMapping("/listMyBidRecords")
	@ResponseBody
	public PageListResultDto<TaskBidEntity> listBidRecords(HttpServletRequest request, String pageIndex) {
		logger.info("try to retrieve list bid records...");
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		MemberEntity member = memberService.getMemeberByCellPhone(loginInfo.getLoginUser().getUserAccount());
		SearchBidRecordParam params = new SearchBidRecordParam();
		params.setPageIndex(Integer.parseInt(pageIndex));
		params.setMemberId(member.getId());
		PageListResultDto<TaskBidEntity> pageDto = taskService.getBidRecordsByMemberId(params);
		return pageDto;
	}
	
	@RequestMapping("/listMyIncomeRecords")
	@ResponseBody
	public PageListResultDto<IncomeRecordEntity> listMyIncomeRecords(HttpServletRequest request, String pageIndex) {
		logger.info("try to retrieve list income records...");
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		MemberEntity member = memberService.getMemeberByCellPhone(loginInfo.getLoginUser().getUserAccount());
		SearchIncomeParam params = new SearchIncomeParam();
		params.setPageIndex(Integer.parseInt(pageIndex));
		params.setMemberId(member.getId());
		
		PageListResultDto<IncomeRecordEntity> pageDto = incomeService.getIncomeRecords(params);
		return pageDto;
	}
	
	@RequestMapping("/listWinBiddingTask")
	@ResponseBody
	public PageListResultDto<TaskEntity> listMyOrders(HttpServletRequest request, String pageIndex) {
		logger.info("try to retrieve list win bidding tasks...");
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo(request);
		MemberEntity member = memberService.getMemeberByCellPhone(loginInfo.getLoginUser().getUserAccount());
		SearchTaskParam params = new SearchTaskParam();
		params.setPageIndex(Integer.parseInt(pageIndex));
		params.setMemberId(member.getId());
		
		PageListResultDto<TaskEntity> pageDto = taskService.getWinBiddingTaskList(params);
		return pageDto;
	}


	@RequestMapping(value = "/getWinBiddingTask", method = RequestMethod.GET)
	public String getWinBiddingTask(Model model, Integer taskId) {
		SearchTaskParam params = new SearchTaskParam();
		params.setId(taskId);
		PageListResultDto<TaskEntity> tasks = taskService.getWinBiddingTaskList(params);
		if(tasks.getModel() != null && tasks.getModel().size() > 0) {
			model.addAttribute("taskInfoBean", tasks.getModel().get(0));
			model.addAttribute(REQUEST_SUCCESS, tasks.getModel().get(0));
			model.addAttribute("status", tasks.getModel().get(0).getStatus());
		}
		return "personcenter/wintaskinfo";
	}
	
	@RequestMapping(value = "/confirmDoneTask", method = RequestMethod.POST)
	public String confirmFinishTask(@ModelAttribute("taskInfoBean") TaskEntity taskEntity, BindingResult result, Model model) {
		TaskEntity toupdateTask = taskService.getTaskById(taskEntity.getId());
		toupdateTask.setStatus(DataEntityConstants.TASK_STATUS_DONE);
		taskService.updateTaskEntity(toupdateTask);
		
		// send system notify
		MemberEntity entity = memberService.getMemberById(toupdateTask.getMemberID());
		MessageEntity msg = new MessageEntity();
		msg.setTitle(ApplicationConstants.FINISH_TASK_MSG_TITLE);
		msg.setContent(ApplicationConstants.FINISH_TASK_MSG_CONTENT);
		msg.setReceiver(entity.getNickName());
		messageService.sendSystemMessage(msg);
		
		return "personcenter/orderlist";
	}
	
	@RequestMapping(value = "taskdetail", method = RequestMethod.GET)
	public String goTaskDetail(HttpServletRequest request, Model model){
		String taskId = request.getParameter("taskId");
		TaskEntity entity = taskService.getTaskById(Long.parseLong(taskId));
		model.addAttribute("taskId", taskId);
		model.addAttribute("status", entity.getStatus());
		return "personcenter/taskdetail";
	}
	
	@RequestMapping(value = "/selectBid", method = RequestMethod.POST)
	public String selectBid(UpdateTaskBidParam param){
		taskService.selectBid(param);
		String orderNumber = taskService.getTaskById(param.getId()).getOrderNumber();
		
		// assemble message
		MemberEntity entity = memberService.getMemberById(param.getMemberId());
		MessageEntity msg = new MessageEntity();
		String content = ApplicationConstants.BID_SELECTED_MSG_CONTENT;
		msg.setContent(content.replace("P", entity.getNickName()).replace("ORDER", orderNumber));
		msg.setTitle(ApplicationConstants.SYSTEM_MSG_TITLE);
		msg.setReceiver(entity.getNickName());
		
		// send system message
		messageService.sendSystemMessage(msg);
		return "personcenter/taskdetail";
	}
	
	@RequestMapping(value = "/updateBidComments", method = RequestMethod.POST)
	@ResponseBody
	public RespJson updateTaskBidComments(BidCommentsDto dto) {
		taskService.updateBidComments(dto);
		TaskEntity task = taskService.getTaskById(dto.getTaskId());
		// assemble message
		MemberEntity entity = memberService.getMemberById(task.getMemberID());
		MemberEntity owner = memberService.getMemberById(dto.getTaskOwnerId());
		MessageEntity msg = new MessageEntity();
		String content = ApplicationConstants.COMMENTS_TASK_MSG_CONTENT;
		msg.setContent(content.replace("P", entity.getNickName()).replace("ORDER", task.getOrderNumber()) + " 雇主评价为:" + dto.getComments());
		msg.setTitle(ApplicationConstants.COMMENTS_TASK_MSG_TITLE);
		msg.setReceiver(owner.getNickName());
		
		// send system message
		messageService.sendSystemMessage(msg);
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value = "/reworktask", method = RequestMethod.POST)
	@ResponseBody
	public RespJson reworkTask(BidCommentsDto dto) {
		TaskEntity task = taskService.getTaskById(dto.getTaskId());
		task.setStatus(DataEntityConstants.TASK_STATUS_IN_PROCESS);
		taskService.updateTaskEntity(task);
		
		// assemble message
		MemberEntity owner = memberService.getMemberById(dto.getTaskOwnerId());
		MessageEntity msg = new MessageEntity();
		String content = ApplicationConstants.REWORK_TASK_MSG_CONTENT;
		msg.setContent(content.replace("P", owner.getNickName()).replace("ORDER", task.getOrderNumber()));
		msg.setTitle(ApplicationConstants.SYSTEM_REWORK_MSG_TITLE);
		msg.setReceiver(owner.getNickName());
		
		// send system message
		messageService.sendSystemMessage(msg);
		return RespJsonFactory.buildSuccess();
	}

}
