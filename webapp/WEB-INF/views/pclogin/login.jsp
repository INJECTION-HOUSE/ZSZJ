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
	      var iHeight=$(window).height();
		  $('.bg').css('height',iHeight-159+'px');
		  <!-- $('.login_width').css('height',iHeight+'px'); -->
	 });
	 $(function(){
	     var fHeight=$('.footer').height();
		 var iHeight=$(window).height();
		 var offsetTop=$('.footer').offset().top;
		 if(offsetTop<(iHeight-fHeight)){
		      $('.footer').css('position','absolute');
			  $('.footer').css('bottom',0);
		 };
	});
		 
		 function saveName() {
				if($("#rememberName").prop("checked")){
					$.cookie("userAccount", $("#userAccount").val(), {expires:7});
				}
				
				$.ajax({
					url:"${pageContext.request.contextPath}/login/signin",
					cache:false,
					data:{userAccount:$("#userAccount").val(),password:$("#userpass").val()},
					success:function(data){
						if(data.result==1){
							window.location.href="${pageContext.request.contextPath}/index/main";
						}else{
							alert(data.msg);
						}
					}
				});
			};
			
			function registerMember() {
				window.location.href="${pageContext.request.contextPath}/login/register";
			}
   </script> 
</head>
<body>
    
    <div class="wrap"><div class="logo1"><img src="${pageContext.request.contextPath}/static/images/logo.png" /></div></div>
    <div class="main">
	    
		<div class="bg">
		     <div class="login_width">
			      
				  <div class="login_right">
				         <img src="${pageContext.request.contextPath}/static/images/sucai1.png" />
				  </div>
			      <div class="login_left">
				          <ul>
							   <li>
									<div class="info">
										  <div class="label"><label>账号</label></div>
										  <div class="input"><input type="text" name="userAccount" placeholder="手机号" id="userAccount" /></div>
									</div>
							   </li>
							   <li>
									<div class="info">
										  <div class="label"><label>密码</label></div>
										  <div class="input"><input type="password" placeholder="密码" name="userpass" id="userpass"/></div>
									</div>
							   </li>
						  </ul>
						  <div class="login_btn">
							   <input type="button" value="登录" onclick="saveName()" class="login_btn_1" />
					   <input type="button" value="注册账号" onclick="registerMember()" class="login_btn_2" />
						  </div>
				  </div>
				  
			 </div>
			 <div class="clear"></div>
		</div>
		<div class="footer">
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
</body>
</html>