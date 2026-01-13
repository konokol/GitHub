package com.ivan.github.app.homepage.model.entity.event;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.ivan.github.account.model.User;
import com.ivan.github.common.util.GsonUtils;

import java.util.Date;

/**
 * Events a user received
 *
 * @author  Ivan J. Lee on 2019-03-30 00:31.
 * @version v0.1
 * @since   v1.0
 */
public class Event {

    private String id;
    private String type;
    private User actor;
    private Repository repo;
    @SerializedName("public")
    private boolean isPublic;
    @SerializedName("created_at")
    private Date createdAt;
    private Org org;
    private JsonObject payload;
    private transient Object parsedPayload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public Repository getRepo() {
        return repo;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    public JsonObject getPayload() {
        return payload;
    }

    public void setPayload(JsonObject payload) {
        this.payload = payload;
    }

    public <T> T parsePayload(Class<T> clazz) {
        if (payload == null) {
            return null;
        }
        if (parsedPayload != null) {
            return (T)parsedPayload;
        } else {
            parsedPayload = GsonUtils.from(payload, clazz);
        }
        return (T)parsedPayload;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}

