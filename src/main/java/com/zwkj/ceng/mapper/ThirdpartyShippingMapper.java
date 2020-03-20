package com.zwkj.ceng.mapper;

import com.zwkj.ceng.entity.ThirdpartyShippingEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.Map;

/**
 * Created by zwhd on 2020/3/20
 */
@Mapper
public interface ThirdpartyShippingMapper extends tk.mybatis.mapper.common.Mapper<ThirdpartyShippingEntity> {

    @Select("select * from thirdparty_shipping where id =#{id , jdbcType=VARCHAR}")
    @ResultType(Map.class)
    @Options(fetchSize = 100000, resultSetType = ResultSetType.FORWARD_ONLY)
    Cursor<Map> selectAllByCursor(@Param("id") String id);
}
