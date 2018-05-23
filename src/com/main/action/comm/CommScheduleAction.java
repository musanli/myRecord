package com.main.action.comm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.dao.comm.entity.Schedule;
import com.main.dao.comm.entity.ScheduleWithBLOBs;
import com.main.dao.comm.inter.ScheduleMapper;
import com.main.service.comm.CommScheduleService;
import com.main.util.SSIUtils;
import com.main.util.mybatis.plugin.PageJson;

/**
 * 日程信息Action
 * @author li_bin
 *
 */
@Controller
@RequestMapping("main/comm")
public class CommScheduleAction {
	@Autowired
	ScheduleMapper scheduleMapper ;
	@Autowired
	CommScheduleService scheduleService ; 
	/** 
	 * @Description:回显日记信息
	 * @author li_bin
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @Date: 2018年1月10日
	 * @throws 
	 * @return
	 *
	 */
	@RequestMapping("scheduleList")
	@ResponseBody
	public String scheduleList(HttpServletRequest request ) throws UnsupportedEncodingException, IOException{
		/**		分页工具		*/
		SSIUtils.pagingUtil(request);
		SSIUtils.printInfo(request);
		String scheduleState = request.getParameter("scheduleState") ;
		String startDate = request.getParameter("startDate") ;
		String endDate = request.getParameter("endDate") ;
		String scheduleSort = request.getParameter("scheduleSort") ;
		String scheduleContent = request.getParameter("scheduleContent") ;
		ScheduleWithBLOBs schedule = new ScheduleWithBLOBs() ;
		schedule.setTaskenddate(endDate);
		schedule.setTaskstartdate(startDate);
		schedule.setContent( SSIUtils.isEmpty(scheduleContent) ? null : "%"+scheduleContent+"%");
		schedule.setSort( SSIUtils.isEmpty(scheduleSort) ? null : "%"+scheduleSort+"%");
		schedule.setIsdelete("N");
//		schedule.setOutline( SSIUtils.isEmpty(scheduleContent) ? null : "%"+scheduleContent+"%");
		schedule.setFinish(SSIUtils.isEmpty(scheduleState) ? "N" : scheduleState);
		List<ScheduleWithBLOBs> model = scheduleMapper.selectBLOBByModel(schedule) ;
		/**		起止时间	将值设置进taskstartdate中	*/
		scheduleService.setStart_End(model) ;
		String encodeObject2GridJson = PageJson.encodeObject2GridJson(model) ;
		return encodeObject2GridJson ;
	}
	
	
	/** 
	 * @Description:日程管理页面。
	 * @url:main/comm/scheduleManage.do
	 * @author li_bin
	 * @Date: 2018年1月12日
	 * @throws 
	 * @param scheduleId
	 * @param map
	 * @return
	 */
	@RequestMapping("scheduleManage")
	public String scheduleManage(String scheduleId,ModelMap map,HttpServletRequest request){
		SSIUtils.printInfo(request);
		String path = "main/common/scheduleManage" ;
		/**		处理添加请求		*/
		if(SSIUtils.isEmpty(scheduleId)) return path ;
		/**		处理修改请求		*/
		Schedule scheduleModel = scheduleMapper.selectOneModel(scheduleId) ;
		map.put("schedule", scheduleModel) ;
		return path ;
	}
	
	/** 
	 * @Description:变更日程提交入口
	 * @author li_bin
	 * @throws UnsupportedEncodingException 
	 * @Date: 2018年1月12日
	 * @throws 
	 * @return
	 */
	@RequestMapping("updateSchedule")
	@ResponseBody
	public String updateSchedule(HttpServletRequest request) throws UnsupportedEncodingException{
		SSIUtils.printInfo(request);
		JSONObject json = new JSONObject() ;
		json.put("code", false) ;
		String scheduleId = request.getParameter("scheduleId") ;
		String scheduleSort = request.getParameter("scheduleSort") ;
		String scheduleTaskStartDate = request.getParameter("scheduleTaskStartDate") ;
		String scheduleTaskEndDate = request.getParameter("scheduleTaskEndDate") ;
		String isFinish = request.getParameter("isFinish") ;
		String recordContent = request.getParameter("recordContent") ;
		String scheduleOutline = request.getParameter("scheduleOutline") ;
		if(SSIUtils.isEmpty(recordContent) || SSIUtils.isEmpty(scheduleOutline)) {
			json.put("msg", "数据不完整") ;
			return json.toString() ;
		}

		int i = scheduleService.saveSchedule(scheduleId, scheduleSort, scheduleTaskStartDate, scheduleTaskEndDate, isFinish, recordContent, scheduleOutline) ;
		if(i > 0){
			json.put("code", true) ;
			json.put("msg", "日程修改成功") ;
		}else{
			json.put("msg", "日程更改失败") ;
		}
		return json.toString();
	}
	
	/** 
	 * @Description:日程删除项
	 * @author li_bin
	 * @Date: 2018年1月12日
	 * @throws 
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping("deleteSchedule")
	@ResponseBody
	public String deleteSchedule(String scheduleId,HttpServletRequest request) {
		SSIUtils.printInfo(request);
		JSONObject json = new JSONObject() ;
		json.put("code", true) ;
		if(scheduleService.deleteScheduleById(scheduleId)){
			json.put("msg", "删除成功") ;
		}else{
			json.put("msg", "删除失败") ;
			json.put("code", false) ;
		}
		
		return json.toString();
	}
	
	
	

}
