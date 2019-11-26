package com.zwkj.ceng.serviceImpl;

import com.zwkj.ceng.entity.Commodity;
import com.zwkj.ceng.mapper.CommodityMapper;
import com.zwkj.ceng.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityMapper commodityMapper;
    @Override
    public List<Commodity> getAllCommodity() {
        return commodityMapper.selectAll();
    }
}
