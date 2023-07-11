package com.rookiesquad.excelparsing.controller;

import com.rookiesquad.excelparsing.entity.AnalysisConfiguration;
import com.rookiesquad.excelparsing.service.AnalysisConfigurationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class AnalysisConfigurationController {

    private final AnalysisConfigurationService analysisConfigurationService;

    public AnalysisConfigurationController(AnalysisConfigurationService analysisConfigurationService) {
        this.analysisConfigurationService = analysisConfigurationService;
    }

    @PostMapping("/analysis-configuration")
    public AnalysisConfiguration addAnalysisConfiguration(@RequestBody AnalysisConfiguration analysisConfiguration){
        return analysisConfigurationService.addAnalysisConfiguration(analysisConfiguration);
    }

    @GetMapping("/analysis-configuration")
    public Page<AnalysisConfiguration> pageAnalysisConfiguration(Pageable pageable){
        return analysisConfigurationService.pageAnalysisConfiguration(pageable);
    }

}
