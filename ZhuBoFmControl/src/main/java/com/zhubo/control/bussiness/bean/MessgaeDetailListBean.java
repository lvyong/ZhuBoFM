package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.util.ArrayList;

/**
 * 私信详情
 * Created by andy_lv on 2014/9/21.
 */
public class MessgaeDetailListBean extends BaseBean{

    private MessageGroupBean.FromUser fromUser;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    private boolean hasNext;
    private int page;
    private ArrayList<MessageBean> messages;

    public MessageGroupBean.FromUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(MessageGroupBean.FromUser fromUser) {
        this.fromUser = fromUser;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MessageBean> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageBean> messages) {
        this.messages = messages;
    }
}
