package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

/**
 * Created by andy_lv on 2014/9/6.
 */
public class UserBean {
    private int id ;
    private String uuid = "";
    private String apiToken = "";
    private String name = "";
    private String nickname = "";
    private String imageUrl = "";
    private int starredAnchorNum ;
    private int attendedActivityNum;
    private int listenedColumnNum;
    private String message = "";
    private boolean anonymous ;
    private int starredColumnNum;
    private int subscribedColumnNum;
    private int unreadMessageNum;
    private int starredProductNum;
    private String signature = "";
    private AnchorBean anchor ;


    public UserBean(){

    }

    public AnchorBean getAnchor() {
        return anchor;
    }

    public void setAnchor(AnchorBean anchor) {
        this.anchor = anchor;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public int getStarredAnchorNum() {
        return starredAnchorNum;
    }

    public void setStarredAnchorNum(int starredAnchorNum) {
        this.starredAnchorNum = starredAnchorNum;
    }

    public int getAttendedActivityNum() {
        return attendedActivityNum;
    }

    public void setAttendedActivityNum(int attendedActivityNum) {
        this.attendedActivityNum = attendedActivityNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getListenedColumnNum() {
        return listenedColumnNum;
    }

    public void setListenedColumnNum(int listenedColumnNum) {
        this.listenedColumnNum = listenedColumnNum;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getStarredColumnNum() {
        return starredColumnNum;
    }

    public void setStarredColumnNum(int starredColumnNum) {
        this.starredColumnNum = starredColumnNum;
    }

    public int getSubscribedColumnNum() {
        return subscribedColumnNum;
    }

    public void setSubscribedColumnNum(int subscribedColumnNum) {
        this.subscribedColumnNum = subscribedColumnNum;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getStarredProductNum() {
        return starredProductNum;
    }

    public void setStarredProductNum(int starredProductNum) {
        this.starredProductNum = starredProductNum;
    }

    public int getUnreadMessageNum() {
        return unreadMessageNum;
    }

    public void setUnreadMessageNum(int unreadMessageNum) {
        this.unreadMessageNum = unreadMessageNum;
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
