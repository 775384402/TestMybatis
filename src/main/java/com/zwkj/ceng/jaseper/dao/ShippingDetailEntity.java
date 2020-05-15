package com.zwkj.ceng.jaseper.dao;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * Created by zzc on 2020/4/22
 */

@Getter
@Setter
public class ShippingDetailEntity {
    private Integer id;
    private String shippingName;
    private Integer shippingCount;
    private String country;
    private Integer countryCount;
    private String toSignTime;
    private String receiptToSignTime;
    private Date month;
}
