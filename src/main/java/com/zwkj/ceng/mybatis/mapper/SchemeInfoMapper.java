package com.zwkj.ceng.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zwhd on 2020/3/23
 */
@Mapper
public interface SchemeInfoMapper {

    @ResultType(HashMap.class)
    @Select("select DISTINCT COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS where table_name =#{tableName,jdbcType=VARCHAR}")
    List<HashMap<String, String>> selectColumnsAndType(@Param("tableName") String tableName);
}
