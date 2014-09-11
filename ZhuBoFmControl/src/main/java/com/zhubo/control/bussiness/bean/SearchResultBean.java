package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.util.ArrayList;

/**
 * Created by andy_lv on 2014/9/8.
 */
public class SearchResultBean extends BaseBean {
    private int page;
    private boolean hasNext;
    private ArrayList<ProductBean> products;

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

    public ArrayList<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductBean> products) {
        this.products = products;
    }
}
