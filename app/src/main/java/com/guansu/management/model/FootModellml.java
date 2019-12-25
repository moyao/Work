package com.guansu.management.model;
import com.google.gson.reflect.TypeToken;
import com.guansu.management.api.ApiWrapper;
import com.guansu.management.bean.FootBean;
import com.guansu.management.model.bean.LoginResult;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * @date:
 * @author: dongyaoyao
 */
public class FootModellml {
    //足迹
    public static final String URL_TRACE = "/content/trace";
    public Observable getVerify(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId",userId);
        Type type = new TypeToken<List<FootBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, URL_TRACE, type,httpParams);
    }
    public Observable getPostVerify(String userId,String address,String longitude,String latitude) {
        HashMap<String,Object> httpParams = new HashMap<>();
        httpParams.put("userId",userId);
        httpParams.put("address",address);
        httpParams.put("longitude",longitude);
        httpParams.put("latitude",latitude);
        Type type = new TypeToken<LoginResult>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, URL_TRACE, type,httpParams);
    }
}
