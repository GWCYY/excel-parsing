package com.rookiesquad.excelparsing.service.impl;

import com.rookiesquad.excelparsing.constant.RegularConstants;
import com.rookiesquad.excelparsing.dto.PaidInData;
import com.rookiesquad.excelparsing.entity.PaidInParsingData;
import com.rookiesquad.excelparsing.listener.content.PaidInExcelListener;
import com.rookiesquad.excelparsing.listener.head.PaidInHeadExcelListener;
import com.rookiesquad.excelparsing.repository.PaidInParsingDataRepository;
import com.rookiesquad.excelparsing.service.ExcelParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaidInParsingService extends ExcelParsingService<PaidInData> {

    private static final Logger logger = LoggerFactory.getLogger(PaidInParsingService.class);

    private final PaidInParsingDataRepository paidInParsingDataRepository;

    public PaidInParsingService(PaidInParsingDataRepository paidInParsingDataRepository) {
        this.paidInParsingDataRepository = paidInParsingDataRepository;
    }

    @Transactional
    @Override
    public Integer parsing(String filePath) {
        customHeadExcelListener = new PaidInHeadExcelListener();
        customContentExcelListener = new PaidInExcelListener();
        return parsingExcel(filePath);
    }

    @Override
    protected void addParsingData() {
        List<PaidInParsingData> paidInParsingDataList = new ArrayList<>();
        customContentExcelListener.getSourceDataList().forEach(sourceData -> {
            String cardNo = sourceData.getCardNo();
            if (!StringUtils.hasText(cardNo) || !RegularConstants.NUMBER_PATTERN.matcher(cardNo).matches()) {
                return;
            }
            logger.info(sourceData.toString());
            PaidInParsingData paidInParsingData = new PaidInParsingData();
            super.buildReconciliationData(paidInParsingData, sourceData, paidInParsingDataList);
        });
        paidInParsingDataRepository.saveAll(paidInParsingDataList);
    }
}
