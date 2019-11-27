package com.zwkj.ceng.controller;

import com.zwkj.ceng.entity.User;
import com.zwkj.ceng.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/t")
    public void test(User user) {
        testService.test(user);
    }
}
