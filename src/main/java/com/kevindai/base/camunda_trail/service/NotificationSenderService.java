package com.kevindai.base.camunda_trail.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationSenderService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String role = (String) execution.getVariable("role");
        log.info("Notification sent to " + role);
    }
}
