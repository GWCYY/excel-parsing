package com.rookiesquad.excelparsing.controller;

import com.rookiesquad.excelparsing.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/test")
    public void testRead(String filePath){
        excelService.testRead(filePath);
    }

}
