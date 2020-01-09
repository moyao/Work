package com.golang.management.bean;


public class PaymentBean {
        /**
         * explanation : 金卡会员
         * orderNo : 1213734449533554688
         * paymentChannel : 支付宝支付
         * paymentFee : 399.00
         * paymentStatus : 已支付
         * paymentTime : 2020-01-05 16:10:18
         * receivedFee : 0
         * userId : 1212990656276860928
         */

        private String explanation;
        private String orderNo;
        private String paymentChannel;
        private String paymentFee;
        private String paymentStatus;
        private String paymentTime;
        private String receivedFee;
        private String userId;

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPaymentChannel() {
            return paymentChannel;
        }

        public void setPaymentChannel(String paymentChannel) {
            this.paymentChannel = paymentChannel;
        }

        public String getPaymentFee() {
            return paymentFee;
        }

        public void setPaymentFee(String paymentFee) {
            this.paymentFee = paymentFee;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getReceivedFee() {
            return receivedFee;
        }

        public void setReceivedFee(String receivedFee) {
            this.receivedFee = receivedFee;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
}
