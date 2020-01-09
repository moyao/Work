package com.golang.management.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by dongyaoyao
 */
public class UserInfo implements Parcelable {
    /**
     * org_code : 610000000000
     * org_level : 1
     * org_name : 陕西省
     * role_code : 402881a0529aaa0401529aae52720005
     * role_name : 系统管理员
     * userId : 9e26acf7bc7f4225aac84fe3853e4d59
     * username : test
     */
    @SerializedName(value = "userId", alternate = {"id"})
    private String userId;
    private Object realName;
    private Object createdAt;
    private UserLevelBean userLevel;
    private Object mobileNumber;
    private String sex;
    private String nickname;
    private Object disLevel;
    private Object accountSalt;

    public Object getRealName() {
        return realName;
    }

    public void setRealName(Object realName) {
        this.realName = realName;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public UserLevelBean getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevelBean userLevel) {
        this.userLevel = userLevel;
    }

    public Object getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Object mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getDisLevel() {
        return disLevel;
    }

    public void setDisLevel(Object disLevel) {
        this.disLevel = disLevel;
    }

    public Object getAccountSalt() {
        return accountSalt;
    }

    public void setAccountSalt(Object accountSalt) {
        this.accountSalt = accountSalt;
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

    private String profileImageUrl;
    private Object age;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {

        this.userId = in.readString();

    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
    public static class UserLevelBean {
        /**
         * createdAt : 2019-09-04 15:34
         * levelCode : L00026
         * levelPicture :
         * enable : true
         * levelDiscount : 10.0
         * sortOrder : 1
         * levelName : 普通用户
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
}