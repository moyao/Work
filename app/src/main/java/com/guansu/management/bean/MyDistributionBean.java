package com.guansu.management.bean;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MyDistributionBean {
        /**
         * createdAt : 2019-12-24
         * disLevel : {"cuoNum":0,"distributionLevel":"MERCHANT","makerNum":0,"managerNum":0,"position":"CUO","userId":1209404585010008064}
         * id : 1209404585010008064
         * nickname : 18009262998
         * profileImageUrl : http://cdn.kdtonline.cn/faker_avator/piliang/6.jpg
         */

        private String createdAt;
        private DisLevelBean disLevel;
        private long id;
        private String nickname;
        private String profileImageUrl;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public static class DisLevelBean {
            /**
             * cuoNum : 0
             * distributionLevel : MERCHANT   运营商   GOLD_MEMBER   创客   PUKA_MEMBER  普通会员
             * makerNum : 0
             * managerNum : 0
             * position : CUO 总监  MAKER  创客    COO  总经理   MANAGER 经理
             * userId : 1209404585010008064
             */

            private int cuoNum;
            private String distributionLevel;
            private int makerNum;
            private int managerNum;
            private String position;
            private long userId;

            public int getCuoNum() {
                return cuoNum;
            }

            public void setCuoNum(int cuoNum) {
                this.cuoNum = cuoNum;
            }

            public String getDistributionLevel() {
                return distributionLevel;
            }

            public void setDistributionLevel(String distributionLevel) {
                this.distributionLevel = distributionLevel;
            }

            public int getMakerNum() {
                return makerNum;
            }

            public void setMakerNum(int makerNum) {
                this.makerNum = makerNum;
            }

            public int getManagerNum() {
                return managerNum;
            }

            public void setManagerNum(int managerNum) {
                this.managerNum = managerNum;
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
        }
}
