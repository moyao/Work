package com.golang.management.bean;


import java.util.List;

public class PaymentBean {
        /**
         * paymentFee : 399.00
         * orderNo : 1218742826072412160
         * receivedFee : 0
         * paymentTime : 2020-01-19 11:51:48
         * explanation : 金卡会员
         * userId : 1218741746431692800
         * paymentStatus : 支付成功
         * paymentChannel : 支付宝支付
         */

        private String paymentFee;
        private String orderNo;
        private String receivedFee;
        private String paymentTime;
        private String explanation;
        private String userId;
        private String paymentStatus;
        private String paymentChannel;

        public String getPaymentFee() {
            return paymentFee;
        }

        public void setPaymentFee(String paymentFee) {
            this.paymentFee = paymentFee;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getReceivedFee() {
            return receivedFee;
        }

        public void setReceivedFee(String receivedFee) {
            this.receivedFee = receivedFee;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public String getExplanation() {
            return explanation;
        }

        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getPaymentChannel() {
            return paymentChannel;
        }

        public void setPaymentChannel(String paymentChannel) {
            this.paymentChannel = paymentChannel;
        }
}
