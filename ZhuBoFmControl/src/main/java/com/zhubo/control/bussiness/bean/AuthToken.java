package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

/**
 * Created by andy_lv on 2014/9/2.
 */
public class AuthToken extends BaseBean{

   private boolean   anonymous ;
   private int   starredAnchorNum ;
   private int   starredColumnNum ;
   private int   attendedActivityNum ;
   private int   subscribedColumnNum ;
   private int   unreadMessageNum;
   private String   uuid = "";
   private int   starredProductNum ;
   private int   listenedColumnNum ;
   private int      id ;
   private String   apiToken = "";

    public AuthToken(){
        super();
    }

    public int getListenedColumnNum() {
        return listenedColumnNum;
    }

    public void setListenedColumnNum(int listenedColumnNum) {
        this.listenedColumnNum = listenedColumnNum;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public int getSubscribedColumnNum() {
        return subscribedColumnNum;
    }

    public void setSubscribedColumnNum(int subscribedColumnNum) {
        this.subscribedColumnNum = subscribedColumnNum;
    }

    public int getAttendedActivityNum() {
        return attendedActivityNum;
    }

    public void setAttendedActivityNum(int attendedActivityNum) {
        this.attendedActivityNum = attendedActivityNum;
    }

    public int getStarredColumnNum() {
        return starredColumnNum;
    }

    public void setStarredColumnNum(int starredColumnNum) {
        this.starredColumnNum = starredColumnNum;
    }

    public int getStarredAnchorNum() {
        return starredAnchorNum;
    }

    public void setStarredAnchorNum(int starredAnchorNum) {
        this.starredAnchorNum = starredAnchorNum;
    }

    public boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
