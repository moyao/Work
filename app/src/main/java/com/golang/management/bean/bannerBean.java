package com.golang.management.bean;
public class bannerBean {
        /**
         * imgUrl : https://golangkeji.oss-cn-beijing.aliyuncs.com/banner/2.png
         * imgSort : 1
         * createdAt : 2020-01-16 16:22
         * imgTitle : 张三丰
         * id : 1
         * status : null
         */

        private String imgUrl;
        private int imgSort;
        private String createdAt;
        private String imgTitle;
        private String id;
        private Object status;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getImgSort() {
            return imgSort;
        }

        public void setImgSort(int imgSort) {
            this.imgSort = imgSort;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getImgTitle() {
            return imgTitle;
        }

        public void setImgTitle(String imgTitle) {
            this.imgTitle = imgTitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
}
