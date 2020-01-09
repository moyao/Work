package com.golang.management.bean;

/**
 * @date:
 * @author: dongyaoyao
 */
public class ActivityCommentsBean {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
