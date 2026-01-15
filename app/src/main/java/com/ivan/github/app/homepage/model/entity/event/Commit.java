package com.ivan.github.app.homepage.model.entity.event;

import com.ivan.github.account.model.User;

/**
 * com.ivan.github.app.events.model.Commit
 * {
 *    "sha": "fe230d663fafb525e684ec797c2ac6a117963052",
 *    "author": {
 *      "email": "amit.shekhar.iitbhu@gmail.com",
 *      "name": "AMIT SHEKHAR"
 *    },
 *    "message": "Update README.md",
 *    "distinct": true,
 *    "url": "https://api.github.com/repos/MindorksOpenSource/android-interview-questions/commits/fe230d663fafb525e684ec797c2ac6a117963052"
 *  }
 * @author Ivan J. Lee on 2020-01-08 00:46
 * @version v0.1
 * @since v1.0
 **/
public class Commit {

    private String sha;
    private User author;
    private String message;
    private boolean distinct;
    private String url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
