package com.rookiesquad.excelparsing.dto;

import java.io.Serializable;

public interface BaseData extends Serializable {

    void setCardNo(String cardNo);

    String getCardNo();

    void setName(String name);

    String getName();

    void setTotalSocialInsuranceBenefit(String ssAccount);

    String getTotalSocialInsuranceBenefit();

    void setTotalProvidentFund(String pafAccount);

    String getTotalProvidentFund();

}
