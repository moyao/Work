package com.guansu.management.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MyActivityBean {
    /**
     * signUpCondition : [1111,]
     * visible : 0
     * activitySignUps : null
     * distance : 1.21827636E7
     * maxPeopleNumber : 0
     * nickName : 小猫猫
     * sex : MALE
     * profileImage : http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1209404585010008064/20d9571a1e50a7941de92414ce261014_1440×2560.jpg
     * signUpPeopleNumber : 0
     * userId : null
     * content : 小猫猫
     * traficCount : 0
     * commentCount : 7
     * startTime : 2019-12-20
     * id : 1207864272420343808
     * age : null
     * imagesList : [{"createdAt":"2019-12-20 11:24","activityId":"1207864272420343808","image":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg","id":"1207864272428732416","isCover":true}]
     * activityComments : null
     */
    private String signUpCondition;
    private int visible;
    private Object activitySignUps;
    private double distance;
    private int maxPeopleNumber;
    private String nickName;
    private String sex;
    private String profileImage;
    private int signUpPeopleNumber;
    private Object userId;
    private String content;
    private int traficCount;
    private int commentCount;
    @SerializedName(value = "startTime", alternate = {"createdAt"})
    private String startTime;
    private long id;
    private Object age;
    private Object activityComments;
    @SerializedName(value = "imagesList", alternate = {"images"})
    private List<ImagesListBean> imagesList;

    public String getSignUpCondition() {
        return signUpCondition;
    }

    public void setSignUpCondition(String signUpCondition) {
        this.signUpCondition = signUpCondition;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public Object getActivitySignUps() {
        return activitySignUps;
    }

    public void setActivitySignUps(Object activitySignUps) {
        this.activitySignUps = activitySignUps;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getMaxPeopleNumber() {
        return maxPeopleNumber;
    }

    public void setMaxPeopleNumber(int maxPeopleNumber) {
        this.maxPeopleNumber = maxPeopleNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getSignUpPeopleNumber() {
        return signUpPeopleNumber;
    }

    public void setSignUpPeopleNumber(int signUpPeopleNumber) {
        this.signUpPeopleNumber = signUpPeopleNumber;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTraficCount() {
        return traficCount;
    }

    public void setTraficCount(int traficCount) {
        this.traficCount = traficCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getActivityComments() {
        return activityComments;
    }

    public void setActivityComments(Object activityComments) {
        this.activityComments = activityComments;
    }

    public List<ImagesListBean> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<ImagesListBean> imagesList) {
        this.imagesList = imagesList;
    }
}

