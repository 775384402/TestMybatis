package com.zwkj.ceng.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zwkj.ceng.consumer.DbConsumer;

@RestController
@RequestMapping("/consumer")
public class DbConsumerController {

	@GetMapping("/start")
	public void startConsumer() {
		List<String> list = new ArrayList<String>();
		list.add("test-db-topic");
		new Thread(new DbConsumer(list, "groupOne", null)).start();
	}
}
