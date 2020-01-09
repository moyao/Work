package com.golang.management.bean;

/**
 * @author: dongyaoyao
 */
public class ImagesListBean {
    /**
     * activityId : 1205040515481866240
     * createdAt : 2019-12-12 16:23
     * id : 1205040515838382080
     * image : http://supcache.airbdata.cn/data/www.gzcts03.com/uploads/1556362395_8333.jpg
     * isCover : true
     */

    private String activityId;
    private String createdAt;
    private String id;
    private String image;
    private boolean isCover;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsCover() {
        return isCover;
    }

    public void setIsCover(boolean isCover) {
        this.isCover = isCover;
    }
}
