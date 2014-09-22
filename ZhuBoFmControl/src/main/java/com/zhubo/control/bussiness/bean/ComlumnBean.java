package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

/**
 * 栏目
 * Created by andy_lv on 2014/9/20.
 */
public class ComlumnBean extends BaseBean {

    private int id;
    private String summary;
    private int channelId;
    private int starredNum;
    private String imageUrl;
    private String description;
    private String name;
    private String playAt;
    private String anchor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getStarredNum() {
        return starredNum;
    }

    public void setStarredNum(int starredNum) {
        this.starredNum = starredNum;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlayAt() {
        return playAt;
    }

    public void setPlayAt(String playAt) {
        this.playAt = playAt;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }
}
