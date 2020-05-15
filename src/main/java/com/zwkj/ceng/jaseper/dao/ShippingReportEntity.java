package com.zwkj.ceng.jaseper.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by zzc on 2020/4/29
 */
@Getter
@Setter
public class ShippingReportEntity {
    private String reportType;
    private Long shipCount;
    private Integer notReceiptCount;
    private Integer receiptedCount;
    private Integer signedCount;
    private Integer waitingSignCount;
    private Integer deliverFailedCount;
    private Integer anomaliesCount;
    private Double avgSigned;
    private Integer receiptedRangeIndex;
    private Integer receiptedRangeCount2;
    private Integer receiptedRangeCount;
    private Integer receiptedRangeCount1;
    private Integer signedRangeIndex;
    private Integer signedRangeCount;
    private Integer signedRangeCount1;
    private Integer signedRangeCount2;
    private List<String> list;
}
