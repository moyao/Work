package com.guansu.management.bean;

import java.util.List;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MyDistributionBean {
    /**
     * realName : null
     * userLevel : null
     * mobileNumber : null
     * sex : null
     * nickname : 18009262998
     * disLevel : MERCHANT   运营商   GOLD_MEMBER   创客   PUKA_MEMBER  普通会员
     * id : 1209404585010008064
     * accountSalt : null
     * profileImageUrl : https://jm-test.oss-cn-beijing.aliyuncs.com/20180719184241.png
     * age : null
     */

    private Object realName;
    private Object userLevel;
    private Object mobileNumber;
    private Object sex;
    private String nickname;
    private String disLevel;
    private long id;
    private Object accountSalt;
    private String profileImageUrl;
    private Object age;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String createdAt;


    public Object getRealName() {
        return realName;
    }

    public void setRealName(Object realName) {
        this.realName = realName;
    }

    public Object getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Object userLevel) {
        this.userLevel = userLevel;
    }

    public Object getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Object mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDisLevel() {
        return disLevel;
    }

    public void setDisLevel(String disLevel) {
        this.disLevel = disLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getAccountSalt() {
        return accountSalt;
    }

    public void setAccountSalt(Object accountSalt) {
        this.accountSalt = accountSalt;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }
}
