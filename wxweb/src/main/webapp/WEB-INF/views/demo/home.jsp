<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link href="${pageContext.request.contextPath}/resource/css/weui.css" rel="stylesheet" type="text/css" />
 <script src="${pageContext.request.contextPath}/resource/js/jquery-2.1.0.min.js" type="text/javascript"></script>
<title>Insert title here</title>
</head>
<body>
<div class="box" style="padding: 10px;">
            <h1 style="text-align: center;color: red;">Button</h1>
            <a href="javascript:;" class="weui_btn weui_btn_primary">按钮1</a>
            <a href="javascript:;" class="weui_btn weui_btn_disabled weui_btn_primary">按钮2</a>
            <a href="javascript:;" class="weui_btn weui_btn_warn">确认3</a>
            <a href="javascript:;" class="weui_btn weui_btn_disabled weui_btn_warn">确认4</a>
            <a href="javascript:;" class="weui_btn weui_btn_default">按钮5</a>
            <a href="javascript:;" class="weui_btn weui_btn_disabled weui_btn_default">按钮6</a>
            <a href="javascript:;" class="weui_btn weui_btn_plain_default">按钮7</a>
            <a href="javascript:;" class="weui_btn weui_btn_plain_primary">按钮8</a>
            <a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_primary">按钮9</a>
            <a href="javascript:;" class="weui_btn weui_btn_mini weui_btn_default">按钮10</a>
            <h1 style="text-align: center;color: red;">Cell</h1>
            <div class="weui_cells_title">带说明的列表项</div>
            <div class="weui_cells">
                <div class="weui_cell">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>标题文字</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </div>
            </div>
            <div class="weui_cells_title">带图标、说明的列表项</div>
            <hr style="color: black;opacity: 0.5;" />
            <div class="weui_cells_title">带图标、说明的列表项</div>
            <div class="weui_cells">
                <div class="weui_cell">
                    <div class="weui_cell_hd">
                    <img src="${pageContext.request.contextPath}/resource/image/icon_nav_actionSheet.png"
                        alt="" style="width:20px;margin-right:5px;display:block"></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>标题文字</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd">
                    <img src="${pageContext.request.contextPath}/resource/image/icon_nav_actionSheet.png"
                        alt="" style="width:20px;margin-right:5px;display:block"></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>标题文字</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </div>
            </div>
            <div class="weui_cells_title">带跳转的列表项</div>
            <div class="weui_cells weui_cells_access">
                <a class="weui_cell" href="javascript:;">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">
                    </div>
                </a>
                <a class="weui_cell" href="javascript:;">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">
                    </div>
                </a>
            </div>
            <div class="weui_cells_title">带说明、跳转的列表项</div>
            <div class="weui_cells weui_cells_access">
                <a class="weui_cell" href="javascript:;">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </a>
                <a class="weui_cell" href="javascript:;">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </a>
            </div>
            <div class="weui_cells_title">带图标、说明、跳转的列表项</div>
            <div class="weui_cells weui_cells_access">
                <a class="weui_cell" href="javascript:;">
                    <div class="weui_cell_hd">
<img src="${pageContext.request.contextPath}/resource/image/icon_nav_actionSheet.png"
                        alt="" style="width:20px;margin-right:5px;display:block"></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </a>
                <a class="weui_cell" href="javascript:;">
                    <div class="weui_cell_hd">
                    <img src="${pageContext.request.contextPath}/resource/image/icon_nav_actionSheet.png"
                        alt="" style="width:20px;margin-right:5px;display:block"></div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">说明文字</div>
                </a>
            </div>
            <div class="weui_cells_title">单选列表项</div>
            <div class="weui_cells weui_cells_radio">
                <label class="weui_cell weui_check_label" for="x11">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">
                        <input type="radio" class="weui_check" name="radio1" id="x11">
                        <span class="weui_icon_checked"></span>
                    </div>
                </label>
                <label class="weui_cell weui_check_label" for="x12">
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>cell standard</p>
                    </div>
                    <div class="weui_cell_ft">
                        <input type="radio" name="radio1" class="weui_check" id="x12" checked="checked">
                        <span class="weui_icon_checked"></span>
                    </div>
                </label>
            </div>
            <div class="weui_cells_title">复选列表项</div>
            <div class="weui_cells weui_cells_checkbox">
                <label class="weui_cell weui_check_label" for="s11">
                    <div class="weui_cell_hd">
                        <input type="checkbox" class="weui_check" name="checkbox1" id="s11" checked="checked">
                        <i class="weui_icon_checked"></i>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>standard is dealt for u.1</p>
                    </div>
                </label>
                <label class="weui_cell weui_check_label" for="s12">
                    <div class="weui_cell_hd">
                        <input type="checkbox" name="checkbox1" class="weui_check" id="s12">
                        <i class="weui_icon_checked"></i>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>standard is dealicient for u.2</p>
                    </div>
                </label>
                <label class="weui_cell weui_check_label" for="s13">
                    <div class="weui_cell_hd">
                        <input type="checkbox" name="checkbox1" class="weui_check" id="s13">
                        <i class="weui_icon_checked"></i>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>standard is dealicient for u.3</p>
                    </div>
                </label>
                <label class="weui_cell weui_check_label" for="s14">
                    <div class="weui_cell_hd">
                        <input type="checkbox" name="checkbox1" class="weui_check" id="s14">
                        <i class="weui_icon_checked"></i>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <p>standard is dealicient for u.4</p>
                    </div>
                </label>
            </div>
            <div class="weui_cells_title">开关1</div>
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell weui_cell_switch">
                    <div class="weui_cell_hd weui_cell_primary">标题文字1</div>
                    <div class="weui_cell_ft">
                        <input class="weui_switch" type="checkbox" />
                    </div>
                </div>
            </div>
            <div class="weui_cells_title">开关2</div>
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell weui_cell_switch">
                    <div class="weui_cell_hd weui_cell_primary">标题文字2</div>
                    <div class="weui_cell_ft">
                        <input class="weui_switch" type="checkbox" />
                    </div>
                </div>
            </div>
            <div class="weui_cells_title">表单</div>
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell">
                    <div class="weui_cell_hd">
                        <label class="weui_label">qq</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" placeholder="请输入qq号" />
                    </div>
                </div>
                <div class="weui_cell weui_vcode">
                    <div class="weui_cell_hd">
                        <label class="weui_label">验证码</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="text" placeholder="请输入验证码(验证码不一定是纯数字，此处weUI有误:type=" text ")" />
                    </div>
                    <div class="weui_cell_ft">
                        <img src="${pageContext.request.contextPath}/resource/image/vcode.jpg" />
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd">
                        <label class="weui_label">银行卡</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" placeholder="请输入银行卡号" />
                    </div>
                </div>
                <div class="weui_cell weui_vcode weui_cell_warn">
                    <div class="weui_cell_hd">
                        <label class="weui_label">验证码</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" placeholder="请输入验证码" />
                    </div>
                    <div class="weui_cell_ft">
                        <i class="weui_icon_warn"></i>
                        <img src="${pageContext.request.contextPath}/resource/image/vcode.jpg" />
                    </div>
                </div>
            </div>
            <div class="weui_cells_tips">底部说明文字底部说明文字</div>
            <div class="weui_btn_area">
                <a class="weui_btn weui_btn_primary" href="javascript:" id="showTooltips">确定</a>
            </div>
            <div class="weui_cells_title">上传</div>
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
                                    <li class="weui_uploader_file" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
                                    <li class="weui_uploader_file" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
                                    <li class="weui_uploader_file" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)"></li>
                                    <li class="weui_uploader_file weui_uploader_status" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)">
                                        <div class="weui_uploader_status_content">
                                            <i class="weui_icon_warn"></i>
                                        </div>
                                    </li>
                                    <li class="weui_uploader_file weui_uploader_status" style="background-image:url(http://shp.qpic.cn/weixinsrc_pic/pScBR7sbqjOBJomcuvVJ6iacVrbMJaoJZkFUIq4nzQZUIqzTKziam7ibg/)">
                                        <div class="weui_uploader_status_content">50%</div>
                                    </li>
                                </ul>
                                <div class="weui_uploader_input_wrp">
                                    <input class="weui_uploader_input" type="file" accept="image/jpg,image/jpeg,image/png,image/gif" multiple />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="weui_cells_title">文本域</div>
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell">
                    <div class="weui_cell_bd weui_cell_primary">
                        <textarea class="weui_textarea" placeholder="请输入评论" rows="3"></textarea>
                        <div class="weui_textarea_counter"><span>0</span>/200</div>
                    </div>
                </div>
            </div>
            <div class="weui_toptips weui_warn js_tooltips">格式不对</div>
            <div class="weui_cells_title">表单报错</div>
            <div class="weui_cells weui_cells_form">
                <div class="weui_cell weui_cell_warn">
                    <div class="weui_cell_hd">
                        <label for="" class="weui_label">卡号</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" value="weui input error" placeholder="请输入卡号1" />
                    </div>
                    <div class="weui_cell_ft">
                        <i class="weui_icon_warn"></i>
                    </div>
                </div>
                <div class="weui_cells_title">没有添加weui_cell_warn类</div>
                <div class="weui_cell ">
                    <div class="weui_cell_hd">
                        <label for="" class="weui_label">卡号</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" value="weui input error" placeholder="请输入卡号2" />
                    </div>
                    <div class="weui_cell_ft">
                        <i class="weui_icon_warn"></i>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd">
                        <label for="" class="weui_label">日期</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="date" value="" />
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd">
                        <label for="" class="weui_label">时间</label>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="datetime-local" value="" placeholder="" />
                    </div>
                </div>
            </div>
            <div class="weui_cells_title">选择</div>
            <div class="weui_cells">
                <div class="weui_cell weui_cell_select weui_select_before">
                    <div class="weui_cell_hd">
                        <select class="weui_select" name="select2">
                            <option value="1">+86</option>
                            <option value="2">+80</option>
                            <option value="3">+84</option>
                            <option value="4">+87</option>
                            <option value="4">+你大爷</option>
                        </select>
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <input class="weui_input" type="number" pattern="[0-9]*" placeholder="请输入号码" />
                    </div>
                </div>
            </div>
            <div class="weui_cells_title">选择</div>
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
                    <div class="weui_cell_hd">
                        国家/地区
                    </div>
                    <div class="weui_cell_bd weui_cell_primary">
                        <select class="weui_select" name="select2">
                            <option value="1">中国</option>
                            <option value="2">美国</option>
                            <option value="3">英国</option>
                        </select>
                    </div>
                </div>
            </div>
            <h1 style="text-align: center;color: red;">Toast</h1>
            <a href="javascript:;" class="weui_btn weui_btn_primary" id="showToast">点击弹出Toast</a>
            <a href="javascript:;" class="weui_btn weui_btn_primary" id="showLoadingToast">点击弹出Loading Toast</a>
            <script>
                $(function() {
                    $('#showToast').click(function() {
                        /**
                         *1.delay函数是jquery 1.4.2新增的函数
                         *2.hide函数里必须放一个0,不然延时不起作用
                         */
                        $('#toast').show().delay(3000).hide(0);
                        //  $('#toast').fadeIn().delay(3000).fadeOut();
                    });
                    $('#showLoadingToast').click(function() {
                        $('#loadingToast').fadeIn().delay(3000).fadeOut();
                    });
                })
            </script>
            <!--BEGIN toast-->
            <div id="toast" style="display:none;">
                <div class="weui_mask_transparent"></div>
                <div class="weui_toast">
                    <i class="weui_icon_toast"></i>
                    <p class="weui_toast_content">已完成</p>
                </div>
            </div>
            <!--end toast-->
            <!-- loading toast -->
            <div id="loadingToast" class="weui_loading_toast" style="display:none;">
                <div class="weui_mask_transparent"></div>
                <div class="weui_toast">
                    <div class="weui_loading">
                        <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                        <div class="weui_loading_leaf weui_loading_leaf_11"></div>
                    </div>
                    <p class="weui_toast_content">数据加载中</p>
                </div>
            </div>
            <h1 style="text-align: center;color: red;">Dialog</h1>
            <div class="bd spacing">
                <a href="javascript:;" class="weui_btn weui_btn_primary" id="showDialog1">点击弹出Dialog样式一</a>
                <a href="javascript:;" class="weui_btn weui_btn_primary" id="showDialog2">点击弹出Dialog样式二</a>
            </div>
            <script>
                $(function() {
                    $('#showDialog1').click(function() {
                        $('#dialog1').show();
                    });
                    $('#quxioa').click(function() {
                        alert('你点击了取消!!!');
                        $('#dialog1').hide();
                    });
                    $('#ok').click(function() {
                        alert('你点击了确认！！！');
                    });
                    $('#showDialog2').click(function() {
                        $('#dialog2').show();
                    });
                    $('#ok1').click(function() {
                        alert('你点击了确认！！！');
                        $('#dialog2').hide();
                    });
                })
            </script>
            <!--BEGIN dialog1-->
            <div class="weui_dialog_confirm" id="dialog1" style="display: none;">
                <div class="weui_mask"></div>
                <div class="weui_dialog">
                    <div class="weui_dialog_hd"><strong class="weui_dialog_title">弹窗标题</strong></div>
                    <div class="weui_dialog_bd">自定义弹窗内容，居左对齐显示，告知需要确认的信息等</div>
                    <div class="weui_dialog_ft">
                        <a id="quxioa" href="javascript:;" class="weui_btn_dialog default">取消</a>
                        <a id="ok" href="javascript:;" class="weui_btn_dialog primary">确定</a>
                    </div>
                </div>
            </div>
            <!--END dialog1-->
            <!--BEGIN dialog2-->
            <div class="weui_dialog_alert" id="dialog2" style="display: none;">
                <div class="weui_mask"></div>
                <div class="weui_dialog">
                    <div class="weui_dialog_hd"><strong class="weui_dialog_title">弹窗标题</strong></div>
                    <div class="weui_dialog_bd">弹窗内容，告知当前页面信息等</div>
                    <div class="weui_dialog_ft">
                        <a id="ok1" href="javascript:;" class="weui_btn_dialog primary">确定</a>
                    </div>
                </div>
            </div>
            <h1 style="text-align: center;color: red;">Progress</h1>
            <div class="weui_progress">
                <div class="weui_progress_bar">
                    <div id="prs" class="weui_progress_inner_bar js_progress" style="width: 0%;"></div>
                </div>
                <a href="javascript:;" class="weui_progress_opr">
                    <i class="weui_icon_cancel"></i>
                </a>
            </div>
            <br>
            <div class="weui_progress">
                <div class="weui_progress_bar">
                    <div class="weui_progress_inner_bar js_progress" style="width: 50%;"></div>
                </div>
                <a href="javascript:;" class="weui_progress_opr">
                    <i class="weui_icon_cancel"></i>
                </a>
            </div>
            <br>
            <div class="weui_progress">
                <div class="weui_progress_bar">
                    <div class="weui_progress_inner_bar js_progress" style="width: 80%;"></div>
                </div>
                <a href="javascript:;" class="weui_progress_opr">
                    <i class="weui_icon_cancel"></i>
                </a>
            </div>
            <div class="weui_btn_area">
                <a href="javascript:;" class="weui_btn weui_btn_primary" id="btnStartProgress">上传</a>
            </div>
            <script>
                $(function() {
                    $('#btnStartProgress').click(function() {
                        $('#prs').animate({
                            width: "100%"
                        }, 2000, function() {
                            $(this).css('width', '0');
                        });
                    })
                })
            </script>
            <h1 style="text-align: center;color: red;">Message</h1>
            <div class="weui_msg" style="border: 1px solid red;">
                <div class="weui_icon_area"><i class="weui_icon_success weui_icon_msg"></i></div>
                <div class="weui_text_area">
                    <h2 class="weui_msg_title">操作成功</h2>
                    <p class="weui_msg_desc">内容详情，可根据实际需要安排</p>
                </div>
                <div class="weui_opr_area">
                    <p class="weui_btn_area">
                        <a href="javascript:;" class="weui_btn weui_btn_primary">确定</a>
                        <a href="javascript:;" class="weui_btn weui_btn_default">取消</a>
                    </p>
                </div>
                <div class="weui_extra_area">
                    <a href="">查看详情</a>
                </div>
            </div>
            <h1 style="text-align: center;color: red;">Article</h1>
            <article class="weui_article" style="border: 1px solid red;">
                <h1>大标题</h1>
                <section>
                    <h2 class="title">章标题</h2>
                    <section>
                        <h3>1.1 节标题</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute</p>
                    </section>
                    <section>
                        <h3>1.2 节标题</h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                            culpa qui officia deserunt mollit anim id est laborum.</p>
                    </section>
                </section>
            </article>
            ActionSheet扩展实例：
            <input type="text" id="showActionSheet1" />
            <script>
                $(function() {
                    $('#showActionSheet1').click(function() {
                        var mask = $('#mask');
                        var weuiActionsheet = $('#weui_actionsheet');
                        weuiActionsheet.addClass('weui_actionsheet_toggle');
                        mask.show().addClass('weui_fade_toggle').click(function() {
                            hideActionSheet(weuiActionsheet, mask);
                        });
                        $('#actionsheet_cancel').click(function() {
                            hideActionSheet(weuiActionsheet, mask);
                        });
                        weuiActionsheet.unbind('transitionend').unbind('webkitTransitionEnd');
                        function hideActionSheet(weuiActionsheet, mask) {
                            weuiActionsheet.removeClass('weui_actionsheet_toggle');
                            mask.removeClass('weui_fade_toggle');
                            weuiActionsheet.on('transitionend', function() {
                                mask.hide();
                            }).on('webkitTransitionEnd',
                                function() {
                                    mask.hide();
                                })
                        }
                    });
                    $('.weui_actionsheet_menu .weui_actionsheet_cell').click(function() {
                        //看下面的js
                        //                      alert($(this).text());
                        //                      $('#showActionSheet1').val($(this).text());
                        //                      $('#actionsheet_cancel').click();
                    })
                });
            </script>
            <h1 style="text-align: center;color: red;">ActionSheet</h1>
            <div class="bd spacing">
                <a href="javascript:;" class="weui_btn weui_btn_primary" id="showActionSheet">点击上拉ActionSheet</a>
            </div>
            <!--BEGIN actionSheet-->
            <div id="actionSheet_wrap">
                <div class="weui_mask_transition" id="mask"></div>
                <div class="weui_actionsheet" id="weui_actionsheet">
                    <div class="weui_actionsheet_menu">
                        <div class="weui_actionsheet_cell">示例菜单1</div>
                        <div class="weui_actionsheet_cell">示例菜单2</div>
                        <div class="weui_actionsheet_cell">示例菜单3</div>
                        <div class="weui_actionsheet_cell">示例菜单4</div>
                    </div>
                    <div class="weui_actionsheet_action">
                        <div class="weui_actionsheet_cell" id="actionsheet_cancel">取消</div>
                    </div>
                </div>
            </div>
            <script>
                $(function() {
                    $('#showActionSheet').click(function() {
                        var mask = $('#mask');
                        var weuiActionsheet = $('#weui_actionsheet');
                        weuiActionsheet.addClass('weui_actionsheet_toggle');
                        mask.show().addClass('weui_fade_toggle').click(function() {
                            hideActionSheet(weuiActionsheet, mask);
                        });
                        $('#actionsheet_cancel').click(function() {
                            hideActionSheet(weuiActionsheet, mask);
                        });
                        weuiActionsheet.unbind('transitionend').unbind('webkitTransitionEnd');
                        function hideActionSheet(weuiActionsheet, mask) {
                            weuiActionsheet.removeClass('weui_actionsheet_toggle');
                            mask.removeClass('weui_fade_toggle');
                            weuiActionsheet.on('transitionend', function() {
                                mask.hide();
                            }).on('webkitTransitionEnd',
                                function() {
                                    mask.hide();
                                })
                        }
                    });
                    $('.weui_actionsheet_menu .weui_actionsheet_cell').click(function() {
                        alert($(this).text());
                        $('#showActionSheet1').val($(this).text());
                        $('#actionsheet_cancel').click();
                    })
                });
            </script>
            <h1 style="text-align: center;color: red;">Icons</h1>
            <i class="weui_icon_msg weui_icon_success"></i>
            <i class="weui_icon_msg weui_icon_info"></i>
            <i class="weui_icon_msg weui_icon_warn"></i>
            <i class="weui_icon_msg weui_icon_waiting"></i>
            <i class="weui_icon_safe weui_icon_safe_success"></i>
            <i class="weui_icon_safe weui_icon_safe_warn"></i>
            <div class="icon_sp_area">
                <i class="weui_icon_success"></i>
                <i class="weui_icon_success_circle"></i>
                <i class="weui_icon_success_no_circle"></i>
                <i class="weui_icon_info"></i>
                <i class="weui_icon_waiting"></i>
                <i class="weui_icon_waiting_circle"></i>
                <i class="weui_icon_circle"></i>
                <i class="weui_icon_warn"></i>
                <i class="weui_icon_download"></i>
                <i class="weui_icon_info_circle"></i>
                <i class="weui_icon_cancel"></i>
                <i class="weui_icon_search"></i>
                <i class="weui_icon_clear"></i>
            </div>
        </div>
</body>
</html>