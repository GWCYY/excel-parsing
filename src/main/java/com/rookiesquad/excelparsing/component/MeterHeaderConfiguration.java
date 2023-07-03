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
    private List<String> nameList;
    /**
     * 身份证号名称列表
     */
    private List<String> cardNoList;
    /**
     * 社保合计名称列表
     */
    private List<String> totalSocialInsuranceBenefitList;
    /**
     * 公积金合计名称列表
     */
    private List<String> totalProvidentFundList;

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getCardNoList() {
        return cardNoList;
    }

    public void setCardNoList(List<String> cardNoList) {
        this.cardNoList = cardNoList;
    }

    public List<String> getTotalSocialInsuranceBenefitList() {
        return totalSocialInsuranceBenefitList;
    }

    public void setTotalSocialInsuranceBenefitList(List<String> totalSocialInsuranceBenefitList) {
        this.totalSocialInsuranceBenefitList = totalSocialInsuranceBenefitList;
    }

    public List<String> getTotalProvidentFundList() {
        return totalProvidentFundList;
    }

    public void setTotalProvidentFundList(List<String> totalProvidentFundList) {
        this.totalProvidentFundList = totalProvidentFundList;
    }
}
