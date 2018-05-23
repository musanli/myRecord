package com.main.service.comm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.comm.entity.Account;
import com.main.dao.comm.inter.AccountMapper;

@Service("accountService")
public class AccountService{
	@Autowired
	AccountMapper accountDao ;
	
	public int deleteByPrimaryKey(String username) {
		return accountDao.deleteByPrimaryKey(username);
	}

	
	public int deleteByModel(Account record) {

		return accountDao.deleteByModel(record);
	}

	
	public int insert(Account record) {

		return accountDao.insert(record);
	}

	
	public int insertByModel(Account record) {

		return accountDao.insertByModel(record);
	}

	
	public Account selectOneModel(String username) {

		return accountDao.selectOneModel(username);
	}

	
	public int updateByModel(Account record) {

		return accountDao.updateByModel(record);
	}

	
	public int update(Account record) {

		return accountDao.update(record);
	}

	
	public int selectCount(Account record) {

		return accountDao.selectCount(record);
	}

	
	public List<Account> selectByModel(Account record) {

		return accountDao.selectByModel(record);
	}

	
	public List<Account> SelectAll() {

		return accountDao.SelectAll();
	}

}
