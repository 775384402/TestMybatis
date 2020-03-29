//package com.zwkj.ceng.controller;
//
//import com.fm.egg.log.util.DatesUtils;
//import com.fm.egg.log.util.StopWatch;
//import com.zwkj.ceng.mybatis.mapper.SchemeInfoMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.batch.MyBatisCursorItemReader;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Timestamp;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequestMapping("/thirdparty")
//public class ThirdpartyShippingController {
//
//    @Autowired
//    SqlSessionFactory sqlSessionFactory;
//
//
//    @Autowired
//    SchemeInfoMapper schemeInfoMapper;
//
//
//
//
//    @GetMapping("/get2")
//    public void testQuery() {
//        MyBatisCursorItemReader<Map> myBatisCursorItemReader = null;
//        try {
//            myBatisCursorItemReader = new MyBatisCursorItemReader();
//            myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
//            myBatisCursorItemReader.setQueryId("com.zwkj.ceng.mybatis.mapper.ZwdcProductMapper.selectAll");
//            myBatisCursorItemReader.open(new ExecutionContext());
//            Map map;
//            StopWatch stopWatch = new StopWatch();
//            while ((map = myBatisCursorItemReader.read()) != null) {
//                map.get("id");
//            }
//            System.out.println("whole loader elapsedTime : " + stopWatch.elapsedTime());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (myBatisCursorItemReader != null) {
//                myBatisCursorItemReader.close();
//            }
//        }
//    }
//
//
//    @RequestMapping(method = RequestMethod.GET, value = "/get")
//    public void getAll(@RequestParam String id) {
//
//        String tableName = "thirdparty_shipping";
//        List<String> fields = new ArrayList<>();
//        fields.add("shipping_id");
//        fields.add("shipping_track_number");
//        fields.add("created");
//        fields.add("modified");
//        fields.add("input_type");
//
//        List<HashMap<String, String>> result = convertSchemeField(tableName, fields);
//
//
//        MyBatisCursorItemReader<Map> myBatisCursorItemReader = null;
//        try {
//            myBatisCursorItemReader = new MyBatisCursorItemReader();
//            myBatisCursorItemReader.setSqlSessionFactory(sqlSessionFactory);
//            myBatisCursorItemReader.setQueryId("com.zwkj.ceng.mybatis.mapper.ThirdpartyShippingMapper.selectAllByCursor");
//            Map<String, Object> parameterMap = new HashMap<>();
//            parameterMap.put("id", id);
//            myBatisCursorItemReader.setParameterValues(parameterMap);
//            myBatisCursorItemReader.open(new ExecutionContext());
//            Map map;
//            while ((map = myBatisCursorItemReader.read()) != null) {
//                Set<Map.Entry<String, Object>> entrySets = map.entrySet();
//                System.out.println(map);
//                entrySets.forEach(entrySet -> {
//                    result.stream().forEach(t -> {
//                        if (entrySet.getKey().contains(t.get("COLUMN_NAME"))) {
//                            if (t.get("DATA_TYPE").equals("Timestamp")) {
//                                if (entrySet.getValue() != null) {
//                                    Timestamp time = (Timestamp) entrySet.getValue();
//                                    entrySet.setValue(DatesUtils.formatDate(new Date(time.getTime()), "yyyy-MM-dd HH:mm:ss"));
//                                }
//                            }
//                        }
//                    });
//                });
//                System.out.println(map);
//            }
//        } catch (Exception e) {
//            log.error(e.toString());
//        } finally {
//            if (myBatisCursorItemReader != null) {
//                myBatisCursorItemReader.close();
//            }
//        }
//    }
//
//    private List<HashMap<String, String>> convertSchemeField(String tableName, List<String> fields) {
//        List<HashMap<String, String>> schemeInfos = schemeInfoMapper.selectColumnsAndType(tableName);
//        schemeInfos = schemeInfos.stream().filter(schemeInfo -> {
//            Set<Map.Entry<String, String>> map = schemeInfo.entrySet();
//            AtomicBoolean flag = new AtomicBoolean(false);
//            for (String field : fields) {
//                if (schemeInfo.get("COLUMN_NAME").equals(field)) {
//                    if (schemeInfo.get("DATA_TYPE").equals("datetime")) {
//                        schemeInfo.put("DATA_TYPE", "Timestamp");
//                        return true;
//                    }
//                }
//            }
//            return flag.get();
//        }).collect(Collectors.toList());
//        return schemeInfos;
//    }
//
//}
