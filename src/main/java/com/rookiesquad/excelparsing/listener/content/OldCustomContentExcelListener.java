package com.rookiesquad.excelparsing.listener.content;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.rookiesquad.excelparsing.dto.BaseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class OldCustomContentExcelListener<T extends BaseData> extends AnalysisEventListener<T> {

    private static final Logger logger = LoggerFactory.getLogger(OldCustomContentExcelListener.class);

    protected final List<T> sourceDataList = new ArrayList<>();

    /**
     * 进行读的操作具体执行方法，一行一行的读取数据
     * 从第二行开始读取，不读取表头
     *
     * @param data 读取到的数据
     * @param context analysis context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if (Objects.isNull(data) || !StringUtils.hasText(data.getCardNo())) {
            return;
        }
        sourceDataList.add(data);
        logger.info("Read a piece of data : [{}]", data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<T> getSourceDataList() {
        return sourceDataList;
    }
}
