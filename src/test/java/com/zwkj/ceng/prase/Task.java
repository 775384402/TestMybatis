package com.zwkj.ceng.prase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Task")
@Getter
@Setter
public class Task {
	@XmlAttribute
	String id;
	@XmlElement
	String name;
	@XmlElement
	String next;
	@XmlElement
	String cmd;
}
