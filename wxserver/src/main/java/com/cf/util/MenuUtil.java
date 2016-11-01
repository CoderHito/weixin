package com.cf.util;

import com.cf.biz.domain.Button;
import com.cf.biz.domain.ClickButton;
import com.cf.biz.domain.CreateMenu;
import com.cf.biz.domain.ViewButton;
import com.google.gson.Gson;

public class MenuUtil {

	// private static String domain =
	// "http://rszhsz.imwork.net/cfweixin";//URL服务器地址（需修改）
	private static String domain = "http://1589834b8f.iok.la";

	public static void main(String[] args) {
		System.out.println(createMenu());
	}

	public static String createMenu() {
		// //卡信息查询
		// ViewButton cardQryVb = new ViewButton();
		// cardQryVb.setName("test");
		// cardQryVb.setType("view");
		// cardQryVb.setUrl(domain+"/wxweb/demo/test");
		// //交易明细查询
		// ViewButton transDetailQry = new ViewButton();
		// transDetailQry.setName("find");
		// transDetailQry.setType("view");
		// transDetailQry.setUrl(domain+"/wxweb/demo/find");

		// //=============================================================
		//
		// ViewButton vbtn1 = new ViewButton();
		// vbtn1.setName("百度搜索");
		// vbtn1.setType("view");
		// vbtn1.setUrl("http://www.baidu.com");

		//
		ClickButton cbtn1 = new ClickButton();
		cbtn1.setName("服务咨询");
		cbtn1.setType("click");
		cbtn1.setKey("click_key1");
		//
		ClickButton ebtn1 = new ClickButton();
		ebtn1.setName("扫一扫");
		ebtn1.setType("scancode_push");
		ebtn1.setKey("click_key2");
		//
		// ClickButton fbtn1 = new ClickButton();
		// fbtn1.setName("发图片");
		// fbtn1.setType("pic_sysphoto");
		// fbtn1.setKey("click_key3");
		//
		// //复合按钮
		// ComplexButton complexButton2 = new ComplexButton();
		// complexButton2.setName("服务特色");
		// complexButton2.setSub_button(new Button[]{vbtn1,cbtn1,ebtn1,fbtn1});
		//
		// //=============================================================
		//
		ClickButton vb3 = new ClickButton();
		vb3.setName("公司地址");
		vb3.setType("click");
		vb3.setKey("click_key4");
		//
		// ClickButton vb4 = new ClickButton();
		// vb4.setName("联系我们");
		// vb4.setType("click");
		// vb4.setKey("click_key5");
		//
		// ViewButton vb5 = new ViewButton();
		// vb5.setName("问卷调查");
		// vb5.setType("view");
		// vb5.setUrl("http://www.sojump.com/jq/7068149.aspx");
		//
		// ComplexButton complexButton3 = new ComplexButton();
		// complexButton3.setName("关于我们");
		// complexButton3.setSub_button(new Button[]{vb3,vb4,vb5});

		// =============================================================

		CreateMenu menu = new CreateMenu();
		menu.setButton(new Button[] { cbtn1, ebtn1, vb3 });

		Gson gson = new Gson();
		return gson.toJson(menu);
	}

}
