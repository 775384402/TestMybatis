package com.zwkj.ceng.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.Map;

/**
 * Created by zwhd on 2020/3/20
 */
@Mapper
public interface ZwdcProductMapper {

    @Select("select id,code,group_id,customer_id,created,modified from zwdc_product")
    @ResultType(Map.class)
    @Options(fetchSize = Integer.MIN_VALUE, resultSetType = ResultSetType.FORWARD_ONLY)
    Cursor<Map> selectAll();
}
