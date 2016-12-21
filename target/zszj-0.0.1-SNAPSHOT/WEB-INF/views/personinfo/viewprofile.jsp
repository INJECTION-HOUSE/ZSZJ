<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript">
	$(function(){
		var genderCode = $('#genderCodeTxt').text();
		if(genderCode == 0) {
			$('#genderCodeTxt').html('男');
		}
		else if(genderCode == 1){
			$('#genderCodeTxt').html('女');
		}
		else {
			$('#genderCodeTxt').html('保密');
		}
	});
	</script>
</head>
<body>
	<div class="edit">
		<div class="edit_title">
			<h3>个人资料</h3>
		</div>
		<div class="edit_content">
		    <div class="edit_wrap">
			    <div class="edit_input border">
					<h3>个人资料</h3>
				</div>
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color">姓名：</span>
					<span class="edit_input_span2">${memberDetailInfo.nickname}</span>
				</div>	
                <div class="edit_input border">
					<span class="edit_input_span1 edit_color">性别：</span>
					<span id="genderCodeTxt" class="edit_input_check">${memberDetailInfo.gender}</span>
				</div>	
                <div class="edit_input border">
					<span class="edit_input_span1 edit_color">生日：</span>
					<span class="edit_input_span2">${memberDetailInfo.birthday}</span>
				</div>	
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color">电话：</span>
					<span class="edit_input_span2">${memberDetailInfo.telephone}</span>
				</div>
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color">QQ：</span>
					<span class="edit_input_span2">${memberDetailInfo.qqNumber}</span>
				</div>
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color">通讯地址：</span>
					<span class="edit_input_span2">${memberDetailInfo.workAddress}</span>
				</div>
				<div class="edit_input " style="margin-top:30px;">
					<h3>个人介绍<span>公开信息</span></h3>
				</div>
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color">现居城市：</span>
					<span class="edit_input_span2">${memberDetailInfo.currentAddress}</span>
				</div>
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color">职业信息：</span>
					<span class="edit_input_span2">${memberDetailInfo.occupy}</span>
				</div>
				<div class="edit_input border">
					<span class="edit_input_span1 edit_color" style="float:left;">个人介绍：</span>
					<span class="edit_input_span2" style="margin-top:9px;float:left;">${memberDetailInfo.introdDesc}</span>
					
				</div>	
				<div class="edit_input border">
				    <div style="clear:both"></div>
					<span class="edit_input_span1 edit_color" style="float:left;">擅长技能：</span>
					<span class="edit_input_span2" style="margin-top:9px;float:left;">${memberDetailInfo.skills}</span>
					<div style="clear:both"></div>		
				</div>	
				 
			</div>
		</div>
	</div>
</body>


</html>