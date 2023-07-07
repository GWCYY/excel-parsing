package com.rookiesquad.excelparsing.controller;

import com.rookiesquad.excelparsing.entity.AnalysisConfiguration;
import com.rookiesquad.excelparsing.service.AnalysisConfigurationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
