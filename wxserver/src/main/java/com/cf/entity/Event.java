package com.cf.entity;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息事件监听数据封装公共类
 * 
 * @author Mall
 * @date 2016年1月21日 下午1:25:25
 */
public class Event {
	
	public static <T> String textMessageToXml(T resp) {
		xstream.alias("xml", resp.getClass());
		return xstream.toXML(resp);
	}

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write("");
					}
				}
			};
		}
	});
}
