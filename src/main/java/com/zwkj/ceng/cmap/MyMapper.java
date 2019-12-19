package com.zwkj.ceng.cmap;

import com.zwkj.ceng.cmap.ipml.CMapperProvider;
import com.zwkj.ceng.entity.Commodity;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface MyMapper {

    @SelectProvider(type = CMapperProvider.class, method = "cSelectByComForUpdate")
    List<Commodity> cSelectByComForUpdate(Commodity commodity);

    @SelectProvider(type = CMapperProvider.class, method = "cSelectAll")
    List<Commodity> cSelectAll(Commodity commodity);

}
