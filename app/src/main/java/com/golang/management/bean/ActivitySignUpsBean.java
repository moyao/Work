package com.golang.management.bean;

/**
 * @date:
 * @author: dongyaoyao
 */
public class ActivitySignUpsBean {
    /**
     * userInfoDto : null
     * createdAt : 2019-12-17 11:20
     * activityId : 1204980186102108160
     * nickname : 小坤哥
     * id : 1206776039640928256
     * userId : 1169501980578549760
     * profileImageUrl : https://jm-test.oss-cn-beijing.aliyuncs.com/20180719184241.png
     * age : null
     * status : HAS_SIGN_UP
     */
    private Object userInfoDto;
    private String createdAt;
    private String activityId;
    private String nickname;
    private String id;
    private String userId;
    private String profileImageUrl;
    private Object age;
    private String status;

    public Object getUserInfoDto() {
        return userInfoDto;
    }

    public void setUserInfoDto(Object userInfoDto) {
        this.userInfoDto = userInfoDto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
