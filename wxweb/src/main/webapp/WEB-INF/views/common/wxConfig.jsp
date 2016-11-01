<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String timestamp = request.getAttribute("timestamp").toString();
	String noncestr = request.getAttribute("noncestr").toString();
	String signature  = request.getAttribute("signature").toString();
	String appId = request.getAttribute("appId").toString();
%>
<%
	if(request.getScheme().equals("http")){
%>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<% 
	}else{
%>	
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>	
<%		
	}
%>

<script type="text/javascript">
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '<%=appId %>', // 必填，公众号的唯一标识
	    timestamp: <%=timestamp %>, // 必填，生成签名的时间戳
	    nonceStr: '<%=noncestr %>', // 必填，生成签名的随机串
	    signature: '<%=signature %>',// 必填，签名，见附录1
	    jsApiList: ["chooseImage","previewImage","uploadImage","downloadImage","openLocation",
	                "getLocation","scanQRCode"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	}); 
	// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	wx.error(function(res){
		if(res.errMsg != 'config:ok'){//TODO
			app.msgTip(res.errMsg);
		}
	});
</script>
