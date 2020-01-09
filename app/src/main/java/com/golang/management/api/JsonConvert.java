package com.golang.management.api;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.golang.management.config.HttpConstants;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dongyaoyao
 */
public class JsonConvert<T> implements Converter<T> {

    private Type type;

    public JsonConvert(Type type) {
        this.type = type;
    }


    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        CommonResponse commonResponse = gson.fromJson(jsonReader, type);
        response.close();
        String code = commonResponse.code;

        if (HttpConstants.SUCCESS_CODE.equals(code)) {
            if (null == commonResponse.data) {
                return (T) commonResponse;
            } else {
                return (T) commonResponse.data;
            }
        } else if (code.equals(HttpConstants.NO_DATA)) {
            throw new ServiceException(HttpConstants.NO_DATA, commonResponse.msg);
        } else if (code.equals(HttpConstants.SESSION_TIMEOUT)) {
            throw new ServiceException(HttpConstants.SESSION_TIMEOUT, commonResponse.msg);
        } else if (code.equals(HttpConstants.ERROR_SYSTEM)) {
            throw new ServiceException(code, commonResponse.msg);
        } else if (HttpConstants.BUSSINESS_CODE_MIN.compareTo(code) <= 0 &&
                HttpConstants.BUSSINESS_CODE_MAX.compareTo(code) >= 0) {
            throw new ServiceException(code, commonResponse.msg);
        } else if (code.equals(HttpConstants.ERROR_SYSTEM)) {
            throw new APIException("服务器异常，请稍后再试");
        } else if (HttpConstants.WARNING_MESSAGE_MIN.compareTo(code) <= 0
                && HttpConstants.WARNING_MESSAGE_MAX.compareTo(code) >= 0) {
            throw new APIException(commonResponse.msg);
        } else {
            throw new ServiceException(code, commonResponse.msg);
        }
    }
}