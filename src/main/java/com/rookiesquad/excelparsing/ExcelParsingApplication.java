package com.rookiesquad.excelparsing;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.rookiesquad.excelparsing.dto.BillData;
import com.rookiesquad.excelparsing.dto.PaidInData;
import com.rookiesquad.excelparsing.dto.ResultData;
import com.rookiesquad.excelparsing.listener.content.BillExcelListener;
import com.rookiesquad.excelparsing.listener.content.PaidInExcelListener;
import com.rookiesquad.excelparsing.listener.head.BillHeadExcelListener;
import com.rookiesquad.excelparsing.listener.head.PaidInHeadExcelListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class ExcelParsingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelParsingApplication.class, args);

        List<BillData> billData = readBill();
//        List<PayData> payData = readPay();
//        if (CollectionUtils.isEmpty(billData) || CollectionUtils.isEmpty(payData)) {
//            System.out.println("没有读取到数据");
//            return;
//        }
//        Map<String, List<PayData>> payMap = payData.stream()
//                .collect(Collectors.groupingBy(p -> p.getName() + p.getCardNo()));
//        List<ResultData> resultDataList = billData.stream().filter(r -> regexCheck(r.getCardNo())).map(r -> {
//            ResultData resultData = new ResultData();
//            resultData.setName(r.getName());
//            resultData.setCardNo(r.getCardNo());
//            resultData.setBillSsAccount(r.getSsAccount());
//            resultData.setBillPafAccount(r.getPafAccount());
//            List<PayData> payData1 = payMap.get(r.getName() + r.getCardNo());
//            if (CollectionUtils.isNotEmpty(payData1)) {
//                resultData.setPaySsAccount(getPaySsAccount(payMap.get(r.getName() + r.getCardNo())));
//                resultData.setPayPafAccount(getPafAccount(payMap.get(r.getName() + r.getCardNo())));
//            }
//            return resultData;
//        }).collect(Collectors.toList());
//        writeResult(resultDataList);
    }

    private static String getPaySsAccount(List<PaidInData> payDataList) {
        Optional<PaidInData> first = payDataList.stream().filter(r -> Float.parseFloat(r.getTotalSocialInsuranceBenefit()) > 0).findFirst();
        return first.map(PaidInData::getTotalSocialInsuranceBenefit).orElse("0.00");
    }

    private static String getPafAccount(List<PaidInData> payDataList) {
        Optional<PaidInData> first = payDataList.stream().filter(r -> Float.parseFloat(r.getTotalProvidentFund()) > 0).findFirst();
        return first.map(PaidInData::getTotalProvidentFund).orElse("0.00");
    }

    private static List<BillData> readBill() {
        List<BillData> billDataList = new ArrayList<>();

        File folder = new File("C:\\Users\\15155\\Desktop\\Desktop\\账单");
        File[] files = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xlsx") || name.endsWith(".xls");
            }
        });

        for (File file : files) {
            if (file.isFile()) {
                System.out.println("读取文件：" + file.getName());
                ExcelReader excelReader = EasyExcel.read(file).build();
                ExcelReadExecutor excelReadExecutor = excelReader.excelExecutor();

                Map<String, Integer> sheetMap = new HashMap<>();
                for (ReadSheet sheet : excelReadExecutor.sheetList()) {
                    BillHeadExcelListener billHeadExcelListener = new BillHeadExcelListener();
                    ExcelReaderBuilder readerBuilder = EasyExcel.read(file, billHeadExcelListener);
                    readerBuilder.sheet(sheet.getSheetNo()).headRowNumber(0).doRead();
                    sheetMap.put(sheet.getSheetName(), billHeadExcelListener.getHeaderRow());
                }

                // 循环sheetMap
                for (Map.Entry<String, Integer> entry : sheetMap.entrySet()) {
                    if (-1 == entry.getValue()) {
                        continue;
                    }
                    String sheetName = entry.getKey();
                    Integer headRowNumber = entry.getValue();
                    BillExcelListener billExcelListener = new BillExcelListener();
                    ExcelReaderBuilder readerBuilder = EasyExcel.read(file, BillData.class, billExcelListener);
                    readerBuilder.sheet(sheetName).headRowNumber(headRowNumber + 1).doRead();
                    billDataList.addAll(billExcelListener.getSourceDataList());
                }
            }
        }
        return billDataList;
    }

    private static List<PaidInData> readPay() {
        List<PaidInData> payDataList = new ArrayList<>();

        File folder = new File("C:\\Users\\15155\\Desktop\\Excel\\6.17实缴\\6.17");
        File[] files = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xlsx") || name.endsWith(".xls");
            }
        });

        for (File file : files) {
            if (file.isFile()) {
                System.out.println("读取文件：" + file.getName());
                ExcelReader excelReader = EasyExcel.read(file).build();
                ExcelReadExecutor excelReadExecutor = excelReader.excelExecutor();

                Map<String, Integer> sheetMap = new HashMap<>();
                for (ReadSheet sheet : excelReadExecutor.sheetList()) {
                    PaidInHeadExcelListener paidInHeadExcelListener = new PaidInHeadExcelListener();
                    ExcelReaderBuilder readerBuilder = EasyExcel.read(file, paidInHeadExcelListener);
                    readerBuilder.sheet(sheet.getSheetNo()).headRowNumber(0).doRead();
                    sheetMap.put(sheet.getSheetName(), paidInHeadExcelListener.getHeaderRow());
                }

                // 循环sheetMap
                for (Map.Entry<String, Integer> entry : sheetMap.entrySet()) {
                    if (-1 == entry.getValue()) {
                        continue;
                    }
                    String sheetName = entry.getKey();
                    Integer headRowNumber = entry.getValue();
                    PaidInExcelListener payExcelListener = new PaidInExcelListener();
                    ExcelReaderBuilder readerBuilder = EasyExcel.read(file, PaidInData.class, payExcelListener);
                    readerBuilder.sheet(sheetName).headRowNumber(headRowNumber + 1).doRead();
                    payDataList.addAll(payExcelListener.getSourceDataList());
                }
            }
        }
        return payDataList;
    }

    private static void writeResult(List<ResultData> list) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            EasyExcel.write(os, ResultData.class).sheet("Sheet1").doWrite(list);
            try {
                Files.write(Paths.get("tmp-" + System.currentTimeMillis() + ".xlsx"), Objects.requireNonNull(os.toByteArray()), StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean regexCheck(String cardNo) {
        if (null == cardNo)
            return false;
        cardNo = cardNo.trim();
        if (15 == cardNo.length() || 18 == cardNo.length()) {
            /*		15位旧居民身份证：
             * 	    "\\d{8}"                  1~6位分别代表省市县，这里不取字典表校验，只校验是否数字。
             * 								  7~8位代表年份后两位数字
             * 		"(0[1-9]|1[012])"          9~10位代表月份，01~12月
             * 		"(0[1-9]|[12]\\d|3[01])"  11~12位代表日期，1~31日
             * 		"\\d{3}"                  13~15位为三位顺序号
             *
             *     	18位新居民身份证：
             * 	    "\\d{6}"                  1~6位分别代表省市县，这里不取字典表校验，只校验是否数字。
             * 		"(18|19|20)\\d{2}"        7~10位代表年份，前两位18、19、20即19世纪、20世纪、21世纪，后两位数字。
             * 		中国寿星之首：阿丽米罕·色依提，女，1886年6月25日出生于新疆疏勒县，现年134岁，身份证起始日期在19世纪
             * 		"(0[1-9]|1[012])"          11~12位代表月份，01~12月
             * 		"(0[1-9]|[12]\\d|3[01])"  13~14位代表日期，1~31日
             * 		"\\d{3}"                  15~17位为三位顺序号
             * 		"(\\d|X|x)"               18位为校验位数字，允许字母x和X
             *
             * 		正则表达式合并为：
             * 		^(\\d{6}(18|19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X|x))|(\\d{8}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3})$
             * */
            Pattern pattern = Pattern.compile("^(\\d{6}(18|19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X|x))|(\\d{8}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3})$");
            Matcher m = pattern.matcher(cardNo);
            return (m.matches()) ? true : false;
        } else {
            return false;
        }
    }
}
