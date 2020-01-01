package com.zwkj.ceng.lock.account.dao;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "balance")
    private BigDecimal balance;
}
