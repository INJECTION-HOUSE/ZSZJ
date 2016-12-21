package com.santi.core.datamodel.dto;

import java.util.List;

import com.santi.core.common.entity.BaseEntity;
import com.santi.core.entity.TaskEntity;
import com.santi.core.entity.TaskMemberEntity;
import com.santi.core.global.common.LoginInfo;
import com.santi.core.global.common.LoginInfoUtil;

public class TaskBidDto extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int memberId;
	private String category;
	private String taskTitle;
	private String taskDesc;
	private String geoPosition;
	private String deadline;
	private String cellphone;
	private String addressDetail;
	private Integer prepayFee;
	private Integer status;
	private Integer totalFee;
	private String imageFiles;
	private String voiceFiles;
	private Integer minFee;
	private Integer taskOwner;
	private Integer maxFee;
	private List<TaskMemberEntity> members;
	private boolean hasBid;
	private String createBy;
	private String createTime;
	private String enterpriser;
	private String nickname;

	public TaskBidDto(TaskEntity task, List<TaskMemberEntity> members) {
		this.setId(task.getId());
		this.setMemberId(task.getMemberID());
		this.setCategory(task.getCategory());
		this.setTaskTitle(task.getTaskTitle());
		this.setTaskDesc(task.getTaskDesc());
		this.setGeoPosition(task.getGeoPosition());
		this.setDeadline(task.getDeadline());
		this.setCellphone(task.getCellphone());
		this.setAddressDetail(task.getAddressDetail());
		this.setPrepayFee(task.getPrepayFee());
		this.setStatus(task.getStatus());
		this.setTaskOwner(task.getTaskOwner());
		this.setTotalFee(task.getTotalFee());
		this.setImageFiles(task.getImageFiles());
		this.setVoiceFiles(task.getVoiceFiles());
		this.setMembers(members);
		this.setEnterpriser(task.getEnterpriser());
		this.setNickname(task.getNickname());

		if (members.size() > 0) {
			int min = Integer.MAX_VALUE;
			int max = 0;
			LoginInfo loginInfo = LoginInfoUtil.getLoginInfo();
			int loginid = loginInfo.getLoginUser().getId();
			for (TaskMemberEntity member : members) {
				if (member.getPrice() > max) {
					max = member.getPrice();
				}
				if (member.getPrice() < min) {
					min = member.getPrice();
				}
				if (member.getId().equals(loginid)) {
					this.setHasBid(true);
				}
			}
			this.setMinFee(min);
			this.setMaxFee(max);
		} else {
			this.setMinFee(0);
			this.setMaxFee(0);
		}
		this.setCreateBy(task.getCreateBy());
		this.setCreateTime(task.getCreateTime());
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Integer getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(Integer taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getGeoPosition() {
		return geoPosition;
	}

	public void setGeoPosition(String geoPosition) {
		this.geoPosition = geoPosition;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(Integer prepayFee) {
		this.prepayFee = prepayFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(String imageFiles) {
		this.imageFiles = imageFiles;
	}

	public String getVoiceFiles() {
		return voiceFiles;
	}

	public void setVoiceFiles(String voiceFiles) {
		this.voiceFiles = voiceFiles;
	}

	public List<TaskMemberEntity> getMembers() {
		return members;
	}

	public void setMembers(List<TaskMemberEntity> members) {
		this.members = members;
	}

	public Integer getMinFee() {
		return minFee;
	}

	public void setMinFee(Integer minFee) {
		this.minFee = minFee;
	}

	public Integer getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(Integer maxFee) {
		this.maxFee = maxFee;
	}

	public boolean isHasBid() {
		return hasBid;
	}

	public void setHasBid(boolean hasBid) {
		this.hasBid = hasBid;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEnterpriser() {
		return enterpriser;
	}

	public void setEnterpriser(String enterpriser) {
		this.enterpriser = enterpriser;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
