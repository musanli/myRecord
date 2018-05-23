package com.test.dao.comm;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.main.dao.comm.entity.MenuManage;
import com.main.dao.comm.inter.MenuManageMapper;
import com.main.util.spring.BeanLoader;

public class MybatisTest {

	public static void main(String[] args) {
		MenuManageMapper bean = BeanLoader.getBean(MenuManageMapper.class) ;
		MenuManage menuManage = new MenuManage() ;
//		menuManage.setCategory("LIFE");
		List<MenuManage> selectTopCategory = bean.selectTopCategory(menuManage) ;
		System.out.println(selectTopCategory.size());
		JSONArray jsonObject = new JSONArray() ;
		JSONArray fromObject = jsonObject.fromObject(selectTopCategory) ;
		System.out.println(fromObject.toString());
	}
}
