package com.zwkj.ceng;

import static org.junit.Assert.assertTrue;

import com.zwkj.ceng.shell.sax.Task;
import jdk.internal.org.xml.sax.InputSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    public static void main(String[] args) throws DocumentException {
        // TODO Auto-generated method stub
        //[1]创建SAXReader对象，用于读取.xml文件
        SAXReader reader = new SAXReader();
        //[2]读取.xml文件，得到document对象，它代表了整个.xml文件。
        Document document = reader.read(new File("tasks.xml"));
        //System.out.println(doc);
        //获取根元素
        Element root = document.getRootElement();
        //开始遍历根节点(迭代器)
        Iterator<Element> rootIter = root.elementIterator();
        List<Task> list = new ArrayList<>();
        while (rootIter.hasNext()) {
            Task task = new Task();
            //获取rot下的元素
            Element e = (Element) rootIter.next();
            //获取id属性
            Attribute id = e.attribute("id");
            task.setId(Integer.valueOf(id.getValue()));
            //获取student的子元素
            Iterator<Element> node = e.elementIterator();
            while (node.hasNext()) {
                Element innerElt = node.next();
                if ("name".equals(innerElt.getName())) {
                    task.setName(innerElt.getText());
                }
                if ("cmd".equals(innerElt.getName())) {
                    task.setName(innerElt.getText());
                }
            }
            list.add(task);
        }

        System.out.println(list.size());

    }




}
