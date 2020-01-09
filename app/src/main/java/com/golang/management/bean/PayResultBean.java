package com.golang.management.bean;

public class PayResultBean {

    /**
     * alipay_trade_app_pay_response : {"app_id":"2016102100731646","auth_app_id":"2016102100731646","charset":"UTF-8","code":"10000","msg":"Success","out_trade_no":"1214105960643694592","seller_id":"2088102180509114","timestamp":"2020-01-06 16:47:10","total_amount":"399.00","trade_no":"2020010622001466611000088272"}
     * sign : McNZnPHoJ4TzP/6jyzQSiWTx/lPA8veIgFdLlPBIWjC/ajQZWEPWtABfDo4WSB2a8/4C27gOOBLsFi35FmvRvA8lwYYTeetAnHXsWF1kMeHPfRcxFCK7SA8ZftOGk4/PYNuxAhkF6IFW3CrbyE7wmysouU2nJkZ9egp0VBj+I/Fc148DtjIPOm1n0ew5tpJ00PxFqIXgH6EzT5GAB9HLqimGKGpyypG6i8IGs7WEt8xkb8Wa7F6uPQwPY+0RFpwFkIQoL5Lc7zecses3YcTGw4RpPTcmOBvZ8Q9JXliYA3tgHxOg05mycprWUG+zWP9nrnFxz/TMctFz3R61HQqTHQ==
     * sign_type : RSA2
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean {
        /**
         * app_id : 2016102100731646
         * auth_app_id : 2016102100731646
         * charset : UTF-8
         * code : 10000
         * msg : Success
         * out_trade_no : 1214105960643694592
         * seller_id : 2088102180509114
         * timestamp : 2020-01-06 16:47:10
         * total_amount : 399.00
         * trade_no : 2020010622001466611000088272
         */

        private String app_id;
        private String auth_app_id;
        private String charset;
        private String code;
        private String msg;
        private String out_trade_no;
        private String seller_id;
        private String timestamp;
        private String total_amount;
        private String trade_no;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }
    }
}
