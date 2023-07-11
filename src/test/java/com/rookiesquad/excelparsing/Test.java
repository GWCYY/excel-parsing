package com.rookiesquad.excelparsing;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Test {

    //    public static void main(String[] args) {
//        String filePath = "C:\\Users\\15155\\Desktop\\Desktop\\账单\\1有限公司202305费用明细.xls";
//        filePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8);
//        System.out.println(filePath);
//    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        File folder = new File("C:\\Users\\wade\\Desktop\\scanner-0705\\scanner");
        String[] list = folder.list();
        for (String s : list) {
            System.out.println(folder.getAbsolutePath() + File.separator + s);
        }
    }
}
