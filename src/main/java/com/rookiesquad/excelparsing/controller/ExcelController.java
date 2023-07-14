package com.rookiesquad.excelparsing.controller;

import com.rookiesquad.excelparsing.entity.ReconciliationResult;
import com.rookiesquad.excelparsing.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/test")
    public void testRead(Long batchNumber){
        excelService.testRead(batchNumber);
    }

    @PostMapping("/reconciliation-data")
    public void parsingExcel(Long batchNumber){
        excelService.parsingExcel(batchNumber);
    }

    @GetMapping("/reconciliation-result")
    public Page<ReconciliationResult> pageParsingExcelResult(Pageable pageable){
        return excelService.pageParsingExcelResult(pageable);
    }

    @GetMapping("/reconciliation-result/excel")
    public void downloadParsingExcelResult(HttpServletResponse response) {
        excelService.downloadParsingExcelResult(response);
    }

    @PostMapping("/test")
    public void buildTestData(){
        excelService.buildTestData();
    }

}
