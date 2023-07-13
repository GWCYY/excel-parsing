package com.rookiesquad.excelparsing.service.impl;

import com.rookiesquad.excelparsing.constant.RegularConstants;
import com.rookiesquad.excelparsing.dto.BillData;
import com.rookiesquad.excelparsing.entity.BillParsingData;
import com.rookiesquad.excelparsing.listener.content.BillExcelListener;
import com.rookiesquad.excelparsing.listener.head.BillHeadExcelListener;
import com.rookiesquad.excelparsing.repository.BillParsingDataRepository;
import com.rookiesquad.excelparsing.service.ExcelParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillParsingService extends ExcelParsingService<BillData> {

    private static final Logger logger = LoggerFactory.getLogger(BillParsingService.class);

    private final BillParsingDataRepository billParsingDataRepository;

    public BillParsingService(BillParsingDataRepository billParsingDataRepository) {
        this.billParsingDataRepository = billParsingDataRepository;
    }

    @Transactional
    @Override
    public Integer parsing(String filePath) {
        customHeadExcelListener = new BillHeadExcelListener();
        customContentExcelListener = new BillExcelListener();
        return parsingExcel(filePath);
    }

    @Override
    protected void addParsingData() {
        List<BillParsingData> billParsingDataList = new ArrayList<>();
        customContentExcelListener.getSourceDataList().forEach(sourceData -> {
            String cardNo = sourceData.getCardNo();
            if (!StringUtils.hasText(cardNo) || !RegularConstants.NUMBER_PATTERN.matcher(cardNo).matches()) {
                return;
            }
            logger.info(sourceData.toString());
            BillParsingData billParsingData = new BillParsingData();
            super.buildReconciliationData(billParsingData, sourceData, billParsingDataList);
        });
        billParsingDataRepository.saveAll(billParsingDataList);
    }
}
