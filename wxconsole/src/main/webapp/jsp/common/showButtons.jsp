<%@ page language="java" pageEncoding="UTF-8"%>
<%@page
	import="org.springframework.security.core.context.SecurityContextImpl,
org.springframework.security.core.Authentication,
com.cf.common.base.CustomUser"%>
<%
	SecurityContextImpl securityContextImpl = (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
	CustomUser customUser = (CustomUser)securityContextImpl.getAuthentication().getPrincipal();
	String showButtons = customUser.getButtons();
%>
<script type="text/javascript">
	var showButtons = '<%=showButtons%>';
</script>