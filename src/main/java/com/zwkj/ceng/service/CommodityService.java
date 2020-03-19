package com.zwkj.ceng.service;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

import com.zwkj.ceng.entity.Account;
import com.zwkj.ceng.entity.Commodity;

public interface CommodityService {

	List<Commodity> getAllCommodity();

	Commodity getCommodityById(String id);

	List<Commodity> getCommodityByExample();

	int insertCommodity(Commodity commodity);

	int updateCommodity(Commodity commodity);

	int deleteCommodityById(int id);

}
