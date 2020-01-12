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
     * content : 看看
     * contentComments : []
     * createdAt : 2020-01-10 01:49:12
     * favoriteCount : 0
     * id : 1215450485545426944
     * nickname : 陌钰瑶
     * objectId : 1214826151315247104
     * parentId : 1209404585010008064
     * profileImageUrl : http://golangkeji.oss-cn-beijing.aliyuncs.com/1215152651742744576/dbfd4fc693cf43a8a439fdf9c49ad7a2_1440×2560.jpg
     * status : NORMAL
     * userId : 1215152651742744576
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
    private String userId;
    @SerializedName(value = "targetNickname", alternate = {"targetUserNickname"})
    private String targetNickname;
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTargetNickname() {
        return targetNickname;
    }

    public void setTargetNickname(String targetNickname) {
        this.targetNickname = targetNickname;
    }

    private List<?> children;
    @SerializedName(value = "contentComments", alternate = {"activityComments"})
    private List<ActivityCommentsBeanX> contentComments;

    public List<ActivityCommentsBeanX> getContentComments() {
        return contentComments;
    }

    public void setContentComments(List<ActivityCommentsBeanX> contentComments) {
        this.contentComments = contentComments;
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


    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
