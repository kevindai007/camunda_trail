package com.kevindai.base.camunda_trail.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushMciResultService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String target = (String) execution.getVariable("target");
        String incidentNo = (String) execution.getVariable("incident_no");
        log.info("incidentNo: {}, pushMciResult to {}", incidentNo, target);
    }
}
