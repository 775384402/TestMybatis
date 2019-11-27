package com.zwkj.ceng.service;

import com.zwkj.ceng.entity.User;

import java.util.List;

public interface UserService {

     List<User> getAllUsers();

     int saveUser(User user);

     User getUserById(int id);

     List<User> getUserLeftTest(int id );
}
