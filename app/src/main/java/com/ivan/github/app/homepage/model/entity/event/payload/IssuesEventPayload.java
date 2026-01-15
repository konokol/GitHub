package com.ivan.github.app.homepage.model.entity.event.payload;

import com.ivan.github.account.model.User;
import com.ivan.github.app.homepage.model.entity.event.Issue;
import com.ivan.github.app.homepage.model.entity.event.Label;
import com.ivan.github.app.homepage.model.entity.event.Milestone;
import com.ivan.github.app.homepage.model.entity.event.Payload;
import com.ivan.github.app.homepage.model.entity.event.Repository;

import java.util.ArrayList;

/**
 * The action that was performed.
 * Can be one of opened, edited, deleted, pinned, unpinned, closed, reopened, assigned, unassigned,
 * labeled, unlabeled, locked, unlocked, transferred, milestoned, or demilestoned.
 *
 * @author Ivan J. Lee on 2020-01-01 20:10
 * @version v0.1
 * @since v1.0
 **/
public class IssuesEventPayload extends Payload {

    private Issue issue;
    private ArrayList<Label> labels;
    private String state;
    private boolean loacked;
    private User assignee;
    private ArrayList<User> assignees;
    private Milestone milestone;
    private String changes; // TODO: 2020-01-01
    private Repository repository;
    private User sender;
}
