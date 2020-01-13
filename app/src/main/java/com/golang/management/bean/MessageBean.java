package com.golang.management.bean;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MessageBean {
    /**
     * realName : null
     * createdAt : 2020-01-09
     * userLevel : {"createdAt":"2019-09-04 15:34","levelCode":"L00026","levelPicture":"","enable":true,"levelDiscount":10,"sortOrder":1,"levelName":"金卡会员","id":"1169151778160119808"}
     * mobileNumber : 13444444444
     * sex : null
     * nickname : 史泰龙
     * disLevel : {"createdAt":null,"managerNum":0,"makerNum":0,"distributionLevel":"GOLD_MEMBER","id":null,"position":"MANAGER","userId":1215197045594263552,"uri":null,"cuoNum":0,"recommendCode":null}
     * id : 1215197045594263552
     * accountSalt : null
     * message : 大江小河
     * profileImageUrl : http://golangkeji.oss-cn-beijing.aliyuncs.com/1215197045594263552/b98a9a6bf480fe56c959181eccba27f5_72×72.png
     * age : null
     */

    private Object realName;
    private String createdAt;
    private UserLevelBean userLevel;
    private String mobileNumber;
    private Object sex;
    private String nickname;
    private DisLevelBean disLevel;
    private long id;
    private Object accountSalt;
    private String message;
    private String profileImageUrl;
    private Object age;


    private int readState;
    private String targetUserNickname;
    private long targetUserId;
    private String userId;
    private String content;
    private String parentId;
    private Object userInfoDto;
    private String objectId;
    private Object activityComments;
    private int favoriteCount;
    private String status;
    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

    public String getTargetUserNickname() {
        return targetUserNickname;
    }

    public void setTargetUserNickname(String targetUserNickname) {
        this.targetUserNickname = targetUserNickname;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Object getActivityComments() {
        return activityComments;
    }

    public void setActivityComments(Object activityComments) {
        this.activityComments = activityComments;
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




    public Object getRealName() {
        return realName;
    }

    public void setRealName(Object realName) {
        this.realName = realName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserLevelBean getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevelBean userLevel) {
        this.userLevel = userLevel;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
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

    public DisLevelBean getDisLevel() {
        return disLevel;
    }

    public void setDisLevel(DisLevelBean disLevel) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public static class UserLevelBean {
        /**
         * createdAt : 2019-09-04 15:34
         * levelCode : L00026
         * levelPicture :
         * enable : true
         * levelDiscount : 10.0
         * sortOrder : 1
         * levelName : 金卡会员
         * id : 1169151778160119808
         */

        private String createdAt;
        private String levelCode;
        private String levelPicture;
        private boolean enable;
        private double levelDiscount;
        private int sortOrder;
        private String levelName;
        private String id;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getLevelCode() {
            return levelCode;
        }

        public void setLevelCode(String levelCode) {
            this.levelCode = levelCode;
        }

        public String getLevelPicture() {
            return levelPicture;
        }

        public void setLevelPicture(String levelPicture) {
            this.levelPicture = levelPicture;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public double getLevelDiscount() {
            return levelDiscount;
        }

        public void setLevelDiscount(double levelDiscount) {
            this.levelDiscount = levelDiscount;
        }

        public int getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class DisLevelBean {
        /**
         * createdAt : null
         * managerNum : 0
         * makerNum : 0
         * distributionLevel : GOLD_MEMBER
         * id : null
         * position : MANAGER
         * userId : 1215197045594263552
         * uri : null
         * cuoNum : 0
         * recommendCode : null
         */

        private Object createdAt;
        private int managerNum;
        private int makerNum;
        private String distributionLevel;
        private Object id;
        private String position;
        private long userId;
        private Object uri;
        private int cuoNum;
        private Object recommendCode;

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public int getManagerNum() {
            return managerNum;
        }

        public void setManagerNum(int managerNum) {
            this.managerNum = managerNum;
        }

        public int getMakerNum() {
            return makerNum;
        }

        public void setMakerNum(int makerNum) {
            this.makerNum = makerNum;
        }

        public String getDistributionLevel() {
            return distributionLevel;
        }

        public void setDistributionLevel(String distributionLevel) {
            this.distributionLevel = distributionLevel;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public Object getUri() {
            return uri;
        }

        public void setUri(Object uri) {
            this.uri = uri;
        }

        public int getCuoNum() {
            return cuoNum;
        }

        public void setCuoNum(int cuoNum) {
            this.cuoNum = cuoNum;
        }

        public Object getRecommendCode() {
            return recommendCode;
        }

        public void setRecommendCode(Object recommendCode) {
            this.recommendCode = recommendCode;
        }
    }
}
