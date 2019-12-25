package com.guansu.management.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: dongyaoyao
 */
public class HomeBean {
    /**
     * age : 18
     * commentCount : 3   评论数
     * content : 大吃大喝，胡吹乱片
     * distance : 1.21993269E7
     * id : 1205040515481866240
     * imagesList : [{"activityId":"1205040515481866240","createdAt":"2019-12-12 16:23","id":"1205040515838382080","image":"http://supcache.airbdata.cn/data/www.gzcts03.com/uploads/1556362395_8333.jpg","isCover":true},{"activityId":"1205040515481866240","createdAt":"2019-12-12 16:23","id":"1205040515871936512","image":"http://supcache.airbdata.cn/data/www.gzcts03.com/uploads/1556362395_8333.jpg","isCover":false}]
     * maxPeopleNumber : 20
     * nickName : 18009262998
     * profileImage : https://jm-test.oss-cn-beijing.aliyuncs.com/20180719184241.png
     * signUpCondition : 参加条件补充说明
     * signUpPeopleNumber : 0  参加人数
     * startTime : 2019-08-28
     * traficCount : 0   浏览数
     * visible : 1
     */
    private int age;
    private int commentCount;
    private String content;
    private double distance;
    private long id;
    private int maxPeopleNumber;
    private String nickName;
    private String profileImage;
    private String signUpCondition;
    private int signUpPeopleNumber;
    @SerializedName(value = "startTime", alternate = {"createdAt"})
    private String startTime;
    private int traficCount;
    private int visible;
    private int type;
    @SerializedName(value = "imagesList", alternate = {"images"})
    private List<ImagesListBean> imagesList;
    private String sex;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSignUpCondition() {
        return signUpCondition;
    }

    public void setSignUpCondition(String signUpCondition) {
        this.signUpCondition = signUpCondition;
    }

    public int getSignUpPeopleNumber() {
        return signUpPeopleNumber;
    }

    public void setSignUpPeopleNumber(int signUpPeopleNumber) {
        this.signUpPeopleNumber = signUpPeopleNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTraficCount() {
        return traficCount;
    }

    public void setTraficCount(int traficCount) {
        this.traficCount = traficCount;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<ImagesListBean> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<ImagesListBean> imagesList) {
        this.imagesList = imagesList;
    }


}
