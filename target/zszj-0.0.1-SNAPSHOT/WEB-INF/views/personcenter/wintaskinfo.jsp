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
	    td input{
	       border:1px solid #e5e5e5;
	       height:38px;
	       padding-left:8px;
	    }
	    td textarea{
	       border:1px solid #e5e5e5;
	       padding-left:8px;
	       padding-top:8px;
	       
	    }
		td {
		    font-family:'微软雅黑';
			padding-top: 5px;
			padding-bottom: 5px;
			padding-right: 5px;
		}
		
		td:first-child {
			padding-left: 5px;
			padding-right: 0;
		}
		.btn-primary-green{
		    background:#84C325;
		    color:#fff;
		}
		.btn-primary-green:hover{
		    background:#78AF19;
		}
		.btn1{
		    display: inline-block;
			padding: 10px 12px;
			margin-bottom: 0;
			font-size: 13px;
			font-weight: 400;
			line-height: 1.42857143;
			text-align: center;
			white-space: nowrap;
			vertical-align: middle;
			-ms-touch-action: manipulation;
			touch-action: manipulation;
			cursor: pointer;
			-webkit-user-select: none;
			-moz-user-select: none;
			-ms-user-select: none;
			user-select: none;
			background-image: none;
			border: 1px solid transparent;
			border-radius: 2px;
		}
	</style>
<script>
	$(document).ready(function() {
		$(function() {
			// format date time
			var errorTimes = document.getElementsByName("errorTime");
			$("#errorTimeText").html(dateFormatter(errorTimes[0].value));
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
			     	<h3 class="panel-title" style="font-family:'微软雅黑'">${data.taskTitle}</h3>
			    </div>
			    <div class="panel-body">
			    <form:form modelAttribute="taskInfoBean" method="post" action="${pageContext.request.contextPath}/personCenter/confirmDoneTask">
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
			    		<form:hidden path="id"/>
			    		<form:hidden path="memberID"/>
				    	<tr><td colspan="4">
				    		<% if(((Integer)request.getAttribute("status")) == 2) {%>
				    			<button type="submit" id="confirmDoneBtn" style="width:100px;" class="btn1 btn-primary-green">完工验收</button>
				    		<% } %>
				    	</td></tr>
			    	</table>
			    	</form:form>
			    </div>
		  	</div>
		</div>
	</div>
</body>
</html>