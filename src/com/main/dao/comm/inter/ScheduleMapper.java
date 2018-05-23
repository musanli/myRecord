package com.main.dao.comm.inter;

import com.main.dao.comm.entity.Schedule;
import com.main.dao.comm.entity.ScheduleWithBLOBs;
import java.util.List;

public interface ScheduleMapper {
    int deleteByPrimaryKey(String id);

    int deleteByModel(ScheduleWithBLOBs record);

    int insert(ScheduleWithBLOBs record);

    int insertByModel(ScheduleWithBLOBs record);

    ScheduleWithBLOBs selectOneModel(String id);

    int updateByModel(ScheduleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ScheduleWithBLOBs record);

    int update(Schedule record);

    int selectCount(ScheduleWithBLOBs record);

    List<Schedule> selectByModel(ScheduleWithBLOBs record);

    List<Schedule> SelectAll();
    
    /**		条件查询		*/
    List<ScheduleWithBLOBs> selectBLOBByModel(ScheduleWithBLOBs record);
}