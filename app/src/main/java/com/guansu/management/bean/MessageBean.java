package com.guansu.management.bean;

import java.util.List;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MessageBean {
        /**
         * realName : null
         * createdAt : 2019-12-18
         * userLevel : null
         * mobileNumber : null
         * sex : null
         * nickname : 13992076915
         * disLevel : {"createdAt":null,"managerNum":0,"makerNum":0,"distributionLevel":"PUKA_MEMBER","id":null,"position":"MANAGER","userId":1207114838900215808,"cuoNum":0,"recommendCode":null}
         * id : 1207114838900215808
         * accountSalt : null
         * profileImageUrl : http://cdn.kdtonline.cn/faker_avator/piliang/6.jpg
         * age : null
         */

        private Object realName;
        private String createdAt;
        private Object userLevel;
        private Object mobileNumber;
        private Object sex;
        private String nickname;
        private DisLevelBean disLevel;
        private long id;
        private Object accountSalt;
        private String profileImageUrl;
        private Object age;

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

        public Object getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(Object userLevel) {
            this.userLevel = userLevel;
        }

        public Object getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(Object mobileNumber) {
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

        public static class DisLevelBean {
            /**
             * createdAt : null
             * managerNum : 0
             * makerNum : 0
             * distributionLevel : PUKA_MEMBER
             * id : null
             * position : MANAGER
             * userId : 1207114838900215808
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
