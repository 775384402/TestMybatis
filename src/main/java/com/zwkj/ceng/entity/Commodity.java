package com.zwkj.ceng.entity;

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

    public String toString() {
        return "{\"id\":\"" + id + "\",\"name\" :\"" + name + "\",\"price\" :\"" + price + "\",\"remaker\" :\"" + remaker + "\"}";

    }
}
