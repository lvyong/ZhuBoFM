package com.zhubo.control.bussiness.bean;

import com.andy.corelibray.net.BaseBean;

import java.util.ArrayList;

/**
 * Created by andy_lv on 2014/9/13.
 */
public class ProductDetailBean extends BaseBean {

    private ProductBean  product;

    private ArrayList<SaleDetailBean> contents;

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public ArrayList<SaleDetailBean> getContents() {
        return contents;
    }

    public void setContents(ArrayList<SaleDetailBean> contents) {
        this.contents = contents;
    }
}
