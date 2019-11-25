package com.zwkj.ceng.serviceImpl;

import com.zwkj.ceng.mapper.UserMapper;
import com.zwkj.ceng.entity.User;
import com.zwkj.ceng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int saveUser(User user) {
        return userMapper.insertUser(user);
    }
}
