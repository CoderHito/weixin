<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	String hostPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
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
		basePath :"<%=basePath%>",
		hostPath :"<%=hostPath%>"
	};
})();
</script>
<script type="text/javascript" src="ext4full/bootstrap.js"></script>
<script type="text/javascript" src="CFrame/common/CFControl.js"></script>
<script type="text/javascript" src="CFrame/common/CFControlWin.js"></script>
<script type="text/javascript" src="CFrame/common/CFCommonWin.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<!-- 
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
 -->
</head>


<body class="bg">
</body>
</html>
