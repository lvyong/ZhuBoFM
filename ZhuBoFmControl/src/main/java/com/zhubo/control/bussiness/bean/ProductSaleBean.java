package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy_lv on 2014/9/11.
 */
public class ProductSaleBean extends BaseBean {

    private int  amount;
    private boolean hasNext;
    private int quantity ;
    private List<ProductBean> products;
    private int page;



    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
