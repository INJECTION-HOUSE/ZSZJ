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
			    // ??????????????????????????????  
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
									<span>??????????????????????????????????????????????????????</span> -->
								</h1>
							</div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main" id="loginMain">
											<h1 class="header">
											           ????????????
											</h1>
											<font style="color: red;"></font>
											<div class="space-6"></div>
											<form class="form-horizontal" method="post" id="loginForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="userpsw" class="form-control" placeholder="?????????" name="userpass" />
														</span>
													</label>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="newuserpsw" class="form-control" placeholder="?????????????" name="newuserpass" />
														</span>
													</label>
													<label class="block clearfix">
														<span class="block login-pass">
															<input type="password" id="confirmuserpsw" class="form-control" placeholder="????????????" name="confirmUserPass" />
														</span>
													</label>
													<div class="space"></div>
													<div class="clearfix">
													    <div class="row">
														    <div class="col-xs-6">
																<button class="btn btn-block btn-table" onclick="passwordUpdate()" type="button">
																	??????
																</button>
															</div>
															<div class="col-xs-6">
																<button class="btn btn-block btn-table" type="reset">
																	??????
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
									//??????
									if($("#userpsw").val() =="" || $("#userpsw").val()==null){
										alert("?????????????????????");
										return;
									}
									if(($("#newuserpsw").val() =="" || $("#newuserpsw").val()==null)){
										alert("?????????????????????");
										return;
									}
									if(($("#confirmuserpsw").val() =="" || $("#confirmuserpsw").val()==null)){
										alert("????????????????????????");
										return;
									}

									//??????
									if($("#newuserpsw").val().length<8){
										alert("???????????????????????????8???");
										return;
									}
									
									var checkNum = /^[A-Za-z0-9]+$/;
									if(!checkNum.test($("#newuserpsw").val())){
										alert("????????????????????????????????????(??????????????????)");
										return;
									}
									
									//??????
									if($("#newuserpsw").val() != $("#confirmuserpsw").val()){
										alert("?????????????????????????????????,????????????????????????");
										return;
									}

									//????????????
									$.ajax({
										url:"${pageContext.request.contextPath}/login/passwordUpdate",
										cache:false,
										data:{password:$("#userpsw").val(),newpassword:$("#newuserpsw").val()},
										success:function(data){
											if(data.result==1){
												alert("??????????????????");
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