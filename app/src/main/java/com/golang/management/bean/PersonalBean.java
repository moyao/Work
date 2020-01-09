package com.golang.management.bean;

import java.util.List;

public class PersonalBean {
        /**
         * auth : Auth_F
         * birthday : 2015-01-02
         * education : 研究生
         * enable : true
         * hobby : (ŐдŐ๑
         * id : 1209404585010008064
         * message : 小猫咪呃呃呃呃呃呃呃
         * nickname : 小猫猫
         * occupation : 程序员
         * photoAlbum : [{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/16b5fc03bd2d7e5df7fb0f603dd4628a_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/1bec8a0e2a42492dcc6a19b192e0f736_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/16b5fc03bd2d7e5df7fb0f603dd4628a_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/f46bbe2984f6af3f6355cfbb9f7845ad_1920×2560.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/5fa965f7449afa0b42fb1e8e405f6be0_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/d55660703e572c48a24b50e8d8dba57f_300×300.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/cb114f3b7921ec63d8813e9f43c1d8a3_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/90fc3210a7d027675df9d84640af4b90_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/024246c710bab5783391abb6454fe7bd_300×300.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/16b5fc03bd2d7e5df7fb0f603dd4628a_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/5fa965f7449afa0b42fb1e8e405f6be0_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/f46bbe2984f6af3f6355cfbb9f7845ad_1920×2560.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/024246c710bab5783391abb6454fe7bd_300×300.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/d55660703e572c48a24b50e8d8dba57f_300×300.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/cb114f3b7921ec63d8813e9f43c1d8a3_2448×3264.jpg"},{"imagePath":"http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1209404585010008064/cb114f3b7921ec63d8813e9f43c1d8a3_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/024246c710bab5783391abb6454fe7bd_300×300.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/90fc3210a7d027675df9d84640af4b90_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/16b5fc03bd2d7e5df7fb0f603dd4628a_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/1bec8a0e2a42492dcc6a19b192e0f736_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/f46bbe2984f6af3f6355cfbb9f7845ad_1920×2560.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/cb114f3b7921ec63d8813e9f43c1d8a3_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/16b5fc03bd2d7e5df7fb0f603dd4628a_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/1bec8a0e2a42492dcc6a19b192e0f736_2448×3264.jpg"},{"imagePath":"http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/5fa965f7449afa0b42fb1e8e405f6be0_2448×3264.jpg"}]
         * profileImageUrl : http://golangkeji.oss-cn-beijing.aliyuncs.com/1209404585010008064/f46bbe2984f6af3f6355cfbb9f7845ad_1920×2560.jpg
         * sex : FEMALE
         * single : COUPLE
         * wantToGo : 天涯海角
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
        private List<PhotoAlbumBean> photoAlbum;

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

        public List<PhotoAlbumBean> getPhotoAlbum() {
            return photoAlbum;
        }

        public void setPhotoAlbum(List<PhotoAlbumBean> photoAlbum) {
            this.photoAlbum = photoAlbum;
        }

        public static class PhotoAlbumBean {
            /**
             * imagePath : http://guan-su-oss.oss-cn-beijing.aliyuncs.com/1207114838900215808/40cc3bf901f427bdf2efd53ff37f09da_1440×2560.jpg
             */

            private String imagePath;

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }
        }
}
