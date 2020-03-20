package com.zwkj.ceng.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import java.util.HashMap;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface AccountMapper {

    @Select("select id,uid,balance from  account where id=#{id, jdbcType=VARCHAR}")
    @ResultType(HashMap.class)
    @Options(fetchSize = Integer.MIN_VALUE)
    Cursor<Map> getAll(@Param("id") String id);

}
