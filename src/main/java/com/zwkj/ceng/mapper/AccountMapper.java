package com.zwkj.ceng.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import com.zwkj.ceng.lock.account.dao.Account;

@org.apache.ibatis.annotations.Mapper
public interface AccountMapper {

	@Select("select * from  account")
	@Options(fetchSize = Integer.MIN_VALUE)
	Cursor<Account> getAll();

}
