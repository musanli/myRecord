package com.main.action.life;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.qrcode.ByteArray;
import com.main.dao.comm.entity.Diary;
import com.main.dao.comm.entity.DiaryWithBLOBs;
import com.main.dao.comm.inter.DiaryMapper;
import com.main.service.comm.CommEveryDayMottoService;
import com.main.util.SSIUtils;
import com.main.util.mybatis.plugin.Page;
import com.main.util.mybatis.plugin.PageJson;

/**
 * 每日一言Action Every 每 Day 天 Motto 座右铭、口号标语
 * 
 * @author li_bin
 * 
 */
@Controller
@RequestMapping("main/life")
public class CommEveryDayMottoAction {
	@Autowired
	CommEveryDayMottoService commEveryDayMottoService ;
	@Autowired
	DiaryMapper diaryMapper ;
	
	@RequestMapping("todayMotto")
	@ResponseBody
	public String todayMotto(HttpServletRequest request,@RequestParam(value = "sourceFile") MultipartFile file) throws IOException {
		String returnBody = "" ;
		if(file.isEmpty()) return "当前文件为空" ;
		String name = request.getParameter("name") ;
		String describe = request.getParameter("describe") ;
		String keywords = request.getParameter("keywords") ;
		String charSet = request.getParameter("fileCharSet") ;
		
		String fileName = file.getOriginalFilename();
		String contentType = file.getContentType() ;
		InputStream inputStream = file.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
		byte b[] = new byte[1024] ;
		int len = -1 ;
		StringBuilder builder = new StringBuilder() ;
		while((len=inputStream.read(b)) != -1){
			baos.write(b, 0, len);
		}
		String content = new String(baos.toString(charSet));
		Integer saveLen = commEveryDayMottoService.saveRecord(name, keywords+";"+fileName, describe, content) ;
		return saveLen>0 ?"数据导入成功":"数据导入失败";
	}
	
	
	@RequestMapping("todayMotto2Text")
	@ResponseBody
	public String todayMotto2Text(HttpServletRequest request){
		String recordContent = request.getParameter("recordContent") ;
		if(SSIUtils.isEmpty(recordContent)) return "当前日记信息为空" ;
		
		String name = request.getParameter("recordTitle") ;
		String describe = request.getParameter("recordDescribe") ;
		String keywords = request.getParameter("recordKeywords") ;
		
		Integer saveLen = commEveryDayMottoService.saveRecord(name, keywords, describe, recordContent) ;
		return saveLen>0 ?"数据添加成功":"数据添加失败";
		
	}
	
	/** 
	 * @Description:日记查看
	 * @author li_bin
	 * @Date: 2018年1月18日
	 * @throws 
	 * @return
	 */
	@RequestMapping("mottoList")
	@ResponseBody
	public String mottoList(HttpServletRequest request){
		SSIUtils.printInfo(request);
		/**		分页工具类		*/
		SSIUtils.pagingUtil(request);
		String startDate = request.getParameter("startDate") ; 
		String endDate = request.getParameter("endDate") ; 
		String diaryContent = request.getParameter("diaryContent") ; 
		String diaryId = request.getParameter("diaryId") ; 
		DiaryWithBLOBs blobs = new DiaryWithBLOBs() ;
		blobs.setCreatedate(startDate);
		blobs.setRemark(endDate);
		blobs.setDiaryid(diaryId);
		blobs.setContent(SSIUtils.isEmpty(diaryContent) ? null : "%"+diaryContent+"%");
		List<DiaryWithBLOBs> selectDiaryWithBLOB = diaryMapper.selectByIDContent(blobs) ;
		String gridJson = PageJson.encodeObject2GridJson(selectDiaryWithBLOB) ;
		return gridJson ;
		
	}
	
	/** 
	 * @Description:查询所有的日记ID，并作为下拉列表显示
	 * main/life/diaryIdList.do
	 * @author li_bin
	 * @Date: 2018年1月22日
	 * @throws 
	 * @return
	 */
	@RequestMapping("diaryIdList")
	@ResponseBody
	public String diaryIdList(){
		List<Diary> list = diaryMapper.SelectAll() ;
		JSONArray josnArray = new JSONArray() ;
		for(Diary tem:list){
			JSONObject json = new JSONObject() ;
			json.put("value", tem.getDiaryid()) ; 
			json.put("text", tem.getDiaryid()) ;
			josnArray.add(json) ;
		}
		return SSIUtils.isEmpty(josnArray) ? "" :josnArray.toString();
	}
	
	
	/** 
	 * @Description:查询单个日记的相信信息
	 * @author li_bin
	 * @Date: 2018年1月22日
	 * @throws 
	 * @param diaryid
	 * @param map
	 * @return
	 */
	@RequestMapping("diaryDetail")
	public String diaryDetail(String diaryid,ModelMap map){
		DiaryWithBLOBs oneModel = diaryMapper.selectOneModel(diaryid) ;
		if(oneModel == null){
			map.put("errmsg", "找不到当前日记ID") ;
			return "main/error" ;
		}
		map.put("diary", oneModel) ;
		return "main/life/diaryDetail" ;
	}

}
