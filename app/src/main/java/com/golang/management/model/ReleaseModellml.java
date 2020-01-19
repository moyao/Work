package com.golang.management.model;
import com.google.gson.reflect.TypeToken;
import com.golang.management.api.ApiWrapper;
import com.lzy.okgo.model.HttpMethod;
import org.json.JSONArray;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;

/**
 * @author: dongyaoyao
 */
public class ReleaseModellml {
    //发圈子
    public static final String USER_IMGAND_TEXTSAVE = "/content/api/userImgAndTextSave";
    //发活动
    public static final String USER_ACTIVITY_INFOSAVE = "/activity/activityInfoSave";
    public Observable<String> user_imgand_textsave(String userId, String content,String latitude,String longitude, JSONArray mlist) {
        Map<String,Object> httpParams = new HashMap<>();
        httpParams.put("uid",userId);
        httpParams.put("content",content);
        httpParams.put("longitude",longitude);
        httpParams.put("latitude",latitude);
        httpParams.put("imageList",mlist);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, USER_IMGAND_TEXTSAVE, type,httpParams);
    }
    public Observable<String> user_activity_infosave(String userId, String content,String startTime,String endTime,String district,
                                                     String address,String maxPeopleNumber,String visible,String signUpCondition
            ,String latitude,String longitude , JSONArray mlist) {
        Map<String,Object> httpParams = new HashMap<>();
        httpParams.put("userId",userId);
        httpParams.put("title",content);
        httpParams.put("startTime",startTime);
        httpParams.put("endTime",endTime);
        httpParams.put("content",content);
        httpParams.put("category","USER");//普通用户，商户
        httpParams.put("coverImage","");
        httpParams.put("district",district);//定位
        httpParams.put("address",address);//详细地址
        httpParams.put("visible",visible);//活动是否可见
        httpParams.put("minPeopleNumber","1");//最少参加人数
        httpParams.put("maxPeopleNumber",maxPeopleNumber);//上限人数
        httpParams.put("tags","");//标签
        httpParams.put("signUpCondition",signUpCondition);//标签
        httpParams.put("signUpMoney","");//活动费用
        httpParams.put("longitude",longitude);
        httpParams.put("latitude",latitude);
        httpParams.put("imageList",mlist);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, USER_ACTIVITY_INFOSAVE, type,httpParams);
    }
}
