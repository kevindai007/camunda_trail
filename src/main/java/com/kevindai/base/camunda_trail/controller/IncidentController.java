package com.kevindai.base.camunda_trail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class IncidentController {
    private final RuntimeService runtimeService;

    @PostMapping("/incident/handling")
    public String handling(@RequestBody Map<String, Object> variables) {
        log.info("Handling incident");
        runtimeService
                .createMessageCorrelation("Message_IncidentStatusChange")
                .processInstanceBusinessKey(variables.get("incident_no").toString())
                .setVariable("updatedStatus", "handling")
                .correlate();
        return "Handling incident";
    }
}
