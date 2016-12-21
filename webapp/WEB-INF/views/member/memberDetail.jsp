<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="../commons/head.jsp" />
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
<script type="text/javascript">
	$(function() {
		var imageIDs = document.getElementsByName("avatarName");
		$("#avatarImage").html("<img style='width:200px; height:200px' src='${pageContext.request.contextPath}/upload/" + imageIDs[0].value +"'>");
	});
</script>
<body>
	<div class="container-fluid">
	<div class="row" style="padding:15px">
		<div class="panel panel-default">
			<div class="panel-heading">
		     	<h3 class="panel-title"><a href="${pageContext.request.contextPath}/member/goList">会员查询</a>->会员详情</h3>
		    </div>
		    <div class="panel-body">
				<form:form modelAttribute="memberForm" method="post" action="${pageContext.request.contextPath}/member/editMember">
				<table>
					<tr>
						<td style="text-align: center;" colspan="4" class="success">${successInfo}</td>
					</tr>
					<tr>
						<td colspan="4">
							<div style="padding:10px;" id="avatarImage"></div>
						</td>
					</tr>
					<tr>
						<td>用户昵称:</td>
						<td><form:input path="nickName"  readonly="true"/><form:hidden path="id"/></td>						
						<td>手机号码:</td>
						<td><form:input path="cellphone" readonly="true" /></td>
					</tr>
					<tr>
						<td>总收入:</td>
						<td><form:input path="totalCash" readonly="true" /></td>
						<td>可提现金额:</td>
						<td><form:input path="avaCash" readonly="true" /></td>
					</tr>
					<tr>
						<td>身份证号码:</td>
						<td><form:input path="identifierId" readonly="true" /></td>
						<td>性别:</td>
						<td><form:select path="gender" items="${genderItems}" /></td>
					</tr>
					<tr>
						<td>信誉等级:</td>
						<td><form:input path="level" /><span style="color: red">*</span></td>
						<td>用户积分:</td>
						<td><form:input path="score" /><span style="color: red">*</span></td>
					</tr>
					<tr>
						<td>认证用户:</td>
						<td><form:checkbox path="verified" cssStyle="width:30px; height:30px;"/></td>
						<td>是否锁定:</td>
						<td><form:checkbox path="lock" cssStyle="width:30px; height:30px;"/></td>
					</tr>
					<tr>
						<form:hidden path="avatarName"/>
						<td colspan="4"><button type="submit" style="width:100px;" class="btn btn-primary">保存</button></td>
					</tr>
				</table>
				</form:form>
			</div>
		</div>
	</div>
	</div>
</body>
</html>