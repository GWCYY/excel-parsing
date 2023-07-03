package com.rookiesquad.excelparsing.dto;

import java.io.Serializable;

public interface BaseData extends Serializable {

    void setCardNo(String cardNo);

    String getCardNo();

    void setName(String name);

    void setTotalSocialInsuranceBenefit(String ssAccount);

    void setTotalProvidentFund(String pafAccount);

}
