<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function validate_email(field, alerttxt) {
		with (field) {
			apos = value.indexOf("@")
			dotpos = value.lastIndexOf(".")
			if (apos < 1 || dotpos - apos < 2) {
				alert(alerttxt);
				return false
			} else {
				return true
			}
		}
	}

	function validate_form(thisform) {
		with (thisform) {
			if (validate_email(email, "Not a valid e-mail address!") == false) {
				email.focus();
				return false
			}
		}
	}
</script>
</head>
<body>
	<form action="submitpage.htm" onsubmit="return validate_form(this);"
		method="post">
		Email: <input type="text" name="email" size="30"> <input
			type="submit" value="Submit">
	</form>
</body>
</html>