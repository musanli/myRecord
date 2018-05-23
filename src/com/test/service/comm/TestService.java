package com.test.service.comm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.comm.entity.Dict;
import com.main.dao.comm.inter.DictMapper;

@Service
public class TestService {

	@Autowired
	DictMapper dictMapper;

	public int deleteByPrimaryKey(Integer id) {
		return dictMapper.deleteByPrimaryKey(id);
	};

	public int deleteByModel(Dict record) {
		return dictMapper.deleteByModel(record);
	};

	public int insert(Dict record) {
		return dictMapper.insert(record);
	};

	public int insertByModel(Dict record) {
		return dictMapper.insertByModel(record);
	};

	public Dict selectOneModel(Integer id) {
		return dictMapper.selectOneModel(id);
	};

	public int updateByModel(Dict record) {
		return dictMapper.updateByModel(record);
	};

	public int update(Dict record) {
		return dictMapper.update(record);
	};

	public int selectCount(Dict record) {
		return dictMapper.selectCount(record);
	};

	public List<Dict> selectByModel(Dict record) {
		return dictMapper.selectByModel(record);
	};

	public List<Dict> SelectAll() {
		return dictMapper.SelectAll();
	};

}
