package com.test.action.comm;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import com.main.dao.comm.entity.Diary;
import com.main.dao.comm.entity.DiaryWithBLOBs;
import com.main.dao.comm.entity.Dict;
import com.main.dao.comm.inter.DiaryMapper;
import com.main.dao.comm.inter.DictMapper;
import com.main.util.spring.BeanLoader;
import com.test.service.comm.TestService;

public class TestMain {
	public static void main(String[] args) {
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//		DictMapper bean = (DictMapper) applicationContext.getBean("dictMapper") ;
//		System.out.println(bean);
//		List<Dict> list = bean.SelectAll();
//		System.out.println(list.get(0).getName());
//		System.out.println(list.get(0).getId());
//		System.out.println(list.get(0).getValue());
//		System.out.println(list.get(0).getRemark());
//		TestService bean = (TestService) BeanLoader.getApplicationContext().getBean("testService") ;
		SqlSessionTemplate bean = (SqlSessionTemplate)BeanLoader.getApplicationContext().getBean("sqlSessionTemplate") ;
		List<Map> list = bean.selectList("com.main.dao.comm.inter.DictMapper.selectAllNameCode") ;
		for (Map map : list) {
			System.out.println("CODE : "+map.get("CODE")+" \t\tCODENAME"+map.get("CODENAME"));
		}
		DictMapper bean2 = BeanLoader.getBean(DictMapper.class);
		Dict selectOneModel = bean2.selectOneModel(1) ;
		System.out.println(selectOneModel);
	}
	@Test
	public void test01(){
		TestService bean = BeanLoader.getBean(TestService.class) ;
		List<Dict> selectAll = bean.SelectAll() ;
		System.out.println(selectAll);
	}
	
	@Test
	public void test02(){
		DiaryMapper bean = BeanLoader.getBean(DiaryMapper.class) ;
		 DiaryWithBLOBs selectOneModel = bean.selectOneModel("1") ;
		System.err.println(selectOneModel.getContent());
	}
}
