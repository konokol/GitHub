package com.ivan.github.app.homepage.model.entity.event.payload;

import com.ivan.github.app.homepage.model.entity.event.Payload;
import com.ivan.github.app.homepage.model.entity.event.Repository;

import java.security.acl.Owner;

/**
 * com.ivan.github.app.events.model.payload.ForkEventPayload
 *
 * @author  Ivan J. Lee on 2020-01-01
 * @version v0.1
 * @since   v1.0
 **/
public class ForkEventPayload extends Payload {

    private Repository forkee;
    private Repository repository;
    private Owner sender;

    public Repository getForkee() {
        return forkee;
    }

    public void setForkee(Repository forkee) {
        this.forkee = forkee;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Owner getSender() {
        return sender;
    }

    public void setSender(Owner sender) {
        this.sender = sender;
    }
}
