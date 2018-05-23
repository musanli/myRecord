package com.main.action.comm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.dao.comm.entity.Account;
import com.main.service.comm.AccountService;
import com.main.service.comm.LoginAction.LoginService;
import com.main.util.SSILogger;
import com.main.util.SSIUtils;

@Controller
public class LoginAction {
	
	@Autowired
	AccountService accountService ;
	@Autowired
	LoginService loginService ;
	@RequestMapping(value = "login")
	public String login(HttpServletRequest request ,ModelMap map){
		SSIUtils.printInfo(request);
		//username=a&userpass=a&code=&yzm=0
		String returnUrl = "" ;
		String username = request.getParameter("username") ;
		String password = request.getParameter("userpass") ;
		/**		基础空校验		*/
		if(SSIUtils.isEmpty(username)){
			request.setAttribute("errorMessage", "账号为空");
			return "index" ;
		}else if(SSIUtils.isEmpty(password)){
			request.setAttribute("errorMessage", "密码为空");
			return "index" ;
		}
		String code= request.getParameter("code") ;
		String yzm= request.getParameter("yzm") ;
		SSILogger.simpleInfo("username : "+username);
		SSILogger.simpleInfo("password : "+password);
		SSILogger.simpleInfo("code : "+code);
		SSILogger.simpleInfo("yzm : "+yzm);
		Account account = new Account() ;
		account.setUsername(username);
		account.setPassword(password);
		List<Account> selectByModel = accountService.selectByModel(account) ;
		if(selectByModel != null && selectByModel.size() > 0){
			HttpSession session = request.getSession();
			/**		当前项目路径		*/
			session.setAttribute("base", request.getContextPath());
			/**		当前用户级别		*/
			session.setAttribute("level", selectByModel.get(0).getUserlevel());
			/**		当前用户sessionId		*/
			SSILogger.simpleInfo("sessionID : "+session.getId());
			/**		指定登录成功跳转地址		*/
			returnUrl = "main/login" ;
			/**		移除错误信息		*/
			request.removeAttribute("errorMessage");
			/**		针对该用户的权限处理		*/
			loginService.setUserInfo(selectByModel.get(0), request);
			
		}else{
			account.setPassword(null);
			List<Account> selectByName = accountService.selectByModel(account) ;
			if(selectByName != null && selectByName.size() > 0){
				request.setAttribute("errorMessage", "密码错误");
			}else{
				request.setAttribute("errorMessage", "当前用户不存在");
			}
			returnUrl="index" ;
		}
		return returnUrl ;
	}
	
	/**
	 * 重新登录
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession() ;
		SSILogger.simpleInfo("session被注销ID为 : "+session.getId());
		session.setMaxInactiveInterval(0);//second
		return "redirect:index" ;
	}
	
	
	@RequestMapping("index")
	public String index(){
		return "redirect:index.htm" ;
	}
}
