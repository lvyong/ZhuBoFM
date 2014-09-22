package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by andy_lv on 2014/9/21.
 */
public class MessageGroupListBean extends BaseBean {
    private int page;
    private boolean hasNext ;
    private LinkedList<MessageGroupBean> groups;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public LinkedList<MessageGroupBean> getGroups() {
        return groups;
    }

    public void setGroups(LinkedList<MessageGroupBean> groups) {
        this.groups = groups;
    }
}
