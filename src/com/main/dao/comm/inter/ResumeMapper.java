package com.main.dao.comm.inter;

import com.main.dao.comm.entity.Resume;
import com.main.dao.comm.entity.ResumeWithBLOBs;
import java.util.List;

public interface ResumeMapper {
    int deleteByPrimaryKey(String resumeid);

    int deleteByModel(ResumeWithBLOBs record);

    int insert(ResumeWithBLOBs record);

    int insertByModel(ResumeWithBLOBs record);

    ResumeWithBLOBs selectOneModel(String resumeid);

    int updateByModel(ResumeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ResumeWithBLOBs record);

    int update(Resume record);

    int selectCount(ResumeWithBLOBs record);

    List<Resume> selectByModel(ResumeWithBLOBs record);

    List<Resume> SelectAll();
}