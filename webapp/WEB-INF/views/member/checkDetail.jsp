<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>会员列表</title>
<!-- 加载jquery-easyui -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<style>
td {
	padding-top: 5px;
	padding-bottom: 5px;
	padding-right: 5px;
}

td:first-child {
	padding-left: 5px;
	padding-right: 0;
}
.error {
	color: red;
	font-style: normal;
	font-weight: bold;
}

.success {
	color: green;
	font-style: normal;
	font-weight: bold;
}
</style>
</head>
<script>
	$(document).ready(function() {
		$(function() {
			var idcardForntImage = document.getElementsByName("idcardForntImg");
			var idcardbackImage = document.getElementsByName("idcardBackImg");
			var idcardenterpriseImage = document.getElementsByName("enterpriseImg");
			var applyTypeId = document.getElementsByName("certType")[0].value;
			$("#idcardfgImg").attr("src","${pageContext.request.contextPath}/upload/"+idcardForntImage[0].value);
			$("#idcardbgImg").attr("src","${pageContext.request.contextPath}/upload/"+idcardbackImage[0].value);
			if(applyTypeId == 1 && idcardenterpriseImage[0].value != "") {
				var resultImg = '<img style="width:200px;height:133px;"  src="${pageContext.request.contextPath}/upload/'+ idcardenterpriseImage[0].value + '"/>';
				$("#businesslicenceImg").html(resultImg);
			}
			else {
				$("#businesslicenceImg").html("");
			}
			
			if(applyTypeId == 0) {
				$("#applyTypeTextDiv").html("个人会员");
			}
			else if(applyTypeId == 1) {
				$("#applyTypeTextDiv").html("企业会员");
			}
		});
	});
	
	function rejectApplicant() {
		console.log("reject the member certification for reason");
		alert("reject it!");
	}
</script>
<body>
	<div class="container-fluid">
		<div class="row" style="padding: 15px">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><a href="${pageContext.request.contextPath}/member/checkList">会员认证</a>->信息审核</h3>
				</div>
				<div class="panel-body">
					<form:form modelAttribute="memberAuditInfoForm" method="post" action="${pageContext.request.contextPath}/member/editAuditDetail">
						<table>
							<tr>
								<td style="text-align: center;" colspan="4" class="success">${successInfo}</td>
							</tr>
							<tr>
								<td>审核人:</td>
								<td><form:input path="auditor" readonly="true" /><span style="color: red">*</span></td>
								<td>审核日期:</td>
								<td><form:input path="auditDate" /><span style="color: red">*(YYYY-MM-DD)</span><form:errors path="auditDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>审核意见:</td>
								<td colspan="3"><form:textarea path="reasonText" rows="4" cols="80" /><span style="color: red">*</span><form:errors path="reasonText" cssClass="error" /></td>
							</tr>
							<tr>
								<td>申请人:</td>
								<td><form:input path="realname" readonly="true" /><span style="color: red">*</span><form:hidden path="id" /></td>
								<td>昵称:</td>
								<td><form:input path="memberNickName" readonly="true" /><span style="color: red">*</span></td>
							</tr>
							<tr>
								<td>身份证号码:</td>
								<td colspan="3"><form:input path="idenCardNumber" readonly="true" /><span style="color: red">*</span></td>
							</tr>
							<tr>
								<td>认证类型:</td>
								<td colspan="3"><form:hidden path="certType"/><div id="applyTypeTextDiv"></div></td>
						    </tr>
							<tr>
								<td>身份证正面:</td>
								<td><div><img id="idcardfgImg" style="width:200px;height:133px;" src="${pageContext.request.contextPath}/static/images/frontIdCard.png"></div></td>
								<form:hidden id="idcardForntImg" path="idcardForntImg"/>
								<td>身份证反面:</td>
								<td><div><img id="idcardbgImg" style="width:200px;height:133px;" src="${pageContext.request.contextPath}/static/images/backIdCard.png"></div></td>
								<form:hidden id="idcardBackImg" path="idcardBackImg"/>
							</tr>
							<tr>
								<td>营业执照正面:</td>
								<td colspan="3"><div id="businesslicenceImg"></div></td>
								<form:hidden id="enterpriseImg" path="enterpriseImg"/>
							</tr>
							<tr>
								<form:hidden path="id"/>
								<form:hidden path="memberId"/>
								<form:hidden path="memberCellPhone"/>
								<td colspan="4"><button type="submit" style="width:100px;" class="btn btn-primary">通过</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" style="width:100px;" class="btn btn-danger" onclick="rejectApplicant()">失败</button></td>
							</tr>
						</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>