package com.zwkj.ceng.serviceImpl;

import com.zwkj.ceng.entity.Test;
import com.zwkj.ceng.mapper.TestMapper;
import com.zwkj.ceng.service.TestService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TestServiceIml implements TestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public void test(int id) {
        Example example = new Example(Test.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        List<Test> list = testMapper.selectByExample(example);
        System.out.println(list.size());


    }

    @Override
    public List<Test> selectByExampleAndRowBounds() {
        Example example = new Example(Test.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "Cc");
        RowBounds rowBounds = new RowBounds(0, 20);
        List<Test> list = testMapper.selectByExampleAndRowBounds(example, rowBounds);
        int count = testMapper.selectCountByExample(example);
        return list;
    }

    @Override
    public List<Test> selectByRowBounds() {
        List<Test> list = testMapper.selectByRowBounds(new Test(), new RowBounds(0, 20));
        return list;
    }



}
