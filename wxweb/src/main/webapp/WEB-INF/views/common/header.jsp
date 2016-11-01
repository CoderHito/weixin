<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" href="../resource/plugs/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="../resource/css/app.css">
	<link rel="stylesheet" href="../resource/css/weui.css">
	<script type="text/javascript" src="../resource/plugs/zepto.min.js"></script>
	<script type="text/javascript" src="../resource/plugs/iscroll/iscroll.js"></script>
	<script type="text/javascript" src="../resource/js/app.js"></script>
	<script type="text/javascript" src="../resource/js/jquery-2.1.0.min.js"></script>
	
	<title>酷服微信</title>
	<script type="text/javascript">
		(function() {
			$.extend(app,{
				basePath :"<%=basePath%>"
			});
		})();
	</script>
	<script type="text/javascript">
		
	</script>
</head>
<body>