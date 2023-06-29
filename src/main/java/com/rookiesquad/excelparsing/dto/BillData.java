package com.rookiesquad.excelparsing.dto;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author lianghonglei
 */
public class BillData implements BaseData{
    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "身份证号码")
    private String cardNo;

    @ExcelProperty(value = "社保合计")
    private String ssAccount;

    @ExcelProperty(value = "公积金合计")
    private String pafAccount;

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

    public String getSsAccount() {
        return ssAccount;
    }

    public void setSsAccount(String ssAccount) {
        this.ssAccount = ssAccount;
    }

    public String getPafAccount() {
        return pafAccount;
    }

    public void setPafAccount(String pafAccount) {
        this.pafAccount = pafAccount;
    }
}
