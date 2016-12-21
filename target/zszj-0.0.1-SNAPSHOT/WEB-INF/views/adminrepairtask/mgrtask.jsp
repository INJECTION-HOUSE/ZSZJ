<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<!-- 加载jquery-easyui -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
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
	</style>
<script>
	$(document).ready(function() {
		$(function() {
			// format date time
			var errorTimes = document.getElementsByName("errorTime");
			var deadlineTime = document.getElementsByName("deadline");
			$("#errorTimeText").html(dateFormatter(errorTimes[0].value));
			$("#deadlineDateTxt").html(dateFormatter(deadlineTime[0].value));
			
			// display images
			var imageFilesName = document.getElementsByName("imageFiles");
			var imageFiles = imageFilesName[0].value;
			if(imageFiles) {
				$("#imagesDiv").html("");
				var imagesIDs = imageFiles.split(";");
				for(var i=0; i<imagesIDs.length; i++) {
					var resultImg = '<img style="padding: 5px; width:600px;height:400px;"  src="${pageContext.request.contextPath}/upload/'+ imagesIDs[i] + '"/>';
					$("#imagesDiv").append(resultImg);
				}
			}
		});
	});
	
	function dateFormatter(value) {
		console.log("value = " + value);
		var formattedDate = new Date(parseInt(value));
		var d = formattedDate.getDate();
		var m =  formattedDate.getMonth();
		m += 1;  // JavaScript months are 0-11
		var y = formattedDate.getFullYear();
		var hhmmss = formattedDate.getHours()+":"+formattedDate.getMinutes()+":"+formattedDate.getSeconds();
		return y + "-" + m + "-" + d + " " + hhmmss;
	}
	
</script>
</head>
<body>
    <div class="container-fluid">
		<div class="row" style="padding:15px">
			<div class="panel panel-default">
				<div class="panel-heading">
			     	<h3 class="panel-title"><a href="${pageContext.request.contextPath}/repairService/queryList"><b>订单查询</b></a>-${data.taskTitle}</h3>
			    </div>
			    <div class="panel-body">
			    <form:form modelAttribute="taskInfoBean" method="post" action="${pageContext.request.contextPath}/repairService/updateTaskStatus">
			    	<table>
			    		<tr>
			    			<td>订单编号:</td>
			    			<td><form:input path="orderNumber" readonly="true" /></td>
			    			<td>发布商:</td>
			    			<td><form:input path="enterpriser" readonly="true" /></td>
			    		</tr>
			    		<tr>
			    			<td>昵称:</td>
			    			<td><form:input path="nickname" readonly="true" /></td>
			    			<td>联系电话:</td>
			    			<td><form:input path="cellphone" readonly="true" /></td>

			    		</tr>
			    		<tr>
			    			<td>上门费:</td>
			    			<td><form:input path="prepayFee" readonly="true" /></td>
							<td>上门地址:</td>
			    			<td><span>${data.geoPosition}</span><span>${data.addressDetail}</span></td>
			    		</tr>
			    		<tr>
			    			<td>问题类型</td>
			    			<td><form:input path="category" readonly="true" /></td>
							<td>发生时间:</td>
			    			<td><span id="errorTimeText"></span></td>
			    		</tr>
			    		<tr>
			    			<td>任务状态</td>
			    			<td><form:select path="status" items="${statusTypeList}" /></td>
							<td>投标截止日期:</td>
			    			<td><span id="deadlineDateTxt"></span></td>
			    		</tr>
			    		<tr>
			    			<td>问题描述:</td>
			    			<td colspan="3"><form:textarea row="10" cols="70" path="taskDesc" readonly="true" /></td>
			    		</tr>
			    		<tr>
			    			<td colspan="4"><h4>现场图片:</h4></td>
			    		</tr>
			    		<tr>
			    			<td colspan="4"><div id="imagesDiv"></div></td>
			    		</tr>
			    		<form:hidden path="errorTime"/>
			    		<form:hidden path="status"/>
			    		<form:hidden path="imageFiles"/>
			    		<form:hidden path="deadline"/>
			    		<form:hidden path="id"/>
			    		<form:hidden path="memberID"/>
				    	<tr><td colspan="4">
				    			<button type="submit" id="confirmDoneBtn" style="width:100px;" class="btn btn-primary">更新状态</button>
				    	</td></tr>
			    	</table>
			    	</form:form>
			    </div>
		  	</div>
		</div>
	</div>
</body>
</html>