package com.zwkj.ceng.mapper;

import com.zwkj.ceng.entity.Commodity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public interface CommodityMapper extends Mapper<Commodity> {
}
