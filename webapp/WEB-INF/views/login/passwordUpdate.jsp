<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>Login</title>
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${pageContext.request.contextPath}/static/loginStyle/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle/font-awesome.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle/ace.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle/ace-rtl.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/loginStyle/style.css" />
		
<!-- 		<STYLE TYPE="text/css">
			BODY {background-image: URL(${pageContext.request.contextPath}/static/images/login.jpg);
			background-position: center;
			background-repeat: no-repeat;
			background-attachment: fixed;}
		</STYLE> -->
		
		<script type="text/javascript">
			getStyle();
			function getStyle(){
					var container = document.getElementsByTagName("head")[0];
	                var addStyle = document.createElement("link");
	                addStyle.rel = "stylesheet";
	                addStyle.type = "text/css";
	                addStyle.media = "screen";
	                addStyle.href = "${pageContext.request.contextPath}/static/loginStyle/ace-theme-darkblue.css";
	                container.appendChild(addStyle);
			};
			/* function getCookie(name) 
			{ 
			    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			 
			    if(arr=document.cookie.match(reg))
			 
			        return unescape(arr[2]); 
			    else 
			        return null; 
			}  */
		</script>


		<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/jquery.min.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/jquery.cookie.js'></script>
		
		<script type="text/javascript">
		$(function() {
			$("#username").val($.cookie("username"));
			$(document).keypress(function(e) {
			    // åè½¦é®äºä»¶  
			       if(e.which == 13) {
			    	   saveName();
			       }
			});
		});
		</script>
		<script type="text/javascript">
/* function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]); 
    else 
        return null; 
} 
function setCookie(name,value) 
{ 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + 15*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) +"; path=/"+ ((value==null) ? "" : ";expires=" + exp.toGMTString());
}  */
</script>
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content" style="background:none;">
				<div class="row">
					<div class="col-xs-12 login-container-wrap">
						<div class="login-container">
							<div class="center">
								<h1 class="login-title">
									<!-- <div class="logo-img"></div>
									<span>ç¦å®éåå°ç®¡çç³»ç»</span> -->
								</h1>
							</div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main" id="loginMain">
											<h1 class="header">
											           修改密码
											</h1>
											<font style="color: red;"></font>
											<div class="space-6"></div>
											<form class="form-horizontal" method="post" id="loginForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="userpsw" class="form-control" placeholder="原密码" name="userpass" />
														</span>
													</label>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="newuserpsw" class="form-control" placeholder="新密码 " name="newuserpass" />
														</span>
													</label>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="confirmuserpsw" class="form-control" placeholder="确认密码" name="confirmUserPass" />
														</span>
													</label>
													<div class="space"></div>
													<div class="clearfix">
													    <div class="row">
														    <div class="col-xs-6">
																<button class="btn btn-block btn-table" onclick="passwordUpdate()" type="button">
																	修改
																</button>
															</div>
															<div class="col-xs-6">
																<button class="btn btn-block btn-table" type="reset">
																	重置
																</button>
															</div> 
														</div>
													</div>
													<div class="space-4"></div>
												</fieldset>
											</form>

										</div><!-- /widget-main -->
									</div><!-- /widget-body -->
							<script type="text/javascript">
								function passwordUpdate() {
									//非空
									if($("#userpsw").val() =="" || $("#userpsw").val()==null){
										alert("原密码不能为空");
										return;
									}
									if(($("#newuserpsw").val() =="" || $("#newuserpsw").val()==null)){
										alert("新密码不能为空");
										return;
									}
									if(($("#confirmuserpsw").val() =="" || $("#confirmuserpsw").val()==null)){
										alert("确认密码不能为空");
										return;
									}

									//格式
									if($("#newuserpsw").val().length<8){
										alert("新密码长度不得小于8位");
										return;
									}
									
									var checkNum = /^[A-Za-z0-9]+$/;
									if(!checkNum.test($("#newuserpsw").val())){
										alert("新密码只能输入数字和字母(不区分大小写)");
										return;
									}
									
									//一致
									if($("#newuserpsw").val() != $("#confirmuserpsw").val()){
										alert("确认密码与新密码不一致,请重新核对后输入");
										return;
									}

									//调用后台
									$.ajax({
										url:"${pageContext.request.contextPath}/login/passwordUpdate",
										cache:false,
										data:{password:$("#userpsw").val(),newpassword:$("#newuserpsw").val()},
										success:function(data){
											if(data.result==1){
												alert("密码修改成功");
												window.location.href="${pageContext.request.contextPath}/index/main";
												return;
											}else{
												alert(data.msg);
												return;
											}
										}
									});								

								};
							</script>
								</div><!-- /login-box -->
							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->
	</body>
</html>