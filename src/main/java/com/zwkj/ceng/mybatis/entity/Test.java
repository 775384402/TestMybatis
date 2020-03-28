package com.zwkj.ceng.mybatis.entity;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Test {
    String id;
    @Column(name = "userId")
    String userId;
    String name;
}
