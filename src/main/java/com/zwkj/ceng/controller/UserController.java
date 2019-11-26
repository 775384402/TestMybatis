package com.zwkj.ceng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwkj.ceng.entity.User;
import com.zwkj.ceng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<JSONObject> getAll() {
        List<User> list = userService.getAllUsers();
        return list.stream().map(t -> JSON.parseObject(t.toString())).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public int saveUser(User user) {
        User result = userService.getUserById(user.getId());
        if (result == null) {
            return userService.saveUser(user);
        }
        return -1;
    }

}
