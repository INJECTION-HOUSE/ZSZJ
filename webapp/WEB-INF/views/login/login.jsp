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
		<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/jquery.min.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/jquery.cookie.js'></script>
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
			
			function getCookie(name) { 
			    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			 
			    if(arr=document.cookie.match(reg))
			 
			        return unescape(arr[2]); 
			    else 
			        return null; 
			} 

			$(function() {
				$("#userAccount").val($.cookie("userAccount"));
				$(document).keypress(function(e) {
				       if(e.which == 13) {
				    	   saveName();
				       }
				});
			});

			function getCookie(name) { 
			    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			    if(arr=document.cookie.match(reg))
			        return unescape(arr[2]); 
			    else 
			        return null; 
			} 
			function setCookie(name,value) { 
			    var exp = new Date(); 
			    exp.setTime(exp.getTime() + 15*24*60*60*1000); 
			    document.cookie = name + "="+ escape (value) +"; path=/"+ ((value==null) ? "" : ";expires=" + exp.toGMTString());
			} 
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
									<div class="logo-img"></div>
								</h1>
							</div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main" id="loginMain">
											<h1 class="header">
											             用户登录
											</h1>
											<font style="color: red;"></font>
											<div class="space-6"></div>
											<form class="form-horizontal" method="post" id="loginForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block login-user">
															<input type="text" class="form-control" placeholder="用户名" name="userAccount" id="userAccount"/>
														</span>
													</label>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="userpass" class="form-control" placeholder="密码" name="userpass" />
														</span>
													</label>
													<div class="space"></div>
													<label>
														<input name="rememberName" type="checkbox" class="ace" id="rememberName">
														<span class="lbl"> 记住用户名</span>
													</label>
													  
													<div class="space"></div>
													<div class="clearfix">
													    <div class="row">
														    <div class="col-xs-6">
																<button class="btn btn-block btn-table" onclick="saveName()" type="button">
																登录
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
								function saveName() {
									if($("#rememberName").prop("checked")){
										$.cookie("userAccount", $("#userAccount").val(), {expires:7});
									}
									
									$.ajax({
										url:"${pageContext.request.contextPath}/login/signinAdmin",
										cache:false,
										data:{userAccount:$("#userAccount").val(),password:$("#userpass").val()},
										success:function(data){
											if(data.result==1){
												window.location.href="${pageContext.request.contextPath}/admin/console123";
											}else{
												alert(data.msg);
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