package com.zwkj.ceng.mybatis.entity;

import javax.persistence.Table;

public class User {

	private int id;
	private String name;

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

	// String jsonStr = "[{\"id\":1001,\"name\":\"Jobs\"}]";
	public String toString() {
		return "{\"id\":\"" + id + "\",\"name\" :\"" + name + "\"}";

	}

}
