package com.santi.core.entity;

import com.santi.core.common.entity.DataEntity;

public class MemberAuditEntity extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer memberId;
	private String idcardforntImage;
	private String idcardbackImage;
	private String enterpriseImage;
	private Integer certType;
	private Integer status;
	private Integer auditor;
	private String auditReason;
	private String auditDate;
	private String memberRealName;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getIdcardforntImage() {
		return idcardforntImage;
	}

	public void setIdcardforntImage(String idcardforntImage) {
		this.idcardforntImage = idcardforntImage;
	}

	public String getIdcardbackImage() {
		return idcardbackImage;
	}

	public void setIdcardbackImage(String idcardbackImage) {
		this.idcardbackImage = idcardbackImage;
	}

	public String getEnterpriseImage() {
		return enterpriseImage;
	}

	public void setEnterpriseImage(String enterpriseImage) {
		this.enterpriseImage = enterpriseImage;
	}

	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAuditor() {
		return auditor;
	}

	public void setAuditor(Integer auditor) {
		this.auditor = auditor;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getMemberRealName() {
		return memberRealName;
	}

	public void setMemberRealName(String memberRealName) {
		this.memberRealName = memberRealName;
	}

}
