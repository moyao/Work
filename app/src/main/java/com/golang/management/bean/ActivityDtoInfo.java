package com.golang.management.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @date:
 * @author: dongyaoyao
 */
public class ActivityDtoInfo {
    /**
     * signUpCondition : 90后，有钱，高富帅
     * visible : 1
     * activitySignUps : [{"userInfoDto":null,"createdAt":"2019-12-17 11:20","activityId":"1204980186102108160","nickname":"小坤哥","id":"1206776039640928256","userId":"1169501980578549760","profileImageUrl":"https://jm-test.oss-cn-beijing.aliyuncs.com/20180719184241.png","age":null,"status":"HAS_SIGN_UP"}]
     * distance : 1.21993269E7
     * maxPeopleNumber : 20
     * nickName : 18009262998
     * sex : MALE
     * profileImage : https://jm-test.oss-cn-beijing.aliyuncs.com/20180719184241.png
     * signUpPeopleNumber : 1
     * content : 大吃大喝，胡吹乱片
     * traficCount : 0
     * commentCount : 2
     * startTime : 2019-08-28
     * id : 1204980186102108160
     * age : 18
     * imagesList : [{"createdAt":"2019-12-12 12:23","activityId":"1204980186102108160","image":"http://supcache.airbdata.cn/data/www.gzcts03.com/uploads/1556362395_8333.jpg","id":"1204980186114691072","isCover":true},{"createdAt":"2019-12-12 12:23","activityId":"1204980186102108160","image":"http://supcache.airbdata.cn/data/www.gzcts03.com/uploads/1556362395_8333.jpg","id":"1204980186114691073","isCover":false}]
     * activityComments : [{"targetUserNickname":"小黑","mobileNumber":"","userId":"1204288329655259136","content":"ccccc","parentId":"1206532672021598208","userInfoDto":null,"createdAt":"2019-12-13 13:52","nickname":"小白","id":"1205364880152989696","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":[{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"aaaa","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:49","nickname":"小黑","id":"1205364034400948224","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"bbbbb","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:52","nickname":"小黑","id":"1205364761051533312","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"天下无难事1111111","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-17 11:57","nickname":"小黑","id":"1206785549797429248","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"}],"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小黑","mobileNumber":"","userId":"1204288329655259136","content":"ddddd","parentId":"1206532672021598208","userInfoDto":null,"createdAt":"2019-12-13 15:00","nickname":"小白","id":"1205381987171307520","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":[{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"aaaa","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:49","nickname":"小黑","id":"1205364034400948224","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"bbbbb","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:52","nickname":"小黑","id":"1205364761051533312","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"天下无难事1111111","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-17 11:57","nickname":"小黑","id":"1206785549797429248","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"}],"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小黑","mobileNumber":"","userId":"1204288329655259136","content":"eeeee","parentId":"1206532672021598208","userInfoDto":null,"createdAt":"2019-12-17 11:44","nickname":"小白","id":"1206782124976246784","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":[{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"aaaa","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:49","nickname":"小黑","id":"1205364034400948224","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"bbbbb","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:52","nickname":"小黑","id":"1205364761051533312","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"天下无难事1111111","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-17 11:57","nickname":"小黑","id":"1206785549797429248","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"}],"favoriteCount":0,"status":"NORMAL"}]
     */
    private String signUpCondition;
    private int visible;
    private double distance;
    private int maxPeopleNumber;
    @SerializedName(value = "nickName", alternate = {"nickname"})
    private String nickName;
    private String sex;
    @SerializedName(value = "profileImage", alternate = {"profileImageUrl"})
    private String profileImage;
    private int signUpPeopleNumber;
    private String content;
    private int traficCount;
    private int commentCount;
    @SerializedName(value = "startTime", alternate = {"createdAt"})
    private String startTime;
    private long id;
    private int age;
    private String userId;
    private List<ActivitySignUpsBean> activitySignUps;
    @SerializedName(value = "imagesList", alternate = {"images"})
    private List<ImagesListBean> imagesList;
    @SerializedName(value = "activityComments", alternate = {"contentComments"})
    private List<ActivityCommentsBeanX> activityComments;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ActivitySignUpsBean> getActivitySignUps() {
        return activitySignUps;
    }

    public void setActivitySignUps(List<ActivitySignUpsBean> activitySignUps) {
        this.activitySignUps = activitySignUps;
    }

    public List<ImagesListBean> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<ImagesListBean> imagesList) {
        this.imagesList = imagesList;
    }

    public List<ActivityCommentsBeanX> getActivityComments() {
        return activityComments;
    }

    public void setActivityComments(List<ActivityCommentsBeanX> activityComments) {
        this.activityComments = activityComments;
    }
}
