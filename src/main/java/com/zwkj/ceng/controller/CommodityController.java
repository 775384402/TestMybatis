package com.zwkj.ceng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwkj.ceng.entity.Commodity;
import com.zwkj.ceng.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @GetMapping("/commoditys")
    public List<JSONObject>  getAllCommodity(){

        List<Commodity> allCommodity = commodityService.getAllCommodity();
        return allCommodity.stream().map(t -> JSON.parseObject(t.toString())).collect(Collectors.toList());

    }
}
