package com.ivan.github.app.homepage.model.entity.event;

import com.google.gson.annotations.SerializedName;
import com.ivan.github.account.model.User;

import java.util.Date;

/**
 * com.ivan.github.app.events.model.Milestone
 *  {
 *       "url": "https://api.github.com/repos/Codertocat/Hello-World/milestones/1",
 *       "html_url": "https://github.com/Codertocat/Hello-World/milestone/1",
 *       "labels_url": "https://api.github.com/repos/Codertocat/Hello-World/milestones/1/labels",
 *       "id": 4317517,
 *       "node_id": "MDk6TWlsZXN0b25lNDMxNzUxNw==",
 *       "number": 1,
 *       "title": "v1.0",
 *       "description": "Add new space flight simulator",
 *       "creator": {
 *         "login": "Codertocat",
 *         "id": 21031067,
 *         "node_id": "MDQ6VXNlcjIxMDMxMDY3",
 *         "avatar_url": "https://avatars1.githubusercontent.com/u/21031067?v=4",
 *         "gravatar_id": "",
 *         "url": "https://api.github.com/users/Codertocat",
 *         "html_url": "https://github.com/Codertocat",
 *         "followers_url": "https://api.github.com/users/Codertocat/followers",
 *         "following_url": "https://api.github.com/users/Codertocat/following{/other_user}",
 *         "gists_url": "https://api.github.com/users/Codertocat/gists{/gist_id}",
 *         "starred_url": "https://api.github.com/users/Codertocat/starred{/owner}{/repo}",
 *         "subscriptions_url": "https://api.github.com/users/Codertocat/subscriptions",
 *         "organizations_url": "https://api.github.com/users/Codertocat/orgs",
 *         "repos_url": "https://api.github.com/users/Codertocat/repos",
 *         "events_url": "https://api.github.com/users/Codertocat/events{/privacy}",
 *         "received_events_url": "https://api.github.com/users/Codertocat/received_events",
 *         "type": "User",
 *         "site_admin": false
 *       },
 *       "open_issues": 1,
 *       "closed_issues": 0,
 *       "state": "closed",
 *       "created_at": "2019-05-15T15:20:17Z",
 *       "updated_at": "2019-05-15T15:20:18Z",
 *       "due_on": "2019-05-23T07:00:00Z",
 *       "closed_at": "2019-05-15T15:20:18Z"
 *     },
 *
 * @author Ivan J. Lee on 2020-01-01 21:49
 * @version v0.1
 * @since v1.0
 **/
public class Milestone {

    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("labels_url")
    private String labelsUrl;
    private int id;
    @SerializedName("node_id")
    private String nodeId;
    private int number;
    private String title;
    private String description;
    private User creator;
    @SerializedName("open_issues")
    private int openIssues;
    @SerializedName("closed_issues")
    private int closedIssues;
    private String state;
    @SerializedName("create_at")
    private Date createAt;
    @SerializedName("updte_at")
    private Date updatedAt;
    @SerializedName("due_on")
    private Date dueOn;
    @SerializedName("closed_at")
    private Date closedAt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getLabelsUrl() {
        return labelsUrl;
    }

    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getClosedIssues() {
        return closedIssues;
    }

    public void setClosedIssues(int closedIssues) {
        this.closedIssues = closedIssues;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDueOn() {
        return dueOn;
    }

    public void setDueOn(Date dueOn) {
        this.dueOn = dueOn;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }
}
