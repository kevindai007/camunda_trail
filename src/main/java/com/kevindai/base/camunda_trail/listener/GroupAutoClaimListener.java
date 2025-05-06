package com.kevindai.base.camunda_trail.listener;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GroupAutoClaimListener implements TaskListener {

    private final IdentityService identityService;

    public GroupAutoClaimListener(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        // only on creation
        if (!"create".equals(delegateTask.getEventName())) {
            return;
        }

        Optional<String> groupId = delegateTask.getCandidates().stream()
                .map(IdentityLink::getGroupId)
                .filter(id -> id != null && !id.isEmpty())
                .findFirst();

        groupId.ifPresent(gid -> {
            List<User> members = identityService
                    .createUserQuery()
                    .memberOfGroup(gid)
                    .listPage(0, 1);

            if (!members.isEmpty()) {
                delegateTask.setAssignee(members.getFirst().getId());
            }
        });
    }
}