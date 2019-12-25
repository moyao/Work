package com.guansu.management.bean;
import java.util.List;
/**
 * @date:
 * @author: dongyaoyao
 */
public class ActivityCommentsBeanX {
    /**
     * targetUserNickname : 小黑
     * mobileNumber :
     * userId : 1204288329655259136
     * content : ccccc
     * parentId : 1206532672021598208
     * userInfoDto : null
     * createdAt : 2019-12-13 13:52
     * nickname : 小白
     * id : 1205364880152989696
     * profileImageUrl : null
     * objectId : 1204980186102108160
     * activityComments : [{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"aaaa","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:49","nickname":"小黑","id":"1205364034400948224","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"bbbbb","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-13 13:52","nickname":"小黑","id":"1205364761051533312","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"},{"targetUserNickname":"小白","mobileNumber":"","userId":"1206532672021598208","content":"天下无难事1111111","parentId":"1204288329655259136","userInfoDto":null,"createdAt":"2019-12-17 11:57","nickname":"小黑","id":"1206785549797429248","profileImageUrl":null,"objectId":"1204980186102108160","activityComments":null,"favoriteCount":0,"status":"NORMAL"}]
     * favoriteCount : 0
     * status : NORMAL
     */
    private String targetUserNickname;
    private String mobileNumber;
    private String userId;
    private String content;
    private String parentId;
    private Object userInfoDto;
    private String createdAt;
    private String nickname;
    private String id;
    private Object profileImageUrl;
    private String objectId;
    private int favoriteCount;
    private String status;
    private int type;
    private List<ActivityCommentsBeanX> activityComments;
    public List<ActivityCommentsBeanX> getActivityComments() {
        return activityComments;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public void setActivityComments(List<ActivityCommentsBeanX> activityComments) {
        this.activityComments = activityComments;
    }
    public String getTargetUserNickname() {
        return targetUserNickname;
    }

    public void setTargetUserNickname(String targetUserNickname) {
        this.targetUserNickname = targetUserNickname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

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

    public Object getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(Object profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
