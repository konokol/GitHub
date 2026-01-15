package com.ivan.github.app.homepage.model.entity.event.payload;

import com.ivan.github.account.model.User;
import com.ivan.github.app.homepage.model.entity.event.Comment;
import com.ivan.github.app.homepage.model.entity.event.Issue;
import com.ivan.github.app.homepage.model.entity.event.Payload;
import com.ivan.github.app.homepage.model.entity.event.Repository;

/**
 * com.ivan.github.app.events.model.payload.IssueCommentEventPayload
 *
 * @author  Ivan J. Lee on 2020-01-01 22:11
 * @version v0.1
 * @since   v1.0
 **/
public class IssueCommentEventPayload extends Payload {

    private Issue issue;
    private Comment comment;
    private Repository repository;
    private User sender;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
