package com.main.action.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.util.SSILogger;
import com.main.util.SSIUtils;

/**
 * 待解决事项
 * @author li_bin
 *
 */
@Controller
@RequestMapping("main/comm")
public class CommAwaitAction {

	/** 
	 * @Description:查看配置文件中的内容
	 * ${base}/main/comm/awaitList.do
	 * @author li_bin
	 * @Date: 2018年1月24日
	 * @throws 
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("awaitList")
	public String awaitList(ModelMap map) throws IOException{
		String classpath = SSIUtils.getRealClasspath();
		if(classpath.endsWith("WEB-INF/classes/")) classpath = classpath.replace("WEB-INF/classes/", "temp")+File.separator+"await.log" ;
		File file = new File(classpath) ;
		if(file==null || !file.isFile()) return "指定的文件不存在";
		List<String> list = new ArrayList<String>() ;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8")) ;
		String readerlin = " - " ;
		while((readerlin = br.readLine()) != null){
			list.add(SSIUtils.isEmpty(readerlin) ? " - " : readerlin );
		}
		map.put("await", list) ;
		return "main/common/await" ;
	}
}
