package com.zwkj.ceng.service;

import java.util.List;

import com.zwkj.ceng.mybatis.entity.Commodity;

public interface CommodityService {

	List<Commodity> getAllCommodity();

	Commodity getCommodityById(String id);

	List<Commodity> getCommodityByExample();

	int insertCommodity(Commodity commodity);

	int updateCommodity(Commodity commodity);

	int deleteCommodityById(int id);

}
