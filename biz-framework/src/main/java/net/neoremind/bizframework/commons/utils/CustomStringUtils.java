package net.neoremind.bizframework.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomStringUtils {

	private static String patternStr = "[^\\w\\-\u4e00-\u9fa5]";	//中文，英文，数字，_，-
	private static Pattern pattern = Pattern.compile(patternStr);
	
	public static boolean validateHasSpecialChar(String keyword){
		if(CustomStringUtils.isEmpty(keyword)){
			return false;
		}
		Matcher matcher = pattern.matcher(keyword);
		if(matcher.find()){
			return true;
		}
		return false;
	}

	/**
	 * 将一个list组装成字符串放在sql in中使用
	 * 
	 * @param idList
	 * @return
	 */
	public static String makeStrCollection(List<?> idList) {

		if (idList == null || idList.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		int i = 0;
		for (; i < idList.size() - 1; i++) {
			builder.append(idList.get(i)).append(",");
		}
		builder.append(idList.get(i));

		return builder.toString();
	}
	
	public static String makeStrFromStringCollection(final Collection<String> collection,
			final String split) {

		if (collection == null || collection.isEmpty()) {
			return "";
		}
		if (split == null){
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for(String element : collection){
			builder.append("'" + element + "'").append(split);
		}
		builder.delete(builder.length()-split.length(), builder.length());

		return builder.toString();
	}

	/**
	 * 将一个list组装成字符串，list各个元素之间以split分割
	 * 
	 * @param collection
	 * @param split
	 *            分隔符, 如果为null, 则返回长度为0的字符串
	 * @return
	 */
	public static String makeStrFromCollection(final Collection<? extends Object> collection,
			final String split) {

		if (collection == null || collection.isEmpty()) {
			return "";
		}
		if (split == null){
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for(Object element : collection){
			builder.append(element).append(split);
		}
		builder.delete(builder.length()-split.length(), builder.length());

		return builder.toString();
	}
	
	
	/**
	 * 将一个list组装成字符串，list各个元素之间以split分割
	 * 
	 * @param collection
	 * @param split
	 *            分隔符, 如果为null, 则返回长度为0的字符串
	 * @return
	 */
	public static <T> String makeStrFromArray(final T[] array,
			final String split) {

		if (array == null || array.length==0) {
			return "";
		}
		
		if (split == null){
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for(Object element : array){
			builder.append(element).append(split);
		}
		builder.delete(builder.length()-split.length(), builder.length());

		return builder.toString();
	}

	public static String makeStrFromArray(final int[] array,
			final String split) {

		if (array == null || array.length==0) {
			return "";
		}
		
		if (split == null){
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for(int element : array){
			builder.append(element).append(split);
		}
		builder.delete(builder.length()-split.length(), builder.length());

		return builder.toString();
	}

	/**
	 * 将一个list组装成字符串，list各个元素之间以split分割,<br>
	 * 为了保持站点和行业的内容与之前的一致,最后有一个spilit分隔符，<br>
	 * 
	 * 
	 * @param collection
	 * @param split
	 *            分隔符, 如果为null, 则返回长度为0的字符串
	 * @return
	 */
	public static String makeStrFromCollectionForSite(final Collection<? extends Object> collection,
			final String split) {

		if (collection == null || collection.isEmpty()) {
			return "";
		}
		if (split == null){
			return "";
		}

		StringBuilder builder = new StringBuilder();

		for(Object element : collection){
			builder.append(element).append(split);
		}

		return builder.toString();
	}

	/**
	 * 把以'\n'分割的字符串切割成字符串列表，每个字符串进行trim,列表中不包含空字符串
	 * 
	 * @author zengyunfeng
	 * @param listString
	 * @param split 连接的字符串，正则表达式
	 * @return
	 */
	public static List<String> splitToList(final String listString, final String split) {
		List<String> result = new ArrayList<String>();
		if (listString == null) {
			return result;
		}
		if(split== null || split.length() == 0){
			return result;
		}
		String tmpListString = listString.trim();

		String[] array = tmpListString.split(split);
		for (String str : array) {
			str = str.trim();
			if (!"".equals(str)) {
				result.add(str);
			}
		}
		return result;
	}
	
	/**
	 * 把以分割的字符串切割成字符串列表，每个字符串进行trim,列表中不包含空字符串，返回的是一个整形列表
	 * 
	 * @param listString
	 * @param split 连接的字符串，正则表达式
	 * @return
	 */
	public static List<Integer> splitToIntList(final String listString, final String split) {
		if (listString == null) {
			return new ArrayList<Integer>(0);
		}
		if(split== null || split.length() == 0){
			return new ArrayList<Integer>(0);
		}
		
		List<Integer> result = new ArrayList<Integer>();
		
		String tmpListString = listString.trim();

		String[] array = tmpListString.split(split);
		for (String str : array) {
			str = str.trim();
			if (!"".equals(str)) {
				result.add(Integer.parseInt(str));
			}
		}
		return result;
	}

	/**
	 * 替换in语句，动态组装or查询条件
	 * 
	 * @param num
	 * @param unit
	 * @return
	 */
	public static String formateOrCondition(int num, final String unit) {

		if (num <= 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder(num * (unit.length() + 2));

		builder.append(unit);

		for (int i = 1; i < num; i++) {
			builder.append("or");
			builder.append(unit);
		}

		return builder.toString();
	}

	/**
	 * 将一个规格字符串变成一个64位long型 1|2|3| ----> 111 = 7
	 * 
	 * @param input
	 * @param delim
	 * @return
	 */
	public static long transToLong(String input, final String delim) {

		String tmpStr = input;

		long result = 0;

		if (tmpStr == null || tmpStr.length() == 0) {
			return result;
		}

		String[] delimal = tmpStr.split(delim);

		if (delimal.length > 63) {
			return result;
		}

		int charInt = 0;

		for (int i = 0; i < delimal.length; i++) {
			try {
				charInt = Integer.parseInt(delimal[i]);
			} catch (Exception e) {
				charInt = 0;
			}

			if (charInt > 0) {
				result += (1 << i);
			}
		}

		return result;
	}

	/**
	 * 将数字转换为百分数表示
	 * 
	 * @param num
	 *            double 需要表示为百分数的数字
	 * @param fraction
	 *            int 百分数中的小数位
	 * @return String 代表百分数的字符串
	 */
	public static String getPercent(double num, int fraction) {
		NumberFormat fmt = NumberFormat.getPercentInstance();

		fmt.setMaximumFractionDigits(fraction);
		return fmt.format(num);
	}

	/**
	 * 转换数值双精度型保留2位小数
	 * 
	 * @param number
	 *            要转换的数值
	 * @param formate
	 *            格式串 "0.00" 返回转换后的数值
	 */
	public static String convNumber(double number, String formate) {
		DecimalFormat df = new DecimalFormat(formate);
		return df.format(number);
	}

	public static boolean isTraditionalChineseCharacter(char c, boolean checkGbk) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		if (!Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block)
				&& !Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
						.equals(block)
				&& !Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
						.equals(block)) {
			return false;
		}
		if (checkGbk) {
			try {
				String s = "" + c;
				return s.equals(new String(s.getBytes("GBK"), "GBK"));
			} catch (java.io.UnsupportedEncodingException e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * GBK验证
	 * @author zengyunfeng
	 * @param input
	 * @return
	 */
	public static boolean isGBK(String input) {
		try {
			if (input.equals(new String(input.getBytes("GBK"), "GBK"))) {
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
	
	/**
	 * 获得字符串的GBK编码的字节长度
	 * @author zengyunfeng
	 * @param input
	 * @return
	 */
	public static int byteLength(String input){
		byte[] array;
		try {
			array = input.getBytes("GBK");
			return array.length;
		} catch (UnsupportedEncodingException e) {
			return Integer.MAX_VALUE;
		}
	}
	
	public static String filterString(String input, final boolean checkGbk){
		StringBuffer sb=new StringBuffer();

		char[] ch = input.toCharArray();

		//int length = 0;

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isNeedAlph(c)) {
				sb.append(c);
			} else if (isTraditionalChineseCharacter(c, checkGbk)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 推广计划和推广组名词验证：[中文a-zA-Z0-9\\-_]*
	 * @author zengyunfeng
	 * @param input
	 * @param checkGbk
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean validBeidouGbkStr(String input,
			final boolean checkGbk, final int minLength, final int maxLength) {

		// 对长度有个预先判断
		if ((minLength > -1) && (input.length() < minLength)) {
			return false;
		} else if ((maxLength > -1) && (input.length() > maxLength)) {
			return false;
		}

		// 验证字符合法性和特殊长度要求
		char[] ch = input.toCharArray();

		int length = 0;

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isNeedAlph(c)) {
				length += 1;
			} else if (isTraditionalChineseCharacter(c, checkGbk)) {
				length += 2;
			} else {
				return false;// 是否为合法字符集
			}
		}

		if ((minLength > -1) && (length < minLength)) {
			return false;
		} else if ((maxLength > -1) && (length > maxLength)) {// bug,怎么为true
			return false;
		}

		return true;
	}

	private static boolean isNeedAlph(char c) {

		if (c >= 'a' && c <= 'z') {
			return true;
		} else if (c >= 'A' && c <= 'Z') {
			return true;
		} else if (c >= '0' && c <= '9') {
			return true;
		} else if (c == '-') {
			return true;
		} else if (c == '_') {
			return true;
		}

		return false;
	}

	public static boolean isLatinCharacter(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		if (!Character.UnicodeBlock.BASIC_LATIN.equals(block)) {
			return false;
		}

		return true;
	}
	
	public static String removeFcPattern(String src){
//        String regEx="\\{([^\\{\\}]+)\\}";
//        src=src.replaceAll(regEx, "$1");
//		src=src.replaceAll("[\\{\\}<>]{1}", "");
		char[] chars=src.toCharArray();
		StringBuffer sb=new StringBuffer();
		for(char c: chars){
			if(c!=0x3c && c!=0x3e && c!=0x7b && c!=0x7d){
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String removeShowUrlFcPattern(String src){
//      String regEx="\\{([^\\{\\}]+)\\}";
//      src=src.replaceAll(regEx, "$1");
//		src=src.replaceAll("[\\{\\}<>]{1}", "");
		char[] chars=src.toCharArray();
		StringBuffer sb=new StringBuffer();
		for(char c: chars){
			if(c!=0x3c && c!=0x3e && c!=0x7b && c!=0x7d && c != 0x2c && c!=0x22 && c!=0x27 ){
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String removeTargetUrlFcPattern(String src){
//      String regEx="\\{([^\\{\\}]+)\\}";
//      src=src.replaceAll(regEx, "$1");
//		src=src.replaceAll("[\\{\\}<>]{1}", "");
		/*
		 * 
		((c == 0x3c) 		//<
				|| (c == 0x3e)		//>
				|| (c == 0x7b)		//{
				|| (c == 0x7d)		//}
				|| (c == 0x2c)		//,
				|| (c == 0x22)		//"
				|| (c == 0x27)		//'
				);
		 * 
		 */
		/*
		 * modified by genglei
		 * 取消大括号{}的限制，target url支持通配符
		 */
		char[] chars=src.toCharArray();
		StringBuffer sb=new StringBuffer();
		for(char c: chars){
			if(c!=0x3c && c!=0x3e && c != 0x2c){
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static int getGBKLen(String orignal) throws UnsupportedEncodingException{
		if(orignal==null || orignal.equals("") ){
			return 0;
		}
		char[] ch = orignal.toCharArray();

		int length = 0;

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isNeedAlph(c)) {
				length += 1;
			}else if (String.valueOf(c).getBytes("GBK").length==2) {
				length += 2;
			}else{//特别字符多算一点
				length += 3;
			}
		}
		return length;
	}
	
	public static String subGBKString(String orignal, int count)   
		throws UnsupportedEncodingException {   
		// 原始字符不为null，也不是空字符串   
		if (!org.apache.commons.lang.StringUtils.isEmpty(orignal)) {   
		    // 将原始字符串转换为GBK编码格式   
//		    orignal = new String(orignal.getBytes(), "GBK");
		    // 要截取的字节数大于0，且小于原始字符串的字节数   
		    if (count > 0 && count < orignal.getBytes("GBK").length) {   
		    	StringBuilder buff = new StringBuilder();   
		        char c;   
		        for (int i = 0; i < count; i++) {   
		            // charAt(int index)也是按照字符来分解字符串的   
		            c = orignal.charAt(i);   
		            if (String.valueOf(c).getBytes("GBK").length>2){
		            	continue;//跳过特别字符
		            }
		            if (String.valueOf(c).getBytes("GBK").length==2) {   
		                // 遇到中文汉字，截取字节总数减1 
		                --count;
		            }
		            if(i<count){
		            	buff.append(c);
		            }
		        }   
		        return buff.toString();   
		    }   
		}   
		return orignal;   
	}   
	
	public static String subGBKString(String orignal, int start, int end)   
	throws UnsupportedEncodingException {   
	// 原始字符不为null，也不是空字符串   
	if (!org.apache.commons.lang.StringUtils.isEmpty(orignal)) {   
	    // 将原始字符串转换为GBK编码格式   
//	    orignal = new String(orignal.getBytes(), "GBK");

	    // 要截取的字节数大于0，且小于原始字符串的字节数   
	    if (end > 0 &&  end <= orignal.getBytes("GBK").length) {   
	    	StringBuilder buff = new StringBuilder();   
	        char c;   
	        for (int i = 0; i < end; i++) {   
	            // charAt(int index)也是按照字符来分解字符串的   
	            c = orignal.charAt(i);   
	            if (String.valueOf(c).getBytes("GBK").length>2){
	            	continue;//跳过特别字符
	            }
	            if (String.valueOf(c).getBytes("GBK").length==2) {   
	                // 遇到中文汉字，截取字节总数减1 
	                --end;
	                --start;
	            }
	            if(i>=start && i<end){
	            	buff.append(c);
	            }

	        }   
	        return buff.toString();   
	    }   
	}   
	return orignal;   
}   


	public static void main(String[] args) throws UnsupportedEncodingException {
//		if (true) {
//			String gbk = "我爱12－-工工";
//			System.out.println(getGBKLen(gbk));
//			System.out.println(subGBKString(gbk, 0, 8));
//			System.out.println(subGBKString(gbk, 0, 9));
//			System.out.println(subGBKString(gbk, 0, 10));
//			System.out.println(subGBKString(gbk, 0, 11));
//			System.out.println(subGBKString(gbk, 0, 17));
//			return;
//		}

		System.out.println(removeFcPattern("aaa{abc}}}我eee{e>asd<asda"));
		
		System.out.println(subGBKString("我、123他他他aa", 3,8));
		
		System.out.println(HTMLEncode("asdas<sss>as\\d&sa"));

		System.out.println(String.valueOf("我a").getBytes("GBK").length);
		System.out.println("a" + isGBK(String.valueOf('a'))); // 全角标点

		try {
			String hostname = InetAddress.getLocalHost().getHostName();
			System.out.println(hostname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println(filterString("<<<", true));
		
		
		System.out.println(validBeidouGbkStr("|", true, 1, 100));
		System.out.println(validBeidouGbkStr("。", true, 1, 100));
		System.out.println(Pattern.matches("[\uFE30-\uFFA0]+", "，"));
		System.out.println(Pattern.matches("[\uFF00-\uFFEF]+", "，"));
		System.out.println(Pattern.matches("[\u4E00-\u9FA5]+", "業銬從"));
		System.out.println(isTraditionalChineseCharacter(',', true));
		System.out.println(isTraditionalChineseCharacter('業', true));
		System.out.println(isTraditionalChineseCharacter('业', true));
		System.out.println(isTraditionalChineseCharacter('，', true));
		System.out.println(isTraditionalChineseCharacter('a', true));
		System.out.println(isTraditionalChineseCharacter('.', true));
		System.out.println(isTraditionalChineseCharacter(',', true));

		System.out.println("、" + isGBK("、")); // 全角标点
		System.out.println("，" + isGBK("，")); // 全角标点
		System.out.println("," + isGBK(","));// 半角标点
		System.out.println("業" + isGBK("業"));// 繁体中文
		System.out.println("，" + isGBK("，")); // 全角标点
		System.out.println("み" + isGBK("み")); // 日文
		System.out.println("gbk자：" + isGBK("자")); // 자

		System.out.println(transToLong("", "\\|"));

		System.out.println(isGBK("、")); // 全角标点
		System.out.println(isGBK("，")); // 全角标点

		System.out.println("----------------------");
		byte[] b = "、".getBytes("gbk");
		for (byte o : b) {
			System.out.println(Integer.toHexString(o));
		}

		b = "み".getBytes("gbk");
		for (byte o : b) {
			System.out.println(Integer.toHexString(o));
		}
		System.out.println("UTF8");
		b = "자".getBytes("UTF8");
		for (byte o : b) {
			System.out.println(Integer.toHexString(o));
		}
		System.out.println("UNICODE");
		b = "자".getBytes("UNICODE");
		for (byte o : b) {
			System.out.println(Integer.toHexString(o));
		}
		System.out.println(CustomStringUtils.validateHasSpecialChar("abc=-def_haha你好"));
		
		// test filterXSSChars
		String url = "http://beidou.baidu.com/jsonError.action;jsessionid=EDC6D4DB6AE43DDD259F82123688D87E/index.html?status=126&messageText=errors.noAuth%22%27%3e%3cDIV+STYLE%3dwidth%3aexpression(window.a==1?1:alert(a=1))%3e";
		url = "http://beidou.baidu.com/jsonError.action;jsessionid=E99EAD357613721EC8C2E9AEAEE847F6/index.html?status=126&messageText=errors.noAuth%22%27%3e%3cDIV+STYLE%3dwidth%3aexpression(window.a==1?1:alert(a=1))%3e";
		url = "<><><><><%3c%3e%3e%3E%3E%3E%3E%3E%3e%3e%3e%3C%3C%3C%3C%3a%3E%3E%3E%3E%3E%3E%3E";
		//url = "";
		url = filterXSSChars(url);
		System.out.println(url);
	}
	
	public static String bufferToHex(byte bytes[]) {  
		return bufferToHex(bytes, 0, bytes.length);  
	}  
		   
	public static String bufferToHex(byte bytes[], int m, int n) {  
		StringBuffer stringbuffer = new StringBuffer(2 * n);  
		int k = m + n;  
		for (int l = m; l < k; l++) {  
			appendHexPair(bytes[l], stringbuffer);  
		}  
		return stringbuffer.toString();  
	}  
		   
	protected static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
		   
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
		char c0 = hexDigits[(bt & 0xf0) >> 4];  
		char c1 = hexDigits[bt & 0xf];  
		stringbuffer.append(c0);  
		stringbuffer.append(c1);  
	}  
	
	

	private static final char c[] = { '<', '>', '&', '\"' };
	private static final String expansion[] = { "&lt;", "&gt;", "&amp;",
			"&quot;" };

	public static String HTMLEncode(String s) {
		StringBuffer st = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			boolean copy = true;
			char ch = s.charAt(i);
			for (int j = 0; j < c.length; j++) {
				if (c[j] == ch) {
					st.append(expansion[j]);
					copy = false;
					break;
				}
			}
			if (copy)
				st.append(ch);
		}
		return st.toString();
	}

	public static String fileNameEncode(String fileName) {
		if (fileName == null)
			return null;
		try {
			return new String(fileName.getBytes("gbk"), "ISO-8859-1");
		} catch (Exception ex) {
			return fileName;
		}
	}

	public static boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}
	
	 public static String substringBefore(String str, String separator) {
	        if (isEmpty(str) || separator == null) {
	            return str;
	        }
	        if (separator.length() == 0) {
	            return "";
	        }
	        int pos = str.indexOf(separator);
	        if (pos == -1) {
	            return str;
	        }
	        return str.substring(0, pos);
	    }
	    
	public static String substringBeforeLast(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return "";
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str, String separator) {
		List segs = new ArrayList();
		String seg = substringBefore(str, separator);
		while ((!isEmpty(str) && !isEmpty(seg) && !seg.equals(str))) {
			segs.add(seg);
			str = substringAfter(str, separator);
			seg = substringBefore(str, separator);
		}
		segs.add(seg);
		String[] result = new String[segs.size()];
		for (int i = 0, j = segs.size(); i < j; i++) {
			result[i] = (String) segs.get(i);
		}
		return result;
	}
	
	public static boolean hitKTBlackRules(String content){
		if (content == null || "".equals(content)) {
			return true;
		}
		try {
			byte[] b = content.getBytes("GBK");
			if(b.length > 3){
				return false;
			}
			if(b.length <= 2){
				return true;
			}
			if(b.length == 3){
				char[] cs = content.toCharArray();
				for(char c : cs){
					//只要有一个不是数字，就行了
					if(c < 48 || c > 57){
						return false;
					}
				}
				return true;
			}
			return false;

		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}

	public static boolean isFieldLengthOK(String content, int length) {
		if (content == null || "".equals(content)) {
			return true;
		}

		try {
			byte[] b = content.getBytes("GBK");

			return b.length <= length;

		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
	
	/**
	 * 对web,report模块的JsonError Action的setMessageText()方法进行“<”、“>”、“%3c”、“%3e”、“%3C”、“%3E”等特殊字符的过滤
	 * add by kanghongwei since cpweb304
	 * @param sourceStr
	 * @return
	 */
	public static String filterXSSChars(String sourceStr){
		String[] xssChars = {"<",">","%3c","%3e","%3C","%3E"};
		for(String xssChar:xssChars){
			if(org.apache.commons.lang.StringUtils.isBlank(sourceStr)){
				return sourceStr;
			}
			if(sourceStr.contains(xssChar)){
				sourceStr = org.apache.commons.lang.StringUtils.replace(sourceStr, xssChar, String.valueOf(""));
			}
		}
		return sourceStr;
	}
}
