package com.github.app.homepage.model.entity.event.payload;

import com.google.gson.annotations.SerializedName;
import com.github.account.model.User;
import com.github.app.homepage.model.entity.event.Payload;
import com.github.app.homepage.model.entity.event.Repository;

/**
 * com.github.app.events.model.payload.DeleteEventPayload
 * <p>
 *
 * <pre>
 * {@code
 *   {
 *     "id": "7722172991",
 *     "type": "DeleteEvent",
 *     "actor": {
 *       "id": 48159191,
 *       "login": "52Lxcloud",
 *       "display_login": "52Lxcloud",
 *       "gravatar_id": "",
 *       "url": "https://api.github.com/users/52Lxcloud",
 *       "avatar_url": "https://avatars.githubusercontent.com/u/48159191?"
 *     },
 *     "repo": {
 *       "id": 1123083345,
 *       "name": "52Lxcloud/core",
 *       "url": "https://api.github.com/repos/52Lxcloud/core"
 *     },
 *     "payload": {
 *       "ref": "fix/s3-upload-encoding",
 *       "ref_type": "branch",
 *       "full_ref": "refs/heads/fix/s3-upload-encoding",
 *       "pusher_type": "user"
 *     },
 *     "public": true,
 *     "created_at": "2026-01-22T16:14:35Z"
 *   }
 *}
 * </pre>
 *
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
