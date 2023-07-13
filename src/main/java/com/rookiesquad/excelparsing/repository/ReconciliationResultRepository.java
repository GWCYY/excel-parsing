package com.rookiesquad.excelparsing.repository;

import com.rookiesquad.excelparsing.entity.ReconciliationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReconciliationResultRepository extends JpaRepository<ReconciliationResult, Long> {

    /**
     * 分页查询对账结果 -- 账单和实缴均需要存在数据
     * @param pageable 分页情况
     * @return 对账结果
     */
//    @Query("select" +
//            " bill.cardNo as cardNo," +
//            " bill.name as name," +
//            " coalesce(bill.totalSocialInsuranceBenefit, 0) as billTotalSocialInsuranceBenefit," +
//            " coalesce(paidIn.totalSocialInsuranceBenefit, 0) as paidInTotalSocialInsuranceBenefit," +
//            " coalesce(bill.totalProvidentFund, 0) as billTotalProvidentFund," +
//            " coalesce(paidIn.totalProvidentFund, 0) as paidInTotalProvidentFund" +
//            " from" +
//            " BillParsingData as bill" +
//            " left join PaidInParsingData as paidIn on bill.cardNo = paidIn.cardNo")
    @Query("select" +
            " new ReconciliationResult(bill.cardNo," +
            " bill.name," +
            " bill.totalSocialInsuranceBenefit," +
            " bill.totalProvidentFund," +
            " paidIn.totalSocialInsuranceBenefit," +
            " paidIn.totalProvidentFund)" +
            " from" +
            " BillParsingData as bill" +
            " left join PaidInParsingData as paidIn on bill.cardNo = paidIn.cardNo")
    Page<ReconciliationResult> findReconciliationResult(Pageable pageable);

    /**
     * 分页查询对账结果 -- 账单和实缴均需要存在数据
     * @return 对账结果
     */
    @Query("select" +
            " new ReconciliationResult(bill.cardNo," +
            " bill.name," +
            " bill.totalSocialInsuranceBenefit," +
            " bill.totalProvidentFund," +
            " paidIn.totalSocialInsuranceBenefit," +
            " paidIn.totalProvidentFund)" +
            " from" +
            " BillParsingData as bill" +
            " left join PaidInParsingData as paidIn on bill.cardNo = paidIn.cardNo")
    List<ReconciliationResult> listReconciliationResult();

    @Query("select" +
            " count(bill.cardNo)" +
            " from" +
            " BillParsingData as bill" +
            " left join PaidInParsingData as paidIn on bill.cardNo = paidIn.cardNo")
    int findAllReconciliationDataCount();
}
