package com.ivan.github.app.homepage.model.entity.event;

import com.google.gson.annotations.SerializedName;
import com.ivan.github.account.model.User;

/**
 * com.ivan.github.app.events.model.Issue
 *
 *{
 *   "url": "https://api.github.com/repos/Codertocat/Hello-World/issues/1",
 *   "repository_url": "https://api.github.com/repos/Codertocat/Hello-World",
 *   "labels_url": "https://api.github.com/repos/Codertocat/Hello-World/issues/1/labels{/name}",
 *   "comments_url": "https://api.github.com/repos/Codertocat/Hello-World/issues/1/comments",
 *   "events_url": "https://api.github.com/repos/Codertocat/Hello-World/issues/1/events",
 *   "html_url": "https://github.com/Codertocat/Hello-World/issues/1",
 *   "id": 444500041,
 *   "node_id": "MDU6SXNzdWU0NDQ1MDAwNDE=",
 *   "number": 1,
 *   "title": "Spelling error in the README file",
 *   "user": {
 *     "login": "Codertocat",
 *     "id": 21031067,
 *     "node_id": "MDQ6VXNlcjIxMDMxMDY3",
 *     "avatar_url": "https://avatars1.githubusercontent.com/u/21031067?v=4",
 *     "gravatar_id": "",
 *     "url": "https://api.github.com/users/Codertocat",
 *     "html_url": "https://github.com/Codertocat",
 *     "followers_url": "https://api.github.com/users/Codertocat/followers",
 *     "following_url": "https://api.github.com/users/Codertocat/following{/other_user}",
 *     "gists_url": "https://api.github.com/users/Codertocat/gists{/gist_id}",
 *     "starred_url": "https://api.github.com/users/Codertocat/starred{/owner}{/repo}",
 *     "subscriptions_url": "https://api.github.com/users/Codertocat/subscriptions",
 *     "organizations_url": "https://api.github.com/users/Codertocat/orgs",
 *     "repos_url": "https://api.github.com/users/Codertocat/repos",
 *     "events_url": "https://api.github.com/users/Codertocat/events{/privacy}",
 *     "received_events_url": "https://api.github.com/users/Codertocat/received_events",
 *     "type": "User",
 *     "site_admin": false
 * }
 *
 * @author Ivan J. Lee on 2020-01-01 21:38
 * @version v0.1
 * @since   v1.0
 **/
public class Issue {

    private String url;
    @SerializedName("repository_url")
    private String respositoryUrl;
    @SerializedName("labels_url")
    private String labelsUrl;
    @SerializedName("comments_url")
    private String commentsUrl;
    @SerializedName("events_url")
    private String eventsUrl;
    @SerializedName("htmlUrl")
    private String htmlUrl;
    private int id;
    @SerializedName("node_id")
    private String nodeId;
    private int number;
    private String title;
    private User user;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRespositoryUrl() {
        return respositoryUrl;
    }

    public void setRespositoryUrl(String respositoryUrl) {
        this.respositoryUrl = respositoryUrl;
    }

    public String getLabelsUrl() {
        return labelsUrl;
    }

    public void setLabelsUrl(String labelsUrl) {
        this.labelsUrl = labelsUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
