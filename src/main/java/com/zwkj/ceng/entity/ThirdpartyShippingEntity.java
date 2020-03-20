package com.zwkj.ceng.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by liu on 2018/09/20
 */
@Getter
@Setter
@Table(name = "thirdparty_shipping")
public class ThirdpartyShippingEntity {

    @Id
    private Integer id;
    private String shippingCodeLast;

}
