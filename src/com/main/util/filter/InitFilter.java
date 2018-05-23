package com.main.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.util.SSILogger;

/**
 * Servlet Filter implementation class InitFilter
 */
public class InitFilter implements Filter {

    /**
     * Default constructor. 
     */
    public InitFilter() {
        SSILogger.getLogger(InitFilter.class).Debug(".......自定义过滤器实例化.......");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		SSILogger.getLogger(InitFilter.class).Debug(".......自定义过滤器销毁.......");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		Boolean islogin= (Boolean)req.getSession().getAttribute("islogin") ;
		islogin = islogin==null ? false : islogin ;
		String servletPath = req.getServletPath() ;
		boolean isrest = servletPath.indexOf("/rest/") != -1 || servletPath.indexOf("\\rest\\") != -1 ;
		SSILogger.getLogger(InitFilter.class).Info("========= 业务请求地址："+servletPath);
		if(islogin){
			//已经登录
		}else if (isrest){
			//rest风格
		}else if(servletPath.lastIndexOf("login.do") != -1){
			/**		登录接口排除校验		*/
		}else{
//			request.getRequestDispatcher("/rest/index").forward(request, response);
			res.sendRedirect(req.getContextPath()+"/rest/index.htm");
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		SSILogger.getLogger(InitFilter.class).Debug(".......自定义过滤器初始化.......");
		ServletContext servletContext = fConfig.getServletContext();
	}

}
