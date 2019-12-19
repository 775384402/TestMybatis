package com.zwkj.ceng.shell.sax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDemoHandel extends DefaultHandler {

	Task task;
	List<Task> list = new ArrayList<Task>();
	String currentTag; // 记录当前解析到的节点名称
	StringBuffer sb = new StringBuffer();

	public List<Task> getList() {
		return list;
	}

	public void setList(List<Task> list) {
		this.list = list;
	}

	// 遍历xml文件开始标签
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	// 遍历xml文件结束标签
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("Task")) {
			task = new Task();
			for (int i = 0; i < attributes.getLength(); i++) {
				System.out.println("attributes" + "attribute_name：" + attributes.getLocalName(i) + "  attribute_value："
						+ attributes.getValue(i));
				if ("id".equals(attributes.getLocalName(i))) {
					task.setId(Integer.parseInt(attributes.getValue(i)));
				}
			}

		}
		currentTag = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (qName.equals("Task")) {
			list.add(task);
			for (Task task : list) {
				Map<String, Task> nextTask = task.getNextTask();
				for (Task task2 : list) {
					for (Map.Entry<String, Task> map : nextTask.entrySet()) {
						if (map.getKey().equals(task2.getName())) {
							map.setValue(task2);
						}
					}
				}
			}
			task = null;
		}
		sb.setLength(0);
		currentTag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String value = new String(ch, start, length).trim();

		sb.append(value);
		if ("name".equals(currentTag)) { // 当前标签为name标签，该标签无子标签，直接将上面获取到的标签的值封装到当前User对象中
			// 该节点为name节点
			task.setName(sb.toString());
		} else if ("cmd".equals(currentTag)) { // 当前标签为password标签，该标签无子标签，直接将上面获取到的标签的值封装到当前User对象中
			// 该节点为password节点
			task.setCmd(sb.toString());
		} else if ("next".equals(currentTag)) { // 当前标签为password标签，该标签无子标签，直接将上面获取到的标签的值封装到当前User对象中
			// 该节点为password节点
			Map<String, Task> map = task.getNextTask();
			map.put(value, null);
		}

	}
}
