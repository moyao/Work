package com.guansu.management.bean;

import java.util.List;

public class PersonalBean {
        /**
         * auth : Auth_F
         * birthday : 1991-03-06
         * education : 大专
         * enable : true
         * hobby : 啦啦
         * id : 1212990656276860928
         * message : 啦啦啦啦
         * nickname : 小仙女
         * occupation : 默默
         * photoAlbum : []
         * profileImageUrl : http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1212990656276860928/024246c710bab5783391abb6454fe7bd_300×300.jpg
         * sex : FEMALE
         * single : COUPLE
         * wantToGo : 啦啦
         */

        private String auth;
        private String birthday;
        private String education;
        private boolean enable;
        private String hobby;
        private long id;
        private String message;
        private String nickname;
        private String occupation;
        private String profileImageUrl;
        private String sex;
        private String single;
        private String wantToGo;
        private List<?> photoAlbum;

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSingle() {
            return single;
        }

        public void setSingle(String single) {
            this.single = single;
        }

        public String getWantToGo() {
            return wantToGo;
        }

        public void setWantToGo(String wantToGo) {
            this.wantToGo = wantToGo;
        }

        public List<?> getPhotoAlbum() {
            return photoAlbum;
        }

        public void setPhotoAlbum(List<?> photoAlbum) {
            this.photoAlbum = photoAlbum;
        }
}
