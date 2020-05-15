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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzc on 2020/4/29
 */
public class JasperTest {


    public static void main(String[] args) throws FileNotFoundException, JRException {


        InputStream in = new FileInputStream("/Users/zwhd/TestMybatis/src/main/resources/jasper/shipping.jasper");


        Map<String, Object> map = new HashMap<>();
        JRDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(ShippingDetailFactory.generateList());
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, jrBeanCollectionDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "/Users/zwhd/TestMybatis/src/main/resources/jasper/report.pdf");


    }


}
