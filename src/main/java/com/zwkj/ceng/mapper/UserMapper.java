package com.zwkj.ceng.mapper;

import com.zwkj.ceng.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> selectAllUsers();

    int insertUser(User user);
}
