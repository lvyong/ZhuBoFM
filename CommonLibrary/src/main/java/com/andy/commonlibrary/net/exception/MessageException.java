package com.andy.commonlibrary.net.exception;

/**
 * Created by andy_lv on 2014/8/23.
 */
public class MessageException {

    private String statusCode = "";

    private String statusMessage = "";

    public MessageException(String statusCode,String statusMessage){
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode(){
        return this.statusCode;
    }

    public String getStatusMessage(){
        return this.statusMessage;
    }

}
