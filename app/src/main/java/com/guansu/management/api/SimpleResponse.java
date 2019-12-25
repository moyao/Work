package com.guansu.management.api;

import java.io.Serializable;
/**
 *
 * Created by dongyaoyao
 */
public class SimpleResponse implements Serializable {
    private static final long serialVersionUID = -1477609349345966116L;
    public String code;
    public String msg;

    public CommonResponse toCommonResponse() {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.code= code;
        commonResponse.msg = msg;
        return commonResponse;
    }
}