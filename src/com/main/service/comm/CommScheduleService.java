package com.main.service.comm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.comm.entity.Schedule;
import com.main.dao.comm.entity.ScheduleWithBLOBs;
import com.main.dao.comm.inter.ScheduleMapper;
import com.main.util.SSIUtils;

@Service
public class CommScheduleService {
	
	@Autowired
	ScheduleMapper scheduleMapper ;
	
	public int saveSchedule(String scheduleId,String scheduleSort, String scheduleTaskStartDate,
			String scheduleTaskEndDate, String isFinish, String recordContent,String scheduleOutline){
		int len = -1 ;
		ScheduleWithBLOBs schedule = new ScheduleWithBLOBs() ;
		schedule.setCreatedate(SSIUtils.getCurDateFormat("yyyy-MM-dd"));
		schedule.setCratetime(SSIUtils.getCurDateFormat("HH:mm:ss"));
		schedule.setFinish(isFinish);
		schedule.setId(SSIUtils.isEmpty(scheduleId) ? getScheduleId(): scheduleId);
		schedule.setSort(scheduleSort);
		schedule.setTaskenddate(SSIUtils.getDateForString(scheduleTaskEndDate, "yyyy-MM-dd HH:mm:ss"));
		schedule.setTaskendtime(SSIUtils.getTimeForString(scheduleTaskEndDate, "yyyy-MM-dd HH:mm:ss"));
		schedule.setTaskstartdate(SSIUtils.getDateForString(scheduleTaskStartDate, "yyyy-MM-dd HH:mm:ss"));
		schedule.setTaskstarttime(SSIUtils.getTimeForString(scheduleTaskStartDate, "yyyy-MM-dd HH:mm:ss"));
		
		schedule.setContent(recordContent);
		schedule.setOutline(scheduleOutline);
		/**		设置默认值，默认不删除		*/
		schedule.setIsdelete("N");
		if(SSIUtils.isEmpty(scheduleId) || scheduleMapper.selectOneModel(scheduleId) == null){
			len = scheduleMapper.insert(schedule) ;
		}else{
			len = scheduleMapper.updateByModel(schedule) ;
		}
		
		return len ;
	}
	
	/** 
	 * @Description:当前项是直接删除，最好是将其字段标识为非现实字段。
	 * @author li_bin
	 * @Date: 2018年1月12日
	 * @throws 
	 * @return
	 */
	public boolean deleteScheduleById(String id){
//		isdelete
		ScheduleWithBLOBs selectOneModel = scheduleMapper.selectOneModel(id);
		selectOneModel.setIsdelete("Y");
		return scheduleMapper.updateByModel(selectOneModel) > 0;
	}
	
	
	/** 
	 * @Description:拼接开始结束时间
	 * @author li_bin
	 * @Date: 2018年1月17日
	 * @throws 
	 * @param model
	 * @return
	 */
	public List<ScheduleWithBLOBs> setStart_End(List<ScheduleWithBLOBs> model){
		if(SSIUtils.isEmpty(model)) return model ;
		for(ScheduleWithBLOBs blob:model){
			String temp = "" ;
			temp = blob.getTaskstartdate()+" "+blob.getTaskstarttime()+" ~ "+blob.getTaskstartdate()+" "+blob.getTaskendtime() ;
			blob.setTaskenddate(temp);
		}
		return model ;
	}
	
	private String getScheduleId(){
		return SSIUtils.getCurDateFormat("yyyyMMddHHmmss") ;
	}
}
