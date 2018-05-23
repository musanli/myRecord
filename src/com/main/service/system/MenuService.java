package com.main.service.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.comm.entity.MenuManage;
import com.main.dao.comm.inter.MenuManageMapper;
import com.main.dao.system.EasyUIComboTreePojo;
import com.main.util.SSIUtils;

@Service
public class MenuService {
	@Autowired
	MenuManageMapper menuManageMapper;

	public List<MenuManage> selectByModel(MenuManage record) {
		return menuManageMapper.selectByModel(record);
	}

	/**
	 * @Description:查询全部
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param record
	 * @return
	 */
	public List<MenuManage> selectAll() {
		return menuManageMapper.SelectAll();
	}

	/**
	 * @Description:根据id查询
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param record
	 * @return
	 */
	public MenuManage selectOneModel(String id) {
		return menuManageMapper.selectOneModel(id);
	}

	/**
	 * @Description:用于easyuicomboTree
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param id
	 * @return
	 */
	public List<EasyUIComboTreePojo> selectAllByAlias() {
		return menuManageMapper.selectAllByAlias();
	}

	/**
	 * @Description:向指定的MenuManage 对象中添加一组数据
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param menu
	 * @param key
	 *            添加数据的名称
	 * @param value
	 *            添加数据的值
	 * @return
	 */
	public String menuJson(MenuManage menu, String key, String value) {
		JSONObject jsonObject = new JSONObject();
		JSONObject fromObject = jsonObject.fromObject(menu);
		fromObject.put(key, value);
		fromObject.remove("category");
		fromObject.remove("isdisplay");
		fromObject.remove("isparent");
		fromObject.remove("primaryKey");
		fromObject.remove("remark");

		return fromObject.toString();
	}

	/**
	 * @Description:向指定的MenuManage 对象中添加一组数据
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param menu
	 * @param key
	 *            添加数据的名称
	 * @param value
	 *            添加数据的值
	 * @return
	 */
	public String menuJson(MenuManage menu) {
		JSONObject jsonObject = new JSONObject();
		JSONObject fromObject = jsonObject.fromObject(menu);
		fromObject.remove("primaryKey");
		return fromObject.toString();
	}

	public String getMenuId(String category){
		MenuManage menuManage = new MenuManage() ;
		menuManage.setCategory(category);
		List<MenuManage> menuManageList = menuManageMapper.selectByModel(menuManage) ;
		Collections.sort(menuManageList,new Comparator<MenuManage>() {
//			第一个参数小于,返回负整数
			public int compare(MenuManage o1, MenuManage o2) {
				/**		降序排列		*/
				return o2.getId().compareTo(o1.getId());
			}
		});
		String maxId = menuManageList.get(0).getId() ;
		String maxNumber = maxId.substring(category.length()) ;
		maxNumber = "000"+(Integer.parseInt(maxNumber)+1) ;
		maxNumber = category+maxNumber.substring( maxNumber.length()-3, maxNumber.length()) ;
		return maxNumber ;
	}
	/**
	 * @Description:组织easyui数据
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param list
	 * @return
	 */
	public String formatComboTree(List<EasyUIComboTreePojo> list) {
		if (list == null || list.size() == 0)
			return null;
		JSONArray jsonArray = new JSONArray();
		for (EasyUIComboTreePojo combo : list) {
			if (combo.getPid() == null) {
				JSONObject temp = new JSONObject();
				temp.put("id", combo.getId());
				temp.put("pid", "~");
				temp.put("text", combo.getText());
				temp.put("state", "open");
				putTreeChild(temp, list, combo.getId());
				jsonArray.add(temp);
			}
		}

		String json = jsonArray.toString();
		return json;
	}

	private void putTreeChild(JSONObject jsonObject,
			List<EasyUIComboTreePojo> list, String id) {
		JSONArray array = new JSONArray();
		JSONObject temp = null;
		for (EasyUIComboTreePojo combo : list) {
			if (id.equals(combo.getPid())) {
				temp = new JSONObject();
				temp.put("id", combo.getId());
				temp.put("pid", id);
				temp.put("text", combo.getText());
				/** 关闭所有节点、节点集 */
				temp.put("state", "closed");
				putTreeChild(temp, list, combo.getId());
				array.add(temp);
			}
		}
		if (!array.isEmpty()) {
			jsonObject.put("children", array);
		} else {
			/** 如果目标为节点则将其打开 */
			jsonObject.put("state", "open");
		}

	}

	public boolean validateMenu(MenuManage menu,StringBuilder stringBuilder) {
		if(!menuNotEmpty(menu, stringBuilder))return false ;
		boolean flag = true ;
		try {
			List<MenuManage> pidList = menuManageMapper.selectByPid(menu.getPid()) ;
			if(SSIUtils.isEmpty(pidList))  flag = false ;
		} catch (Exception e) {
		}

		return flag;
	}

	/** 
	 * @Description:验证目标对象是否为空
	 * @author li_bin
	 * @Date: 2017年12月29日
	 * @throws 
	 * @param menu
	 * @param stringBuilder
	 * @return
	 */
	public boolean menuNotEmpty(MenuManage menu, StringBuilder stringBuilder) {
		boolean flag = true ;
		if(menu == null){
			flag = false ;
			stringBuilder.append("MenuManage目标对象为空;");
		}else if(SSIUtils.isEmpty(menu.getCategory())){
			flag = false ;
			stringBuilder.append("类型为空;");
		}else if(SSIUtils.isEmpty(menu.getId())){
			flag = false ;
			stringBuilder.append("ID为空;");
		}else if(SSIUtils.isEmpty(menu.getIsdisplay())){
			flag = false ;
			stringBuilder.append("是否显示标识为空;");
		}else if(SSIUtils.isEmpty(menu.getName())){
			flag = false ;
			stringBuilder.append("Name为空;");
		}else if(SSIUtils.isEmpty(menu.getPid())){
			flag = false ;
			stringBuilder.append("PID为空;");
		}else if(SSIUtils.isEmpty(menu.getUrl())){
			flag = false ;
			stringBuilder.append("URL为空;");
		}
		return flag;
	}
}
