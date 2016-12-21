package com.santi.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.util.CodeTypeConstants;
import com.santi.core.entity.TaskEntity;
import com.santi.core.param.SearchTaskParam;
import com.santi.core.service.TaskService;

@Controller
@RequestMapping("repairService")
public class AdminRepairServiceController extends BaseController {
	
	@Autowired
    private TaskService taskService;
	
	@RequestMapping(value="goCreate", method=RequestMethod.GET)
	public String goCreateView(Model model){
		TaskEntity entity = new TaskEntity();
		model.addAttribute("taskForm", entity);
		model.addAttribute("successInfo", "");
		return "adminrepairtask/createtask";
	}
	
	@RequestMapping(value="queryList", method=RequestMethod.GET)
	public String goQueryTaskView(Model model) {
		return "adminrepairtask/tasklist";
	}
	
	@RequestMapping(value="manageTasks", method=RequestMethod.GET)
	public String goMgrmTasks(Model model, @RequestParam String id) {
		TaskEntity taskInfo = taskService.getTaskById(Long.parseLong(id));
		model.addAttribute("taskInfoBean", taskInfo);
		model.addAttribute("data", taskInfo);
		model.addAttribute("statusTypeList", CodeTypeConstants.getTaskStatus());
		return "adminrepairtask/mgrtask";
	}
	
	@RequestMapping(value="/updateTaskStatus", method=RequestMethod.POST)
	public String updateTaskStatus(@Valid @ModelAttribute("taskInfoBean") TaskEntity taskEntity,
			BindingResult result, Model model){
		if(result.hasErrors()){
			return "adminrepairtask/mgrtask";
		}
		taskService.save(taskEntity);
		return "adminrepairtask/tasklist";
	}
	
	@RequestMapping(value = "/listTasks", method = RequestMethod.POST)
	@ResponseBody
	public List<TaskEntity> listTasks(SearchTaskParam param) {
		List<TaskEntity> list = taskService.listAllTasks(param);
		logger.info("query all matched tasks by admin, result size : " + list.size());
		return list;
	}
	
	@RequestMapping(value="/deleteTask", method = RequestMethod.DELETE)
	@ResponseBody
	public RespJson deleteDemoEntity(@RequestBody TaskEntity dto) {
		logger.info("delete task data from db...");
		return taskService.delete(dto.getId());

	}

}
