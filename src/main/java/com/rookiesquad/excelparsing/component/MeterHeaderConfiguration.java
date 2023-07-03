package com.rookiesquad.excelparsing.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("meterHeaderConfiguration")
@ConfigurationProperties(prefix = "excel.head")
public class MeterHeaderConfiguration {

    /**
     * 姓名名称列表
     */
    private List<String> nameNameList;
    /**
     * 身份证号名称列表
     */
    private List<String> cardNoNameList;
    /**
     * 社保合计名称列表
     */
    private List<String> totalSocialInsuranceBenefitNameList;
    /**
     * 公积金合计名称列表
     */
    private List<String> totalProvidentFundNameList;

    public List<String> getNameNameList() {
        return nameNameList;
    }

    public void setNameNameList(List<String> nameNameList) {
        this.nameNameList = nameNameList;
    }

    public List<String> getCardNoNameList() {
        return cardNoNameList;
    }

    public void setCardNoNameList(List<String> cardNoNameList) {
        this.cardNoNameList = cardNoNameList;
    }

    public List<String> getTotalSocialInsuranceBenefitNameList() {
        return totalSocialInsuranceBenefitNameList;
    }

    public void setTotalSocialInsuranceBenefitNameList(List<String> totalSocialInsuranceBenefitNameList) {
        this.totalSocialInsuranceBenefitNameList = totalSocialInsuranceBenefitNameList;
    }

    public List<String> getTotalProvidentFundNameList() {
        return totalProvidentFundNameList;
    }

    public void setTotalProvidentFundNameList(List<String> totalProvidentFundNameList) {
        this.totalProvidentFundNameList = totalProvidentFundNameList;
    }
}
