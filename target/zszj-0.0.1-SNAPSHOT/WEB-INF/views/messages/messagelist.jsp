<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>代码表管理</title>
<!-- 加载jquery-easyui -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
<script>
	
</script>
</head>
<body>
	<div class="easyui-tabs" style="width: auto; height: auto;">
		<div title="系统通知" style="padding: 10px;">
			<iframe scrolling="auto" frameborder="0"  src="${pageContext.request.contextPath}/messages/systemNotify" style="width:100%;height:80%;"></iframe>
		</div>
		<div title="私信" style="padding: 10px;">
			<iframe scrolling="auto" frameborder="0"  src="${pageContext.request.contextPath}/messages/privacy" style="width:100%;height:80%;"></iframe>
		</div>
	</div>
</body>
</html>
