package com.kevindai.base.camunda_trail.controller;

import com.kevindai.base.camunda_trail.dto.CreateDeploymentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WorkFlowManagementController {
    private final RepositoryService repositoryService;

    @PostMapping("/deploy")
    public ResponseEntity<String> deploy(@RequestBody @Valid CreateDeploymentRequest createDeploymentRequest) {
        Deployment deploy = repositoryService.createDeployment()
                .name(createDeploymentRequest.getName())
                .addClasspathResource(createDeploymentRequest.getSource())
                .deploy();
        return ResponseEntity.ok(deploy.getId());
    }

    @GetMapping("/deployments")
    public ResponseEntity<List<String>> getDeployments() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        List<String> ids = list.stream().map(Deployment::getId).toList();
        return ResponseEntity.ok(ids);
    }

    @DeleteMapping("/deployments/{deploymentId}")
    public ResponseEntity<Void> deleteDeployment(@PathVariable String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return ResponseEntity.noContent().build();
    }
}
