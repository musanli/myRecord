package com.main.action.comm;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("rest")
public class CommonAction {
	/** 
	 * @Description:  匹配欢迎页
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		return "index";
	}

	/** 
	 * @Description: 访问Web-INF一级匹配
	 * http://localhost:8080/MyRecord/rest/a.htm
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @param pathid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{param}",method = RequestMethod.GET) 
	public String restOneParam(@PathVariable String param ,HttpServletRequest request, ModelMap model){
		System.out.println("请求路径 ： "+request.getRequestURI());
		/*	SpringMVC自定义跳转会将request中的参数过滤		*/
		Map<String,String[]> parameterMap = request.getParameterMap() ;
		/*		遍历集合最好有map的内部类entrymap，这样速度回稍块		*/
		Set<Entry<String,String[]>> entrySet = parameterMap.entrySet() ;
		for (Entry<String,String[]> entry : entrySet) {
			model.put(entry.getKey(), entry.getValue() != null && entry.getValue().length > 0 ? entry.getValue()[0] : null);
		}
		/*		直接访问WebINF下的jsp页面		*/
		return param ;
	}
	/** 
	 * @Description: 访问Web-INF二级匹配
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @param paramOne
	 * @param paramTwo
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{paramOne}/{paramTwo}",method = RequestMethod.GET) 
	public String restTwoParam(@PathVariable String paramOne ,@PathVariable String paramTwo ,HttpServletRequest request, ModelMap model){
		System.out.println("请求路径 ： "+request.getRequestURI());
		/*	SpringMVC自定义跳转会将request中的参数过滤		*/
		Map<String,String[]> parameterMap = request.getParameterMap() ;
		/*		遍历集合最好有map的内部类entrymap，这样速度回稍块		*/
		Set<Entry<String,String[]>> entrySet = parameterMap.entrySet() ;
		for (Entry<String,String[]> entry : entrySet) {
			model.put(entry.getKey(), entry.getValue() != null && entry.getValue().length > 0 ? entry.getValue()[0] : null);
		}
		/*		直接访问WebINF下的jsp页面		*/
		return paramOne+File.separator+paramTwo ;
	}
	
	/** 
	 * @Description: 访问Web-INF三级匹配
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @param paramOne
	 * @param paramTwo
	 * @param paramThree
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{paramOne}/{paramTwo}/{paramThree}",method = RequestMethod.GET) 
	public String restThreeParam(@PathVariable String paramOne ,@PathVariable String paramTwo,@PathVariable String paramThree ,HttpServletRequest request, ModelMap model){
		System.out.println("请求路径 ： "+request.getRequestURI());
		/*	SpringMVC自定义跳转会将request中的参数过滤		*/
		Map<String,String[]> parameterMap = request.getParameterMap() ;
		/*		遍历集合最好有map的内部类entrymap，这样速度回稍块		*/
		Set<Entry<String,String[]>> entrySet = parameterMap.entrySet() ;
		for (Entry<String,String[]> entry : entrySet) {
			model.put(entry.getKey(), entry.getValue() != null && entry.getValue().length > 0 ? entry.getValue()[0] : null);
		}
		/*		直接访问WebINF下的jsp页面		*/
		return paramOne+File.separator+paramTwo+File.separator+paramThree ;
	}
}
