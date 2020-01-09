package com.golang.management.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @date:
 * @author: dongyaoyao
 */
public class ActivityCommentsBeanX {
    /**
     * children : []
     * commentCount : 0
     * content : 我就是看看热闹
     * contentComments : []
     * createdAt : 2020-01-08 10:05:00
     * favoriteCount : 0
     * id : 1214850481457913856
     * nickname : 小仙女
     * objectId : 1214826975026221056
     * parentId : 1209404585010008064
     * profileImageUrl : http://golangkeji.oss-cn-beijing.aliyuncs.com/1212990656276860928/59aef6b041b26e704249a4935671255d_1440×2560.jpg
     * status : NORMAL
     * targetNickname : 小猫猫
     * targetProfileImageUrl : http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/f46bbe2984f6af3f6355cfbb9f7845ad_1920×2560.jpg
     * targetUserId : 1209404585010008064
     * userId : 1212990656276860928
     */

    private int commentCount;
    private String content;
    private String createdAt;
    private int favoriteCount;
    private String id;
    private String nickname;
    private String objectId;
    private String parentId;
    private String profileImageUrl;
    private String status;
    @SerializedName(value = "targetNickname", alternate = {"targetUserNickname"})
    private String targetNickname;
    private String targetProfileImageUrl;
    private String targetUserId;
    private String userId;
    private List<?> children;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @SerializedName(value = "contentComments", alternate = {"activityComments"})
    private List<ActivityCommentsBeanX> contentComments;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetNickname() {
        return targetNickname;
    }

    public void setTargetNickname(String targetNickname) {
        this.targetNickname = targetNickname;
    }

    public String getTargetProfileImageUrl() {
        return targetProfileImageUrl;
    }

    public void setTargetProfileImageUrl(String targetProfileImageUrl) {
        this.targetProfileImageUrl = targetProfileImageUrl;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public List<ActivityCommentsBeanX> getContentComments() {
        return contentComments;
    }

    public void setContentComments(List<ActivityCommentsBeanX> contentComments) {
        this.contentComments = contentComments;
    }


}
