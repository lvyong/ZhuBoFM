package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.util.LinkedList;

/**
 * 栏目评论列表bean
 * Created by andy_lv on 2014/9/21.
 */
public class ColumnCommentsListBean extends BaseBean{

    private int page;
    private boolean hasNext ;
    private LinkedList<ColumnCommentsBean> comments;


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

    public LinkedList<ColumnCommentsBean> getComments() {
        return comments;
    }

    public void setComments(LinkedList<ColumnCommentsBean> comments) {
        this.comments = comments;
    }
}
