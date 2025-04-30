package com.kevindai.base.camunda_trail.config;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaProcessFlowEngineConfig {
//    @Bean
//    public ProcessEnginePlugin historyTTLPlugin() {
//        return new AbstractProcessEnginePlugin() {
//            @Override
//            public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
//                processEngineConfiguration.setHistoryTimeToLive("180");
//            }
//        };
//    }

}
