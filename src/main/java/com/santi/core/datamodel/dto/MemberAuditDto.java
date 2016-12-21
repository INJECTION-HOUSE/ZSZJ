package com.santi.core.datamodel.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.santi.core.common.entity.DataEntity;

public class MemberAuditDto extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicant; // 申请人
	private Integer memberId; // 会员ID
	private String idcardForntImg; // 身份证照片
	private String idcardBackImg;
	private String enterpriseImg; // 企业营业执照
	private Integer certType; // 申请状态，驳回、通过
	private Integer auditStatus;
	private Integer auditorId;
	private String auditor;
	
	private String memberNickName;
	private String memberCellPhone;
	private String realname;
	
	@NotNull(message="{module.member.aduitinfo.notnull}")
	@NotBlank(message="{module.member.aduitinfo.notnull}")
	private String auditDate;
	
	@NotNull(message="{module.member.aduitinfo.notnull}")
	@NotBlank(message="{module.member.aduitinfo.notnull}")
	private String reasonText;
	
	private String idenCardNumber;

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getEnterpriseImg() {
		return enterpriseImg;
	}

	public void setEnterpriseImg(String enterpriseImg) {
		this.enterpriseImg = enterpriseImg;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public String getMemberNickName() {
		return memberNickName;
	}

	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}

	public String getMemberCellPhone() {
		return memberCellPhone;
	}

	public void setMemberCellPhone(String memberCellPhone) {
		this.memberCellPhone = memberCellPhone;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getIdenCardNumber() {
		return idenCardNumber;
	}

	public void setIdenCardNumber(String idenCardNumber) {
		this.idenCardNumber = idenCardNumber;
	}

	public String getIdcardForntImg() {
		return idcardForntImg;
	}

	public void setIdcardForntImg(String idcardForntImg) {
		this.idcardForntImg = idcardForntImg;
	}

	public String getIdcardBackImg() {
		return idcardBackImg;
	}

	public void setIdcardBackImg(String idcardBackImg) {
		this.idcardBackImg = idcardBackImg;
	}

	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

}
