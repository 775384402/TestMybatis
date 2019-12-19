package com.zwkj.ceng.shell.sax;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Task {

	String name;
	int id;
	String cmd;
	Map<String, Task> nextTask = new HashMap<String, Task>();

	public void excutor() {
	}
}
