package com.rookiesquad.excelparsing.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serial;

/**
 * @author lianghonglei
 */
public class BillData implements BaseData {

    @Serial
    private static final long serialVersionUID = -3259144118497285339L;

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "身份证号码")
    private String cardNo;

    @ExcelProperty(value = "社保合计")
    private String totalSocialInsuranceBenefit;

    @ExcelProperty(value = "公积金合计")
    private String totalProvidentFund;

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

    @Override
    public String toString() {
        return "BillData{" +
                "name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", totalSocialInsuranceBenefit='" + totalSocialInsuranceBenefit + '\'' +
                ", totalProvidentFund='" + totalProvidentFund + '\'' +
                '}';
    }
}
