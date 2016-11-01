package com.cf.framework.utils.string;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * String Utility Class This is used to encode passwords programmatically
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @version $Revision: 1.1 $ $Date: 2010/10/29 03:03:06 $
 */
@SuppressWarnings({ "unused", "rawtypes", "unchecked","restriction" })
public final class StringUtil {

	private StringUtil() {

	}

	// ~ Static fields/initializers
	// =============================================

	/**
	 * The <code>Log</code> instance for this class.
	 */
	private static Log log = LogFactory.getLog(StringUtil.class);

	/**
	 * The empty String <code>""</code>.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	public static final String UNKNOW = "未知";

	/**
	 * Represents a failed index search.
	 * 
	 * @since 2.1
	 */
	public static final int INDEX_NOT_FOUND = -1;

	// ~ Methods
	// ================================================================

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			log.error("Exception: ", e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if (((int) encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * <p/>
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeString(String str) {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	public static String toPlainText(String htmlString) {
		if (htmlString == null || htmlString.trim().length() == 0)
			return "";
		String plainString = htmlString;
		plainString = plainString.replaceAll("<[^>]*>", "");
		plainString = plainString.replaceAll("&lt;", "<");
		plainString = plainString.replaceAll("&gt;", ">");
		plainString = plainString.replaceAll("&quot;", "\"");
		plainString = plainString.replaceAll("&nbsp;", " ");
		plainString = plainString.replaceAll("&amp;", "&");
		plainString = plainString.replaceAll("\\n|\\r", "");
		return plainString;
	}

	public static String htmlEncodeEntity(String encodedString) {
		if (encodedString == null || encodedString.trim().length() == 0)
			return "";
		String plainString = encodedString;

		plainString = plainString.replaceAll("&", "&amp;");
		plainString = plainString.replaceAll("<", "&lt;");
		plainString = plainString.replaceAll(">", "&gt;");
		plainString = plainString.replaceAll("\"", "&quot;");

		return plainString;
	}

	public static String htmEncode(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		}
		StringBuffer stringbuffer = new StringBuffer();
		int j = s.length();
		for (int i = 0; i < j; i++) {
			char c = s.charAt(i);
			switch (c) {
			case 60:
				stringbuffer.append("&lt;");
				break;
			case 62:
				stringbuffer.append("&gt;");
				break;
			case 38:
				stringbuffer.append("&amp;");
				break;
			case 34:
				stringbuffer.append("&quot;");
				break;
			case 169:
				stringbuffer.append("&copy;");
				break;
			case 174:
				stringbuffer.append("&reg;");
				break;
			case 165:
				stringbuffer.append("&yen;");
				break;
			case 8364:
				stringbuffer.append("&euro;");
				break;
			case 8482:
				stringbuffer.append("&#153;");
				break;
			case 13:
				if (i < j - 1 && s.charAt(i + 1) == 10) {
					stringbuffer.append("<br>");
					i++;
				}
				break;
			case 32:
				if (i < j - 1 && s.charAt(i + 1) == ' ') {
					stringbuffer.append(" &nbsp;");
					i++;
					break;
				}
			default:
				stringbuffer.append(c);
				break;
			}
		}
		return new String(stringbuffer.toString());
	}

	public static String toPlainTextIncludeCss(String htmlString) {
		if (htmlString == null || htmlString.trim().length() == 0)
			return "";
		String plainString = htmlString;
		// plainString = plainString.replaceAll("<style[^>]*>.*</style>", "");
		int begin = -1;
		while ((begin = plainString.indexOf("<style")) > -1) {
			// int begin = plainString.indexOf("<style");
			int last = plainString.indexOf("</style>");
			if (begin > -1 && last > -1) {
				plainString = plainString.substring(0, begin)
						+ plainString.substring(last + 8);
			}
		}
		plainString = plainString.replaceAll("<[^>]*>", "");
		plainString = plainString.replaceAll("&lt;", "<");
		plainString = plainString.replaceAll("&gt;", ">");
		plainString = plainString.replaceAll("&quot;", "\"");
		plainString = plainString.replaceAll("&nbsp;", " ");
		plainString = plainString.replaceAll("&amp;", "&");
		plainString = plainString.replaceAll("&copy;", "Copyright");
		plainString = plainString.replaceAll("\\n|\\r", "");
		return plainString;
	}

	public static String toPlainTextWithoutNewLine(String htmlString) {
		if (htmlString == null || htmlString.trim().length() == 0)
			return "";
		String plainString = htmlString;
		plainString = plainString.replaceAll("<[^>]*>", "");
		plainString = plainString.replaceAll("&lt;", "<");
		plainString = plainString.replaceAll("&gt;", ">");
		plainString = plainString.replaceAll("&quot;", "\"");
		plainString = plainString.replaceAll("&nbsp;", " ");
		plainString = plainString.replaceAll("&amp;", "&");
		// plainString = plainString.replaceAll("\\n|\\r", "");
		return plainString;
	}

	/**
	 * Split one line of csv file
	 * 
	 * @param src
	 * @return String[]
	 * @throws Exception
	 */
	public static String[] splitCSV(String src) {
		if (src == null || src.equals(""))
			return new String[0];
		StringBuffer st = new StringBuffer();
		List<String> result = new ArrayList<String>();
		boolean beginWithQuote = false;
		for (int i = 0; i < src.length(); i++) {
			char ch = src.charAt(i);
			if (ch == '\"') {
				if (beginWithQuote) {
					i++;
					if (i >= src.length()) {
						result.add(st.toString());
						st = new StringBuffer();
						beginWithQuote = false;
					} else {
						ch = src.charAt(i);
						if (ch == '\"') {
							st.append(ch);
						} else if (ch == ',') {
							result.add(st.toString());
							st = new StringBuffer();
							beginWithQuote = false;
						} else {
							throw new RuntimeException(
									"Single double-quote char mustn't exist in filed "
											+ (result.size() + 1)
											+ " while it is begined with quote\nchar at:"
											+ i);
						}
					}
				} else if (st.length() == 0) {
					beginWithQuote = true;
				} else {
					throw new RuntimeException(
							"Quote cannot exist in a filed which doesn't begin with quote!\nfield:"
									+ (result.size() + 1));
				}
			} else if (ch == ',') {
				if (beginWithQuote) {
					st.append(ch);
				} else {
					result.add(st.toString());
					st = new StringBuffer();
					beginWithQuote = false;
				}
			} else {
				st.append(ch);
			}
		}
		if (st.length() != 0) {
			if (beginWithQuote) {
				throw new RuntimeException(
						"last field is begin with but not end with double quote");
			} else {
				result.add(st.toString());
			}
		}
		String rs[] = new String[result.size()];
		for (int i = 0; i < rs.length; i++) {
			rs[i] = (String) result.get(i);
		}
		return rs;
	}

	/**
	 * Split one line of csv file without exceptions.
	 * 
	 * @param src
	 * @return String[] splited fields
	 */
	public static String[] splitCSV2(String src) {
		if (src == null || src.equals(""))
			return new String[0];
		ArrayList<String> result = new ArrayList<String>();
		int i = 0;
		while (i < src.length()) {
			boolean beginWithQuote;
			int count = countContinuousQuote(i, src);
			if (count % 2 == 0) {
				beginWithQuote = false;
				i = i + count / 2;
			} else {
				beginWithQuote = true;
				// i = i+count-1;
			}
			if (beginWithQuote) {
				// till next quote and then till next comma after the index of
				// quote
				int nextQuote = src.indexOf('\"', i + 1);
				int quoteCount;
				if (nextQuote != -1) {
					do {
						quoteCount = countContinuousQuote(nextQuote, src);
						// assert quoteCount >= 1;
						if (quoteCount % 2 == 0) {
							nextQuote = src.indexOf('\"', nextQuote
									+ quoteCount);
							if (nextQuote == -1)
								break;
						} else {
							nextQuote = nextQuote + quoteCount - 1;
							break;
						}
					} while (true);
				}
				if (nextQuote == -1) {
					// row over
					result.add(src.substring(i).replaceAll("\"\"", "\""));
					break;
				} else {
					int nextComma = src.indexOf(',', nextQuote);
					if (nextComma == -1) {
						// row over
						result.add(src.substring(i + 1)
								.replaceAll("\"\"", "\""));
						break;
					} else {
						// column over
						result.add(src.substring(i + 1, nextComma - 1)
								.replaceAll("\"\"", "\""));// nextComma-1 <=>
															// nextComma -
															// "\",".length + 1
						i = nextComma + 1;
						if (i >= src.length()) {
							result.add("");
							break;
						}
					}
				}
			} else {
				// till next comma
				int comma = src.indexOf(',', i);
				if (comma == -1) {
					// row over
					result.add(src.substring(i));
					break;
				} else {
					// column over
					result.add(src.substring(i, comma));
					i = comma + 1;
					if (i >= src.length()) {
						result.add("");
						break;
					}
				}
			}
		}

		String rs[] = new String[result.size()];
		for (int j = 0; j < rs.length; j++) {
			rs[j] = (String) result.get(j);
		}
		return rs;
	}

	private static int countContinuousQuote(int startIndex, String src) {
		int count = 0;
		for (int i = startIndex; i < src.length(); i++) {
			char ch = src.charAt(i);
			if (ch == '\"')
				count++;
			else
				break;
		}
		return count;
	}

	public static void main(String[] args) {
		// String str =
		// "\"\"\"asd--\"\"asasdsds,\",zhoyan2004@hotmail.com,,,,,,,,,,,,,,";
		// String str =
		// "\"\"asd--\"\"asasdsds,zhoyan2004@hotmail.com,,,,,,,,,,,,,,";
		String str = "asdf,rainy.xia@126.com,,,,,,,,,,,,,,";
		String[] s = splitCSV2(str);
		System.out.println("s.length = " + s.length);
		for (int i = 0; i < s.length; i++) {
			String s1 = s[i];
			System.out.println("s1 = " + s1);
		}
	}

	public static String[] split(String value) {
		return splitWithSpacesQuoteSensitive2(value);
	}

	private static String[] splitWithSpacesQuoteSensitive1(String value) {
		value = StringUtils.trim(value);
		Pattern p = Pattern.compile("[\"]([^\"])*[\"]");
		Matcher m = p.matcher(value);
		List list = new ArrayList();
		while (m.find()) {
			String group = m.group();
			list.add(group.substring(1, group.length() - 1));

		}
		value = p.matcher(value).replaceAll(" ");
		String[] temp = value.split("(\\s|\u3000)+");
		for (int i = 0; i < temp.length; i++) {
			if (StringUtil.notEmpty(temp[i])) {
				list.add(temp[i]);
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			String s = (String) list.get(i);
			if (StringUtil.isNullOrEmpty(s))
				list.remove(i);
		}
		String[] result;
		result = (String[]) list.toArray(new String[0]);
		return result;
	}

	private static String[] splitWithSpacesQuoteSensitive2(String value) {
		boolean quoteStart = false;
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case ' ':
			case '\u3000':
				if (quoteStart) {
					sb.append(c);
				} else {
					if (sb.length() != 0) {
						list.add(sb.toString());
						sb = new StringBuffer();
					}
				}
				break;
			case '\"':
				if (quoteStart) {
					if (sb.length() != 0) {
						list.add(sb.toString());
						sb = new StringBuffer();
					}
					quoteStart = false;
				} else {
					if (sb.length() != 0) {
						list.add(sb.toString());
						sb = new StringBuffer();
					}
					quoteStart = true;
				}
				break;
			default:
				sb.append(c);
				break;
			}
		}
		if (sb.length() != 0) {
			list.add(sb.toString());
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			String s = (String) list.get(i);
			if (StringUtil.isNullOrEmpty(s))
				list.remove(i);
		}
		return convertToStringArray(list);
	}

	public static boolean notEmpty(String str) {
		return str != null && str.trim().length() > 0;
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String formatNull(String str) {
		return nullToEmpty(str);
	}

	public static String formatNull(Object o) {
		if (o == null)
			return "";
		return nullToEmpty(o.toString());
	}

	public static String nullToEmpty(String str) {
		return str == null ? EMPTY : str;
	}

	public static String nullToUnknow(String str) {
		if (notEmpty(str)) {
			return str;
		}
		return UNKNOW;
	}

	public static String unknowToNull(String str) {
		if (UNKNOW.equals(str)) {
			str = "";
		}
		return str;
	}

	public static String getWhereForm(String str1, String str2) {
		if (notEmpty(unknowToNull(str1)) && notEmpty(unknowToNull(str2))) {
			return str1 + "·" + str2;
		} else if (notEmpty(unknowToNull(str1))) {
			return str1;
		} else {
			return "未知";
		}
	}

	/**
	 * @param bt
	 * @param encodeing
	 * @return
	 */
	public static String parseBytes(byte[] bt, String encodeing) {
		String s = null;
		try {
			s = new String(bt, encodeing);
		} catch (UnsupportedEncodingException e) {
			// logger.debug("Parse byte to String error",e);
		}
		return s;
	}

	/**
	 * @param bt
	 * @return
	 */
	public static String parseBytes(byte[] bt) {
		return parseBytes(bt, "UTF-8");
	}

	/**
	 * @param input
	 * @return
	 */
	public static String encodeUTF8(String input) {
		return encode(input, "UTF-8");
	}

	/**
	 * @param input
	 * @return
	 */
	public static String encode(String input) {
		return encodeUTF8(input);
	}

	/**
	 * @param input
	 * @return
	 */
	public static String encode(String input, String coding) {
		if (isNullOrEmpty(input))
			return input;
		try {
			return URLEncoder.encode(input, coding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * @param input
	 * @return
	 */
	public static String decode(String input) {
		return decodeUTF8(input);
	}

	/**
	 * @param input
	 * @return
	 */
	public static String decodeUTF8(String input) {
		return decode(input, "UTF-8");
	}

	public static String decode(String input, String coding) {
		if (isNullOrEmpty(input))
			return input;
		try {
			return URLDecoder.decode(input, coding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	public static boolean isNumber(String input) {
		try {
			Long.parseLong(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static String trim(String str) {
		str = str.trim();
		StringBuffer sb = new StringBuffer(str);
		while (sb.length() > 0 && sb.charAt(0) == '\u3000')
			sb = sb.deleteCharAt(0);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\u3000')
			sb = sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String[] splitWithCommaQuote(String value) {
		if (value == null || value.trim().equals(EMPTY)) {
			return null;
		}

		// boolean quoteStart = false;
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '\uff0c':
				if (sb.length() != 0) {
					list.add(sb.toString());
					sb = new StringBuffer();
				}
				break;
			case ',':
				if (sb.length() != 0) {
					list.add(sb.toString());
					sb = new StringBuffer();
				}
				break;
			default:
				sb.append(c);
				break;
			}
		}
		if (sb.length() != 0) {
			list.add(sb.toString());
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			String s = (String) list.get(i);
			if (StringUtil.isNullOrEmpty(s))
				list.remove(i);
		}
		return (String[]) list.toArray(new String[0]);
	}

	public static String formatDate(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:mm");
		return df.format(date);
	}

	public static String substring(String str, int toCount) {
		int reInt = 0;
		String reStr = EMPTY;
		if (str == null)
			return EMPTY;
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		// if (toCount == reInt || (toCount == reInt - 1))
		// reStr += "...";
		return reStr;
	}

	public static List splitSmsString(String str, int toCount) {
		ArrayList al = new ArrayList();

		int len = str.length();
		int startPoint = 0;
		while (true) {
			int myLen = Math.min(len, startPoint + toCount);
			String subStr = str.substring(startPoint, myLen);
			al.add(subStr);
			if (startPoint + toCount >= len)
				break;
			startPoint = startPoint + toCount;

		}
		return al;
	}

	/**
	 * 分割字符串到多个子字符串， 每个子字符串最大长度为toCount
	 * 
	 * @param str
	 *            分割字符数
	 * @param toCount
	 *            子字符串最大长度
	 * @param charsetName
	 *            字符集
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List splitShortString(String str, int toCount,
			String charsetName) throws UnsupportedEncodingException {
		ArrayList al = new ArrayList();

		String reStr = EMPTY;
		if (str == null)
			return al;
		char[] tempChar = str.toCharArray();
		int reInt = 0;
		int newInt = 0;

		for (int kk = 0; kk < tempChar.length; kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes(charsetName);
			newInt = reInt + b.length;
			if (newInt > toCount) {
				al.add(reStr);
				reStr = EMPTY;
				newInt = 0;
			}
			reStr += tempChar[kk];
			reInt = newInt;
		}
		if (reStr.length() > 0) {
			al.add(reStr);
		}
		return al;

	}

	public static String substring(String str, int toCount, String charsetName)
			throws UnsupportedEncodingException {
		int reInt = 0;
		String reStr = EMPTY;
		if (str == null)
			return EMPTY;
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes(charsetName);
			reInt += b.length;
			reStr += tempChar[kk];
		}
		// if (toCount == reInt || (toCount == reInt - 1))
		// reStr += "...";
		return reStr;
	}

	public static int length(String str) {
		int length = 0;
		if (str == null)
			return 0;
		char[] tempChar = str.toCharArray();
		for (int kk = 0; kk < tempChar.length; kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			length += b.length;
		}

		return length;
	}

	public static int length(String str, String charsetName)
			throws UnsupportedEncodingException {
		int length = 0;
		if (str == null)
			return 0;
		char[] tempChar = str.toCharArray();
		for (int kk = 0; kk < tempChar.length; kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes(charsetName);
			length += b.length;
		}

		return length;
	}

	public static int strlength(String str) {
		return str.length();
	}

	public static String StrParse(String str, int len) {
		String[] resultStr = StrParse(new String[] { str }, len);

		return resultStr[0];
	}

	public static String[] StrParse(List str, int len) {
		String[] arrayStr = new String[str.size()];

		for (int i = 0; i < str.size(); i++) {
			arrayStr[i] = (String) str.get(i);
		}

		String[] resultStr = StrParse(arrayStr, len);

		return resultStr;
	}

	public static String[] StrParse(String[] str, int len) {
		List<String> v = new ArrayList<String>();
		int rstLen = len;
		int size = 0;

		for (int i = 0; i < str.length && rstLen > 0; i++) {
			String cnt = EMPTY;
			int charLen = length(str[i]);
			// System.out.println(charLen);
			if (rstLen > charLen) {
				// 总长度大于当前字符长度
				cnt = str[i];
				rstLen = rstLen - charLen;
				v.add(cnt);
				size++;
			} else {
				// 总长度小于或等于当前字符长度
				cnt = strByLen(str[i], rstLen, i == str.length - 1);// get part
																	// of str
				v.add(cnt);
				size++;
				break;
			}
		}

		String[] s = new String[size];
		for (int k = 0; k < s.length; k++) {
			s[k] = v.get(k);
		}
		return s;
	}

	public static String strByLen(String str, int len, boolean isLastStr) {
		String showstr = EMPTY;
		int strLength = length(str);
		/*
		 * if the fist one is chinese of str, and the retlen=1,it can not show
		 * the chinese. if the last one is chinese of str, and there is only 1
		 * rest, so can not show the chinese two only there are more than 4 char
		 * left, need to show ... after the string.
		 */
		int length = 0;
		char[] tempChar = str.toCharArray();
		for (int kk = 0; length < len && kk < tempChar.length; kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			showstr = showstr + s1;
			byte[] b = s1.getBytes();
			length += b.length;
		}

		if (strLength - 2 > len || (strLength - 2 == len && !isLastStr))
			// 服务器上多出2个字符
			showstr += "...";

		return showstr;
	}

	public static String replaceFCKEditorString(String target) {
		if (target == null)
			return EMPTY;

		target = target.replaceAll("&ldquo;", "“");
		target = target.replaceAll("&rdquo;", "”");
		target = target.replaceAll("<br />", "\n");
		return target;
	}

	/**
	 * This method takes a string which may contain URLs and replaces them with
	 * working links. It does this by adding the html tags &lt;a href&gt; and
	 * &lt;/a&gt;.
	 * <p/>
	 * don't need http or https auto convert, such as img src="http://" modified
	 * by banq
	 * <p/>
	 * The patterns recognized are <code>ftp://path-of-url</code>,
	 * <code>http://path-of-url</code>, <code>https://path-of-url</code>
	 * <code>[url path-of-url]descriptive text[/url]</code> and
	 * <code>[url=path-of-url]descriptive text[/url]</code> the
	 * <code>[url]</code> allows any path to be defined as link.
	 * 
	 * @param input
	 *            the text to be converted.
	 * @return the input string with the URLs replaced with links.
	 */
	public static String convertURL(String input, boolean newWindowEnabled) {
		// Check if the string is null or zero length
		// -- if so, return what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}

		// Build the response in a buffer
		StringBuffer buf = new StringBuffer(input.length() + 25);
		char[] chars = input.toCharArray();
		int len = input.length();

		int index = -1, i = 0;
		int j = 0, oldend = 0, k = 0;
		// int u1, u2;
		char cur;

		// Handle the occurences of the ftp:// , http:// , https:// patterns
		// and the [url] pattern.
		while (++index < len) {
			cur = chars[i = index];

			// save valuable cpu resources by expanding the tests here instead
			// of calling String.indexOf()
			j = -1;
			if ((cur == 'f' && index < len - 6 && chars[++i] == 't' && chars[++i] == 'p')
					|| (cur == 'h' && (i = index) < len - 7
							&& chars[++i] == 't' && chars[++i] == 't'
							&& chars[++i] == 'p' && (chars[++i] == 's' || chars[--i] == 'p'))) {
				if (i < len - 4 && chars[++i] == ':' && chars[++i] == '/'
						&& chars[++i] == '/') {
					j = ++i;
				}
			}

			// did we find http or ftp?
			if (j > 0) {
				// check context, dont handle patterns preceded by any of '"<=
				if (index == 0
						|| ((cur = chars[index - 1]) != '\'' && cur != '"'
								&& cur != '<' && cur != '=')) {
					cur = chars[j];
					// now collect url pattern upto next " <\n\r\t"
					while (j < len) {
						// Is a space?
						if (cur == ' ')
							break;
						if (cur == '\t')
							break;
						// Is a quote?
						if (cur == '\'')
							break;
						if (cur == '\"')
							break;
						// Is html?
						if (cur == '<')
							break;
						if (cur == '[')
							break;
						// Is a Unix newline?
						if (cur == '\n')
							break;
						// Is Win32 newline?
						if (cur == '\r' && j < len - 1 && chars[j + 1] == '\n')
							break;
						// 中文
						if (String.valueOf(cur).getBytes()[0] < 0)
							break;
						//
						k = j;
						if ((cur == 'f' && j < len - 6 && chars[++k] == 't' && chars[++k] == 'p')
								|| (cur == 'h' && (k = j) < len - 7
										&& chars[++k] == 't'
										&& chars[++k] == 't'
										&& chars[++k] == 'p' && (chars[++k] == 's' || chars[--k] == 'p'))) {
							if (k < len - 4 && chars[++k] == ':'
									&& chars[++k] == '/' && chars[++k] == '/') {
								break;
							}
						}
						j++;
						if (j < len) {
							cur = chars[j];
						}
					}
					// Check the ending character of the URL. If it's a ".,)]"
					// then we'll remove that part from the URL.
					cur = chars[j - 1];
					if (cur == '.' || cur == ',' || cur == ')' || cur == ']') {
						j--;
					}
					buf.append(chars, oldend, index - oldend);
					buf.append("<a href=\"");
					buf.append(chars, index, j - index);
					buf.append('\"');
					if (newWindowEnabled) {
						buf.append(" target=\"_blank\"");
					}
					buf.append('>');

					buf.append(chars, index, j - index);
					buf.append("</a>");
				} else {
					buf.append(chars, oldend, j - oldend);
				}
				oldend = index = j;
			}
		}
		if (oldend < len) {
			buf.append(chars, oldend, len - oldend);
		}
		return buf.toString();
	}

	public static String formatInteger(int i, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(i);
	}

	public static String formatLong(long i, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(i);
	}

	public static String filterSpecialChar(String origin) {
		// 保留数字和字母
		origin = origin.replaceAll("\\W", EMPTY).replaceAll("_", EMPTY);
		return origin;
	}

	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	public static String[] convertToStringArray(List list) {
		if (list != null) {
			int length = list.size();
			String strs[] = new String[length];
			for (int i = 0; i < length; i++) {
				String str = ((Object) list.get(i)).toString();
				strs[i] = str;
			}

			return strs;
		} else {
			return null;
		}
	}

	public static String urlOpenWithNewWindow(String oUrl) {
		int firstPos = oUrl.indexOf("<a", 0);
		int endPos = 0;
		// int targetPos = 0;
		String strA = EMPTY;
		if (firstPos < 0)
			return oUrl;

		String nUrl = oUrl.substring(0, firstPos);
		while (firstPos >= 0) {
			endPos = oUrl.indexOf(">", firstPos);
			strA = oUrl.substring(firstPos, endPos + 1);
			if (strA.indexOf("\"_blank\"") > 0 || strA.indexOf("'_blank'") > 0) {
				nUrl = nUrl + strA;
			} else {
				nUrl = nUrl + oUrl.substring(firstPos, endPos)
						+ " target=\"_blank\">";
			}
			firstPos = oUrl.indexOf("<a", endPos + 1);

			if (firstPos > endPos)
				nUrl = nUrl + oUrl.substring(endPos + 1, firstPos);
			else if (oUrl.length() > endPos)
				nUrl = nUrl + oUrl.substring(endPos + 1, oUrl.length());
		}
		return nUrl;
	}

	private static String __ENCODE__ = "GBK";
	private static String __SERVER_ENCODE__ = "UTF-8";

	public static int compareChinese(String s1, String s2) {
		String m_s1 = null, m_s2 = null;
		try {
			// 先将两字符串编码成GBK
			m_s1 = new String(s1.getBytes(__SERVER_ENCODE__), __ENCODE__);
			m_s2 = new String(s2.getBytes(__SERVER_ENCODE__), __ENCODE__);
		} catch (Exception ex) {
			return s1.compareTo(s2);
		}

		int res = chineseCompareTo(m_s1, m_s2);
		return res;

	}

	// 获取一个汉字/字母的Char值
	private static int getCharCode(String s) {
		if (s == null || s.equals(EMPTY))
			return -1;

		byte[] b = s.getBytes();

		int value = 0;

		// 保证取第一个字符（汉字或者英文）

		for (int i = 0; i < b.length && i <= 2; i++) {
			value = value * 100 + b[i];
		}
		return value;

	}

	// 比较两个字符串
	private static int chineseCompareTo(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
		int n = Math.min(len1, len2);
		for (int i = 0; i < n; i++) {
			int s1_code = getCharCode(s1.charAt(i) + EMPTY);
			int s2_code = getCharCode(s2.charAt(i) + EMPTY);

			if (s1_code != s2_code)
				return s1_code - s2_code;
		}
		return len1 - len2;
	}

	// 准换ppuid
	public static String ppuidToString(String s1) {
		String raw = s1.toLowerCase();
		StringBuffer sb = new StringBuffer();
		sb.append(raw.substring(0, 8));
		sb.append("-");
		sb.append(raw.substring(8, 12));
		sb.append("-");
		sb.append(raw.substring(12, 16));
		sb.append("-");
		sb.append(raw.substring(16, 20));
		sb.append("-");
		sb.append(raw.substring(20));

		return sb.toString();
	}

	public static String removeHttpOrHttps(String origin) {
		if (origin == null)
			return EMPTY;
		origin = origin.trim().toLowerCase();
		if (origin.toLowerCase().startsWith("http://")) {
			return origin.substring(7, origin.length());
		}
		if (origin.toLowerCase().startsWith("https://")) {
			return origin.substring(8, origin.length());
		}
		return origin;
	}

	/***
	 * 隐藏身份证
	 * 
	 * @param id
	 * @return
	 */
	public static String getIdPrivate(String id) {
		if (id == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int length = id.length();
		if (length == 18) {
			sb.append(id.substring(0, 3)).append("*************")
					.append(id.substring(length - 2));
		} else {
			sb.append(id.substring(0, 3)).append("************")
					.append(id.substring(length - 2));
		}
		return sb.toString();
	}

	/***
	 * 手机号码隐藏
	 * 
	 * @param id
	 * @return
	 */
	public static String getPhonePrivate(String phone) {
		if (phone == null || phone.length() != 11) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(phone.substring(0, 3)).append("*************")
				.append(phone.substring(9));
		return sb.toString();
	}

	/***
	 * email隐藏
	 * 
	 * @param id
	 * @return
	 */
	public static String getMailPrivate(String email) {
		if (isNullOrEmpty(email)) {
			return "";
		}
		StringBuilder strBuilder = new StringBuilder();
		int pivot = email.lastIndexOf("@");
		if (pivot > 2) {
			strBuilder.append(email.subSequence(0, 1));
			for (int i = 1; i < pivot - 1; i++) {
				strBuilder.append('*');
			}
			strBuilder.append(email.subSequence(pivot - 1, email.length()));
			return strBuilder.toString();
		}
		return email;
	}

	public static String appendHttpParam(String url, Map<String, String> params, String charset)
			throws UnsupportedEncodingException {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(url);
		if (!url.contains("?")) {
			strBuilder.append("?");
		} else if (!url.endsWith("&") && !url.endsWith("?")) {
			strBuilder.append("&");
		}
		Set<Entry<String, String>> entries = params.entrySet();
		for (Entry<String, String> entry : entries) {
			strBuilder.append(URLEncoder.encode(entry.getKey(),
					charset));
			strBuilder.append("=");
			strBuilder.append(URLEncoder.encode(entry.getValue(),
					charset));
			strBuilder.append("&");
		}
		strBuilder.deleteCharAt(strBuilder.length() - 1);
		return strBuilder.toString();
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
}
