<%@ page pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pccs.js"></script>
</head>
<body>
	<div class="edit">
		<div class="edit_title">
			<h3>个人资料</h3>
		</div>
		<div class="edit_content">
		<form:form modelAttribute="detailInfoForm" method="post" action="${pageContext.request.contextPath}/profile/updateDetail">
		    <div class="edit_wrap">
			    <div class="edit_input">
			    	<h4 style="width:100%; color:green; text-align:center">${successInfo}</h4>
					<h3>个人资料</h3>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1">姓名：</span>
					<span class="edit_input_span2"><form:input path="nickname" readonly="true"/></span>
				</div>	
                <div class="edit_input">
					<span class="edit_input_span1">性别：</span>
					<span class="edit_input_check">
					     <form:select path="gender" items="${genderItems}" />
					</span>
				</div>	
                <div class="edit_input">
					<span class="edit_input_span1">生日：</span>
					<span class="edit_input_span2">
                         <form:input path="birthday"/>
					</span>
				</div>	
				<div class="edit_input">
					<span class="edit_input_span1">联系电话：</span>
					<span class="edit_input_span2">
                         <form:input path="telephone"/>
					</span>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1">QQ：</span>
					<span class="edit_input_span2">
                         <form:input path="qqNumber"/>
					</span>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1">通讯地址：</span>
					<span class="edit_input_span2">
                         <form:input path="workAddress"/>
					</span>
				</div>
				<div class="edit_input" style="margin-top:30px;">
					<h3>个人介绍<span>公开信息</span></h3>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1">职业信息：</span>
					<span class="edit_input_span2">
						<form:input path="occupy"/>	
					</span>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1">现居城市：</span>
					<span class="edit_input_span2">
                        <form:input path="currentAddress"/>	
					</span>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1" style="float:left;">个人介绍：</span>
					<span class="edit_input_span2">
					    <form:textarea id="task_desc" placeholder="请输入您的个人介绍" path="introdDesc"></form:textarea>
					</span>
				</div>
				<div class="edit_input">
					<span class="edit_input_span1" style="float:left;">擅长技能：</span>
					<span class="edit_input_span2">
					     <form:textarea id="skill_desc" placeholder="请输入职业技能认证与资格介绍" path="skills"></form:textarea>
					</span>
				</div>
			</div>
			<div class="submit">
				<form:hidden path="memeberId"/>
				<form:hidden path="id"/>
				<input value="立即提交" type="submit" class="submit_btn"/>
			</div>
		</form:form>	
		</div>
	</div>
</body>
</html>