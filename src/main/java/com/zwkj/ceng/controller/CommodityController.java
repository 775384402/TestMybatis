package com.zwkj.ceng.controller;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwkj.ceng.entity.Commodity;
import com.zwkj.ceng.lock.account.dao.Account;
import com.zwkj.ceng.mapper.AccountMapper;
import com.zwkj.ceng.service.CommodityService;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

	@Autowired
	AccountMapper accountMapper;
	@Autowired
	CommodityService commodityService;

	@RequestMapping(method = RequestMethod.GET, value = "/get")
	public void getAll() {
		Cursor<Account> cursor = accountMapper.getAll();
		Iterator<Account> iterator = cursor.iterator();
		Account user = iterator.next();
		cursor.forEach(c -> {
			System.out.println(c.getId());
		});
	}

	@GetMapping("/all")
	public List<JSONObject> getAllCommodity() {
		List<Commodity> allCommodity = commodityService.getAllCommodity();
		return allCommodity.stream().map(t -> JSON.parseObject(t.toString())).collect(Collectors.toList());
	}

	@PostMapping("/add")
	public int saveCommodity(Commodity commodity) {

		return commodityService.insertCommodity(commodity);
	}

	@PutMapping("/update")
	public int updateCommodity(Commodity commodity) {
		return commodityService.updateCommodity(commodity);
	}

	@DeleteMapping("/delete/{id}")
	public int deleteCommodityById(@PathVariable("id") int id) {
		return commodityService.deleteCommodityById(id);
	}
}
