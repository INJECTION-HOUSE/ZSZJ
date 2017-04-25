<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
   <meta name="viewport" content="width=device-width,height=device-height, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
   <script type="text/javascript">
		function loadPage(e, src){
    		$("#iframeTouchDianCai").attr("src", src);
			$($(e)).addClass("active2").siblings().removeClass("active2");
			var outboxtxt = $('.content_left_bottom .active2').text();
			$('.small_title').html(outboxtxt);
		}
		$(function(){
		     var fHeight=$('.index_footer').height();
			 var iHeight=$(window).height();
			 var offsetTop=$('.index_footer').offset().top;
			 if(offsetTop<(iHeight-fHeight)){
			      $('.index_footer').css('position','absolute');
				  $('.index_footer').css('bottom',0);
			 };
			 
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
			 
			$.ajax({
					url:"${pageContext.request.contextPath}/personCenter/avatar",
					cache:false,
					success:function(data){
						if(data.result==1){
							$("#avatarImgId").attr("src","${pageContext.request.contextPath}/upload/"+data.avatarPath);
						}else{
							alert(data.result);
						}
					}
			});
			
			$.ajax({
				url:"${pageContext.request.contextPath}/personCenter/queryRole",
				cache:false,
				success:function(data){
					if(data.result==1){
						$("#repairtaskId").hide();
						if(data.role == 'unknown') {
							$("#applyCertificationBtn").val("去认证");
							$("#applyCertificationBtn").bind("click", function () {
				            	console.log("go personal or enterpriser certification...");
				            	window.location.href="${pageContext.request.contextPath}/personCenter/goCertification"
				         	});
						}
						if(data.role == 'processing') {
							$("#applyCertificationBtn").val("认证处理中");
						}
						if(data.role == 'member') {
							$("#applyCertificationBtn").val("个人会员");
							$("#pubTask").show();
						}
						if(data.role == 'enterprise') {
							$("#applyCertificationBtn").val("企业会员");
							$("#repairtaskId").show();
						}
					}else{
						alert(data.result);
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
							    <a href="#" onclick="signout()" class="free_register">[退出]</a>
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
		     <div class="info">当前所在位置：<a class="small_title">收益记录</a></div>
			 <div class="content_all">
			       <div class="content_left">
					   <div class="content_left_top">
							<div class="content_left_pic">
								 <img id="avatarImgId" src="${pageContext.request.contextPath}/static/images/profile.png" />
							</div>
							<div class="content_left_pic_info">
								 <p class="title">${nickname}</p>
								 <p class="integral">积分 : <span class="num">${myscore}</span></p>
								 <p class="earningsall">收益总额：<span class="num">${myincome}元</span></p>
							</div>
							<div class="clear"></div>
							<div class="content_left_btn">
								 <input type="button" value="去认证" id="applyCertificationBtn"/>
							</div>
					   </div>
					   <div class="content_left_middle"></div>
					   <div class="content_left_bottom">
							<a onclick="loadPage(this, '${pageContext.request.contextPath}/personCenter/incomerecords')" class="active2">收益记录</a>
							<a onclick="loadPage(this, '${pageContext.request.contextPath}/personCenter/orderlist')">维修订单</a>
							<a onclick="loadPage(this, '${pageContext.request.contextPath}/personCenter/bidlist')">投标记录</a>
							<a id="repairtaskId" onclick="loadPage(this, '${pageContext.request.contextPath}/personCenter/tasklist')">维修任务</a>
							<a onclick="loadPage(this, '${pageContext.request.contextPath}/personCenter/myskillset')">擅长技能</a>
							<a id="pubTask" style="display: none" onclick="javascript:alert('只有企业会员才可以发布任务，请认证为企业会员')">发布任务</a>
					   </div>
				 </div>
			 
				 <div class="content_right" id="content_right">				
                        <iframe id="iframeTouchDianCai"  name="frameTouchDianCai" src="${pageContext.request.contextPath}/personCenter/incomerecords" scrolling="no" ></iframe>
                        <div class="clear"></div>
				 </div>
				 <div class="clear"></div>
		    </div>
			<div class="clear"></div>
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
    
</body>
<script>
	  function reinitIframe(){
			var iframe = document.getElementById("iframeTouchDianCai");
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