package com.zwkj.ceng.serviceImpl;

import com.zwkj.ceng.entity.Test;
import com.zwkj.ceng.entity.User;
import com.zwkj.ceng.mapper.TestMapper;
import com.zwkj.ceng.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TestServiceIml implements TestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public void test(User user) {
        Example example = new Example(Test.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("id = 1");
        List<Test> list = testMapper.selectByExample(example);
        System.out.println(list.size());


    }
}
