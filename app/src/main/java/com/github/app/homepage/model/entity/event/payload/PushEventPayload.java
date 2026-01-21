package com.github.app.homepage.model.entity.event.payload;

import com.google.gson.annotations.SerializedName;
import com.github.app.homepage.model.entity.event.Commit;
import com.github.app.homepage.model.entity.event.Payload;

import java.util.List;

/**
 * com.github.app.events.model.payload.PushEventPayload
 *
 * {
 *       "push_id": 4464248863,
 *       "size": 1,
 *       "distinct_size": 1,
 *       "ref": "refs/heads/master",
 *       "head": "fe230d663fafb525e684ec797c2ac6a117963052",
 *       "before": "d78a574615130de48a02edd75735061f688b0416",
 *       "commits": [
 *         {
 *           "sha": "fe230d663fafb525e684ec797c2ac6a117963052",
 *           "author": {
 *             "email": "amit.shekhar.iitbhu@gmail.com",
 *             "name": "AMIT SHEKHAR"
 *           },
 *           "message": "Update README.md",
 *           "distinct": true,
 *           "url": "https://api.github.com/repos/MindorksOpenSource/android-interview-questions/commits/fe230d663fafb525e684ec797c2ac6a117963052"
 *         }
 *       ]
 *     }
 *
 * @author Ivan J. Lee on 2020-01-08 00:38
 * @version v0.1
 * @since v1.0
 **/
public class PushEventPayload extends Payload {

    @SerializedName("push_id")
    private String pushId;
    private int size;
    @SerializedName("distinct_size")
    private int distinctSize;
    private String ref;
    private String head;
    private String before;
    private List<Commit> commits;

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDistinctSize() {
        return distinctSize;
    }

    public void setDistinctSize(int distinctSize) {
        this.distinctSize = distinctSize;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }
}
