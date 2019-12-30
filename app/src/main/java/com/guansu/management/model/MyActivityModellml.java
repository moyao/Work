package com.guansu.management.model;

import com.google.gson.reflect.TypeToken;
import com.guansu.management.api.ApiWrapper;
import com.guansu.management.bean.MyActivityBean;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MyActivityModellml {
    //1.	活动查询接口
    public static final String QUERY_ACTIVITY_INFOPAGE = "/activity/app/queryActivityInfoPage";
    //我参加的活动
    public static final String ACTIVITY_SIGNUP = "/activity/ActivitySignUp";
    //查询圈子
    public static final String USERIMGANDTEXTSAVE = "/content/queryContentUserImgAndText";
    public  String HTTP_URL;
    public Observable<List<MyActivityBean>> query_activity_infopage(String userId, int status) {
        HttpParams httpParams=new HttpParams();
        httpParams.put("userId",userId);
        if (status==0){
            HTTP_URL=QUERY_ACTIVITY_INFOPAGE;
            httpParams.put("type","activity");
            httpParams.put("pageSize","8");
            httpParams.put("page","1");
        }else if (status==1){
            HTTP_URL=ACTIVITY_SIGNUP;
        }else if (status==2){
            HTTP_URL=USERIMGANDTEXTSAVE;
            httpParams.put("userId",userId);
            httpParams.put("type","commet");
            httpParams.put("pageSize","8");
            httpParams.put("page","1");
        }
        Type type = new TypeToken<List<MyActivityBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, HTTP_URL, type,httpParams);
    }
}
