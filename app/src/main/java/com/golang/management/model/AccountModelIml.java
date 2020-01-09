package com.golang.management.model;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.golang.management.api.ApiWrapper;
import com.golang.management.api.CommonResponse;
import com.golang.management.bean.UserInfo;
import com.golang.management.model.bean.LoginResult;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
/**
 *
 * Created by dongyaoyao
 */
public class AccountModelIml {

    public static final String URL_LOGIN = "/user/open/loginOrRegister";
    public static final String URL_VERIFY = "/system/open/sendRegLoginMsg";
    public static final String URL_UPDATE = "updatePassword.do";
    public static final String URL_EXIT = "desTSession.do";
    public static final String URL_APPVERSIONINFO = "getAppVersionInfo.do";


    public Observable<UserInfo> login(String user, String pass) {
        Map<String,Object> httpParams = new HashMap<>();
        httpParams.put("mobileNumber",user);
        httpParams.put("password",pass);
        httpParams.put("clientId","186930579493359616");
        httpParams.put("requestSource","APP");
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, URL_LOGIN, type,httpParams);
    }

    public Observable getVerify(String account) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile",account);
        Type type = new TypeToken<LoginResult>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, URL_VERIFY, type,httpParams);
    }

    public Observable<CommonResponse> getUpdate(String user, String pass, String newpassone){
        HttpParams httpParams = new HttpParams();
        httpParams.put("user",user);
        httpParams.put("pass",pass);
        httpParams.put("newpassone",newpassone);
        httpParams.put("newpasstwo",newpassone);
        Type type = new TypeToken<CommonResponse>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.POST, URL_UPDATE, type,httpParams);
    }
    public Observable<CommonResponse> getExit() {
        HttpParams httpParams = new HttpParams();
        Type type = new TypeToken<CommonResponse>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.POST, URL_EXIT, type,httpParams);
    }
 /*   public Observable<List<VersionBean>> version() {
        HttpParams httpParams = new HttpParams();
        Type type = new TypeToken<List<VersionBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.POST, URL_APPVERSIONINFO, type,httpParams);
    }*/
}
