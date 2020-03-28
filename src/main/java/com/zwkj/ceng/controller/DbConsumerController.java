package com.zwkj.ceng.controller;

import com.zwkj.ceng.kafka.consumer.DbConsumer;
import com.zwkj.ceng.mybatis.mapper.ThirdpartyShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/consumer")
public class DbConsumerController {


    List<String> list = new ArrayList<String>();

    @Autowired
    ThirdpartyShippingMapper thirdpartyShippingMapper;
    DbConsumer dbConsumer = new DbConsumer(list, "groupOne", null);
    ExecutorService executor = Executors.newFixedThreadPool(3);


    @GetMapping("/start")
    public void startConsumer() {
        dbConsumer.setThirdpartyShippingMapper(thirdpartyShippingMapper);
        list.add("Test-Topic");
        executor.submit(dbConsumer);

    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    @ResponseBody
    public void stop() {
        dbConsumer.stop();
        executor.shutdown();
    }


}
