package com.golang.management.model;

import com.google.gson.reflect.TypeToken;
import com.golang.management.api.ApiWrapper;
import com.golang.management.bean.DistributiopnHomepageBean;
import com.golang.management.bean.EditBean;
import com.golang.management.bean.MyDistributionBean;
import com.golang.management.bean.PaymentBean;
import com.golang.management.bean.PersonalBean;
import com.golang.management.bean.orcode;
import com.golang.management.config.Constant;
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
public class MeModellml {
    //我的二维码生成
    public static final String FIND_ACTIVITY_BYUSERID = "/user/findR_codeByUserId";
    public static final String FIND_BASEINFO_USERBYID = "/user/app/findBaseInfoUserById";
    public static final String MY_DISTRI_BUTION = "/user/myDistribution";
    //我的分销
    public static final String QUERY_POINT_ACCOUNT_PAGE = "/point/queryPointAccountPage";
    //我的主页
    public static final String FIND_INFO_USERBYID = "/user/findInfoUserById";
    //修改我的信息
    public static final String USER_INFOEDIT = "/user/userInfoEdit";
    //上传图像
    public static final String ICON_UPLOAD = "/system/iconUpload";
    //意见反馈
    public static final String USER_SUGGEST = "/user/userSuggest";
    //个人主页
    public static final String USER_WEBPAGE = "/user/webPage";
    //支付宝
    public static final String USER_GENERATEORDERINFO = "/pay/aliPay/generateOrderInfo";
    //支付宝拉取授权
    public static final String USER_ALIPAYAUTH = "/pay/aliPay/alipayAuth";
    //检验是否授权
    public static final String USER_VALIDATEAUTH = "/user/validateAuth";
    //我的账单
    public static final String USER_MYBILL = "/pay/myBill";
    //实名认证
    public static final String USER_FACERECOGNITION = "/user/faceRecognition";

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

    //我的主页
    public Observable<EditBean> find_info_userbyid(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<EditBean>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, FIND_INFO_USERBYID, type, httpParams);
    }

    //编辑我的资料
    public Observable<String> user_infoedit(String userid, String birthday, String education,
                                            String occupation, String message, String sex,
                                            String single, String wantToGo, String auth,
                                            String nickname, String hobby, String imageList) {
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("id", userid);
        httpParams.put("birthday", birthday);
        httpParams.put("education", education);
        httpParams.put("occupation", occupation);
        httpParams.put("message", message);
        httpParams.put("sex", sex);
        httpParams.put("single", single);
        httpParams.put("wantToGo", wantToGo);
        httpParams.put("auth", auth);
        httpParams.put("nickname", nickname);
        httpParams.put("hobby", hobby);
        httpParams.put("profileImageUrl", imageList);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, USER_INFOEDIT, type, httpParams);
    }

    //意见反馈
    public Observable<String> user_suggest(String userId, String content, String contact, String types) {
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("userId", userId);
        httpParams.put("content", content);
        httpParams.put("contact", contact);
        httpParams.put("types", types);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, USER_SUGGEST, type, httpParams);
    }

    //我的主页
    public Observable<PersonalBean> user_webpage(String userId, String visitorId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("visitorId", userId);
        httpParams.put("userId", visitorId);
        Type type = new TypeToken<PersonalBean>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, USER_WEBPAGE, type, httpParams);

    }

    /**
     * 支付宝
     */
    public Observable<String> user_validateauth(String userId) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        Type type = new TypeToken<String>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, USER_VALIDATEAUTH, type, httpParams);

    }

    public Observable<List<PaymentBean>> user_mybill(String userId,String orderNo) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userId);
        httpParams.put("orderNo", orderNo);
        Type type = new TypeToken<List<PaymentBean>>() {
        }.getType();
        return ApiWrapper.request(HttpMethod.GET, USER_MYBILL, type, httpParams);
    }

   /* public Observable<List<PaymentBean>> user_facerecognition(String userId, String path,String identityImage,String identity
    ,String name) {
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("userId",userId);
        //生活照
        httpParams.put("faceImage", path);
        //身份证照片
        httpParams.put("identityImage", identityImage);
        //身份证号码
        httpParams.put("identity",identity);
        //姓名
        httpParams.put("name", name);
        Type type = new TypeToken<List<PaymentBean>>() {
        }.getType();
        return ApiWrapper.babyrequest(HttpMethod.POST, USER_FACERECOGNITION, type, httpParams);
    }*/
}
