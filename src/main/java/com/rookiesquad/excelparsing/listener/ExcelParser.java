package com.rookiesquad.excelparsing.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson2.JSON;
import com.rookiesquad.excelparsing.dto.BillData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelParser {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\15155\\Desktop\\Desktop\\账单\\1有限公司202305费用明细.xls";
        String[] possibleHeaders = {"身份证号", "身份证号码"};

        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(filePath, excelListener).sheet("合肥").doRead();
        System.out.println(excelListener.resultList);

//        List<String> headers = excelListener.getHeaders();
////        String header = findMatchingHeader(headers, possibleHeaders);
//
//        if (header != null) {
//            // 读取身份证号列的数据
//            List<String> idNumbers = excelListener.getColumnData(header);
//            for (Object idNumber : idNumbers) {
//                // 处理身份证号的逻辑
//                System.out.println("身份证号：" + idNumber);
//            }
//        } else {
//            System.out.println("找不到身份证号的表头");
//        }
    }

    private static String findMatchingHeader(List<String> headers, String[] possibleHeaders) {
        for (String header : headers) {
            for (String possibleHeader : possibleHeaders) {
                if (header.equalsIgnoreCase(possibleHeader)) {
                    return header;
                }
            }
        }
        return null;
    }

    static class ExcelListener extends AnalysisEventListener<Map<Integer, String>> {
        private List<String> headers = new ArrayList<>();
        private Map<Integer, String> columnIndexMap = new HashMap<>();
        private Map<String, List<BillData>> columnDataMap = new HashMap<>();
        private List<BillData> resultList = new ArrayList<>();
        private String currentHeader;

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            // 获取表头名称
            for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                headers.add(entry.getValue());
            }
        }

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            boolean hasIdCardNo = data.containsValue("身份证号码") || data.containsValue("身份证");
            if (hasIdCardNo) {
                currentHeader = JSON.toJSONString(data);
                // 当前行是表头行
                data.forEach((columnIndex, columnValue) -> {
                    if (null == columnValue) {
                        return;
                    }
                    switch (columnValue) {
                        case "身份证号码":
                        case "身份证":
                            columnIndexMap.put(columnIndex, "cardNum");
                            break;
                        case "姓名":
                        case "名称":
                            columnIndexMap.put(columnIndex, "name");
                            break;
                        default:
                            System.out.println(111);
                    }
                });

            } else {
                // 当前行是数据行
                if (currentHeader != null) {
                    BillData billData = new BillData();
                    columnIndexMap.keySet().forEach(columnIndex -> {
                        String columnName = columnIndexMap.get(columnIndex);
                        String columnValue = data.get(columnIndex);
                        switch (columnName){
                            case "cardNum":
                                billData.setCardNo(columnValue);
                                break;
                            case "name":
                                billData.setName(columnValue);
                                break;
                            default:
                                System.out.println(222);
                        }
                    });
                    resultList.add(billData);
                }
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

        public List<String> getHeaders() {
            return headers;
        }

        public List<BillData> getColumnData(String header) {
            return columnDataMap.get(header);
        }
    }
}

