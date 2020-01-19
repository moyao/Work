package com.golang.management.model;
import com.google.gson.reflect.TypeToken;
import com.golang.management.api.ApiWrapper;
import com.golang.management.bean.FootBean;
import com.golang.management.model.bean.LoginResult;
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
}
