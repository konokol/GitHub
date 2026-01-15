package com.ivan.github.app.homepage.model.entity.event.payload;

import com.google.gson.annotations.SerializedName;
import com.ivan.github.app.homepage.model.entity.event.Payload;
import com.ivan.github.app.homepage.model.entity.event.Repository;

/**
 * com.ivan.github.app.events.model.payload.CreateEventPayload
 *
 * @author  Ivan on 2019-12-29
 * @version v0.1
 * @since   v1.0
 **/
public class CreateEventPayload extends Payload {

    private String ref;
    @SerializedName("ref_type")
    private String refType;
    @SerializedName("master_branch")
    private String masterBranch;
    private String description;
    @SerializedName("pusher_type")
    private String pusherType;
    private Repository repository;


}
