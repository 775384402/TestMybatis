//package com.zwkj.ceng.jasper;
//
//import net.sf.jasperreports.engine.JREmptyDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRExporter;
//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Created by zzc on 2020/4/17
// */
//public class JasperMain {
//
//    public static void main(String[] args) {
//        String fileName = "/devel/examples/test.jasper";
//        String outFileName = "/devel/examples/test.pdf";
//        HashMap hm = new HashMap();
//        try
//        {
//            JasperPrint print = JasperFillManager.fillReport(
//                fileName,
//                hm,
//                new JREmptyDataSource());
//            JRExporter exporter =
//                new net.sf.jasperreports.engine.export.JRPdfExporter();
//            exporter.setParameter(
//                JRExporterParameter.OUTPUT_FILE_NAME,
//                outFileName);
//            exporter.setParameter(
//                JRExporterParameter.JASPER_PRINT,print);
//            exporter.exportReport();
//            System.out.println("Created file: " + outFileName);
//        }
//        catch (JRException e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
//
//    public void exportPdf() throws Exception{
//
//        //1.读取.japser文件，构建输入流
//        InputStream in = session.getServletContext().getResourceAsStream("/jasper/test05_group.jasper");
//
//
//        //2.构建Print对象，用于让模块结合数据
//        //注意：JavaBean的属性名称和模版的Fileds的名称一致的
//        List<User> list = new ArrayList<>();
//        for(int j=1;j<5;j++) {
//            for (int i = 1; i <= 10; i++) {
//                User user = new User();
//                user.setUserName("张三-" + i);
//                user.setEmail("zhangsan-" + i + "@qq.com");
//                user.setCompanyName("熊掌科技-" + j);
//                user.setDeptName("开发部");
//                list.add(user);
//            }
//        }
//
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
//
//        //第三个参数：JavaBean作为数据源，使用JRBeanCollectionDataSource对象来填充
//        JasperPrint print = JasperFillManager.fillReport(in,new HashMap<>(),dataSource);
//
//        //3.使用Exporter导出PDF
//        JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
//
//    }
//}
