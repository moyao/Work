package com.guansu.management.api;
/**
 *
 * Created by dongyaoyao
 */
public class ServiceException extends Exception {
    public String code;
    public String message;

    public ServiceException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}