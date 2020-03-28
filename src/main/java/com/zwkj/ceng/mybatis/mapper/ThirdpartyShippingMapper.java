package com.zwkj.ceng.mybatis.mapper;

import com.zwkj.ceng.mybatis.entity.ThirdpartyShippingEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.Map;

/**
 * Created by zwhd on 2020/3/20
 */
@Mapper
public interface ThirdpartyShippingMapper extends tk.mybatis.mapper.common.Mapper<ThirdpartyShippingEntity> {

    @Select("select shipping_id,shipping_track_number,created,modified from thirdparty_shipping where shipping_id =#{id , jdbcType=VARCHAR}")
    @ResultType(Map.class)
    @Options(fetchSize = 100000, resultSetType = ResultSetType.FORWARD_ONLY)
    Cursor<Map> selectOneByCursor(@Param("id") String id);

    @Select("select shipping_id,shipping_track_number,created,modified from thirdparty_shipping ")
    @ResultType(Map.class)
    @Options(fetchSize = Integer.MIN_VALUE, resultSetType = ResultSetType.FORWARD_ONLY)
    Cursor<Map> selectAllByCursor();


}
