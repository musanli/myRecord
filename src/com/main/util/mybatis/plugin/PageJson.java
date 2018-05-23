package com.main.util.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.main.util.SSILogger;
import com.main.util.SSIUtils;
import com.main.util.json.JsonValueProcessorImpl;


/**
 * 将制定格式转换为分页所需的日期格式
 * 另外Json日期转换为一般日期：json日期：/Date(1316756746000)
 * 需要对日期个是特定处理
 * @author li_bin
 *
 */
public class PageJson {

	public static final String encodeObject2GridJson(List list) {
		String jsonString = "";
		if (SSIUtils.isEmpty(list)) {
			jsonString+="{\"total\":0,\"rows\":[]}";
		} else {
				if (list instanceof Page) {
					Page p = (Page) list;
					String jsonArray = encodeObject2Json(list,"yyyy-MM-dd");
					jsonString+="{\"total\":"+p.getTotal()+",\"rows\":";
					jsonString+= jsonArray.toString();
					jsonString+="}";
				}else{
					String jsonArray = encodeObject2Json(list,"yyyy-MM-dd");
					jsonString+="{\"total\":"+list.size()+",\"rows\":";
					jsonString+= jsonArray.toString();
					jsonString+="}";
				}
				
		}
			SSILogger.getLogger(PageJson.class).Info("序列化后的JSON资料输出:\n" + jsonString);
		return jsonString;
	}
	
	
	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (SSIUtils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		SSILogger.getLogger(PageJson.class).Info("序列化后的JSON资料输出:\n" + jsonString);
		return jsonString;
	}
	

}
