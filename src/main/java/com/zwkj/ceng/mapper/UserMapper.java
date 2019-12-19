package com.zwkj.ceng.mapper;

import com.zwkj.ceng.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> selectAllUsers();

    int insertUser(User user);

    User selectUserById(int id);

    List<User> selectTest(@Param("id") int id);
}
