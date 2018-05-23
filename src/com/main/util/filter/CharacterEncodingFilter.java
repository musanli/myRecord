package com.main.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CharacterEncodingFilter implements Filter{
	  private String encoding;
	  private boolean forceEncoding = false;
	  
	  public void setEncoding(String encoding)
	  {
	    this.encoding = encoding;
	  }
	  
	  public void setForceEncoding(boolean forceEncoding)
	  {
	    this.forceEncoding = forceEncoding;
	  }
	  
//	  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//	    throws ServletException, IOException
//	  {
//	    if ((this.encoding != null) && ((this.forceEncoding) || (request.getCharacterEncoding() == null)))
//	    {
//	      request.setCharacterEncoding(this.encoding);
//	      if (this.forceEncoding) {
//	        response.setCharacterEncoding(this.encoding);
//	      }
//	    }
//	    filterChain.doFilter(request, response);
//	  }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0 ;
		HttpServletResponse response = (HttpServletResponse) arg1 ;
//		System.out.println(request.getParameter("name"));
	    if ((this.encoding != null) && ((this.forceEncoding) || (request.getCharacterEncoding() == null)))
	    {
	      request.setCharacterEncoding(this.encoding);
	      if (this.forceEncoding) {
	        response.setCharacterEncoding(this.encoding);
	      }
	    }
	    filterChain.doFilter(request, response);
	  }

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.encoding = "UTF-8" ;
		//this.forceEncoding = true ;
	}}
