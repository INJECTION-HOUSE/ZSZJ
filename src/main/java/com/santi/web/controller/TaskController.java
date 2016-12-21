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

import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.datamodel.dto.BidCommentsDto;
import com.santi.core.datamodel.dto.TaskBidDto;
import com.santi.core.entity.TaskEntity;
import com.santi.core.entity.TaskListEntity;
import com.santi.core.entity.TaskMemberEntity;
import com.santi.core.param.SearchTaskParam;
import com.santi.core.service.TaskService;

@Controller
@RequestMapping("task")
public class TaskController extends BaseController{

	@Autowired
    private TaskService taskService;
	
	@RequestMapping(value = "/getTaskList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object>   getTaskList(SearchTaskParam param) {
		Map<String,Object> result=new HashMap<String,Object>();
		List<TaskListEntity> list = taskService.getTaskList(param);
 		result.put(REQUEST_SUCCESS, list);
		return result;
	}
	
	@RequestMapping(value="/addTask", method=RequestMethod.POST)
	public String createTask(@Valid @ModelAttribute("taskForm") TaskEntity taskEntity,
			BindingResult result, Model model){
		if(result.hasErrors()){
			return "task/createtask";
		}
		taskService.save(taskEntity);
		return "redirect:goCreate";
	}
	
	@RequestMapping(value="goTaskList", method=RequestMethod.GET)
	public String goTasksView(Model model) {
		return "task/tasks";
	}
	
	@RequestMapping(value="goBidTask", method=RequestMethod.GET)
	public String goBidTaskView(HttpServletRequest request, Model model) {
		String taskId = request.getParameter("taskId");
		model.addAttribute("taskId", taskId);
		return "task/taskdetail";
	}
	
	@RequestMapping(value = "/getBidTask", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBidTask(Integer taskId) {
		Map<String,Object> result=new HashMap<String,Object>();
		TaskEntity task = taskService.getTaskById(taskId);
		List<TaskMemberEntity> list = taskService.getTaskMember(taskId);
		TaskBidDto dao = new TaskBidDto(task, list);
 		result.put(REQUEST_SUCCESS, dao);
		return result;
	}
	
	@RequestMapping(value = "/getTaskMember", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTaskMember(Integer taskId) {
		Map<String,Object> result=new HashMap<String,Object>();
		List<TaskMemberEntity> list = taskService.getTaskMember(taskId);
 		result.put(REQUEST_SUCCESS, list);
		return result;
	}
	
	@RequestMapping(value = "/saveTaskBid", method = RequestMethod.POST)
	public String saveTaskBid(int taskId, String bidContent, int price, int arriveTime){
		taskService.saveTaskBid(taskId, bidContent, price, arriveTime);
		//TODO: notify task owner, best be a notice from system, not implemented by class MessageService yet.
		
		return "task/taskdetail";
	}

}
