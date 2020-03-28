package com.zwkj.ceng.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ExecutionContext;
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
import com.zwkj.ceng.mybatis.entity.Commodity;
import com.zwkj.ceng.mybatis.mapper.AccountMapper;
import com.zwkj.ceng.service.CommodityService;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CommodityService commodityService;
    @Autowired
    SqlSessionFactory sqlSessionFactory;


    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public void getAll() {
        MyBatisCursorItemReader<Map> myBatisCursorItemReader = null;
        try {
            myBatisCursorItemReader = new MyBatisCursorItemReader();
            myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
            myBatisCursorItemReader.setQueryId("com.zwkj.ceng.mybatis.mapper.ThirdpartyShippingMapper.selectAll");
            myBatisCursorItemReader.open(new ExecutionContext());
            Map map;
            while ((map = myBatisCursorItemReader.read()) != null) {
                System.out.println(map);
            }
        } catch (Exception e) {

        } finally {
            if (myBatisCursorItemReader != null) {
                myBatisCursorItemReader.close();
            }
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/get2")
    public void getAll2() throws Exception {

        MyBatisCursorItemReader<Map> myBatisCursorItemReader = new MyBatisCursorItemReader();
        myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisCursorItemReader.setQueryId("com.zwkj.ceng.mybatis.mapper.AccountMapper.getAll");
        myBatisCursorItemReader.open(new ExecutionContext());
        Map account;
        while ((account = myBatisCursorItemReader.read()) != null) {
            System.out.println(account);
        }
        myBatisCursorItemReader.close();

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
