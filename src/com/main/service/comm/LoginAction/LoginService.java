package com.main.service.comm.LoginAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.comm.UserRoleDao;
import com.main.dao.comm.entity.Account;
import com.main.dao.comm.entity.MenuManage;
import com.main.dao.comm.inter.MenuManageMapper;
import com.main.service.system.MenuService;

@Service
public class LoginService {

	@Autowired
	MenuManageMapper menuManageMapper ;
	@Autowired
	MenuService menuService ;
	/** 
	 * @Description:获取菜单项
	 * @author li_bin
	 * @Date: 2017年12月25日
	 * @throws 
	 * @return
	 */
	public List<UserRoleDao> getUserInfo(Account account){
		List<UserRoleDao> list = new ArrayList<UserRoleDao>() ;
		MenuManage manage = new MenuManage() ;
		String userlevel = account.getUserlevel().toLowerCase();
		manage.setCategory("admin".equals(userlevel)? null : userlevel.toUpperCase());
		List<MenuManage> categoryList = menuManageMapper.selectTopCategory(manage) ;
		for (MenuManage menu : categoryList) {
			String category = menu.getCategory().toLowerCase();
			UserRoleDao dao1 = new UserRoleDao(menu.getName(), category+"Id", category+"Setting", category) ;
			list.add(dao1) ;
		}
		return list ;
	}
	/** 
	 * @Description:获取当前菜单项的具体内容
	 * {id:"M053001",
	 * pid:"M053",
	 * name:"保单信息查询",
	 * _url:"ddw/PolicyQuery.do",target:""}
	 * @author li_bin
	 * @Date: 2017年12月25日
	 * @throws 
	 * @param menutype
	 * @return
	 */
	public List<MenuManage> getMenuInfo(String menutype){
		List<MenuManage> menuList = new ArrayList<MenuManage>(10) ;
		MenuManage menuManage = new MenuManage() ;
		menuManage.setCategory(menutype.toUpperCase());
		menuManage.setIsdisplay("1");
		menuList = menuManageMapper.selectSonMenu(menuManage) ;
		return menuList ;
	}
	
	
	/**
	 * 用户登录成功后绑定用户信息，
	 * @param account
	 * @param request
	 */
	public void setUserInfo(Account account,HttpServletRequest request){
		request.setAttribute("userRole", getUserInfo(account));
		/**		登录标识		*/
		request.getSession().setAttribute("islogin", true);
	}
}
