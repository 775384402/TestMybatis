package com.zwkj.ceng.controller;

import com.zwkj.ceng.mybatis.entity.Test;
import com.zwkj.ceng.mybatis.mapper.TestMapper;
import com.zwkj.ceng.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @Autowired
    TestMapper testMapper;

    @GetMapping("/t/{id}")
    public void test(@PathVariable("id") int id) {
        testService.test(id);
    }

    @GetMapping("/rows")
    public void selectByExampleAndRowBounds() {
        testService.selectByExampleAndRowBounds();
    }

    @GetMapping("/rows2")
    public void selectByRowBounds() {
        testService.selectByRowBounds();
    }

    @GetMapping("/selectOneMapper")
    public void selectOneMapper() {
        Test test = new Test();
        test.setId("1");
        test = testMapper.selectOne(test);
    }

    @GetMapping("/select")
    public void select() {
        Test test = new Test();
        test.setId("1");
        List<Test> lits = testMapper.select(test);
        System.out.println(" select " + lits.size());
    }

    @GetMapping("/selectCount")
    public void selectCount() {
        Test test = new Test();
        test.setId("1");
        int num = testMapper.selectCount(test);
        System.out.println(" selectCount " + num);
    }


    @GetMapping("/selectByPrimaryKey")
    public void selectByPrimaryKey() {
        testMapper.selectByPrimaryKey("1");
    }

    @GetMapping("/updateByPrimaryKey")
    public void updateByPrimaryKey() {
        Test test = new Test();
        test.setId("1");
        test.setName("updateByPrimaryKey");
        int num = testMapper.updateByPrimaryKey(test);
        System.out.println(" updateByPrimaryKey " + num);
    }

    @GetMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective() {
        Test test = new Test();
        test.setId("2");
        test.setName("updateByPrimaryKeySelective");
        int num = testMapper.updateByPrimaryKeySelective(test);
        System.out.println(" updateByPrimaryKeySelective " + num);
    }
    @GetMapping("/updateByExample")
    public void updateByExample() {
        Test test = new Test();
        test.setId("3");
        test.setUserId("sd");
        test.setName("updateByPrimaryKeySelective");
        Example example =new Example(Test.class);
        example.createCriteria().andEqualTo("id","3");
        int num = testMapper.updateByExample(test,example);
        System.out.println(" updateByPrimaryKeySelective " + num);
    }

    @GetMapping("/delete")
    public void delete() {
        Test test = new Test();
        test.setId("2");
        int num = testMapper.delete(test);
        System.out.println(" delete " + num);
    }

    @GetMapping("/selectCountByExample")
    public void selectCountByExample() {
        Example example = new Example(Test.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("name like","Cc");
        int lits = testMapper.selectCountByExample(example);
        System.out.println(" selectCountByExample " + lits);
    }

}
