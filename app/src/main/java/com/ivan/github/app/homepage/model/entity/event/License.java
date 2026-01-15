package com.ivan.github.app.homepage.model.entity.event;

import com.google.gson.annotations.SerializedName;

/**
 * com.ivan.github.app.events.model.License
 *
 * {
 *    "key": "apache-2.0",
 *    "name": "Apache License 2.0",
 *    "spdx_id": "Apache-2.0",
 *    "url": "https://api.github.com/licenses/apache-2.0",
 *    "node_id": "MDc6TGljZW5zZTI="
 * }
 * @author Ivan J. Lee on 2020-01-02 23:37
 * @version v0.1
 * @since v1.0
 **/
public class License {

    private String key;
    private String name;
    @SerializedName("spdx_id")
    private String spdxId;
    private String url;
    @SerializedName("node_id")
    private String nodeId;
}
