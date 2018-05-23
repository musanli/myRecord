package com.test;

import java.io.UnsupportedEncodingException;

import com.main.dao.comm.inter.MenuManageMapper;
import com.main.service.system.MenuService;
import com.main.util.spring.BeanLoader;

public class TestMain {

	public static void main(String[] args) throws UnsupportedEncodingException {
		MenuService bean = BeanLoader.getBean(MenuService.class) ;
		String menuId = bean.getMenuId("COMMON") ;
		System.out.println(menuId);
	}

}
