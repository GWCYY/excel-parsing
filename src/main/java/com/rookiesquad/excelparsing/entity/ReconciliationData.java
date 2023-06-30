package com.rookiesquad.excelparsing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;

public class ReconciliationData implements Serializable {

    @Serial
    private static final long serialVersionUID = -40731148723266202L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String cardNo;

    private String totalSocialInsuranceBenefit;

    private String totalProvidentFund;

    private Integer type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTotalSocialInsuranceBenefit() {
        return totalSocialInsuranceBenefit;
    }

    public void setTotalSocialInsuranceBenefit(String ssAccount) {
        this.totalSocialInsuranceBenefit = ssAccount;
    }

    public String getTotalProvidentFund() {
        return totalProvidentFund;
    }

    public void setTotalProvidentFund(String pafAccount) {
        this.totalProvidentFund = pafAccount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
