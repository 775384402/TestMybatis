package com.zwkj.ceng.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Integer id;
    private String name;


    // String jsonStr = "[{\"id\":1001,\"name\":\"Jobs\"}]";
    public String toString() {
        return "{\"id\":\"" + id + "\",\"name\" :\"" + name + "\"}";

    }

}
