<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>卡管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" type="text/css" href="css/cframe.css">	
<script type="text/javascript">
var App = (function() {
	return {
		basePath :"<%=basePath%>"
}
})();
</script>
<script type="text/javascript" src="ext4full/bootstrap.js"></script>
<script type="text/javascript" src="js/3des.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript">
if(top!=self){
if(top.location != self.location)
	top.location=self.location;
}
</script>
</head>


<body class="bg">
<div id="render-div" class="x-body x-gecko x-theme-access login1" align="center" vertical-align="center"></div>
</body>
</html>
