package com.rookiesquad.excelparsing;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\15155\\Desktop\\Desktop\\账单\\1有限公司202305费用明细.xls";
        filePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);
        System.out.println(filePath);
    }
}
