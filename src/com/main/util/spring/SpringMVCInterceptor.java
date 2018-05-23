package com.main.util.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.main.util.SSILogger;

public class SpringMVCInterceptor implements HandlerInterceptor  {

	/**
	 * 执行方法后
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		SSILogger.getLogger(SpringMVCInterceptor.class).Debug("Debug");
	}


	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		SSILogger.getLogger(SpringMVCInterceptor.class).Debug("postHandle()"+arg0.getContextPath());
//		arg0.getSession().setAttribute("basesession", arg0.getContextPath());
		
	}

	/**
	 * 在处理器映射器执行之前执行
	 * return false;表示拦截 return true;标识方行；
	 * 一个栈中多个拦截器，凡是preHandle 返回true 则afterCompletion（）肯定执行
	 */
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		SSILogger.getLogger(SpringMVCInterceptor.class).Debug("在处理器映射器执行之前执行");
		return true;
	}

}
