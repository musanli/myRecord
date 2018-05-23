package com.main.dao.comm.inter;

import com.main.dao.comm.entity.Account;
import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(String username);

    int deleteByModel(Account record);

    int insert(Account record);

    int insertByModel(Account record);

    Account selectOneModel(String username);

    int updateByModel(Account record);

    int update(Account record);

    int selectCount(Account record);

    List<Account> selectByModel(Account record);

    List<Account> SelectAll();
}