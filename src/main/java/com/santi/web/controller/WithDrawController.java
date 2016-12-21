package com.santi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.entity.TaskEntity;
import com.santi.core.entity.WithDrawEntity;
import com.santi.core.param.SearchWithDrawParam;
import com.santi.core.service.WithDrawService;

@Controller
@RequestMapping("withDraw")
public class WithDrawController extends BaseController{

	@Autowired
	private WithDrawService withDrawService;
	
	@RequestMapping(value = "/getWithDrawList", method = RequestMethod.POST)
	@ResponseBody
	public List<WithDrawEntity>  getWithDrawList(SearchWithDrawParam param) {
		return withDrawService.getWithDrawList(param);
	}
	
	
	@RequestMapping(value = "/editWithDrawStatus", method = RequestMethod.POST)
	@ResponseBody
	public RespJson editWithDrawStatus(WithDrawEntity param) {		
		withDrawService.checkWithDraw(param);
		return RespJsonFactory.buildSuccess();
	}
	
	@RequestMapping(value = "/paymentIncome", method = RequestMethod.POST)
	@ResponseBody
	public RespJson paymentIncome(WithDrawEntity entity) {
		entity.setStatus(2); // 已经打款
		withDrawService.payOffLine(entity);
		return RespJsonFactory.buildSuccess();
	}
	
	/***
	 * 编辑选中的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("editWithDraw")
	@ResponseBody
	public WithDrawEntity editWithDraw(Integer id){
		logger.info("try to load the withdraw data entity by id : " + id);
		return withDrawService.findEntityById(id.longValue());
	}
 
	@RequestMapping(value="goApplicants", method=RequestMethod.GET)
	public String goA(Model model){
		TaskEntity entity = new TaskEntity();
		model.addAttribute("taskForm", entity);
		model.addAttribute("successInfo", "");
		return "withdraw/applicantlist";
	}
	
	@RequestMapping(value="goAuditPay", method=RequestMethod.GET)
	public String goB(Model model){
		TaskEntity entity = new TaskEntity();
		model.addAttribute("taskForm", entity);
		model.addAttribute("successInfo", "");
		return "withdraw/prepaymentlist";
	}
	
	
	@RequestMapping(value="goPayment", method=RequestMethod.GET)
	public String goC(Model model){
		TaskEntity entity = new TaskEntity();
		model.addAttribute("taskForm", entity);
		model.addAttribute("successInfo", "");
		return "withdraw/withdrawList";
	}
	
	
}
