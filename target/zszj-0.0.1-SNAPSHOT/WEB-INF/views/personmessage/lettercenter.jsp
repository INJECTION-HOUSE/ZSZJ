<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
   <meta name="description" content="">
   <meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
   <script type="text/javascript">
		$(function(){
		    $('.notice_title span').click(function(){
			     var index=$(this).index();
			     $(this).addClass('active3').siblings().removeClass('active3');
				 $('.task .task_list').eq(index).show().siblings().hide();
			});
		    
			 $.ajax({
					url:"${pageContext.request.contextPath}/messages/countInBox",
					cache:false,
					data:{userAccount:"xxxxxxxx",password:"xxxxxxxx"},
					success:function(data){
						if(data.result==1){
							$("#mymsg_txt").html("消息(" + data.count +")");
						}else{
							alert(data.count);
						}
					}
			});

		});
		
		function loadPage(e, src){
    		$("#iframe_message").attr("src", src);
			$($(e)).addClass("active3").siblings().removeClass("active3");    		
		}
		
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
<body style="background:#EFEFEF;;">
    <div class="notice_main">
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
								    <li><a id="mymsg_txt" href="${pageContext.request.contextPath}/mymessage/home">消息(0)</a></li>
								    <li><a href="${pageContext.request.contextPath}/profile/home">个人中心</a></li>
								    <li>设置</li>
								    <li>帮助</li>
							  </ul>
					    </div>
				   </div>
			  </div>
		      <div class="wrap">
			       <div class="task_logo float">
						<a href="${pageContext.request.contextPath}/index/main"><img src="${pageContext.request.contextPath}/static/images/logo.png" /></a>
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
		     <div class="info">当前所在位置：<a>消息 / </a></div>
			 <div class="notice">
			      <div class="notice_title">
				        <span class="active3" onclick="loadPage(this, '${pageContext.request.contextPath}/mymessage/sysnotices')">通知</span>
						<span onclick="loadPage(this, '${pageContext.request.contextPath}/mymessage/privacyletters')">私信</span>
						<span onclick="loadPage(this, '${pageContext.request.contextPath}/mymessage/activities')">活动列表</span>
				  </div>
				  <div class="notice_content">
		                 <iframe id="iframe_message"  name="iframe_message" src="${pageContext.request.contextPath}/mymessage/sysnotices"></iframe>
				  </div>
			 </div>
		</div>
        <div class="index_footer position" id="index_footer">
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
	<div class="tanchu" style="width:400px;">
	     <h3>发私信给用户</h3>
		 <div class="tanchu_content">
		 <form id="fm" method="post" class="form-horizontal" role="form">
			  <div class="input_box1">
			       <span>用户</span>
				   <span>
				        <input id="usernameId" name="receiver" type="text" placeholder="输入用户名" style="width:400px;"></input>
				   </span>
			  </div>
			  <div class="input_box2">
			       <span>主题</span>
				   <span>
				        <input id="messagetitleId" name="title" type="text" placeholder="输入主题" style="width:400px;"></input>
				   </span>
			  </div>
			  <div class="text_box">
			       <span>内容</span>
				   <span>
				        <textarea id="messagecontentId" name="content" style="width:400px;"></textarea>
				   </span>
			  </div>
		</form>
		 </div>
		 <div class="tanchu_btn">
			  <input id="sendPrivacymsgBtn" type="button" value="发送" class="toub"/>
		      <input type="button" value="取消" class="cancel" />
		 </div>
	</div>
</body>
<script>
     function reinitIframe(){
				var iframe = document.getElementById("iframe_message");
				try{
				var bHeight = iframe.contentWindow.document.body.scrollHeight;
				var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				var height = Math.max(bHeight, dHeight);
				iframe.height =  height;
				}catch (ex){}
	 }
	 window.setInterval("reinitIframe()", 200);
</script>
</html>