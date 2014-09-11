package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

/**
 * Created by andy_lv on 2014/9/6.
 */
public class UserBean extends BaseBean {
    private String id = "" ;
    private String uuid = "";
    private String apiToken = "";
    private String name = "";
    private String nickname = "";
    private String imageUrl = "";

    public UserBean(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
