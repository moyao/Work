package com.golang.management.bean;

/**
 * @date:
 * @author: dongyaoyao
 */
public class updateTextEvent {
    private static final String TAG = "AA";
    private String msg;
    private String tag;

    public static String getTAG() {
        return TAG;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public updateTextEvent(String msg, String tag) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
