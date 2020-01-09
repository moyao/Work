package com.golang.management.model;

import com.google.gson.reflect.TypeToken;
import com.golang.management.api.ApiWrapper;
import com.golang.management.bean.MessageBean;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @date:
 * @author: dongyaoyao
 */
public class FriendModellml {
    //好友
    public static final String USER_FRIEND = "/user/userFriend";
    //是否同意参加活动
    public static final String USER_EDITSIGNUPUSER = "/activity/api/editSignUpUser";

    //查询好友
    public Observable<List<MessageBean>> user_friend(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<List<MessageBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, USER_FRIEND, type, httpParams);
    }

    public Observable<String> add_friend(String userId, String friendId) {
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("userId", userId);
        httpParams.put("friendId", friendId);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, USER_FRIEND, type, httpParams);
    }
}
