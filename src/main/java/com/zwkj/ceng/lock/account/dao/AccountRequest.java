package com.zwkj.ceng.lock.account.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "account_request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountRequest {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "uid")
    private Long uid;
    @XmlElement(name = "deduction")
    private BigDecimal deduction;

}
