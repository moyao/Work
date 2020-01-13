package com.golang.management.utils;

public class MessageEvent {
    private String message;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public  MessageEvent(String message,int code){
        this.message=message;
        this.code=code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
