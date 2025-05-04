package com.kevindai.base.camunda_trail.listener;

import java.util.List;
import java.util.Optional;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

@Component("groupAutoClaimListener")
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

        // find the first configured candidate‐group on the task
        Optional<String> groupId = delegateTask.getCandidates().stream()
                .map(IdentityLink::getGroupId)
                .filter(id -> id != null && !id.isEmpty())
                .findFirst();

        groupId.ifPresent(gid -> {
            // fetch up to one user in that group
            List<User> members = identityService
                    .createUserQuery()
                    .memberOfGroup(gid)
                    .listPage(0, 1);

            if (!members.isEmpty()) {
                // assign to that user
                delegateTask.setAssignee(members.getFirst().getId());
            }
        });
    }
}