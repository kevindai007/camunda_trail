package com.kevindai.base.camunda_trail.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AssigneeListener implements ExecutionListener {
    private final IdentityService identityService;

    @Override
    public void notify(DelegateExecution execution) {
        String next = (String) execution.getVariable("nextAssignee");
        if (next == null) {
            return;
        }
        TaskService tasks = execution.getProcessEngineServices().getTaskService();
        Task task = tasks.createTaskQuery()
                .processInstanceId(execution.getProcessInstanceId())
                .taskDefinitionKey("Activity_06dnfsw")
                .singleResult();
        if (task != null) {
            List<User> users = identityService.createUserQuery()
                    .userFirstName(next)
                    .list();
            if (!users.isEmpty()) {
                tasks.setAssignee(task.getId(), users.get(0).getId());
            }
        }
    }
}
