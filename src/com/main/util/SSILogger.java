package com.main.util;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
public class SSILogger {
	/**		设计模式需要		*/
	private Logger logger ;
	
	/**		默认logger名称为		*/
	private static final String defaultName="SSILogger" ;
	
	/**		私有化构造方法		*/
	private SSILogger(String logName2Str){
		this.logger = Logger.getLogger(logName2Str) ;
	}
	
	/**		私有化构造方法		*/
	private SSILogger(Class<Object> LogName2Cla){
		this.logger = Logger.getLogger(LogName2Cla) ;
	}
	
	

	/**		对外公开创建实例		*/
	public static SSILogger getLogger(String logName2Str){
		SSILogger ssiLogger = new SSILogger(logName2Str) ;
		return ssiLogger;
	}
	
	/**		对外公开创建实例		*/
	public static SSILogger getLogger(Class LogName2Cla){
		return new SSILogger(LogName2Cla) ;
	}
	
	/** 
	 * Debug 级别
	 * @Description: 创建简单的Logger
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public static void simpleDebug(Object message){
		Logger.getLogger(defaultName).debug(message);
	}
	
	/** 
	 * Info 级别
	 * @Description: 创建简单的Logger
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public static void simpleInfo(Object message){
		Logger.getLogger(defaultName).info(message);
	}
	
	/** 
	 * @Description: 输出info级别日志
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public void Info(Object message){
		logger.info(message);
	}
	/** 
	 * @Description: 输出debug级别日志
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public void Debug(Object message){
		logger.debug(message);
	}
	
	/** 
	 * @Description: 输出warn级别日志
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public void Warn(Object message){
		logger.warn(message);
	}
	
	
	/** 
	 * @Description: 输出error级别日志
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public void Error(Object message){
		logger.error(message);
	}
	
	/** 
	 * @Description: 输出fatal级别日志
	 * @author li_bin
	 * @Date: 2017年12月8日
	 * @throws 
	 * @param message
	 */
	public void fatal(Object message){
		logger.fatal(message);
	}


	
	
	
	
	public static void main(String[] args) {
		
		long newTime = System.currentTimeMillis();
		System.out.println(newTime);
		Logger logger = Logger.getLogger("吾乃常山赵子龙") ;
		SimpleDateFormat sdf = new SimpleDateFormat("当前时间 :"+"yyyy-MM-dd HH:mm:ss:SSS") ;
		String currentDate = sdf.format(new Date()) ;
	       //  记录 debug 级别的信息   
        logger.debug(currentDate);  
        //  记录 info 级别的信息   
        logger.info(currentDate);  
        //  记录 error 级别的信息   
        logger.error(currentDate);
        logger.warn(currentDate);
        logger.fatal(currentDate);
        
        System.out.println(currentDate);
//        throw new RuntimeException("大老大老大");
        System.out.println("(SSILogger.java:19)");
        
	}
}

