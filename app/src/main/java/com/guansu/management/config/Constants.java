package com.guansu.management.config;

import com.guansu.management.bean.ImagesListBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by dongyaoyao
 */
public class Constants {
    public static String ACTION_BROADCAST_FILTER = "com.wisdom.filter";
    public static String KEY_URL = "url";
    public static String KEY_TITLE = "title";
    public static String KEY_TYPE = "type";
    public static int PAGE = 0;
    public static List<ImagesListBean> IMAGE = new ArrayList<>();


    public static final int PAGE_SIZE = 20;
    // 存储
    public static final String USERINFO = "userInfo";
    public static final String ACCOUNT = "account";
    public static final String PWD = "password";
    public static final String ROOM = "room";
    public static final String AUTO_LOGIN = "auto_login";
    public static final String LOGOUT = "logout";
    public static final String ICON_URL = "icon_url";

    public static final String CHAT_INFO = "chatInfo";
}
