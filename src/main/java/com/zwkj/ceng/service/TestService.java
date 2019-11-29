package com.zwkj.ceng.service;

import com.zwkj.ceng.entity.Test;

import java.util.List;

public interface TestService {
    void test(int id);

    List<Test> selectByExampleAndRowBounds();

    List<Test> selectByRowBounds();



}
