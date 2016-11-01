<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/wxConfig.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>HomePage</title>
	<link href="${pageContext.request.contextPath}/resource/css/weui.css" rel="stylesheet" type="text/css" />
 	<script src="${pageContext.request.contextPath}/resource/js/jquery-2.1.0.min.js" type="text/javascript"></script>
	
	<script language="JavaScript">
	 function check_login(){
		 if(document.form1.qq.value=='')/*document.表单名.文本域名.value==''"*/
		 {
		 alert("用户名不能为空！");
		 return false;
		 }
		 
		 var user = document.getElementById('qq').value;
		 var patten = /^\d{1,9}$/;
		 if(!patten.test(user))
		 {
		 alert('请输1-9个数字！');
		 return false;
		 }else{
			 alert('测试：输入正确！');
		 }
		
	 }
	</script>
	</head>
<body>
  <form action="" method="post" name="form1" target="_blank" id="form1">
	<div class="weui_cells_title"><b>主页</b></div>
	<div class="weui_cells weui_cells_form">
		<div class="weui_cell">
			<div  class="weui_cell_hd">
				<label  class="weui_label">账户名</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input id="qq" name="qq" class="weui_input" type="text"
				placeholder="请输入账号" />
			</div>
			
		</div>
			<a   id="btn_submit" href="javascript:;"
		   class="weui_btn weui_btn_primary" onclick="check_login()">查询用户信息</a>
	
	</div>
	
	
	
	
	
	
	
  </form>

</body>
</html>