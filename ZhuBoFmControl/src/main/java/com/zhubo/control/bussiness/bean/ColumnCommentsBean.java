package com.zhubo.control.bussiness.bean;

/**
 * 栏目bean
 * Created by andy_lv on 2014/9/21.
 */
public class ColumnCommentsBean {
    private int id;
    private String body;
    private String createdAt;
    private ColumnUser user;
    private ColumnContent content;
    private boolean isChecked;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ColumnUser getUser() {
        return user;
    }

    public void setUser(ColumnUser user) {
        this.user = user;
    }

    public ColumnContent getContent() {
        return content;
    }

    public void setContent(ColumnContent content) {
        this.content = content;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public class ColumnUser{
         private int id;
        private String nickname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

    }

    public class ColumnContent{
         private int id;
         private String name;
         private String imageUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
