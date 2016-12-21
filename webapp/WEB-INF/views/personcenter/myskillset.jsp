<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
   <meta name="description" content="">
   <meta name="keywords" content="">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/pc/zszjmain.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
</head>
<body>
		<div class="skills">
			<div class="skills_title">
				<h3>擅长技能</h3>
					<input type="button" value="编辑技能" class="records_btn" onclick="window.open('${pageContext.request.contextPath}/profile/home');" />
		    </div>
			<div class="clear"></div>
		    <div class="skills_content">
				<p style="padding: 10px;">${skillDesc}</p>
			</div>
		</div>
</body>
</html>