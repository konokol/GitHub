package com.ivan.github.account.model;

import com.google.gson.annotations.SerializedName;
import com.ivan.github.core.net.NetModel;

/**
 * plan
 *
 * @author Ivan on 2018-11-28 00:06.
 * @version v0.1
 * @since v1.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Plan implements NetModel {
    public String name;
    public int space;
    @SerializedName("private_repos")
    public int privateRepos;
    public int collaborators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getPrivateRepos() {
        return privateRepos;
    }

    public void setPrivateRepos(int privateRepos) {
        this.privateRepos = privateRepos;
    }

    public int getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(int collaborators) {
        this.collaborators = collaborators;
    }
}
