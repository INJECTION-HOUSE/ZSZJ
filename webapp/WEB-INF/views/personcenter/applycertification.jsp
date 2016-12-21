<%@ page pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/vendor/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/load-image.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/canvas-to-blob.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.blueimp-gallery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.iframe-transport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/cors/jquery.xdr-transport.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload-process.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/fileupload/jquery.fileupload-validate.js"></script>
   <script type="text/javascript">
		var p1=0;
		var p2=0;
		var p3=0;
		var p4=0;
		$(function(){
		     var fHeight=$('.index_footer').height();
			 var iHeight=$(window).height();
			 var offsetTop=$('.index_footer').offset().top;
			 if(offsetTop<(iHeight-fHeight)){
			      $('.index_footer').css('position','absolute');
				  $('.index_footer').css('bottom',0);
			 };
			 $("#enterpriserDiv").hide();
			 $.ajax({
					url:"${pageContext.request.contextPath}/messages/countInBox",
					cache:false,
					data:{userAccount:"xxxxxxxx",password:"xxxxxxxx"},
					success:function(data){
						if(data.result==1){
							$("#mymsg_txt").html("我的消息(" + data.count +")");
						}else{
							alert(data.count);
						}
					}
			});
			 
			$("#file").fileupload({
				iframe:true,			
				url:'${pageContext.request.contextPath}/fileUpload/picture',
				maxFileSize:2097152, //(最大2M)字节
				acceptFileTypes:/(\.|\/)('|bmp|jpeg|jpg|png')$/i,
				maxNumberofFiles:1,
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
					if (p1==1) {
						$("#avatarImg").attr("src","${pageContext.request.contextPath}/upload/"+data.result.path);
						$("#avatarPathId").val(data.result.path);
						p1=0;
					}
					if (p2==1) {
						$("#idencardfgImg").attr("src","${pageContext.request.contextPath}/upload/"+data.result.path);
						$("#idencardfgImgPathId").val(data.result.path);
						p2=0;
					}
					if (p3==1) {
						$("#idencardbgImg").attr("src","${pageContext.request.contextPath}/upload/"+data.result.path);
						$("#idencardbgImgPathId").val(data.result.path);
						p3=0;
					}
					if (p4==1) {
						$("#enterprisecardImg").attr("src","${pageContext.request.contextPath}/upload/"+data.result.path);
						$("#enterpriseImgId").val(data.result.path);
						p4=0;
					}
			});
			
			$('#certificationTypeId').change(function(){ 
				alert($(this).children('option:selected').val()); 
				var p1=$(this).children('option:selected').val();
				console.log(p1);
				if(p1 == 1) {
					$("#enterpriserDiv").show();
				}
				else {
					$("#enterpriserDiv").hide();
				}
			});
				
		});
		
        function signout() {
        	console.log("sign out...");
        	$.ajax({
				url:"${pageContext.request.contextPath}/login/signout",
				cache:false,
				data:{userAccount:"xxxxxxxx",password:"xxxxxxxx"},
				success:function(data){
					if(data.result==1){
						window.location.href="${pageContext.request.contextPath}/login/index";
					}else{
						alert(data.msg);
					}
				}
			});
        }
        
        function imgOne() {
        	p1=1;
			$("#file").click();
        }
        
        function imgTwo() {
        	p2=1;
			$("#file").click();
        }
        
        function imgThree() {
        	p3=1;
			$("#file").click();
        }
        function imgfour() {
        	p4=1;
			$("#file").click();
        }
   </script>
</head>
<body style="background:#EFEFEF;">
    <div class="main">
		<div class="head">
		      <div class="index_top">
				   <div class="wrap">
					     <p>
					   		<%Object username = request.getSession().getAttribute("USERNAME"); if(username == null) {%>
						   		<a href="${pageContext.request.contextPath}/login/index">登陆|注册</a>
						    <% } else {%>
						    	<a><%=pageContext.getSession().getAttribute("USERNAME")%></a>
							    <a href="#" onclick="signout()" class="free_register">退出</a>
						    <% }%>
					    </p>
					    <div class="right_a">
							  <ul>
								    <li id="mymsg_txt">消息(0)</li>
								    <li>个人中心</li>
								    <li>设置</li>
								    <li>帮助</li>
							  </ul>
					    </div>
				   </div>
			  </div>
		      <div class="wrap">
			       <div class="task_logo float">
						  <a><img src="${pageContext.request.contextPath}/static/images/logo.png" /></a>
				   </div>
				   <div class="head_right">
				          <div class="head_right_input">
						        <input type="text" placeholder="输入任务名称" class="search_text"/>
								<input type="button" value="搜索" class="search_btn"/>
						  </div>
				   </div>
			  </div>
		</div>
		<div class="content">
		     <div class="info">当前所在位置：<a href="${pageContext.request.contextPath}/personCenter/home">个人中心 / </a><a class="small_title">申请认证</a></div>
			 <div class="app">
			      <div class="app_title">
				        <h3>申请认证</h3>
				        <input style="display: none;" type="file" id="file" name="imgs" />
				  </div>
				  <div class="app_content">
				        <div class="app_wrap">
				        	<form:form modelAttribute="memberForm" method="post" action="${pageContext.request.contextPath}/personCenter/applyCertif">
						       <div class="input1">
									  <label>真实姓名</label><form:input path="realName"/>
									  <label style="margin-left:40px;">身份证号</label><form:input path="identifierId"/>
								</div>
								<div class="input1">
									  <label>性别</label>
									  <form:select path="gender" items="${genderItems}" />
									  <label style="margin-left:40px;">认证类型</label>
									   <form:select id="certificationTypeId" path="certificationType" items="${lisenceTypeList}" />
								</div>
								<div class="input1" style="margin-top:30px;">
									  <label class="touxiang_label">头像</label>
									  <form:hidden id="avatarPathId" path="avatarName"/>
									  <div class="pic_touxiang"><img id="avatarImg" style="width:150px;height:120px;cursor:pointer;" src="${pageContext.request.contextPath}/static/images/defaultprofile.png" onclick="imgOne()" /></div>
								</div>
								<div class="input1" style="margin-top:30px;">
									  <label class="shenfen_label">身份证照片</label>
									  <div class="pic_shenfen">
									       <div class="img">
										         <img id="idencardfgImg" style="width:200px;height:133px;cursor:pointer;" src="${pageContext.request.contextPath}/static/images/frontIdCard.png" onclick="imgTwo()"/>
												 <img id="idencardbgImg" style="width:200px;height:133px;cursor:pointer;" src="${pageContext.request.contextPath}/static/images/backIdCard.png" onclick="imgThree()"/>
												 <form:hidden id="idencardfgImgPathId" path="idencardfgImg"/>
												 <form:hidden id="idencardbgImgPathId" path="idencardbgImg"/>
										   </div>
										   <div class="clear"></div>
									  
								      </div>
						       </div>
						       <div class="input1" id="enterpriserDiv" style="margin-top:30px;">
									  <label class="touxiang_label">营业执照</label>
									  <form:hidden id="enterpriseImgId" path="enterpriseImg"/>
									  <div class="pic_touxiang"><img id="enterprisecardImg" style="width:200px;height:133px;cursor:pointer;" src="${pageContext.request.contextPath}/static/images/businesslisence.png" onclick="imgfour()" /></div>
								</div>
							   <div class="submit">
							   		  <form:hidden path="id"/>
							   		  <form:hidden path="cellphone"/>
									  <input value="提交认证" type="submit" class="submit_btn"/>
									  <input value="取消" type="button" class="back_btn"/>
							   </div>
							   </form:form>
				         </div>
			         </div>
		        </div>	
        </div>				
        <div class="index_footer">
		     <div class="footer_nav">
			     <a>首页</a><span>|</span>
				 <a>关于我们</a><span>|</span>
				 <a>维修任务</a><span>|</span>
				 <a>个人中心</a><span>|</span>
				 <a>联系我们</a>
			 </div>
			 <p class="info">宁波市三体网络科技有限公司<span><a href="http://www.miitbeian.gov.cn/">浙ICP备15044527号-1</a></span></p>
		</div>
	</div>
	<div class="screen"></div>
	<div class="tanchu">
	     <h3>我要竞标</h3>
		 <div class="tanchu_content">
		      <div class="text_box">
			       <span>维修原因</span>
				   <span>
				        <textarea></textarea>
				   </span>
			  </div>
			  <div class="input_box1">
			       <span>维修金额</span>
				   <span>
				        <input type="text" placeholder="输入金额"></input>
				   </span>
			  </div>
			  <div class="input_box2">
			       <span>保证金(选填)</span>
				   <span>
				        <input type="text" placeholder="输入金额"></input>
				   </span>
			  </div>
		 </div>
		 <div class="tanchu_btn">
		      <input type="button" value="取消" class="cancel" />
			  <input type="button" value="我要投标" class="toub"/>
		 </div>
	</div>
    
</body>


</html>