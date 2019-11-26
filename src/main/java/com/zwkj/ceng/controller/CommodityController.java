package com.zwkj.ceng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwkj.ceng.entity.Commodity;
import com.zwkj.ceng.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    CommodityService commodityService;

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
