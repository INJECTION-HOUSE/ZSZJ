package com.santi.core.dao;

import java.util.List;

import com.santi.core.datamodel.dto.BidCommentsDto;
import com.santi.core.entity.TaskAppListEntity;
import com.santi.core.entity.TaskBidEntity;
import com.santi.core.entity.TaskEntity;
import com.santi.core.entity.TaskListEntity;
import com.santi.core.entity.TaskMemberEntity;
import com.santi.core.param.SearchAppTaskParam;
import com.santi.core.param.SearchBidRecordParam;
import com.santi.core.param.SearchTaskParam;
import com.santi.core.param.UpdateTaskBidParam;

public interface TaskDao {
	 
	 void addTask(TaskEntity task);
	 
	 List<TaskListEntity> getTaskList(SearchTaskParam param);
	 
	 public List<TaskEntity> listAllTasksByAdmin(SearchTaskParam param);
	
	 public TaskEntity getTaskById(long id);
	 
	 public void deleteTaskById(long id);
	 
	 public void deleteTaskBidEntityById(long id);

	 List<TaskMemberEntity> getTaskMember(Integer taskId);

	 void addTaskBid(TaskBidEntity entity);
	 
	 List<TaskBidEntity> getBidRecordsByMemberId(SearchBidRecordParam pagesetting);
	 
	 public int countBidRecordsByMemberId(long memberId);
	 
	 public int countWinBidingTasksByParams(SearchTaskParam params);
	 
	 public List<TaskEntity> getWinBidingTasksByParams(SearchTaskParam params);
	 
	 public void updateTaskEntity(TaskEntity entity);
	 
	 public List<TaskBidEntity> getTaskBidsByTaskId(Integer taskId);

	void selectBid(UpdateTaskBidParam param);
	
	public TaskBidEntity findBidEntityById(int id);
	
	public void updateBidComments(BidCommentsDto dto);

	List<TaskAppListEntity> getTaskListForApp(SearchAppTaskParam param);

}
