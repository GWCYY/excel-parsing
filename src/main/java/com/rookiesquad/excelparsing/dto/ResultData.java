package com.rookiesquad.excelparsing.dto;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author lianghonglei
 */
public class ResultData {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;
    @ExcelProperty(value = "身份证号码", index = 1)
    private String cardNo;
    @ExcelProperty(value = "账单社保合计", index = 2)
    private String billSsAccount;
    @ExcelProperty(value = "账单公积金合计", index = 3)
    private String billPafAccount;
    @ExcelProperty(value = "实缴社保合计", index = 4)
    private String paySsAccount;
    @ExcelProperty(value = "实缴公积金合计", index = 5)
    private String payPafAccount;

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

    public String getBillSsAccount() {
        return billSsAccount;
    }

    public void setBillSsAccount(String billSsAccount) {
        this.billSsAccount = billSsAccount;
    }

    public String getBillPafAccount() {
        return billPafAccount;
    }

    public void setBillPafAccount(String billPafAccount) {
        this.billPafAccount = billPafAccount;
    }

    public String getPaySsAccount() {
        return paySsAccount;
    }

    public void setPaySsAccount(String paySsAccount) {
        this.paySsAccount = paySsAccount;
    }

    public String getPayPafAccount() {
        return payPafAccount;
    }

    public void setPayPafAccount(String payPafAccount) {
        this.payPafAccount = payPafAccount;
    }
}
