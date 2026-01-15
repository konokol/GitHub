package com.ivan.github.app.homepage.model.entity.event.payload;

import com.google.gson.annotations.SerializedName;
import com.ivan.github.account.model.User;
import com.ivan.github.app.homepage.model.entity.event.Payload;
import com.ivan.github.app.homepage.model.entity.event.Repository;

/**
 * com.ivan.github.app.events.model.payload.DeleteEventPayload
 *
 * @author  Iavn J. Lee on 2020-01-01
 * @version v0.1
 * @since   v1.0
 **/
public class DeleteEventPayload extends Payload {

    public static final String REF_TYPE_BRANCH = "branch";
    public static final String REF_TYPE_TAG = "tag";

    private String ref;
    @SerializedName("ref_type")
    private String refType; // branch or tag
    @SerializedName("push_type")
    private String pusherType;
    private Repository repository;
    private User sender;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getPusherType() {
        return pusherType;
    }

    public void setPusherType(String pusherType) {
        this.pusherType = pusherType;
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
