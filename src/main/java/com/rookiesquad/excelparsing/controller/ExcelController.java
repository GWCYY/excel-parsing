package com.rookiesquad.excelparsing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    @GetMapping("/test")
    public void testRead(String filePath){

    }
}
