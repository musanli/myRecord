package com.main.dao.comm.inter;

import com.main.dao.comm.entity.Diary;
import com.main.dao.comm.entity.DiaryWithBLOBs;
import java.util.List;

public interface DiaryMapper {
    int deleteByPrimaryKey(String diaryid);

    int deleteByModel(DiaryWithBLOBs record);

    int insert(DiaryWithBLOBs record);

    int insertByModel(DiaryWithBLOBs record);

    DiaryWithBLOBs selectOneModel(String diaryid);

    int updateByModel(DiaryWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DiaryWithBLOBs record);

    int update(Diary record);

    int selectCount(DiaryWithBLOBs record);

    List<DiaryWithBLOBs> selectByModel(DiaryWithBLOBs record);
    List<DiaryWithBLOBs> selectByIDContent(DiaryWithBLOBs record);

    List<Diary> SelectAll();
}