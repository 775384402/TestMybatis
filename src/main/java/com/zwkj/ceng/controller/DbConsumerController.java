package com.zwkj.ceng.controller;

import com.zwkj.ceng.consumer.DbConsumer;
import com.zwkj.ceng.mapper.ThirdpartyShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class DbConsumerController {


    List<String> list = new ArrayList<String>();

    @Autowired
    ThirdpartyShippingMapper thirdpartyShippingMapper;
    DbConsumer dbConsumer = new DbConsumer(list, "groupOne", null, thirdpartyShippingMapper);
    ;
//    DbConsumer dbConsumer = new DbConsumer(list, "groupOne", Collections.singletonList(new TopicPartition("Test-Topic", 2)));

    @GetMapping("/start")
    public void startConsumer() {

        list.add("Test-Topic");

        new Thread(dbConsumer).start();
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    @ResponseBody
    public void stop(@PathVariable String consumerName) {
        dbConsumer.stop();
    }


}
