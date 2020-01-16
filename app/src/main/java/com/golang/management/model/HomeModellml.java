package com.golang.management.model;

import com.golang.management.api.ApiWrapper;
import com.golang.management.bean.HomeBean;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
/**
 * @date: $ $
 * @author: dongyaoyao
 */
public class HomeModellml {
     //1.	活动查询接口
    public static final String QUERYACTIVITYINFOPAGE = "/activity/app/queryActivityInfoPage";
    //查询圈子
    public static final String USERIMGANDTEXTSAVE = "/content/queryContentUserImgAndText";
    //
    public static final String IMAGEUPLOADLIST = "/system/imageUploadList";
    public  String HTTP_URL;
    public Observable<List<HomeBean>> queryactivityinfopage(int currentPage,int status,String latitude,String longitude ) {
        HttpParams httpParams=new HttpParams();
        if (status==0){
            httpParams.put("type","activity");
            HTTP_URL=QUERYACTIVITYINFOPAGE;
        }else if (status==1){
            httpParams.put("type","activity");
            HTTP_URL=QUERYACTIVITYINFOPAGE;
        }else {
            httpParams.put("type","commet");
            HTTP_URL=USERIMGANDTEXTSAVE;
        }
        httpParams.put("pageSize","8");
        httpParams.put("page",currentPage);
        httpParams.put("longitude", longitude);
        httpParams.put("latitude", latitude);
        Type type = new TypeToken<List<HomeBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, HTTP_URL, type,httpParams);
    }
}