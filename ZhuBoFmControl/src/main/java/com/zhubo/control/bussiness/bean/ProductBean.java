package com.zhubo.control.bussiness.bean;

/**
 * 产品Bean
 * Created by andy_lv on 2014/9/8.
 */
public class ProductBean {
    private String  id ;
    private String  price;
    private String imageUrl;
    private String listPrice;
    private String description;
    private String name;
    private int  starredCount ;
    private int  viewCount;
    private int  quantity;
    private int  recommendTimes;
    private String amount;

    //自定义字段，表示是否选中
    private boolean add = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStarredCount() {
        return starredCount;
    }

    public void setStarredCount(int starredCount) {
        this.starredCount = starredCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public int getRecommendTimes() {
        return recommendTimes;
    }

    public void setRecommendTimes(int recommendTimes) {
        this.recommendTimes = recommendTimes;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
