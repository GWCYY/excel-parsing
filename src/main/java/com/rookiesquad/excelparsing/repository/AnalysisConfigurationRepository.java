package com.rookiesquad.excelparsing.repository;

import com.rookiesquad.excelparsing.entity.AnalysisConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisConfigurationRepository extends JpaRepository<AnalysisConfiguration, Long> {

    AnalysisConfiguration findByBatchNumber(Long batchNumber);
}
