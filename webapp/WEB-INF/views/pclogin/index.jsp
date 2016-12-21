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
			 var moudelHeight=424;
		     var halfHeight=(iHeight-430)/2;
			 $('.logo').css('margin-bottom',halfHeight+'px');
			 
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
   </script> 
</head>
<body style="background:#F7F7F7;">
    <div class="main">
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
						    <li><a id="mymsg_txt" href="${pageContext.request.contextPath}/mymessage/home">消息(0)</a></li>
						    <li><a href="${pageContext.request.contextPath}/profile/home">个人中心</a></li>
						    <li>设置</li>
						    <li>帮助</li>
						</ul>
				  </div>
			 </div>
		</div>
		<div class="middle">
		     <div class="logo">
			      <a><img src="${pageContext.request.contextPath}/static/images/logo.png" /></a>
			 </div>
			 <ul>
			      <li class="moudel c_right color4"><div class="menus list4"><a href="${pageContext.request.contextPath}/personCenter/home">会员管理</a></div></li>
			      <li class="moudel color1"><div class="menus list1"><a href="${pageContext.request.contextPath}/task/goTaskList">维修任务</a></div></li>
				  <li class="moudel color3"><div class="menus list2" style="color:#3e3a39;">商城</div></li>
				  <li class="moudel color2"><div class="menus list3" style="color:#fff;">论坛</div></li>
			 </ul>
			 <div class="clear"></div>
		</div>
		<div class="index_footer position">
		     <div class="footer_nav">
			     <a href="http://www.zhusuhome.com/zszj/index/main">首页</a><span>|</span>
				 <a>关于我们</a><span>|</span>
				 <a href="">维修任务</a><span>|</span>
				 <a>个人中心</a><span>|</span>
				 <a>联系我们</a>
			 </div>
			 <p class="info">宁波市三体网络科技有限公司<span><a href="http://www.miitbeian.gov.cn/">浙ICP备15044527号-1</a></span></p>
		</div>
	</div>
    
</body>


</html>