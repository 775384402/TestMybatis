package com.zwkj.ceng.service;

import com.zwkj.ceng.entity.Commodity;

import java.util.List;

public interface CommodityService {


    List<Commodity> getAllCommodity();

    Commodity getCommodityById(String id);

    List<Commodity>  getCommodityByExample();

    int insertCommodity(Commodity commodity);

   int updateCommodity(Commodity commodity);

   int deleteCommodityById(int id);

}
