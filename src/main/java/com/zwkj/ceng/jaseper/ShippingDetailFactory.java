package com.zwkj.ceng.jaseper;

import com.google.common.collect.Lists;
import com.zwkj.ceng.jaseper.dao.ShippingDetailEntity;
import com.zwkj.ceng.jaseper.dao.ShippingReportEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by zzc on 2020/4/22
 */
public class ShippingDetailFactory {

    private static final List<String> shippingNames = Lists.newArrayList("test1", "test2", "test3", "test4", "test5");
    private static final List<String> country = Lists.newArrayList("USA", "JP", "UK", "UN", "AF");

    public static List<ShippingDetailEntity> generateCollection() {
        List<ShippingDetailEntity> list = new ArrayList<>();
        Map<String, ShippingDetailEntity> result = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            shippingNames.forEach(shippingName -> {
                ShippingDetailEntity shippingDetailEntity = createdShippingDetail(shippingName);
                result.put(shippingDetailEntity.getShippingName() + shippingDetailEntity.getMonth(), shippingDetailEntity);
            });
        }
        result.entrySet().forEach(map -> list.add(map.getValue()));
        return list;
    }

    private static ShippingDetailEntity createdShippingDetail(String shippingName) {
        ShippingDetailEntity shippingDetailEntity = new ShippingDetailEntity();
        shippingDetailEntity.setShippingName(shippingName);
        shippingDetailEntity.setCountry(country.get(new Random().nextInt(3)));
        shippingDetailEntity.setCountryCount(new Random().nextInt(100));
        int time = new Random().nextInt(20);
        int time2 = time + new Random().nextInt(5);
        String timeStr = String.valueOf(time);
        String timeStr2 = String.valueOf(time2);
        shippingDetailEntity.setToSignTime(timeStr2);
        shippingDetailEntity.setReceiptToSignTime(timeStr);
        int moth = new Random().nextInt(12);
        shippingDetailEntity.setMonth(new Date(120, moth, 1));
        return shippingDetailEntity;
    }


    public static List<ShippingReportEntity> generateList() {
        List<ShippingReportEntity> list = new ArrayList<>();
        ShippingReportEntity shippingReportEntity = new ShippingReportEntity();
        shippingReportEntity.setReportType("1");
        shippingReportEntity.setShipCount(231L);
        shippingReportEntity.setNotReceiptCount(32);
        shippingReportEntity.setReceiptedCount(123);
        shippingReportEntity.setSignedCount(59);
        shippingReportEntity.setWaitingSignCount(87);
        shippingReportEntity.setDeliverFailedCount(11);
        shippingReportEntity.setAnomaliesCount(3);
        shippingReportEntity.setReceiptedRangeCount(27);
        shippingReportEntity.setReceiptedRangeCount1(65);
        shippingReportEntity.setReceiptedRangeCount2(154);
        shippingReportEntity.setSignedRangeCount(98);
        shippingReportEntity.setSignedRangeCount1(180);
        shippingReportEntity.setSignedRangeCount2(230);
        shippingReportEntity.setAvgSigned(7.4);
        list.add(shippingReportEntity);

        shippingReportEntity = new ShippingReportEntity();
        shippingReportEntity.setReportType("2");
        shippingReportEntity.setShipCount(131L);
        shippingReportEntity.setNotReceiptCount(12);
        shippingReportEntity.setReceiptedCount(123);
        shippingReportEntity.setSignedCount(59);
        shippingReportEntity.setWaitingSignCount(87);
        shippingReportEntity.setDeliverFailedCount(11);
        shippingReportEntity.setAnomaliesCount(3);
        shippingReportEntity.setReceiptedRangeCount(27);
        shippingReportEntity.setReceiptedRangeCount1(65);
        shippingReportEntity.setReceiptedRangeCount2(76);
        shippingReportEntity.setSignedRangeCount(98);
        shippingReportEntity.setSignedRangeCount1(80);
        shippingReportEntity.setSignedRangeCount2(130);
        shippingReportEntity.setAvgSigned(7.1);
        list.add(shippingReportEntity);


        shippingReportEntity = new ShippingReportEntity();
        shippingReportEntity.setReportType("3");
        shippingReportEntity.setShipCount(331L);
        shippingReportEntity.setNotReceiptCount(12);
        shippingReportEntity.setReceiptedCount(241);
        shippingReportEntity.setSignedCount(145);
        shippingReportEntity.setWaitingSignCount(82);
        shippingReportEntity.setDeliverFailedCount(31);
        shippingReportEntity.setAnomaliesCount(9);
        shippingReportEntity.setReceiptedRangeCount(47);
        shippingReportEntity.setReceiptedRangeCount1(65);
        shippingReportEntity.setReceiptedRangeCount2(76);
        shippingReportEntity.setSignedRangeCount(68);
        shippingReportEntity.setSignedRangeCount1(60);
        shippingReportEntity.setSignedRangeCount2(170);
        shippingReportEntity.setAvgSigned(7.9);
        list.add(shippingReportEntity);


        return list;
    }


}
