package com.zwkj.ceng.mybatis.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
public class Commodity {
    @Id
    int id;
    String name;
    BigDecimal price;
    String remaker;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemaker() {
        return remaker;
    }

    public void setRemaker(String remaker) {
        this.remaker = remaker;
    }

    public String toString() {
        return "{\"id\":\"" + id + "\",\"name\" :\"" + name + "\",\"price\" :\"" + price + "\",\"remaker\" :\"" + remaker + "\"}";

    }
}
