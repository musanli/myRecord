package com.main.action.comm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.dao.comm.entity.MenuManage;
import com.main.service.comm.LoginAction.LoginService;
import com.main.service.system.MenuService;
import com.main.util.SSILogger;
import com.main.util.SSIUtils;
import com.main.util.mybatis.plugin.PageInterceptor;

@Controller
@RequestMapping("menu")
public class MenuListAction {

	@Autowired
	LoginService loginService ;
	@Autowired
	MenuService menuService ;
	@RequestMapping("jsonlist")
	public void jsonlist(HttpServletRequest request,HttpServletResponse response,String menutype,String target) throws IOException{
		
		List<MenuManage> menuInfo = loginService.getMenuInfo(menutype) ;
		StringBuilder stringBuilder = new StringBuilder() ;
		stringBuilder.append("[");
		for (MenuManage menu:menuInfo) {
			stringBuilder.append(menuService.menuJson(menu, "target", target)) ;
			stringBuilder.append(",") ;
		}
		if(stringBuilder.lastIndexOf(",") != -1)	stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")) ;
		stringBuilder.append("]");
		String json = stringBuilder.toString() ;
		SSILogger.simpleInfo(json);
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
	}
}
