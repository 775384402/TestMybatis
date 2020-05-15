package com.zwkj.ceng.jaseper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzc on 2020/4/22
 */
public class JasperController {


    public static void main(String[] args) throws FileNotFoundException, JRException, ParseException {
        //1.读取.japser文件，构建输入流

        InputStream in = new FileInputStream("/Users/zwhd/TestMybatis/src/main/resources/jasper/shipping.jasper");

        //2.构建Print对象，用于让模块结合数据
        //第二个参数就是用来填充模板中的parameters
        Map<String, Object> map = new HashMap<>();
        JRDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(ShippingDetailFactory.generateCollection());
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, jrBeanCollectionDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/zwhd/TestMybatis/src/main/resources/jasper/reportPreviewTemp.pdf");
//        JasperExportManager.exportReportToHtmlFile(jasperPrint, "/Users/zwhd/TestMybatis/src/main/resources/jasper/reportPreviewTemp.html");
        //3.使用Exporter导出PDF
//        JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
    }
}
