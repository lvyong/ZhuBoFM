package com.andy.corelibray.net;

import java.io.Serializable;

/**
 * Created by andy_lv on 2014/9/6.
 */
public class BaseBean implements Serializable {
    protected int code ;
    protected String message;
    protected boolean success ;

    public BaseBean(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
