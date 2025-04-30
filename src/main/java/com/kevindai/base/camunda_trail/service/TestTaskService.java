package com.kevindai.base.camunda_trail.service;

import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.stereotype.Service;

@Service
public class TestTaskService implements ActivityBehavior {
    @Override
    public void execute(ActivityExecution activityExecution) throws Exception {
        System.out.println("TestTaskService executed");
        activityExecution.end(true);
    }
}
