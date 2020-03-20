package com.zwkj.ceng.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/thirdparty")
public class ThirdpartyShippingController {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public void getAll(@RequestParam String id) {
        MyBatisCursorItemReader<Map> myBatisCursorItemReader = null;
        try {
            myBatisCursorItemReader = new MyBatisCursorItemReader();
            myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
            myBatisCursorItemReader.setQueryId("com.zwkj.ceng.mapper.ThirdpartyShippingMapper.selectAllByCursor");
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("id", id);
            myBatisCursorItemReader.setParameterValues(parameterMap);
            myBatisCursorItemReader.open(new ExecutionContext());
            Map map;
            while ((map = myBatisCursorItemReader.read()) != null) {
                System.out.println(map);
            }
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            if (myBatisCursorItemReader != null) {
                myBatisCursorItemReader.close();
            }
        }
    }

}
