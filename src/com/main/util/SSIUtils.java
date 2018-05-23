package com.main.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.main.util.exception.UtilException;
import com.main.util.mybatis.plugin.PageInterceptor;

public class SSIUtils {

    /** 
     * @Description:用于查看请求中的数据
     * 			该方法有待完善
     * 注意，getParamter、getReader、getInputStream 只能调用一次，相互调用会有冲突。因为流只能读取一次。，
     * @author li_bin
     * @Date: 2017年10月31日
     * @throws 
     * @param request
     */
	@Deprecated
    public static void printInfo(HttpServletRequest request){
    	try {
    		if(request == null) return ;
    		String line = System.getProperty("line.separator") ;
    		StringBuffer strBuffer = new StringBuffer("printInfo开始"+line) ;
    		//获取请求的方法 post get
    		String method = request.getMethod() ;
    		strBuffer.append("当前web请求的方式为 ： "+method+line) ;
    		strBuffer.append("当前web请求的编码为 ： "+request.getCharacterEncoding()+line) ;
    		strBuffer.append("当前web请求路径为 ： "+request.getRequestURL()+line) ;
    		if("GET".equals(method)){
    			//处理get请求
    			strBuffer.append("GET请求参数"+request.getQueryString()+line) ;
    		}else if("POST".equals(method)){
    			//处理POST请求
//    			ServletInputStream inputStream = request.getInputStream() ;
//    			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8")) ;

				Map map = new TreeMap() ;
				//获取表单数据
	    		Enumeration parameterNames = request.getParameterNames() ;
	    		while(parameterNames.hasMoreElements()){
	    			Object element = parameterNames.nextElement() ;
	    			if(!element.toString().startsWith("org.springframework.web.")){
	    				//去除一些系统属性
	    				map.put(element, request.getParameter(element.toString())) ;
	    			}
	    		}
	    		strBuffer.append("POST请求数据表单数据 ："+map+line) ;
	    		map = new TreeMap() ;
				//获取非标单数据
				Enumeration attributeNames = request.getAttributeNames() ;
				//将表单数据非标单数据整合打印
	    		while(attributeNames.hasMoreElements()){
	    			Object element = attributeNames.nextElement() ;
	    			if(!element.toString().startsWith("org.springframework.web.")){
	    				map.put(element, request.getParameter(element.toString())) ;
	    			}
	    			
	    		}
	    		strBuffer.append("POST请求数据非标单数据："+map+line) ;
    			BufferedReader bufferedReader = request.getReader();
    			bufferedReader.mark(1);
    			String str ="" ;
    			strBuffer.append("POST请求流信息 : ") ;
    			while((str = bufferedReader.readLine()) != null){
    				strBuffer.append(str) ;
    			}
    			bufferedReader.reset();
				strBuffer.append(line) ;
    		}else{
    			strBuffer.append("不支持的请求方式"+line) ;
    		}
    		/**		将信息打印到控制台		*/
    		System.out.println(strBuffer.append("printInfo结束"+line).toString());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }
    
    /** 
     * @Description:校验当前日期是否符合目标日期格式
     * @author li_bin
     * @Date: 2017年11月17日
     * @param date		目标日期
     * @param format	目标日期格式
     * @return  true 	表示符合目标格式
     */
    public static boolean validDateFormat(String date,String format){
    	boolean flag = false ;
    	if(date==null || date.equals("") || format==null || format.equals(""))	return flag ;
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		Date parse = sdf.parse(date) ;
    		String tarDate = sdf.format(parse) ;
    		if(tarDate.equals(date)) flag = true ;
    	} catch (Exception e) {
    		System.out.println("date :"+date+"\t\tformat :"+format);
    		System.out.println("SSIUtils.valid(String,String) : 目标日期转换异常");
    	}
    	return flag ;
    }
    /** 
     * @Description:获取当前项目的classpath，区分linux，windows
     * @author li_bin
     * @Date: 2017年11月30日
     * @throws 
     * @return
     */
    public static String getRealClasspath(){
    	ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    	URL url = contextClassLoader.getResource("") ;//定位到当前classes位置。/E:/iPay/Branches/iPay-DEV-branch/WebContent/WEB-INF/classes/
    	String path = url.getPath() ;//获取URL实际资源路径
    	String os = System.getProperty("os.name") ;//获取当前操作系统的类型
    	path = os.startsWith("Windows") ? path.substring(1) : path;//如果目标为linux系统，则在路径前不去除“/”
    	try {
    		path = URLDecoder.decode(path,"UTF-8");//针对特别符号处理，如空格等、
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}
    	System.out.println("getRealPath2Classpath---当前操作系统： "+os+"\t获取的classpath路径 ："+path);
    	return path ;
    }
    
	/** 
	 * @Description:utf-8 转换成 unicode
	 * 		本数据源自网络，经测试所有汉字，及绝大部分字符都没有任何问题
	 * @author li_bin
	 * @Date: 2017年12月1日
	 * @throws 
	 * @param inStr
	 * @return
	 */
	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				// 英文及数字等
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				// 全角半角字符
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				// 汉字
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	/** 
	 * @Description:unicode 转换成 utf-8
	 * 	本数据源自网络，经测试所有汉字，及绝大部分字符都没有任何问题
	 * @author li_bin
	 * @Date: 2017年12月1日
	 * @throws 
	 * @param theString
	 * @return
	 */
	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	
	/**
	 * @param path根据指定字符集，读取文件
	 * @param charset
	 * @return
	 */
	public static String readerFile(String path,String charset){
		ByteArrayOutputStream baos = null ;
		FileInputStream fis = null ;
		String readString = null ;
		try {
			File file = new File(path) ;
			if(!file.isFile()){
				return null ;
			}
			baos = new ByteArrayOutputStream(1024) ;
			fis = new FileInputStream(file);
			byte[] b = new byte[1024] ;
			int len=-1 ;
			while((len=fis.read(b)) != -1){
				baos.write(b, 0, len);
			}
			readString = new String(baos.toByteArray(),charset) ;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return readString ;
		}
	}
	
	/** 
	 * @Description:判断对象是否为空
	 * 目前仅仅支持 String,Collection，Map，JSONArray，JSONObject
	 * @author li_bin
	 * @Date: 2017年12月29日
	 * @throws 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		/**		整体判断		*/
		if(obj == null) return true ;
		if(obj instanceof String){
			/**		字符串		*/
			if("".equals(obj)) return true ;
		}else if(obj instanceof Collection){
			/**		集合		*/
			int size = ((Collection)obj).size();
			if(size == 0) return true ;
		}else if(obj instanceof Map){
			/**		Map		*/
			int size = ((Map)obj).size();
			if(size == 0) return true ;
		}else if(obj instanceof JSONArray){
			/**		net.sf.json.JSONArray		*/
			int size = ((JSONArray)obj).size();
			if(size == 0) return true ;
		}else if(obj instanceof JSONObject){
			/**		net.sf.json.JSONObject		*/
			int size = ((JSONObject)obj).size();
			if(size == 0) return true ;
		}else{
			throw new UtilException("不支持的类型转换");
		}
		
		return false ;
	}
	
	/** 
	 * @Description:返回指定的日期格式
	 * @author li_bin
	 * @Date: 2018年1月9日
	 * @throws 
	 * @param format
	 * @return
	 */
	public static String getCurDateFormat(String format){
		/**		设置默认的时间格式		*/
		if(SSIUtils.isEmpty(format)) format = "yyyy-MM-dd HH:mm:ss" ;
		SimpleDateFormat sdf = new SimpleDateFormat(format) ;
		String dateFormat = sdf.format(new Date()) ;
		return dateFormat ;
		
	}
	
	
	/** 
	 * @Description:获取时间戳格式中的年月日
	 * yyyy-MM-dd HH:mm:ss
	 * @deprecated
	 * @author li_bin
	 * @Date: 2018年1月12日
	 * @throws 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateForString(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format) ;
		Calendar instance = Calendar.getInstance() ;
		String dateFormat = null ;
		try {
			instance.setTime(sdf.parse(date));
			int year = instance.get(Calendar.YEAR) ;
			int month = instance.get(Calendar.MONTH )+1 ;
			int day = instance.get(Calendar.DATE) ;
			dateFormat = replenishToLength(year+"", 4)+"-"+replenishToLength(month+"", 2)+"-"+replenishToLength(day+"", 2) ;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dateFormat ;
	}
	
	/** 
	 * @Description:获取时间戳格式中的时分秒
	 * yyyy-MM-dd HH:mm:ss
	 * @deprecated
	 * @author li_bin
	 * @Date: 2018年1月12日
	 * @throws 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getTimeForString(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format) ;
		Calendar instance = Calendar.getInstance() ;
		String dateFormat = null ;
		try {
			instance.setTime(sdf.parse(date));
			int hours = instance.get(Calendar.HOUR_OF_DAY) ;
			
			int minutes = instance.get(Calendar.MINUTE) ;
			int seconds = instance.get(Calendar.SECOND) ;
			dateFormat = replenishToLength(hours+"", 2)+":"+replenishToLength(minutes+"", 2)+":"+replenishToLength(seconds+"", 2) ;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return dateFormat ;
	}
	
	/** 
	 * @Description:针对不足指定位数的字符串前面补零
	 * @author li_bin
	 * @Date: 2018年1月17日
	 * @throws 
	 * @param source	指定字符串
	 * @param length	指定字符串长度
	 * @return
	 */
	private static String replenishToLength(String source,int length){
		String tar = "" ;
		int len = source.length() ;
		if(len>=length) return source ;
		for(int i=0;i<length-len;i++){
			tar +="0" ;
		}
		return tar+source ;
	}
	
	/** 
	 * @Description:分页工具类
	 * @author li_bin
	 * @Date: 2018年1月22日
	 * @throws 
	 * @param request
	 */
	public static void pagingUtil(HttpServletRequest request){
		String pageStr = request.getParameter("page") ;	//{page=1, rows=10}
		String rowsStr = request.getParameter("rows") ;	//{page=1, rows=10}
		int page = 0 ;
		int rows = 0 ;
		try {
			page=Integer.parseInt(pageStr);
			rows=Integer.parseInt(rowsStr);
		} catch (Exception e) {
			System.out.println(e.getMessage()) ;
		}
		if(page!=0 && rows!=0){
			PageInterceptor.startPage(page,rows) ;
		}
	}
	
	
	
	/** 
	 * @Description:打印byte数组
	 * @author li_bin
	 * @Date: 2018年1月22日
	 * @throws 
	 * @param b
	 * @return
	 */
	public static String printByteArray(byte[] b){
		StringBuilder stringBuilder = new StringBuilder() ;
		if(b.length <1) return "" ;
		for (int i = 0; i < b.length; i++) {
			stringBuilder.append(", "+b[i]) ;
		}
		stringBuilder.delete(0, 2) ;
		return stringBuilder.toString() ;
		
	}
	
	/** 
	 * @Description:用固定的编码读取一个文件
	 * @author li_bin
	 * @Date: 2018年1月24日
	 * @throws 
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException 
	 */
	public String readerFile(File file,String charset) throws IOException{
		FileInputStream is = new FileInputStream(file) ;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		byte[] b= new byte[1024];
		int len = -1 ;
		while((len=is.read(b)) != -1){
			baos.write(b, 0, len);
		}
		return new String(baos.toByteArray(),charset) ;
	}
	
}
