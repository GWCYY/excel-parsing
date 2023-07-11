package com.rookiesquad.excelparsing.repository;

import com.rookiesquad.excelparsing.entity.BillParsingData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BillParsingDataRepository extends ReconciliationDataRepository<BillParsingData> {

    @Query(nativeQuery = true, value = "select bill.id_card_number, bill.username," +
            " (bill.total_social_insurance_benefit + paidIn.total_social_insurance_benefit) as total_social_insurance_benefit," +
            " (bill.total_provident_fund + paidIn.total_provident_fund) as total_provident_fund" +
            " from excel_bill_parsing_data bill, excel_paid_in_parsing_data paidIn where bill.id_card_number = paidIn.id_card_number")
    Page<BillParsingData> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "select bill.id_card_number, bill.username," +
            " (bill.total_social_insurance_benefit + paidIn.total_social_insurance_benefit) as total_social_insurance_benefit," +
            " (bill.total_provident_fund + paidIn.total_provident_fund) as total_provident_fund" +
            " from excel_bill_parsing_data bill" +
            " left join excel_paid_in_parsing_data paidIn" +
            " on bill.id_card_number = paidIn.id_card_number")
    Page<BillParsingData> findBillData(Pageable pageable);

}
