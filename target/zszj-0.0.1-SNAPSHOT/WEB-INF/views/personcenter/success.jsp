<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
</head>
<script>
	$(document).ready(function(){
		setTimeout(back2MemberCenter(),3000); 
		function back2MemberCenter() 
		{ 
			window.location.href = "${pageContext.request.contextPath}/personCenter/home";
		} 
	}); 	
</script>
<body>
    <div class="main">
		<div class="order">
			 <div class="order_right">
				  <div class="order_title">
					   <h3>${successInfo},3秒自动返回个人中心</h3>
				  </div>
				  <div class="clear"></div>
			</div>
		</div>
	</div>
</body>
</html>