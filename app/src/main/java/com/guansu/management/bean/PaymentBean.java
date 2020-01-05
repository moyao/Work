package com.guansu.management.bean;

public class PaymentBean {
    /**
     * code : 0000000
     * data : alipay_sdk=alipay-sdk-java-4.8.10.ALL&app_id=2016102100731646&biz_content=%7B%22body%22%3A%22399%22%2C%22out_trade_no%22%3A%221213708171065561088%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22%E9%87%91%E5%8D%A1%E4%BC%9A%E5%91%98%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F85bc016a.ngrok.io%2Fpay%2FaliPay%2FnotifyUrl&sign=RTtxDDz44keUkhID4weuQE2EXriES17ukqrc%2Fcpqzf7FPOO7AvUnuSqYtndGydQ8hkcwkogjA92N3pc5GBnhtu05Up8%2FzgwkaTU0AFNp3aD%2BzDxUMgUL6MB5N%2FQopVLE344gE9j8u4PQd0G7dE%2BKcV4Uhcs9ZOGrTdr5%2FGAxYu7N3wZfW3vZHcJ30hZ2PbchXzIdeAflq0mbIITdPki6V4rLQK%2FQahHMG4QQN0hkmlWz61Z5O7dFRbZdhk0dsItr1WPQ6pwOxSh6K%2FSWXfJYx7TI4x37u5MaVFiGxzg1VSTrgSbQSx7UuMbQSIM%2B9Bd0ckvcZ7fa%2FUT%2By44Sz%2F4boA%3D%3D&sign_type=RSA2&timestamp=2020-01-05+14%3A25%3A52&version=1.0
     * developMsg :
     * msg : 请求成功
     * ts : 1578205552265
     * uri :
     */
    private String code;
    private String data;
    private String developMsg;
    private String msg;
    private long ts;
    private String uri;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDevelopMsg() {
        return developMsg;
    }

    public void setDevelopMsg(String developMsg) {
        this.developMsg = developMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
