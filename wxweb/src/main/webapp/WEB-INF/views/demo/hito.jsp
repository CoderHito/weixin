<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resource/css/hito.css">
	<script type="text/javascript">
		function testOnclick(item) {
			alert (item);
		}
	</script>
<title>hito</title>
</head>
<body>
	<div class="home_slide">
		<img class="img"
			src="${pageContext.request.contextPath}/resource/image/1.jpg">
	</div>
	<br>
	<br>
	<div class="home_list">
		<div class="item">
			<button  class="item_btn" onclick="testOnclick('您点击了微博')">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/001.png">
				</div>
			</button>
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/002.png">
				</div>
			</button>
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/003.png">
				</div>
			</button>
		</div>
		<div class="item">
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/004.png">
				</div>
			</button>
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/005.png">
				</div>
			</button>
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/006.png">
				</div>
			</button>
		</div>
		<div class="item">
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/007.png">
				</div>
			</button>
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/008.png">
				</div>
			</button>
			<button class="item_btn">
				<div class="btn">
					<img class="btn_img" alt="图片"
						src="${pageContext.request.contextPath}/resource/image/009.png">
				</div>
			</button>
		</div>
	</div>
</body>
</html>