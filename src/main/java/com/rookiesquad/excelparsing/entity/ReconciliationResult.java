package com.rookiesquad.excelparsing.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

@Entity
@Table
public class ReconciliationResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -8618460886941281081L;

    public static final Set<String> EXCLUDE_FIELD = Collections.singleton("id");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "身份证号码", index = 1)
    private String cardNo;

    @NumberFormat("#.##")
    @ExcelProperty(value = "账单社保合计", index = 2)
    private Float billTotalSocialInsuranceBenefit;

    @NumberFormat("#.##")
    @ExcelProperty(value = "账单公积金合计", index = 3)
    private Float billTotalProvidentFund;

    @NumberFormat("#.##")
    @ExcelProperty(value = "实缴社保合计", index = 4)
    private Float paidInTotalSocialInsuranceBenefit;

    @NumberFormat("#.##")
    @ExcelProperty(value = "实缴公积金合计", index = 5)
    private Float paidInTotalProvidentFund;

    public ReconciliationResult() {

    }

    public ReconciliationResult(String cardNo, String name, Float billTotalSocialInsuranceBenefit,
                                Float billTotalProvidentFund, Float paidInTotalSocialInsuranceBenefit, Float paidInTotalProvidentFund) {
        this.cardNo = cardNo;
        this.name = name;
        this.billTotalSocialInsuranceBenefit = null == billTotalSocialInsuranceBenefit ? 0 : billTotalSocialInsuranceBenefit;
        this.billTotalProvidentFund = null == billTotalProvidentFund ? 0 : billTotalProvidentFund;
        this.paidInTotalSocialInsuranceBenefit = null == paidInTotalSocialInsuranceBenefit ? 0 : paidInTotalSocialInsuranceBenefit;
        this.paidInTotalProvidentFund = null == paidInTotalProvidentFund ? 0 : paidInTotalProvidentFund;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getBillTotalSocialInsuranceBenefit() {
        return billTotalSocialInsuranceBenefit;
    }

    public void setBillTotalSocialInsuranceBenefit(Float billTotalSocialInsuranceBenefit) {
        this.billTotalSocialInsuranceBenefit = billTotalSocialInsuranceBenefit;
    }

    public Float getBillTotalProvidentFund() {
        return billTotalProvidentFund;
    }

    public void setBillTotalProvidentFund(Float billTotalProvidentFund) {
        this.billTotalProvidentFund = billTotalProvidentFund;
    }

    public Float getPaidInTotalSocialInsuranceBenefit() {
        return paidInTotalSocialInsuranceBenefit;
    }

    public void setPaidInTotalSocialInsuranceBenefit(Float paidInTotalSocialInsuranceBenefit) {
        this.paidInTotalSocialInsuranceBenefit = paidInTotalSocialInsuranceBenefit;
    }

    public Float getPaidInTotalProvidentFund() {
        return paidInTotalProvidentFund;
    }

    public void setPaidInTotalProvidentFund(Float paidInTotalProvidentFund) {
        this.paidInTotalProvidentFund = paidInTotalProvidentFund;
    }
}
