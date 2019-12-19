package com.zwkj.ceng.cmap;

import com.zwkj.ceng.cmap.ipml.CMapperProvider2;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

public interface MyMapper2<T> extends Marker {

    @SelectProvider(type = CMapperProvider2.class, method = "cSelectForUpdate")
    List<T> cSelectForUpdate();
}
