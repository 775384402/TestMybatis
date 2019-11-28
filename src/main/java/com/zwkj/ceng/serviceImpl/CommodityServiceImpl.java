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

    @Override
    public Commodity getCommodityById(String id) {
        return commodityMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Commodity> getCommodityByExample() {
//        Example example =new Example()
//        return commodityMapper.selectByExample();
        return null;
    }

    @Override
    public int insertCommodity(Commodity commodity) {
        return commodityMapper.insertSelective(commodity);
    }

    @Override
    public int updateCommodity(Commodity commodity) {
        return commodityMapper.updateByPrimaryKeySelective(commodity);
    }

    @Override
    public int deleteCommodityById(int id) {
        if (commodityMapper.selectByPrimaryKey(id) != null) {
            return commodityMapper.deleteByPrimaryKey(id);
        }
        return -1;
    }


}
