package com.guansu.management.api;
/**
 *
 * Created by dongyaoyao
 */
public class APIException extends Exception {
    public String message;

    public APIException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}