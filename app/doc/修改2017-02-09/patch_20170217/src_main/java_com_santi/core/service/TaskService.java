package com.santi.core.service;

import java.util.List;

import com.santi.core.common.entity.RespJson;
import com.santi.core.datamodel.dto.BidCommentsDto;
import com.santi.core.datamodel.dto.PageListResultDto;
import com.santi.core.entity.TaskAppListEntity;
import com.santi.core.entity.TaskBidEntity;
import com.santi.core.entity.TaskEntity;
import com.santi.core.entity.TaskListEntity;
import com.santi.core.entity.TaskMemberEntity;
import com.santi.core.param.SearchAppTaskParam;
import com.santi.core.param.SearchBidRecordParam;
import com.santi.core.param.SearchTaskParam;
import com.santi.core.param.UpdateTaskBidParam;

public interface TaskService {
	 
	public List<TaskListEntity> getTaskList(SearchTaskParam param);

	public void save(TaskEntity taskEntity);
	
	public RespJson delete(int id);
	
	public TaskEntity getTaskById(long id);
	
	public void updateTaskEntity(TaskEntity entity);
	
	public List<TaskMemberEntity> getTaskMember(Integer taskId);
	
	public void saveTaskBid(int taskId, String bidContent, int price, int arriveTime);
	/**
	 * 获取所有的投标记录
	 * @param params
	 * @return
	 */
	public PageListResultDto<TaskBidEntity> getBidRecordsByMemberId(SearchBidRecordParam params);
	/**
	 * 获取所有中标的task
	 * @param params
	 * @return
	 */
	public PageListResultDto<TaskEntity> getWinBiddingTaskList(SearchTaskParam params);
	
	/**
	 * back-end ADMIN console can query all tasks by any search keys
	 */
	public List<TaskEntity> listAllTasks(SearchTaskParam params);
	
	public void selectBid(UpdateTaskBidParam param);
	
	public void updateBidComments(BidCommentsDto commentsDto);

	public List<TaskAppListEntity> getTaskListForApp(SearchAppTaskParam param);
	
}
