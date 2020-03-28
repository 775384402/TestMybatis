package com.zwkj.ceng.mybatis.cmap;

import com.zwkj.ceng.mybatis.cmap.ipml.CMapperTemplate;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface MyMapper<T>  {

    @SelectProvider(type = CMapperTemplate.class ,method = "dynamicSQL")
    public List<T> cselectAll();
}
