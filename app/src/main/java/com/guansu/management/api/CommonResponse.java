package com.guansu.management.api;
/**
 *
 * Created by dongyaoyao
 */
public class CommonResponse<T> {
    public String code;
    public String developMsg;
    public String msg;
    public String uri;
    public long ts;
    public T data;
}
