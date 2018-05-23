package com.main.action.system;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.dao.comm.entity.MenuManage;
import com.main.dao.comm.inter.MenuManageMapper;
import com.main.dao.system.EasyUIComboTreePojo;
import com.main.service.system.MenuService;
import com.main.util.SSILogger;
import com.main.util.SSIUtils;
import com.main.util.mybatis.plugin.Page;
import com.main.util.mybatis.plugin.PageInterceptor;
import com.main.util.mybatis.plugin.PageJson;
import com.main.util.spring.BeanLoader;

/**
 * 当前类用于菜单管理
 * 
 * @author li_bin
 * 
 */
@Controller
@RequestMapping("main/menu")
public class MenuAction {

	@Autowired
	MenuService menuService;
	
	@Autowired
	MenuManageMapper menuManageMapper ;

	@RequestMapping("showList")
	public String showList(HttpServletRequest request,
			HttpServletResponse response, String id) {
		SSIUtils.printInfo(request);
		System.out.println("id : " + id);
		request.setAttribute("menuid", id);
		MenuManage menuManage = new MenuManage();
		request.setAttribute("menuid", id);
		return "main/system/menuView";
	}

	/**
	 * @Description:显示所有节点，包括祖节点，以及不显示节点。
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param response
	 * @param id
	 * @param folder
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showAll")
	public void showAll(HttpServletRequest request,
			HttpServletResponse response, String id, String folder,
			String target) throws UnsupportedEncodingException, IOException {
		SSIUtils.printInfo(request);
		List<MenuManage> selectAll = menuService.selectAll();
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (MenuManage menu : selectAll) {
			json.append(menuService.menuJson(menu, "target", ""));
			json.append(",");
		}
		json.deleteCharAt(json.lastIndexOf(","));
		json.append("]");
		SSILogger.simpleInfo(json.toString());
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
	}

	/**
	 * @Description:显示所有节点，包括祖节点，以及不显示节点。
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param response
	 * @param id
	 * @param folder
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showMenu")
	public void showMenu(HttpServletRequest request,HttpServletResponse response, String id, String folder,String target) throws UnsupportedEncodingException, IOException {
		
		SSIUtils.printInfo(request);
		List<MenuManage> selectAll = menuService.selectAll();
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (MenuManage menu : selectAll) {
			json.append(menuService.menuJson(menu));
			json.append(",");
		}
		json.deleteCharAt(json.lastIndexOf(","));
		json.append("]");
		String returnJosn = json.toString() ;
		SSILogger.getLogger(MenuAction.class).Info("showMenu() :" + returnJosn);
		response.getOutputStream().write(returnJosn.toString().getBytes("UTF-8"));
	}
	
	/**
	 * @Description:显示所有节点，包括祖节点，以及不显示节点。
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param response
	 * @param menuid
	 * @param folder
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */

	@RequestMapping("showMenuPage")
	public void showMenuPage(String page,String rows ,HttpServletRequest request,HttpServletResponse response, String menuid) throws UnsupportedEncodingException, IOException {
		if(!(SSIUtils.isEmpty(page) || SSIUtils.isEmpty(rows)) ){
		int pageNum = Integer.parseInt(request.getParameter("page")) ;
		int pageSize = Integer.parseInt(request.getParameter("rows")) ;
		PageInterceptor.startPage(pageNum, pageSize) ;
		}
		
		List<MenuManage> selectAll =null ;
		SSIUtils.printInfo(request);
		if(SSIUtils.isEmpty(menuid)){
			selectAll = menuService.selectAll();
		}else{
			MenuManage selectOneModel = menuManageMapper.selectOneModel(menuid) ;
			List<MenuManage> selectByPid = menuManageMapper.selectByPid(menuid) ;
			selectByPid.add(selectOneModel) ;
			selectAll = selectByPid ;
		}
		
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (MenuManage menu : selectAll) {
			json.append(menuService.menuJson(menu));
			json.append(",");
		}
		json.deleteCharAt(json.lastIndexOf(","));
		json.append("]");
		String returnJosn = PageJson.encodeObject2GridJson(selectAll) ;
		SSILogger.getLogger(MenuAction.class).Info("showMenu() :" + returnJosn);
		response.getOutputStream().write(returnJosn.toString().getBytes("UTF-8"));
	}

	/**
	 * @Description:新增、修改的入口
	 * @author li_bin
	 * @Date: 2017年12月27日
	 * @throws
	 * @param request
	 * @return
	 */
	@RequestMapping("toupdateMenu")
	public String updatMenu(ModelMap model, String menuid) {
		if (menuid == null || "".equals(menuid)) {
			// 此处说明该请求为add请求
		} else {
			// 此处说明该请求为修改请求___为页面赋初值
			MenuManage menuManage = menuService.selectOneModel(menuid);
			if (menuManage != null) {
				model.put("menudisplay", menuManage.getIsdisplay());
				model.put("menufolder", menuManage.getIsparent());
				model.put("menuname", menuManage.getName());
				model.put("menupath", menuManage.getUrl());
				model.put("menuremark", menuManage.getRemark());
			}
			model.put("menu", menuManage) ;
		}
		return "main/system/updateMenu";
	}
	
	
	@RequestMapping("ejsonlist")
	public void ejsonlist(HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		MenuService bean = BeanLoader.getBean(MenuService.class) ;
		List<EasyUIComboTreePojo> selectAllByAlias = bean.selectAllByAlias() ;
		System.out.println(selectAllByAlias);
		String formatComboTree = bean.formatComboTree(selectAllByAlias) ;
		response.getOutputStream().write(formatComboTree.getBytes("UTF-8"));
	}
	
	/**
	 * @throws IOException 
	 * @throws UnsupportedEncodingException  
	 * @Description:获取菜单类型节点
	 * @author li_bin
	 * @Date: 2017年12月28日
	 * @throws 
	 */
	@RequestMapping(value = "ecategory",produces = "application/json; charset=utf-8")
	@ResponseBody
	public String menuCategory(HttpServletRequest request ,ModelMap map ,HttpServletResponse response,String menutype) throws UnsupportedEncodingException, IOException{
		SSIUtils.printInfo(request);
		JSONArray jsonArray = new JSONArray() ;
		JSONObject tar = null ;
		MenuManage menuManageBean = new MenuManage() ;
		menuManageBean.setPid("HIGHEST");
		List<MenuManage> selectAll = menuService.selectByModel(menuManageBean) ;
		for(MenuManage menu:selectAll){
			JSONObject jsonObject = new JSONObject() ;
			jsonObject.put("value", menu.getCategory()) ;
			jsonObject.put("text", menu.getName()) ;
			if(menu.getCategory().equals(menutype)){
				tar=jsonObject ;
			}else{
				jsonArray.add(jsonObject) ;
			}
		}
		jsonArray.add(0, tar);
		return jsonArray.toString() ;
//		response.getOutputStream().write(jsonArray.toString().getBytes("UTF-8"));
	}
	
	@RequestMapping("menu_json")
	@ResponseBody
	public String menu_json(String menutid){
		MenuManage menu = menuManageMapper.selectOneModel(menutid) ;
		JSONObject json = new JSONObject() ;
		if(menu != null && !SSIUtils.isEmpty(menutid)){
			
		json.put("menuid", menu.getId()) ;
		json.put("menuname", menu.getName()) ;
		json.put("menuurl", menu.getUrl()) ;
		json.put("menuparentid", menu.getPid()) ;
		json.put("menucategory", menu.getCategory()) ;
		json.put("menuremark", menu.getRemark()) ;
		json.put("menudisplay", menu.getIsdisplay()) ;
		json.put("menufolder", menu.getIsparent()) ;
		}
		return json.toString() ;
	}
	@RequestMapping("updateMenu")
	public void updateMenu(HttpServletRequest request,ModelMap map ,HttpServletResponse response) throws IOException{
		SSIUtils.printInfo(request);
		JSONObject json = new JSONObject() ;
		boolean next = true ;
		json.put("code", false) ;
		
		StringBuilder stringBuilder = new StringBuilder() ;
		MenuManage menu = new MenuManage((String)request.getParameter("menuid"), (String)request.getParameter("menuparentid"), (String)request.getParameter("menuname"), 
				(String)request.getParameter("menuurl"), (String)request.getParameter("menucategory"), (String)request.getParameter("menufolder"),
				(String)request.getParameter("menudisplay"), (String)request.getParameter("menuremark")) ;
		
		MenuManage menuList = menuManageMapper.selectOneModel(menu.getId()) ;
		MenuManage menuPidList = menuManageMapper.selectOneModel(menu.getPid()) ;
		if(menuPidList.getPid() == null){
			next=false;
			stringBuilder.append("当前节点不允许增加") ;
		}
		if(next && SSIUtils.isEmpty(menu.getCategory())) menu.setCategory(menuPidList.getCategory());
		if(next && menuList == null) menu.setId(menuService.getMenuId(menu.getCategory()));
		if(next) next = menuService.validateMenu(menu, stringBuilder) ;
		if(next){
			int i = menuList== null ? menuManageMapper.insert(menu): menuManageMapper.update(menu);
			if(i<1) json.put("msg", "数据修改失败") ;
			json.put("code",true) ;
			json.put("msg", "数据修改成功") ;
		}else{
			json.put("msg", stringBuilder.toString()) ;
		}
		
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));
		
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public String deleteOneMenu(HttpServletRequest request,MenuManage menu){
		int deleteByModel = menuManageMapper.deleteByPrimaryKey(menu.getId()) ;
		JSONObject json = new JSONObject() ;
		if(deleteByModel <1){
			json.put("success", false) ;
			json.put("msg", "删除失败") ;
		}else{
			json.put("success", true) ;
			json.put("msg", "删除成功") ;
		}
		
		return json.toString() ;
		
	}

}
