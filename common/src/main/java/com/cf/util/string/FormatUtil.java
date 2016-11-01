package com.cf.util.string;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public final class FormatUtil {

/*	public static String createMemo(String title, String body, String amt) {
		StringBuffer result = new StringBuffer();
		result.append(title);
		result.append(":");
		result.append(body);
		result.append("，");
		result.append(amt);
		result.append(MemoNameConstant.PUBLIC_UNIT);
		return result.toString();
	}*/

	/**
	 * 将字符串转换为日期格式，格式化后再转换位字符串输出
	 * 
	 * @throws ParseException
	 * 
	 * */
	public static String formatDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(time);
			time = sdf1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

	public static String formatDateToString(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss",
				Locale.ENGLISH);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date;
		try {
			date = sf.parse(time);
			time = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 保留小数点后len位返回 为空时返回0.00;
	 * 
	 * */
	public static String formatAmt(Object obj, int len) {
		if (obj == null) {
			return "0.00";
		}
		String amt = "";
		// if(obj instanceof BigDecimal){
		amt = String.valueOf(obj);
		// }
		if (amt == null || amt.length() < 1) {
			return "0.00";
		}
		NumberFormat formater = null;
		double num = Double.parseDouble(amt);
		if (len == 0) {
			formater = new DecimalFormat("###,###");
			formater.setRoundingMode(RoundingMode.FLOOR);
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
			formater.setRoundingMode(RoundingMode.FLOOR);
		}
		amt = formater.format(num);
		if (amt.indexOf(".") == -1) {
			amt += ".00";
		}
		return amt;
	}

	/**
	 * 保留小数点后len位返回 为空时返回"";
	 * 
	 * */
	public static String formatNamt(Object obj, int len) {
		if (obj == null) {
			return "";
		}
		String amt = "";
		// if(obj instanceof BigDecimal){
		amt = String.valueOf(obj);
		// }
		if (amt == null || amt.length() < 1) {
			return "";
		}
		NumberFormat formater = null;
		double num = Double.parseDouble(amt);
		if (len == 0) {
			formater = new DecimalFormat("###,###");
			formater.setRoundingMode(RoundingMode.FLOOR);
		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
			formater.setRoundingMode(RoundingMode.FLOOR);
		}
		amt = formater.format(num);
		if (amt.indexOf(".") == -1) {
			amt += ".00";
		}
		return amt;
	}

	/**
	 * 设置头的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 16);// 定义字体
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
			// format.setBackground(Colour.YELLOW);//黄色背景
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置标题样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getTitle(int flag) {
		WritableFont font = new WritableFont(WritableFont.TIMES, 11);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			if (flag == 0) {
				format.setAlignment(jxl.format.Alignment.CENTRE);
			}
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置其他单元格样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getNormolCell() {// 12号字体,上下左右居中,带黑色边框
		WritableFont font = new WritableFont(WritableFont.TIMES, 11);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			// format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 设置转码，若为乱码转成汉字，若为汉字不转
	 * 
	 * @return
	 */
	public static String toUtf8(String str) {
		if (str == null)
			return null;
		String retStr = str;
		byte b[];
		try {
			b = str.getBytes("ISO8859-1");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63)
					break; // 1
				else if (b1 > 0)
					continue;// 2
				else if (b1 < 0) { // 不可能为0，0为字符串结束符
					// 小于0乱码
					retStr = new String(b, "utf-8");
					break;
				}
			}
		} catch (Exception e) {
			return retStr;
		}
		return retStr;
	}

	/**
	 * 用poi生成excel的样式设置 
	 * 设置头的样式
	 * @return
	 */
	public static HSSFCellStyle getHeader(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		// 创建字体样式
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short) 16);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("宋体");// 设置为宋体字
		style.setFont(font);
		// 设置对齐方式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return style;
	}

	/**
	 * 用poi生成excel的样式设置 
	 * 设置标题样式
	 * @return
	 */
	public static HSSFCellStyle getTitle(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		// 创建字体样式
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
		font.setFontName("宋体");// 设置为宋体字
		style.setFont(font);
		// 设置对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return style;
	}
   
	/**
	 * 用poi生成excel的样式设置 
	 * 设置标题样式
	 * @return
	 */
	public static HSSFCellStyle getNormolCell(HSSFWorkbook workBook) {
		HSSFCellStyle style = workBook.createCellStyle();
		// 创建字体样式
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		font.setFontName("宋体");// 设置为宋体字
		style.setFont(font);
		// 设置对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		return style;
	}
	
	
	
	
}