package com.rookiesquad.excelparsing.repository;

import com.rookiesquad.excelparsing.entity.BillParsingData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BillParsingDataRepository extends ReconciliationDataRepository<BillParsingData, Long> {

    /**
     * 分页查询对账结果 -- 账单和实缴均需要存在数据
     * @param pageable 分页情况
     * @return 对账结果
     */
    @Query("select bill.cardNo, bill.name," +
            " (coalesce(bill.totalSocialInsuranceBenefit, 0) + coalesce(paidIn.totalSocialInsuranceBenefit, 0)) as totalSocialInsuranceBenefit," +
            " (coalesce(bill.totalProvidentFund, 0) + coalesce(paidIn.totalProvidentFund, 0)) as totalProvidentFund" +
            " from BillParsingData as bill, PaidInParsingData as paidIn where bill.cardNo = paidIn.cardNo")
    Page<BillParsingData> findAll(Pageable pageable);

    /**
     * 分页查询对账结果 -- 以账单为基准表
     * @param pageable 分页情况
     * @return 对账结果
     */
    @Query("select" +
            " bill.cardNo," +
            " bill.name," +
            " (coalesce(bill.totalSocialInsuranceBenefit, 0) + coalesce(paidIn.totalSocialInsuranceBenefit, 0)) as totalSocialInsuranceBenefit," +
            " (coalesce(bill.totalProvidentFund, 0) + coalesce(paidIn.totalProvidentFund, 0)) as totalProvidentFund" +
            " from" +
            " BillParsingData as bill" +
            " left join PaidInParsingData as paidIn on bill.cardNo = paidIn.cardNo")
    Page<BillParsingData> findBillData(Pageable pageable);

}
