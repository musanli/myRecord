package com.test.action.comm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.dao.comm.entity.Dict;
import com.main.service.util.ReaderDao;
import com.main.util.SSILogger;
import com.main.util.SSIUtils;
import com.main.util.spring.BeanLoader;
import com.main.util.spring.SQLSessionFactory;
import com.test.service.comm.TestService;
@RequestMapping("test")
@Controller
public class TestAction {
	@Autowired
	ReaderDao reader ;
	@Autowired
	TestService service ;
	public static void main(String[] args) throws Exception {
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
		List<Map> list = bean.selectList("com.main.dao.comm.inter.DictMapper.SelectAllNameCode") ;
		for (Map map : list) {
			System.out.println("CODE : "+map.get("CODE")+" \t\tCODENAME"+map.get("CODENAME"));
		}
	}
	@Autowired TestService testService ;
	@RequestMapping("hello")
	public String hello(HttpServletRequest request,ModelMap map){
		//http://locahost:8080/record/test/hello.do
		List<Dict> list = testService.SelectAll() ;
		System.out.println(list.get(0).getName());
		System.out.println(list.get(0).getId());
		System.out.println(list.get(0).getValue());
		System.out.println(list.get(0).getRemark());
		map.put("name", "米特尼克") ;
		map.put("password", "root") ;
		map.put("like", "攻击海军国防部") ;
		map.put("address", "监狱") ;
		return "index" ;
	}
	
	/** 
	 * @Description: 测试freemarker
	 * @author li_bin
	 * @Date: 2017年12月13日
	 * @throws 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("freemarkerList")
	public String selectDictAll(HttpServletRequest request,HttpServletResponse response){
		List<Dict> selectAll = service.SelectAll() ;
		request.setAttribute("list", selectAll);
		Map<String,String> map = new HashMap<String, String>() ;
		map.put("key1", "value1") ;
		map.put("key2", "value2") ;
		map.put("key3", "value3") ;
		map.put("key4", "value4") ;
		map.put("key5", "value5") ;
		map.put("key6", "value6") ;
		request.setAttribute("map", map);
		request.setAttribute("name", "楚云飞");
		return "test/freemarkerList" ;
	}
	@RequestMapping("ui01")
	@ResponseBody
	public String easyui01(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		SSIUtils.printInfo(request);
		response.setCharacterEncoding("UTF-8");
		List<Map<String,String>> selectList = reader.createSqlSessionFactoryBean().selectList("com.main.dao.comm.inter.DictMapper.SelectAllNameCode") ;
		StringBuilder strbuilder = new StringBuilder() ;
		for (Map<String, String> map : selectList) {
			strbuilder.append(",{") ;
			strbuilder.append("\"CODE\":\""+map.get("CODE")+"\",\"CODENAME\":\""+map.get("CODENAME")+"\"" );
			strbuilder.append("}") ;
		}
		String tar ="["+ (strbuilder.toString().startsWith(",") ?strbuilder.toString().substring(1) : strbuilder.toString())+"]" ;
		tar = new String(tar.getBytes("UTF-8"),"ISO8859-1") ;
//		tar= URLEncoder.encode(tar, "UTF-8") ;
		System.out.println(tar);
		return tar ;
	}
	
}
