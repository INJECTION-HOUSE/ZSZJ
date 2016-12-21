<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="../commons/head.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/load-image.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.blueimp-gallery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/cors/jquery.xdr-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pccs.js"></script>
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
<body>
	<div id="" class="easyui-panel" title="维修服务->订单生成" style="width: 100%">
		<div style="padding: 10px 60px 20px 60px">
			<form:form modelAttribute="taskForm" method="post" action="${pageContext.request.contextPath}/task/create">
				<input style="display: none;" type="file" id="file" name="imgs" />
				<table>
					<tr>
						<td style="text-align: center;" colspan="9" class="success">${successInfo}</td>
					</tr>
					<tr>
						<td><label>标题</label></td>
						<td><form:input path="taskTitle" /><span style="color: red">*</span></td>
						<td><form:errors path="taskTitle" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>联系电话:</label></td>
						<td><form:input path="cellphone" /><span style="color: red">*</span></td>
						<td><form:errors path="cellphone" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>问题分类:</label></td>
						<td><form:select id="category" path="category" items="${problemList}"/><span style="color: red">*</span></td>
						<td><form:errors path="category" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>问题描述:</label></td>
						<td><form:input path="taskDesc" /><span style="color: red">*</span></td>
						<td><form:errors path="taskDesc" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>问题发生时间:</label></td>
						<td><form:input path="errorTime" /><span style="color: red">*</span></td>
						<td><form:errors path="errorTime" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>维修任务截止有效期:</label></td>
						<td><form:input path="errorTime" /><span style="color: red">*</span></td>
						<td><form:errors path="errorTime" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>位置选择:</label></td>
						<td>
							<form:select name="province" id="province" path="province"/>
							<form:select name="city" id="city" path="city"/>
							<form:select name="county" id="county" path="county" /><span style="color: red">*</span>
						</td>
						<td><form:errors path="geoPosition" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>详细地址:</label></td>
						<td><form:input path="addressDetail" /><span style="color: red">*</span></td>
						<td><form:errors path="addressDetail" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>预付金额:</label></td>
						<td><form:input path="prepayFee" /><span style="color: red">*</span></td>
						<td><form:errors path="prepayFee" cssClass="error" /></td>
					</tr>
					<tr>
						<td><label>已上传的图片:</label></td>
						<td style="text-align: center;" colspan="2">
							<div class="row">
								<div id="issue_image_001" class="col-sm-9" >
								</div>
							</div>
							<div class="row">
								<div id="issue_image_002" class="col-sm-9" >
								</div>
							</div>
							<div class="row">
								<div id="issue_image_003" class="col-sm-9" >
								</div>
							</div>
							<div class="row">
								<div id="issue_image_004" class="col-sm-9" >
								</div>
							</div>
							<div class="row">
								<div id="issue_image_005" class="col-sm-9" >
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3">
							<button type="submit" class="btn btn-primary btn-sm" style="width: 100px;">发布任务</button>
							<form:hidden id="imgfile1" path="imgfile1" />
							<form:hidden id="imgfile2" path="imgfile2" />
							<form:hidden id="imgfile3" path="imgfile3" />
							<form:hidden id="imgfile4" path="imgfile4" />
							<form:hidden id="imgfile5" path="imgfile5" />
						</td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3"><span style="color: red">*</span><span><b>星号标记为必填</b></span></td>
					</tr>				
				</table>
			</form:form>
		</div>
	</div>
	<script>
		setup();
	</script>
</body>
</html>