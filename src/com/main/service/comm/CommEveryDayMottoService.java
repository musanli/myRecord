package com.main.service.comm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.comm.entity.Diary;
import com.main.dao.comm.entity.DiaryWithBLOBs;
import com.main.dao.comm.inter.DiaryMapper;
import com.main.util.SSIUtils;

@Service
public class CommEveryDayMottoService {
	@Autowired
	DiaryMapper diaryMapper ;
	
	/** 
	 * @Description:向日记表中添加数据
	 * @author li_bin
	 * @Date: 2018年1月9日
	 * @throws 
	 * @param name			日及名称
	 * @param keywords		关键字
	 * @param describe		内容概括		
	 * @param content		内容信息
	 * @return
	 */
	public Integer saveRecord(String name,String keywords,String describe,String content){
		DiaryWithBLOBs diary = new DiaryWithBLOBs() ;
		diary.setAcquire(describe);
		diary.setDiarytitle(name);
		diary.setContent(content);
		diary.setRemark(keywords);
		/**		1、数据替换		*/
		/**		2、数据分类		*/
		/**		3、数据填充		*/
		dataFill(diary) ;
		/**		4、数据持久化		*/
		
		return diaryMapper.insertByModel(diary) ;
	}
	
	
	public List<DiaryWithBLOBs> selectDiaryWithBLOB(DiaryWithBLOBs blob){
		List<DiaryWithBLOBs> selectByModel = diaryMapper.selectByModel(blob) ;
		return selectByModel ;
	}
	/** 
	 * @Description:日记信息填充
	 * @author li_bin
	 * @Date: 2018年1月9日
	 * @throws 
	 * @param diary
	 * @return
	 */
	private boolean dataFill(DiaryWithBLOBs diary){
		boolean falg = true ;
		/**		id填充		*/
		if(falg){
			dataForRecordId(diary) ;
		}
		
		/**		填充日期		*/
		if(falg){
			falg = dataForDate(diary) ;
		}
		/**		填充title		*/
		if(falg && SSIUtils.isEmpty(diary.getDiarytitle())){
			falg = dataForTitle(diary) ;
		}
		return falg ;
	}
	
	
	
	private boolean dataForRecordId(DiaryWithBLOBs diary){
		String recordId = SSIUtils.getCurDateFormat("yyyyMMddHHmmssSSS") ;
		diary.setDiaryid(recordId);
		return true ;
	}
	/** 
	 * @Description:日期填充
	 * @author li_bin
	 * @Date: 2018年1月9日
	 * @throws 
	 * @param diary
	 * @return
	 */
	private boolean dataForDate(DiaryWithBLOBs diary){
		String curDateFormat = SSIUtils.getCurDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String diaryDate = curDateFormat.substring(0,10) ;
		String diaryTime = curDateFormat.substring(11) ;
		diary.setCreatedate(diaryDate);
		diary.setCreatetime(diaryTime);
		return true ;
	}
	
	/** 
	 * @Description:日期头部填充
	 * @author li_bin
	 * @Date: 2018年1月9日
	 * @throws 
	 * @param diary
	 * @return
	 */
	private boolean dataForTitle(DiaryWithBLOBs diary){
		String diarytitle = SSIUtils.getCurDateFormat("yyyy年 MM月 dd日") ;
		diary.setDiarytitle(diarytitle);
		return true ;
	}
}
