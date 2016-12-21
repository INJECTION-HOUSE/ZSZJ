<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript">
   		var requestsms = false;
   		var requestTime = null;
   		var smscode = "";
         $(function(){
		      var iHeight=$(window).height();
			  $('.bg').css('height',iHeight/2-32+'px');

		 });
		 $(function(){
		      var iHeight=$(window).height();
		      var halfHeight=(iHeight-537)/2;
			  $('.logo').css('margin-bottom',halfHeight+'px');
			  
			  $("#smscode").bind("click", function () {
				  // 验证手机号码是否合法
				  var cellphone = $("#cellphone").val();
				  var reg = new RegExp("^[0-9]*$");
				  if(empty(cellphone)) {
					  alert('请输入手机号码', '警告对话框');
					  return;
				  }
				  else if(!reg.test(cellphone)){
					  alert('非法输入', '手机号码输入不正确', 'error');
					  return;
				  }
				  else if(cellphone.length != 11) {
					  alert('非法输入', '手机号码输入不正确', 'error');
					  return;
				  }
				  // 验证时间是否过期，是否可以重新获取验证码 5min
				  var newTime = new Date();
				  if(!empty(requestTime)) {
					  var oneMinute=1000*60
					  var duration = (newTime.getTime() - requestTime.getTime()) / oneMinute;
					  if(duration <= 5) {
						  return;
					  }
				  }
				  $.ajax({  
                      url : '${pageContext.request.contextPath}/login/getSMSCode?cellphone='+$("#cellphone").val(), 
                      type: 'GET', 
                      cache: false,
                      dataType : 'json',  
                      contentType: "application/json",
                      success : function(r) {  
                          if (r.result) {  
                        	  smscode = r.data;
                        	  requestTime = new Date();
                        	  requestsms = true;
                        	  resendSmsTimeout();
                          } else {  
                        	  alert('错误', r.msg, 'error');
                              requestTime = null;
                        	  requestsms = false;
                          }  
                      }  
                  });  
              });
			  
			  $("#registerBtn").bind("click", function () {
				  createMember();
			  });
		 });
		 
		 var second=300; // 5 mins
		 var timer;
		 function resendSmsTimeout()
		 {
			second--;
			if(second>-1)
			{
				$("#smscode").attr("disabled", true);
				$("#callsmstips").html(second + "秒");
				timer = setTimeout('resendSmsTimeout()',1000);
			}
			else
			{
				clearTimeout(timer);
				$("#smscode").attr("disabled", false);
				$("#callsmstips").html("");
			}
		}
		 
		 function createMember() {
			 console.log("create new member");
			 var cellphone = $("#cellphone").val();
			 var receivedsmscode = $("#mysmscode").val();
			 var nickname = $("#nickname").val();
			 var userpass = $("#password").val();
			 var repass = $("#repassword").val();
			 if(empty(userpass) || empty(repass) || empty(receivedsmscode) || empty(cellphone) || empty(nickname)) {
				 alert('提示', '注册信息不能为空', 'error');
				 return;
			 }
			 
			 if(userpass === repass && receivedsmscode === smscode) {
				 $.ajax({
						url:"${pageContext.request.contextPath}/login/createNewUser",
						type: 'POST', 
						cache:false,
						data:{userAccount:$("#cellphone").val(),password:$("#password").val(), username:$("#nickname").val()},
						success:function(data){
							if(data.result==1){
								alert('提示', '自动跳转到登陆页面', 'info');
								window.location.href="${pageContext.request.contextPath}/login/index";
							}else{
								alert(data.msg);
							}
						}
				});
			 }
			 
		 }
		 
		 function empty(data)
		 {
		   if(typeof(data) == 'number' || typeof(data) == 'boolean')
		   { 
		     return false; 
		   }
		   if(typeof(data) == 'undefined' || data === null)
		   {
		     return true; 
		   }
		   if(typeof(data.length) != 'undefined')
		   {
		     return data.length == 0;
		   }
		   var count = 0;
		   for(var i in data)
		   {
		     if(data.hasOwnProperty(i))
		     {
		       count ++;
		     }
		   }
		   return count == 0;
		 }
</script> 
</head>
<body>
    <div class="main">
	    <div class="top">
		     <div class="wrap">
				   <p>
					    <a href="${pageContext.request.contextPath}/login/index">请登录</a>
					    <a class="free_register">免费注册</a>
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
		<div class="bg">
		     <div class="wrap"><div class="logo"><img src="${pageContext.request.contextPath}/static/images/login_logo.png" /></div></div>
		     <div class="register">
			      <ul>
				       <li>
					        <div class="info">
							      <div class="label"><label>手机号码<label></div>
								  <div class="input"><input type="text" id="cellphone" name="cellphone" placeholder="手机号" /></div>
							</div>
					   </li>
					   	<li>
					        <div class="info">
							      <div class="label"><label>密码设置<label></div>
								  <div class="input"><input type="password" id="password" name="password" placeholder="密码" /></div>
							</div>
					   </li>
					   <li>
					        <div class="info">
							      <div class="label"><label>确认密码<label></div>
								  <div class="input"><input type="password" id="repassword" name="repassword" placeholder="确认密码" /></div>
							</div>
					   </li>
					   <li>
					        <div class="info">
							      <div class="label"><label>会员昵称<label></div>
								  <div class="input"><input type="text" id="nickname" name="nickname" placeholder="昵称" /></div>
							</div>
					   </li>
					   <li>
					        <div class="info">
							      <div class="label_dx"><label>短信验证码<label></div>
								  <div class="input_dx"><input type="text" id="mysmscode" placeholder="短信验证码"/></div>
								  <div class="huoqu">
								       <input type="button" value="获取验证码" id="smscode"/>
								       <span id="callsmstips"></span>
								  </div>
							</div>
					   </li>
				  </ul>
				  <div class="login_btn">
				       <input type="button" value="注册" id="registerBtn" class="login_btn_3" />
					   <input type="button" value="取消" id="cancelBtn" class="login_btn_4" />
				  </div>
			 </div>
		</div>
		<div class="clear"></div>
		<div class="footer position">
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