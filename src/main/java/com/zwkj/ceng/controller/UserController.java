package com.zwkj.ceng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwkj.ceng.entity.User;
import com.zwkj.ceng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<JSONObject> getAll() {
        List<User> list = userService.getAllUsers();
        return list.stream().map(t -> JSON.parseObject(t.toString())).collect(Collectors.toList());
    }
    @GetMapping("/test/{id}")
    public List<JSONObject> getTest(@PathVariable("id")int id) {
        List<User> list = userService.getUserLeftTest(id);
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
