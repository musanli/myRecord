package com.main.util.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLoader {


	/** 
	 * @Description: 获取ApplicationContext对象
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	}
	
	/** 
	 * @Description: 根据名称获取bean
	 * @author li_bin
	 * @Date: 2017年12月11日
	 * @throws 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		return getApplicationContext().getBean(name) ;
	}
	
	
	/** 
	 * @Description: 根据类型获取bean
	 * @author li_bin
	 * @Date: 2017年12月11日
	 * @throws 
	 * @param name
	 * @return
	 */
	public static<T> T getBean(Class<T> name){
		return getApplicationContext().getBean(name) ;
	}
	
	
	/** 
	 * @Description: 根据类型获取bean
	 * @author li_bin
	 * @Date: 2017年12月11日
	 * @throws 
	 * @param name
	 * @return
	 */
	public static<T> T getBean(String name,Class<T> calss){
		return getApplicationContext().getBean(name,calss) ;
	}
}
