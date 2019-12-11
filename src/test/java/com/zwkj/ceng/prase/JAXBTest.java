package com.zwkj.ceng.prase;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBTest {

	public static void main(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Tasks.class);

		Marshaller marshaller = context.createMarshaller();
		Unmarshaller unmarshaller = context.createUnmarshaller();

		URL path = Thread.currentThread().getContextClassLoader().getResource("Tasks.xml");
		InputStream inpu = new FileInputStream(path.getPath());
		Tasks task = (Tasks) unmarshaller.unmarshal(inpu);
		System.out.println(task.getTasks().get(1).getCmd());
	}

}
