package com.guansu.management.model;

import com.google.gson.reflect.TypeToken;
import com.guansu.management.api.ApiWrapper;
import com.guansu.management.bean.DistributiopnHomepageBean;
import com.guansu.management.bean.MyDistributionBean;
import com.guansu.management.bean.orcode;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MeModellml {
    //我的二维码生成
    public static final String FIND_ACTIVITY_BYUSERID = "/user/findR_codeByUserId";
    public static final String FIND_BASEINFO_USERBYID = "/user/app/findBaseInfoUserById";
    public static final String MY_DISTRI_BUTION = "/user/myDistribution";
    //我的分销
    public static final String QUERY_POINT_ACCOUNT_PAGE = "/point/queryPointAccountPage";

    public Observable<orcode> find_activity_byuserid(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<orcode>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, FIND_ACTIVITY_BYUSERID, type, httpParams);
    }

    public Observable<orcode> find_baseinfo_userbyid(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<orcode>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, FIND_BASEINFO_USERBYID, type, httpParams);
    }

    public Observable<List<MyDistributionBean>> my_distri_bution(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<List<MyDistributionBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, MY_DISTRI_BUTION, type, httpParams);
    }

    public Observable<DistributiopnHomepageBean> query_Point_Account_Page(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<DistributiopnHomepageBean>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, QUERY_POINT_ACCOUNT_PAGE, type, httpParams);
    }
}
