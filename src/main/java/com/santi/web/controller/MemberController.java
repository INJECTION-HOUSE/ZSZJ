package com.santi.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.messages.Global;
import com.santi.core.common.util.CodeTypeConstants;
import com.santi.core.datamodel.dto.MemberAuditDto;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.TaskEntity;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.SearchMemberCheckParam;
import com.santi.core.param.SearchMemberParam;
import com.santi.core.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController extends BaseController{
	private static final Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
    private MemberService memberService;
	
	@RequestMapping(value = "/getMemberList", method = RequestMethod.POST)
	@ResponseBody
	public List<MemberEntity>  getMemberList(SearchMemberParam param) {
		return memberService.getMemberList(param);
	}
	
	@RequestMapping(value = "/getMemberCheckList", method = RequestMethod.POST)
	@ResponseBody
	public List<MemberAuditDto>  getMemberCheckList(SearchMemberCheckParam param) {
		return memberService.getMemberAuditList(param);
	}

	@RequestMapping(value="goMemberCheckDetail", method=RequestMethod.GET)
	public String goMemberCheckDetail(HttpServletRequest request, Model model,Integer id){
		SearchMemberCheckParam param = new SearchMemberCheckParam();
		param.setId(id);
		List<MemberAuditDto> list = memberService.getMemberAuditList(param);
		if(list.size()>0){
			list.get(0).setAuditor(LoginInfoUtil.getUserName(request));
			model.addAttribute("memberAuditInfoForm", list.get(0));
		}
		model.addAttribute("successInfo", "");
		return "member/checkDetail";
	}

	@RequestMapping(value = "/editAuditDetail", method = RequestMethod.POST)
	public String editCheckDetail(@Valid @ModelAttribute("memberAuditInfoForm") MemberAuditDto auditInfoDto, BindingResult result, Model model) {
		logger.info("update memeber audition detail information from admin console...");
		if(result.hasErrors()){
			return "member/checkDetail";
		}
		memberService.processAuditionInfo(auditInfoDto, true);
		model.addAttribute("memberAuditInfoForm", auditInfoDto);
		model.addAttribute("successInfo",Global.getMessage("common.applymember.success"));
		return "member/checkDetail";
	}
	
	@RequestMapping(value="goList", method=RequestMethod.GET)
	public String goMemberList(Model model){
		return "member/memberList";
	}
	
	@RequestMapping(value="checkList", method=RequestMethod.GET)
	public String checkList(Model model){
		TaskEntity entity = new TaskEntity();
		model.addAttribute("taskForm", entity);
		model.addAttribute("successInfo", "");
		return "member/checkList";
	}
	
	@RequestMapping(value = "/editMember", method = RequestMethod.POST)
	public String editMember(@Valid MemberEntity member, BindingResult result, Model model) {
		logger.info("update memeber detail infor from admin console...");
		memberService.editMember(member);
		model.addAttribute("memberForm", member);
		model.addAttribute("successInfo",Global.getMessage("common.update.success"));
		model.addAttribute("genderItems", CodeTypeConstants.getGenderMap());
		return "member/memberDetail";
	}
	
	@RequestMapping(value="goMemberDetail", method=RequestMethod.GET)
	public String goMemberDetail(Model model,Integer id){
		SearchMemberParam param = new SearchMemberParam();
		param.setId(id);;
		  List<MemberEntity> list = memberService.getMemberList(param);
		if(list.size()>0){
			model.addAttribute("memberForm", list.get(0));
		}
		model.addAttribute("successInfo", "");
		model.addAttribute("genderItems", CodeTypeConstants.getGenderMap());
		return "member/memberDetail";
	}
	
	@RequestMapping(value="/deleteMember", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteMember(@RequestBody MemberEntity dto) {
		if(dto != null)
		{
			memberService.deleteMember(dto);
			logger.info("delete Code type data from db with id : " + dto.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "删除成功...");
		return map;
	}
	
	@RequestMapping(value = "/getBalance", method = RequestMethod.GET)
	@ResponseBody
	public int  getBalance(int id) {
		return memberService.getBalance(id);
	}
	
	@RequestMapping(value = "/addTotalCash", method = RequestMethod.POST)
	@ResponseBody
	public void  addTotalCash(int id, int cash) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		map.put("cash", cash);
		memberService.addTotalCash(map);
	}
}
