<%@ page pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8"> 
</head>
<body>
	<h1>我的第一个JSP页面</h1>
	<p>Hello world, <%=pageContext.getSession().getId()%></p>
</body>
</html>