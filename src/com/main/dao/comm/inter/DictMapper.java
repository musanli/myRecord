package com.main.dao.comm.inter;

import com.main.dao.comm.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByModel(Dict record);

    int insert(Dict record);

    int insertByModel(Dict record);

    Dict selectOneModel(Integer id);

    int updateByModel(Dict record);

    int update(Dict record);

    int selectCount(Dict record);

    List<Dict> selectByModel(Dict record);

    List<Dict> SelectAll();
    
    List<Map<String,String>> selectAllNameCode() ;
}