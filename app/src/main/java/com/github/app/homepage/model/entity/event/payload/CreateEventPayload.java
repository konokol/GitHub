package com.github.app.homepage.model.entity.event.payload;

import com.google.gson.annotations.SerializedName;
import com.github.app.homepage.model.entity.event.Payload;
import com.github.app.homepage.model.entity.event.Repository;

/**
 * com.github.app.events.model.payload.CreateEventPayload
 * <p>
 * <pre>
 *
 *    {
 *       "ref": "pr_mec5_dtsi_gpio_fix",
 *       "ref_type": "branch",
 *       "full_ref": "refs/heads/pr_mec5_dtsi_gpio_fix",
 *       "master_branch": "main",
 *       "description": "Primary Git Repository for the Zephyr Project. Zephyr is a new generation, scalable, optimized, secure RTOS for multiple hardware architectures.",
 *       "pusher_type": "user"
 *     },
 * </pre>
 *
 * @author  Ivan on 2019-12-29
 * @version v0.1
 * @since   v1.0
 **/
public class CreateEventPayload extends Payload {

    public String ref;
    @SerializedName("ref_type")
    public String refType;
    @SerializedName("master_branch")
    public String masterBranch;
    public String description;
    @SerializedName("pusher_type")
    public String pusherType;

}
