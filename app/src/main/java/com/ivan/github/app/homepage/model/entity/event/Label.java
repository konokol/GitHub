package com.ivan.github.app.homepage.model.entity.event;

import com.google.gson.annotations.SerializedName;

/**
 * com.ivan.github.app.events.model.Label
 * {
 *    "id": 1362934389,
 *    "node_id": "MDU6TGFiZWwxMzYyOTM0Mzg5",
 *    "url": "https://api.github.com/repos/Codertocat/Hello-World/labels/bug",
 *    "name": "bug",
 *    "color": "d73a4a",
 *    "default": true
 *  }
 *
 * @author Ivan J. Lee on 2020-01-01 21:43
 * @version v0.1
 * @since v1.0
 **/
public class Label {

    private int id;
    @SerializedName("node_id")
    private String nodeId;
    private String url;
    private String name;
    private String color;
    @SerializedName("default")
    private boolean isDefault;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
