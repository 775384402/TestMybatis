package com.zwkj.ceng.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Select;

import com.zwkj.ceng.lock.account.dao.Account;

import tk.mybatis.mapper.common.Mapper;

public interface AccountMapper extends Mapper<Account> {

	@Select("Select balance from account where id = #{id}")
	BigDecimal getBalanceById(String id);
}
