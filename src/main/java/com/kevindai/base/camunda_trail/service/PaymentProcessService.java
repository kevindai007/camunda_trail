package com.kevindai.base.camunda_trail.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentProcessService {

    private final RuntimeService runtimeService;

//    @EventListener
//    private void processPostDeploy(PostDeployEvent event) {
//        runtimeService.startProcessInstanceByKey("payment-retrival");
//    }
}
