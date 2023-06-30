package com.rookiesquad.excelparsing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @GetMapping("/test")
    public void testRead(String filePath){
        logger.info(filePath);
    }
}
