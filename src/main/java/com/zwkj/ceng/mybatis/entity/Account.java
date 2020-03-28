package com.zwkj.ceng.mybatis.entity;

import javax.persistence.Table;

import lombok.Data;

@Table(name = "account")
@Data
public class Account {
	private String id;
	private String uid;
	private Integer balance;
}
