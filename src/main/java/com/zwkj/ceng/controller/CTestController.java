package com.zwkj.ceng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwkj.ceng.entity.Commodity;
import com.zwkj.ceng.mapper.CTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cc")
public class CTestController {

    @Autowired
    CTestMapper cTestMapper;

    @GetMapping("/all")
    public List<JSONObject> test() {

        List<Commodity> allCommodity = cTestMapper.cselectAll();
        return allCommodity.stream().map(t -> JSON.parseObject(t.toString())).collect(Collectors.toList());
    }
}
