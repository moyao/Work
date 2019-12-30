package com.guansu.management.common;

import android.content.Context;
import android.content.SharedPreferences;
import com.guansu.management.bean.UserInfo;
import com.guansu.management.utils.ParcelableUtil;
/**
 *
 * Created by dongyaoyao
 */


public class UserSharedPreferencesUtils {
    private static final String USER_PARAMERS = "UserParams";
    private final String KEY_USER = "user";
    private final String KEY_ACCOUNT = "account";
    private final String KEY_PWD = "pwd";
//    private final String KEY_TOKEN = "token";
    private final String KEY_USER_ID = "userid";
    private final String NICKNAME = "nickname";
    private final String PROFILEIMAGEURL = "profileImageUrl";
    private final String LEVELNAME = "levelName";
    private final String LOGIN = "Login";

    private UserInfo userInfo;
    private String account;
    private String pwd;
//    private String token;
    private String userid;
    private String nickname;
    private String Login;

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String profileImageUrl;
    private String levelName;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private final SharedPreferences sharedPreferences;

    public UserSharedPreferencesUtils(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(USER_PARAMERS,
                Context.MODE_PRIVATE);
        loadSharedPreferences();
    }

    /**
     * 加载配置
     */
    private void loadSharedPreferences() {
        this.setUserInfo(ParcelableUtil.string2Object(sharedPreferences.getString(KEY_USER, ""),
                UserInfo.CREATOR));
        this.setAccount(sharedPreferences.getString(KEY_ACCOUNT, ""));
        this.setPwd(sharedPreferences.getString(KEY_PWD, ""));
//        this.setToken(sharedPreferences.getString(KEY_TOKEN, ""));
        this.setUserid(sharedPreferences.getString(KEY_USER_ID, ""));
        this.setNickname(sharedPreferences.getString(NICKNAME, ""));
        this.setProfileImageUrl(sharedPreferences.getString(PROFILEIMAGEURL, ""));
        this.setLevelName(sharedPreferences.getString(LEVELNAME, ""));
        this.setLogin(sharedPreferences.getString(LOGIN, ""));
    }


    /**
     * 保存配置文件到android配置
     */

    public void saveSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, ParcelableUtil.object2String(getUserInfo()));
        editor.putString(KEY_ACCOUNT, getAccount());
        editor.putString(KEY_PWD, getPwd());
//        editor.putString(KEY_TOKEN, getToken());
        editor.putString(KEY_USER_ID, getUserid());
        editor.putString(NICKNAME, getNickname());
        editor.putString(PROFILEIMAGEURL, getProfileImageUrl());
        editor.putString(LEVELNAME, getLevelName());
        editor.putString(LEVELNAME, getLogin());
        editor.commit();
    }

    public void clearSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (editor != null) {
            editor.clear().commit();
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



}
