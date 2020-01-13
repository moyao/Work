package com.golang.management.bean;

/**
 * @date:
 * @author: dongyaoyao
 */
public class FootBean {
        /**
         * createdAt : 2019-12-18 17:55
         * address : 灞桥区香湖湾地铁D口
         * latitude : 34.34
         * id : 1207237983099424768
         * userId : 1207114838900215808
         * updatedAt : 2019-12-18 17:55
         * longitude : 109.06
         */

        private String createdAt;
        private String address;
        private double latitude;
        private String id;
        private long userId;
        private String updatedAt;
        private double longitude;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
}
