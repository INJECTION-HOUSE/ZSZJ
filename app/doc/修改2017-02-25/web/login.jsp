<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/jquery.cookie.js'></script>
   <script type="text/javascript">
   $(function(){
	      var iHeight=$(window).height();
		  $('.bg').css('height',iHeight-159+'px');
		  <!-- $('.login_width').css('height',iHeight+'px'); -->
	 });
	 $(function(){
		 $("#reset_panel").hide();
	     var fHeight=$('.footer').height();
		 var iHeight=$(window).height();
		 var offsetTop=$('.footer').offset().top;
		 if(offsetTop<(iHeight-fHeight)){
		      $('.footer').css('position','absolute');
			  $('.footer').css('bottom',0);
		 };
		 $("#userAccount").val($.cookie("normalUserAccount"));
	});
		 
	 function saveName() {
			if($("#rememberName").prop("checked")){
				$.cookie("normalUserAccount", $("#userAccount").val(), {expires:7});
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
		
		function doReset() {
			$("#login_panel").hide();
			$("#reset_panel").show();
		}
		
		function cancelReset(){
			$("#login_panel").show();
			$("#reset_panel").hide();
		}
		
		var sms_code = "";
		var sms_timeout = null;
		var sms_mobile = "";
		function sendCode(){
			if(sms_timeout){
				alert('请勿频繁发送验证码');
				return;
			}
			var mobile = $("#userMobile").val();
			var reg = new RegExp("^[0-9]*$");
			if(empty(mobile)) {
				alert('请输入手机号码', '警告对话框');
				return;
			}
			else if(!reg.test(mobile)){
				alert('非法输入', '手机号码输入不正确', 'error');
				return;
			}
			else if(mobile.length != 11) {
				alert('非法输入', '手机号码输入不正确', 'error');
				return;
			}
			
			sms_timeout = setTimeout(function(){
				sms_timeout = null;
			}, 60000);
			
			$.ajax({  
                url : '${pageContext.request.contextPath}/login/getSMSCode?cellphone='+mobile, 
                type: 'GET', 
                cache: false,
                dataType : 'json',  
                contentType: "application/json",
                success : function(r) {  
                    if (r.result) {
                    	alert('发送成功');
                    	sms_code = r.data;
                    	sms_mobile = mobile;
                    } else {  
                    	clearTimeout(sms_timeout);
                  	  	alert(r.msg, 'error');
                    }  
                }  
            });  
		}
		
		function resetPassword(){
			var username = $("#userMobile").val();
			var newPwd = $("#newPassword").val();
			var inpoutCode = $("#verifyCode").val();
			if(inpoutCode != sms_code){
				alert('输入的验证码不正确');
				return;
			}
			if(username != sms_mobile && !empty(sms_code)){
				alert('输入的手机号不正确');
				return;
			}
			if(empty(newPwd)){
				alert('请输入新密码');
				return;
			}
			
			
			$.ajax({  
                url : '${pageContext.request.contextPath}/login/resetPassword', 
                type: 'GET', 
                cache: false,
                data: {
                	mobile: username,
                	pwd: newPwd
                },
                dataType : 'json',  
                contentType: "application/json",
                success : function(r) {  
                    if (r) {
                    	alert('重置密码成功');
                    	cancelReset();
                    } else {  
                    	alert('重置密码失败');
                    }  
                }  
            });  
			
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
    
    <div class="wrap"><div class="logo1"><img src="${pageContext.request.contextPath}/static/images/logo.png" /></div></div>
    <div class="main">
	    
		<div class="bg">
		     <div class="login_width">
			      
				  <div class="login_right">
				         <img src="${pageContext.request.contextPath}/static/images/sucai1.png" />
				  </div>
			      <div class="login_left" id="login_panel">
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
					  <div style="width:84%;margin:auto;">
					   		<label>
								<input name="rememberName" type="checkbox" class="ace" id="rememberName" checked="checked">
								<span class="lbl"> 记住账号</span>
							</label>
							<a onclick="doReset()" style="text-decoration: underline;color: blue; margin-left: 5%;cursor: pointer;">忘记密码？</a>
				   	  </div>
					  
					  <div class="login_btn">
						   	<input type="button" value="登录" onclick="saveName()" class="login_btn_1" />
			   		  	  	<input type="button" value="注册账号" onclick="registerMember()" class="login_btn_2" />
					  </div>
				  </div>
				  
				  <div class="login_left" id="reset_panel" style="dispaly: none">
			          <ul>
						   <li>
								<div class="info">
									  <div class="label"><label>手机号</label></div>
									  <div class="input"><input type="text" name="userMobile" placeholder="手机号" id="userMobile" /></div>
								</div>
						   </li>
						   <li>
								<div class="info">
									  <div class="label"><label>验证码</label></div>
									  <div class="input" style="width: 45%;"><input type="text" placeholder="验证码" name="verifyCode" id="verifyCode"/></div>
									  <input class="send_code" type="button" value="发送" onclick="sendCode()" />
								</div>
						   </li>
						   <li>
								<div class="info">
									  <div class="label"><label>新密码</label></div>
									  <div class="input"><input type="text" name="newPassword" placeholder="新密码" id="newPassword" /></div>
								</div>
						   </li>
						   
					  </ul>
					  
					  <div class="login_btn">
						   	<input type="button" value="确定" onclick="resetPassword()" class="login_btn_1" />
			   		  	  	<input type="button" value="取消" onclick="cancelReset()" class="login_btn_2" />
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