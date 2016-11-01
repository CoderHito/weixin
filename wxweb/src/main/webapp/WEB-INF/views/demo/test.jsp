<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp"%>
<%@ include file="../common/wxConfig.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HomePage</title>
<link href="${pageContext.request.contextPath}/resource/css/weui.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-2.1.0.min.js"
	type="text/javascript"></script>

<script language="JavaScript">
	function check() {
		if (document.form1.phone.value == '')/*document.表单名.文本域名.value==''"*/
		{
			alert("手机号不能为空！");
			return false;
		}

		var user = document.getElementById('phone').value;
		var patten = /^\d{11}$/;
		if (!patten.test(user)) {
			alert('请输入11位正确的手机号码！');
			return false;
		} else {
			alert('测试：输入正确！');
		}

	}
</script>

<script type="text/javascript">
	wx.ready(function() {

		document.getElementById("btn_sao").onclick = function() {
			wx.scanQRCode({
				needResult : 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
				success : function(res) {
					var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果

				}
			});
		}
		document.getElementById("btn_choose_location").onclick = function() {
			/* wx.getLocation({
			type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				var speed = res.speed; // 速度，以米/每秒计
				var accuracy = res.accuracy; // 位置精度
			}
			}); */
			wx.openLocation({
				latitude : 31.254, // 纬度，浮点数，范围为90 ~ -90
				longitude : 121.583, // 经度，浮点数，范围为180 ~ -180。
				name : '', // 位置名
				address : '', // 地址详情说明
				scale : 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
				infoUrl : '' // 在查看位置界面底部显示的超链接,可点击跳转
			});
		}

	});
</script>


</head>
<body>
	<form action="<%=basePath %>" method="post" name="form1" target="_blank" id="form1">
		<div class="weui_cells_title">
			<b>性别</b>
		</div>
		<div class="weui_cells weui_cells_radio">
			<label class="weui_cell weui_check_label" for="x11">
				<div class="weui_cell_bd weui_cell_primary">
					<p>男</p>
				</div>
				<div class="weui_cell_ft">
					<input type="radio" class="weui_check" name="radio1" id="x11"
						checked="checked"> <span class="weui_icon_checked"></span>
				</div>
			</label> <label class="weui_cell weui_check_label" for="x12">
				<div class="weui_cell_bd weui_cell_primary">
					<p>女</p>
				</div>
				<div class="weui_cell_ft">
					<input type="radio" name="radio1" class="weui_check" id="x12">
					<span class="weui_icon_checked"></span>
				</div>
			</label>
		</div>

		<div class="weui_cells_title">
			<b>爱好</b>
		</div>
		<div class="weui_cells weui_cells_checkbox">
			<label class="weui_cell weui_check_label" for="s11">
				<div class="weui_cell_hd">
					<input type="checkbox" class="weui_check" name="checkbox1" id="s11"
						checked="checked"> <i class="weui_icon_checked"></i>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<p>运动</p>
				</div>
			</label> <label class="weui_cell weui_check_label" for="s12">
				<div class="weui_cell_hd">
					<input type="checkbox" name="checkbox1" class="weui_check" id="s12">
					<i class="weui_icon_checked"></i>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<p>音乐</p>
				</div>
			</label> <label class="weui_cell weui_check_label" for="s13">
				<div class="weui_cell_hd">
					<input type="checkbox" name="checkbox1" class="weui_check" id="s13">
					<i class="weui_icon_checked"></i>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<p>读书</p>
				</div>
			</label> <label class="weui_cell weui_check_label" for="s14">
				<div class="weui_cell_hd">
					<input type="checkbox" name="checkbox1" class="weui_check" id="s14">
					<i class="weui_icon_checked"></i>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<p>游戏</p>
				</div>
			</label> <label class="weui_cell weui_check_label" for="s15">
				<div class="weui_cell_hd">
					<input type="checkbox" name="checkbox1" class="weui_check" id="s15">
					<i class="weui_icon_checked"></i>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<p>其他</p>
				</div>
			</label>
		</div>

		<div class="weui_cells_title">
			<b>电话号码</b>
		</div>
		<div class="weui_cells">
			<div class="weui_cell weui_cell_select weui_select_before">
				<div class="weui_cell_hd">
					<select class="weui_select" name="select2">
						<option value="1">+86</option>
						<option value="2">+80</option>
						<option value="3">+84</option>
						<option value="4">+87</option>
						<option value="4">+91</option>
					</select>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input id="phone" name="phone" class="weui_input" type="number"
						pattern="[0-9]*" placeholder="请输入号码" />
				</div>
			</div>
		</div>

		<div class="weui_cells_title">
			<b>开关1</b>
		</div>
		<div class="weui_cells weui_cells_form">
			<div class="weui_cell weui_cell_switch">
				<div class="weui_cell_hd weui_cell_primary">标题文字1</div>
				<div class="weui_cell_ft">
					<input class="weui_switch" type="checkbox" />
				</div>
			</div>
		</div>


		<div class="weui_cells_title">
			<b>上传</b>
		</div>
		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
				<div class="weui_cell_bd weui_cell_primary">
					<div class="weui_uploader">
						<div class="weui_uploader_hd weui_cell">
							<div class="weui_cell_bd weui_cell_primary">图片上传</div>
							<div class="weui_cell_ft">0/2</div>
						</div>
						<div class="weui_uploader_bd">
							<ul class="weui_uploader_files">
								<li class="weui_uploader_file"
									style="background-image: url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
								<li class="weui_uploader_file"
									style="background-image: url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
								<li class="weui_uploader_file"
									style="background-image: url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
								<li class="weui_uploader_file weui_uploader_status"
									style="background-image: url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)">
									<div class="weui_uploader_status_content">
										<i class="weui_icon_warn"></i>
									</div>
								</li>
								<li class="weui_uploader_file weui_uploader_status"
									style="background-image: url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)">
									<div class="weui_uploader_status_content">50%</div>
								</li>
							</ul>
							<div class="weui_uploader_input_wrp">
								<input class="weui_uploader_input" type="file"
									accept="image/jpg,image/jpeg,image/png,image/gif" multiple />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label for="" class="weui_label">时间</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input class="weui_input" type="datetime-local" value=""
					placeholder="" />
			</div>
		</div>

		<div class="weui_cells_title">
			<b>选择</b>
		</div>
		<div class="weui_cells">
			<div class="weui_cell weui_cell_select">
				<div class="weui_cell_bd weui_cell_primary">
					<select class="weui_select" name="select1">
						<option selected="" value="1">微信号</option>
						<option value="2">QQ号</option>
						<option value="3">Email</option>
					</select>
				</div>
			</div>
			<div class="weui_cell weui_cell_select weui_select_after">
				<div class="weui_cell_hd">国家/地区</div>
				<div class="weui_cell_bd weui_cell_primary">
					<select class="weui_select" name="select2">
						<option value="1">中国</option>
						<option value="2">美国</option>
						<option value="3">英国</option>
					</select>
				</div>
			</div>
		</div>

		<div class="weui_cells_title">
			<b>文本框</b>
		</div>
		<div>
			<textarea rows="10px" cols="50px"></textarea>
		</div>
		<input type="submit" value="提交" id="btn_submit" href="javascript:;"
			class="weui_btn weui_btn_primary" />

	</form>
	</div>
	<!-- 	<a id="btn_submit" href="javascript:;"
		class="weui_btn weui_btn_primary" onclick="check()">提交</a> -->
	<a id="btn_sao" href="javascript:;" class="weui_btn weui_btn_primary">扫一扫</a>
	<a id="btn_choose_location" href="javascript:;"
		class="weui_btn weui_btn_primary">地理位置</a>
	</div>

</body>
</html>