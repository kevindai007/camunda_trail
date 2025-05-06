package com.kevindai.base.camunda_trail.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskFinishNotificationService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String initiator = (String) execution.getVariable("initiator");
        String currentActivityName = execution.getCurrentActivityName();
        log.info("Current activity name is {}", currentActivityName);
        log.info("Mock sending notification to user {}", initiator);
    }
}
