package com.rookiesquad.excelparsing.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "excel_reconciliation_data", schema = "excel", uniqueConstraints = {@UniqueConstraint(name = "unique_idx_id_card_number",
        columnNames = {"id_card_number"})})
public class ReconciliationData implements Serializable {

    @Serial
    private static final long serialVersionUID = -40731148723266202L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(32) comment '姓名'")
    private String name;

    @Column(name = "id_card_number", nullable = false, columnDefinition = "varchar(32) comment '身份证号码'")
    private String cardNo;

    @Column(name = "total_social_insurance_benefit", columnDefinition = "float(10,2) comment '社保合计'")
    private String totalSocialInsuranceBenefit;

    @Column(name = "total_provident_fund", columnDefinition = "float(10,2) comment '公积金合计'")
    private String totalProvidentFund;

    @Column(name = "type", nullable = false, columnDefinition = "tinyint comment '0-账单; 1-实缴'")
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

    public void setTotalSocialInsuranceBenefit(String totalSocialInsuranceBenefit) {
        this.totalSocialInsuranceBenefit = totalSocialInsuranceBenefit;
    }

    public String getTotalProvidentFund() {
        return totalProvidentFund;
    }

    public void setTotalProvidentFund(String totalProvidentFund) {
        this.totalProvidentFund = totalProvidentFund;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
