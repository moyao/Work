package com.guansu.management.model;
import com.google.gson.reflect.TypeToken;
import com.guansu.management.api.ApiWrapper;
import com.guansu.management.bean.ActivityDtoInfo;
import com.guansu.management.config.Constant;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.HashMap;

import io.reactivex.Observable;

/**
 * @author: dongyaoyao
 */
public class MessageModellml {
    //活动详情
    public static final String FIND_ACTIVITY_DTOINFO = "/activity/findActivityDtoInfo";
    //活动报名
    public static final String FIND_ACTIVITY_SIGNUPSAVE = "/activity/api/activitySignUpSave";
    //活动发表评论
    public static final String FIND_ACTIVITY_COMMENTSAVE = "/activity/api/activityCommentSave";
    ///圈子添加评论
    public static final String FIND_ACTIVITY_TEXTSAVE = "/content/api/contentCommentSave1";
    //圈子查询
    public static final String FIND_ACTIVITY_IMGANDTEXT = "/content/api/getContentUserImgAndTextById";
    public Observable<ActivityDtoInfo> find_activity_dtoinfo(String userId, String activityId,String tag) {
        String HTTPURL;
        HttpParams httpParams=new HttpParams();
        httpParams.put("userId",userId);
        if (tag.equals(Constant.VIEW_CIRCLE)){
            httpParams.put("id",activityId);
            HTTPURL=FIND_ACTIVITY_IMGANDTEXT;
        }else {
            HTTPURL=FIND_ACTIVITY_DTOINFO;
            httpParams.put("activityId",activityId);
        }
        Type type = new TypeToken<ActivityDtoInfo>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, HTTPURL, type,httpParams);
    }
    public Observable<String> find_activity_signupsave(String userId, String activityId) {
        HashMap<String,Object> httpParams = new HashMap<>();
        httpParams.put("userId",userId);
        httpParams.put("activityId",activityId);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, FIND_ACTIVITY_SIGNUPSAVE, type,httpParams);
    }
    public Observable<String> find_activity_commentsave(String userId, String objectId,String content,
                                                        String parentId,String targetUserNickname,String tag) {
        String HTTPURL;
        HashMap<String,Object> httpParams = new HashMap<>();
        if (tag.equals(Constant.VIEW_CIRCLE)){
            HTTPURL=FIND_ACTIVITY_TEXTSAVE;
        }else {
            HTTPURL=FIND_ACTIVITY_COMMENTSAVE;
        }
        httpParams.put("userId",userId);//自己
        httpParams.put("objectId",objectId);//活动
        httpParams.put("content",content);
        httpParams.put("parentId",parentId);//目标id
        httpParams.put("targetUserNickname",targetUserNickname);//目标昵称
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, HTTPURL, type,httpParams);
    }
}
