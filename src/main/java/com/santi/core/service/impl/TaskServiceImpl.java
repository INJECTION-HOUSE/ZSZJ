package com.santi.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santi.core.common.entity.DataEntityConstants;
import com.santi.core.common.entity.RespJson;
import com.santi.core.common.entity.RespJsonFactory;
import com.santi.core.common.util.SequenceNumberUtil;
import com.santi.core.dao.MemberDao;
import com.santi.core.dao.TaskDao;
import com.santi.core.datamodel.dto.BidCommentsDto;
import com.santi.core.datamodel.dto.PageListResultDto;
import com.santi.core.entity.MemberEntity;
import com.santi.core.entity.TaskAppListEntity;
import com.santi.core.entity.TaskBidEntity;
import com.santi.core.entity.TaskEntity;
import com.santi.core.entity.TaskListEntity;
import com.santi.core.entity.TaskMemberEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;
import com.santi.core.param.SearchAppTaskParam;
import com.santi.core.param.SearchBidRecordParam;
import com.santi.core.param.SearchTaskParam;
import com.santi.core.param.UpdateTaskBidParam;
import com.santi.core.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	
	private static final Logger logger = Logger.getLogger(TaskServiceImpl.class);
	@Autowired
    private TaskDao taskDao;
	
	@Autowired
    private MemberDao memberDao;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskListEntity> getTaskList(SearchTaskParam param) {
		return taskDao.getTaskList(param);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(TaskEntity taskEntity) {
		long now = new Date().getTime();
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		logger.info("publish new repair task by : " + loginInfo.getLoginUser().getUsername());
		taskEntity.setOrderNumber(SequenceNumberUtil.generateRepairNumber(taskEntity.getCellphone().substring(taskEntity.getCellphone().length() - 4)));
		taskEntity.setStatus(0);
		taskEntity.setGeoPosition(taskEntity.getProvince() + taskEntity.getCity() + taskEntity.getCounty());
		taskEntity.setCashFund(taskEntity.getPrepayFee());
		// taskEntity.setTaskOwner(0);这个以后需要更新的字段
		
		taskEntity.setType(taskEntity.getType() > 0 ? taskEntity.getType() : 1);
		taskEntity.setMemberID(loginInfo.getLoginUser().getId());
		taskEntity.setVoiceFiles("");
		taskEntity.setCreateTime(String.valueOf(now));
		taskEntity.setUpdateTime(String.valueOf(now));
		taskEntity.setCreateBy(loginInfo.getLoginUser().getUsername());
		taskEntity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		
		taskDao.addTask(taskEntity);
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", taskEntity.getMemberID());
		map.put("cash", taskEntity.getPrepayFee() * -1);
		memberDao.addTotalCash(map);
	}
	
	@Transactional(readOnly = true)
	public TaskEntity getTaskById(long id) {
		return taskDao.getTaskById(id);
	}


	@Override
	@Transactional(readOnly = false)
	public RespJson delete(int id) {
		List<TaskBidEntity> taskbids = taskDao.getTaskBidsByTaskId(id);
		TaskEntity te = taskDao.getTaskById(id);
		if(te.getStatus() == DataEntityConstants.TASK_STATUS_BEGIN || te.getStatus() == DataEntityConstants.TASK_STATUS_SELECT_BID) {
			if(taskbids != null && taskbids.size() > 0) {
				for(TaskBidEntity bidRecord : taskbids) {
					taskDao.deleteTaskBidEntityById(bidRecord.getId());
				}
			}
			taskDao.deleteTaskById(te.getId());
			return RespJsonFactory.buildSuccess();
		} else {
			return RespJsonFactory.buildFailure();
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateTaskEntity(TaskEntity entity) {
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		long now = new Date().getTime();
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateTime(String.valueOf(now));
		taskDao.updateTaskEntity(entity);
	}

	@Override
	public List<TaskMemberEntity> getTaskMember(Integer taskId) {
		return taskDao.getTaskMember(taskId);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveTaskBid(int taskId, String bidContent, int price, int arriveTime) {
		TaskBidEntity entity = new TaskBidEntity();
		LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
		long now = new Date().getTime();
		entity.setBidNumber(SequenceNumberUtil.generateBidNumber(loginInfo.getLoginUser().getUserAccount().substring(loginInfo.getLoginUser().getUserAccount().length() - 4)));
		entity.setTaskId(taskId);
		entity.setMemberId(loginInfo.getLoginUser().getId());
		entity.setBidContent(bidContent);
		entity.setIsView(0);
		entity.setPrice(price);
		entity.setArriveTime(arriveTime);
		entity.setCreateTime(String.valueOf(now));
		entity.setCreateBy(loginInfo.getLoginUser().getUsername());
		entity.setUpdateTime(String.valueOf(now));
		entity.setUpdateBy(loginInfo.getLoginUser().getUsername());
		taskDao.addTaskBid(entity);
		
	}

	@Override
	@Transactional(readOnly = true)
	public PageListResultDto<TaskBidEntity> getBidRecordsByMemberId(SearchBidRecordParam params) {
		int total = taskDao.countBidRecordsByMemberId(params.getMemberId());
		params.setRowoffset(params.getPageIndex()*10);
		int tt = total - (params.getPageIndex()*10);
		if(tt > 10) {
			params.setRows(10);
		}
		else {
			params.setRows(tt);
		}
		List<TaskBidEntity> bidRecords = taskDao.getBidRecordsByMemberId(params);
		PageListResultDto<TaskBidEntity> pageResult = new PageListResultDto<TaskBidEntity>();
		pageResult.setModel(bidRecords);
		pageResult.setPageNum(params.getPageIndex()+1); // index base 0
		pageResult.setPageSize(params.getRows());
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	@Transactional(readOnly = true)
	public PageListResultDto<TaskEntity> getWinBiddingTaskList(SearchTaskParam params) {
		int total = taskDao.countWinBidingTasksByParams(params);
		params.setRowoffset(params.getPageIndex()*10);
		int tt = total - (params.getPageIndex()*10);
		if(tt > 10) {
			params.setRows(10);
		}
		else {
			params.setRows(tt);
		}
		
		List<TaskEntity> bidRecords = taskDao.getWinBidingTasksByParams(params);
		PageListResultDto<TaskEntity> pageResult = new PageListResultDto<TaskEntity>();
		pageResult.setModel(bidRecords);
		pageResult.setPageNum(params.getPageIndex()+1); // index base 0
		pageResult.setPageSize(params.getRows());
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskEntity> listAllTasks(SearchTaskParam params) {
		return taskDao.listAllTasksByAdmin(params);
	}

	@Override
	@Transactional(readOnly = false)
	public void selectBid(UpdateTaskBidParam param) {
		long now = new Date().getTime();
		param.setDeadline(String.valueOf(now));
		param.setUpdatedTime(String.valueOf(now));
		taskDao.selectBid(param);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBidComments(BidCommentsDto dto) {
		long now = new Date().getTime();
		dto.setUpdatedTime(String.valueOf(now));
		taskDao.updateBidComments(dto);
		TaskEntity task = taskDao.getTaskById(dto.getTaskId());
		Integer totalFee = task.getTotalFee();
		Integer memberID = task.getTaskOwner();
		MemberEntity repairor = memberDao.findMemberById(memberID);
		MemberEntity hirer = memberDao.findMemberById(task.getMemberID());
		
		// payment now
		Integer repairor_total = repairor.getTotalCash() + totalFee;
		Integer hirer_total = hirer.getTotalCash() - totalFee;
		repairor.setTotalCash(repairor_total);
		repairor.setUpdateTime(String.valueOf(now));
		hirer.setTotalCash(hirer_total);
		hirer.setUpdateTime(String.valueOf(now));
		memberDao.editMember(repairor);
		memberDao.editMember(hirer);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskAppListEntity> getTaskListForApp(SearchAppTaskParam param) {
		return taskDao.getTaskListForApp(param);
	}


}
