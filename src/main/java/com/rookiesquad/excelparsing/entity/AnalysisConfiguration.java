package com.rookiesquad.excelparsing.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "excel_analysis_configuration", schema = "excel", uniqueConstraints = {@UniqueConstraint(name = "unique_idx_batch_number",
        columnNames = {"batch_number"})})
public class AnalysisConfiguration implements Serializable {

    @Serial
    private static final long serialVersionUID = 3599946894389619874L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "batch_number", nullable = false, columnDefinition = "varchar(32) comment '批次号'")
    private Long batchNumber;

    @Column(name = "file_path", nullable = false, columnDefinition = "varchar(32) comment '文件所在目录'")
    private String filePath;

    @Column(name = "reconciliation_type", nullable = false, columnDefinition = "tinyint comment '对账类型: 0-账单,1-实缴'")
    private Integer reconciliationType;

    @Column(name = "parsing_result", columnDefinition = "tinyint comment '解析结果: 0-失败,1-成功'")
    private Integer parsingResult;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Long batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getReconciliationType() {
        return reconciliationType;
    }

    public void setReconciliationType(Integer reconciliationType) {
        this.reconciliationType = reconciliationType;
    }

    public Integer getParsingResult() {
        return parsingResult;
    }

    public void setParsingResult(Integer parsingResult) {
        this.parsingResult = parsingResult;
    }
}
