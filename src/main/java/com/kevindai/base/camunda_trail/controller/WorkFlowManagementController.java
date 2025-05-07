package com.kevindai.base.camunda_trail.controller;

import com.kevindai.base.camunda_trail.dto.CreateDeploymentRequest;
import com.kevindai.base.camunda_trail.dto.ProcessStarterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.util.JsonUtil;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WorkFlowManagementController {
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final IdentityService identityService;

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

    @GetMapping("/definitions")
    public ResponseEntity<List<String>> getDefinitions() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        List<String> ids = list.stream().map(ProcessDefinition::getId).toList();
        return ResponseEntity.ok(ids);
    }

    @DeleteMapping("/deployments/{deploymentId}")
    public ResponseEntity<Void> deleteDeployment(@PathVariable String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return ResponseEntity.noContent().build();
    }

    /**
     * @param definitionId id in table:act_re_procdef
     * @return ProcessInstance id,executionId in table:act_ru_task
     */
//    @PostMapping("/start-process/{definitionId}")
//    public ResponseEntity<String> startProcess(@PathVariable String definitionId) {
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).singleResult();
//        if (processDefinition == null) {
//            return ResponseEntity.badRequest().body("Deployment not found");
//        }
//        ProcessInstance processInstance = runtimeService.startProcessInstanceById(definitionId);
//        return ResponseEntity.ok(processInstance.getId());
//    }

    /**
     * @param processKey
     * @return
     */
    @PostMapping("/start-process/{processKey}")
    public ResponseEntity<String> startProcess(@PathVariable String processKey, @RequestBody ProcessStarterRequest processStarterRequest) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion().singleResult();
        if (processDefinition == null) {
            return ResponseEntity.badRequest().body("Deployment not found");
        }
        identityService.setAuthenticatedUserId("kevin");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, processStarterRequest.getVariables());
        return ResponseEntity.ok(processInstance.getId());
    }

    @PostMapping("/run-process/{executionId}")
    public ResponseEntity<String> runProcess(@PathVariable String executionId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
        if (processInstance == null) {
            return ResponseEntity.badRequest().body("ProcessInstance not found");
        }
        Task task = taskService.createTaskQuery().executionId(executionId).singleResult();
        log.info("task: {}", JsonUtil.asString(task));
        taskService.complete(task.getId());
        task = taskService.createTaskQuery().executionId(executionId).singleResult();
        log.info("task: {}", JsonUtil.asString(task));
        return ResponseEntity.ok(task.getId());

    }
}
