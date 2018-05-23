package com.main.service.util;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.util.spring.BeanLoader;

@Service
public class ReaderDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate ;
	/** 
	 * @Description: 
	 * @author li_bin
	 * @Date: 2017年12月11日
	 * @throws 
	 * @return
	 */
	public SqlSessionTemplate createSqlSessionFactoryBean(){
		return sqlSessionTemplate ;
	}
	
	public List selectList(String sql) throws Exception{
		/**此处：
		 * 		在Spring中getBean的时候因为SqlSessionFactoryBean实现了FactoryBean接口，spring在获取bean的时候回调用getObject接口，返回的对象。
		 * 		也就是说getBean（）获取到的不是配置的bean的class属性的对象。而是其定义的方法getObject（）返回的对象。
		 * 例如 ： BeanLoader.getBean("sqlSessionFactory")的正常返回值为SqlSessionFactoryBean 但是实际应用中返回的类型是SqlSessionFactory(的实现类)。
		 * 另外 鉴于SqlSessionTemplate为Spring建议用的dao层查询框架所以使用SqlSessionTemplate
		 * 	*/
		SqlSessionTemplate  factoryBean = (SqlSessionTemplate) BeanLoader.getBean("sqlSessionTemplate") ;
		List<Object> selectList = factoryBean.selectList(sql) ;
		return selectList;
	}

}
