<%@ page pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
	<head>
	   <meta name="description" content="">
	   <meta name="keywords" content="">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/jquery.datetimepicker.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.datetimepicker.full.js"></script>
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
		<style type="text/css">
			.error {
				color: red;
				font-style: normal;
				font-weight: bold;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				
				//初始化页面插件
				function initPage(){
					setup();
					$.datetimepicker.setLocale('zh');
					$("#error_time").datetimepicker({
						startDate: new Date(),
						//format: 'Y-m-d H:m',
						step:10,
						onChangeDateTime: function(date){
							if(date){
								$("#errorTimeHidden").val(date.getTime());
							}
						}
					}); 
					
					$("#deadline").datetimepicker({
						startDate:new Date(),
						minDate: new Date(),
						//format: 'Y-m-d H:m',
						step:30,
						onChangeDateTime: function(date){
							if(date){
								$("#deadlineHidden").val(date.getTime());
							}
						}
					}); 
					
					
					
					$("#file").fileupload({
						iframe:true,			
						url:'${pageContext.request.contextPath}/fileUpload/picture',
						maxFileSize:2097152, //(最大2M)字节
						acceptFileTypes:/(\.|\/)('|bmp|jpeg|jpg|png')$/i,
						maxNumberofFiles:5
					}).on('fileuploadprocessalways',function (e,data){
						if (data.files.error) {
							if ('File is too large' == data.files[0].error) {
							alert("照片大小不能超过2M");
							}else if('File type not allowed' == data.files[0].error){
								alert("上传的图片格式必须为jpg/jpeg/png/bmp");
							}						
						}else{
							data.submit();	
						}
					}).on('fileuploaddone',function(e,data){
						var newItem = '<li><img src="${pageContext.request.contextPath}/upload/' + data.result.path + '"/></li>'
						$("#upload_default").before(newItem);
						var value = $("#image_files").val();
						if(value !== ""){
							$("#image_files").val(value + ";" + data.result.path);
						}else{
							$("#image_files").val(data.result.path);
						}
					});
					
				}
				
				initPage();
				
			});
			
			$(function(){
				$('#gotoQRCodeView').click(function(){
					$.ajax({
						url: "${pageContext.request.contextPath}/payment/unifiedorder",
						type: "GET",
						data:{cash:$("#cashId").val()},
						dataType: "json",     
						contentType: "application/json; charset=utf-8",
						success: function (data) {
							if(data.success) {
								var value = data.success;
								var imgurl = data.QRIMAGE;
				            	console.log("success : " + value);
				            	console.log("image url : " + imgurl);
				            	var resultImg = '<img style="padding: 5px; width:300px;height:300px;"  src="${pageContext.request.contextPath}/upload/'+ imgurl + '"/>';
								$("#qrcodediv").append(resultImg);
								$('.screen').show();
								$('.tanchu').show();
							} else {
								alert(data.error);
							}
			            }
					});
				});
				$('.cancel').click(function(){
				    $('.screen').hide();
					$('.tanchu').hide();
					   $(document.body).css({
						   "overflow-y":"auto"
					   });
				 });
				$('.toub').click(function(){
				    $('.screen').hide();
					$('.tanchu').hide();
					   $(document.body).css({
						   "overflow-y":"auto"
					   });
				 });
			});
			
			function startPayment() {
				console.log("start to call weixin payment api");

			}
			
			function uploadFiles(){
				$("#file").click();
			}
		</script>
	</head>
	<body>
		 <div class="publish_task_iframe">
		      <div class="publish_task_iframe_title">
			        <h3>发布任务详情</h3>
			        <input style="display: none;" type="file" id="file" name="imgs"/>
			  </div>
			  <div class="publish_task_iframe_content">
			        <div class="publish_task_iframe_wrap">
			        	<form:form modelAttribute="taskForm" method="post" action="${pageContext.request.contextPath}/personCenter/savetask">
			        		<div class="input1">
								  <label>标题</label>
								  <form:input type="text" path="taskTitle" /><span style="color: red">*<form:errors path="taskTitle" cssClass="error" /></span>
								  <label style="margin-left:16px;">联系电话</label>
								  <form:input id="cellphone" type="text" path="cellphone"/><span style="color: red">*<form:errors path="cellphone" cssClass="error" /></span>
							</div>
							<div class="input1">
								  <label>问题分类</label>
								  <form:select id="category" name="category" path="category" items="${issueType}" /><span style="color: red">*</span>
								 
								  <label style="margin-left:16px;">发生时间</label>
								  <input id="error_time" type="text"/><span style="color: red">*</span>
								  <form:hidden id="errorTimeHidden" path="errorTime"></form:hidden>
							</div>
							<div class="input1">
								  <label class="text_label">问题描述</label>
								  <form:textarea id="task_desc" placeholder="请输入您的内容" path="taskDesc"></form:textarea>
								  <form:errors path="taskDesc" cssClass="error" />
							</div>
							<div class="input1">
								<label>截止日期</label>
								<input id="deadline" type="text" /><span style="color: red">*</span>
								<form:hidden id="deadlineHidden" path="deadline"></form:hidden>
							</div>
							<div class="input1">
								<label>位置选择</label>
								<form:select name="province" id="province" path="province" style="width: 200px; margin-right: 10px;"/>
								<form:select name="city" id="city" path="city" style="width: 200px; margin-right: 10px;"/>
								<form:select name="county" id="county" path="county" style="width: 200px; margin-right: 10px;"/><span style="color: red">*</span>
							</div>
							<div class="input1">
								  <label>维修地址</label>
								  <form:input id="address_detail" type="text" path="addressDetail"/><span style="color: red">*<form:errors path="addressDetail" cssClass="error" /></span>
							</div>
							<div class="input1">
								  <label>预付金额</label>
								  <form:input id="cashId" type="number" min="50" path="prepayFee"/><span style="color: red">*<form:errors path="prepayFee" cssClass="error" /><a href="#" id="gotoQRCodeView">微信支付</a></span>
							</div>
							<div class="input1" style="margin-top:16px;">
								  <label class="load_label">已上传图片</label>
								  <ul id="images_list" class="pic_load">
									   <li id="upload_default"><img src="${pageContext.request.contextPath}/static/images/upload_default.png" onclick="uploadFiles()" /></li>
								  </ul>
								  <div class="clear"></div>
								  <form:hidden id="image_files" path="imageFiles"/>
							</div>
							<div class="submit">
								  <input value="立即提交" type="submit" class="submit_btn"/>
								  <input value="取消" type="button" class="back_btn"/>
							</div>
			        	</form:form>    
					</div>
					
			  </div>
		 </div>
		 <div class="screen"></div>
		 <div class="tanchu">
	     <h3>微信扫码支付</h3>
		 <div class="tanchu_btn">
		      <div class="text_box" id="qrcodediv" style="width:310px; height:310px;">
			  </div>
		 </div>
		 <div class="tanchu_btn">
		      <input type="button" value="取消" class="cancel" />
			  <input type="button" value="关闭" class="toub"/>
		 </div>
	</div>			      
	</body>
</html>