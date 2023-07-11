package com.rookiesquad.excelparsing.service;

import com.rookiesquad.excelparsing.entity.AnalysisConfiguration;
import com.rookiesquad.excelparsing.repository.AnalysisConfigurationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnalysisConfigurationService implements BaseService {

    private final AnalysisConfigurationRepository analysisConfigurationRepository;

    public AnalysisConfigurationService(AnalysisConfigurationRepository analysisConfigurationRepository) {
        this.analysisConfigurationRepository = analysisConfigurationRepository;
    }

    @Transactional
    public AnalysisConfiguration addAnalysisConfiguration(AnalysisConfiguration analysisConfiguration) {
        analysisConfiguration.setBatchNumber(snowflake.nextId());
        return analysisConfigurationRepository.save(analysisConfiguration);
    }

    public Page<AnalysisConfiguration> pageAnalysisConfiguration(Pageable pageable) {
        return analysisConfigurationRepository.findAll(pageable);
    }
}
