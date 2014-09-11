package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by andy_lv on 2014/9/6.
 */
public class MainProgram extends BaseBean implements Serializable{

    private int  page;
    private boolean hasNext;
    private ArrayList<ProgramBean> contents;

    public MainProgram(){

    }

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

    public ArrayList<ProgramBean> getContents() {
        return contents;
    }

    public void setContents(ArrayList<ProgramBean> contents) {
        this.contents = contents;
    }

}
