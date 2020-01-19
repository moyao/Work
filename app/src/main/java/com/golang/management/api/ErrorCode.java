package com.golang.management.api;

public class ErrorCode {
    /**
     * *基础错误信息
     * ID_NOT_NULL("10001","ID不能为NULL"),
     * DATA_NOT_EXIST("10002","数据不存在"),
     * INVALID_ARGUMENT("10003","无效的参数"),
     * UPLOAD_FILE_FAIL("10004","上传文件失败"),
     * UPLOAD_FILE_SIZE_LIMIT("020001","上传文件大小超过限制"),
     * VERIFY_CODE_INVALID("10005","无效的验证码"),
     * DATA_INTEGRITY_VIOLATION("10006","数据正在被使用，请依次删除！"),
     * UPLOAD_FILE_CONTENT_IS_NULL("10007","上传文件内容为空！"),
     * MSG_TEMPLATE_CODE_IS_NULL("10008","短信模板为空！"),
     * FILE_POSTFIX_ERROR("10009","文件格式不支持！"),
     * ILLEGAL_OPERATION("10010","非法操作"),
     * <p>
     * * 会员错误信息
     USERNAME_OR_PASSWORD_INVALID("20001","用户名或密码不正确"),
     OLD_PASSWORD_ERROR("20003","旧密码不正确"),
     PASSWORD_ERROR("20004","请填写6到12位密码"),
     PASSWORD_DIFFERING("20005","两次密码不一致"),
     LOGIN_ACCOUNT_EXIST("20006","登陆账号已存在"),
     ACCOUNT_OR_MOBILE_INVALID("20007","账号或手机号不存在"),
     ACCOUNT_DISABLE("20008","账户已禁用"),
     USERNAME_OR_PASSWORD_TOW_ERROR("20009","用户名或密码错误次数过多"),
     OVERDUE_VALIDATION_CODE("20010","验证码已过期"),
     ACCOUNT_OR_MOBILE_NOTNULL("20011","账号或手机号不能为空"),
     MOBILE_EXIST("20012","手机号已存在"),
     PASSWORD_INVALID("20013","密码不正确"),
     MOBILE_NOT_INVALID("20014","手机号不正确"),
     PASSWORD_AND_PAY_PASSWORD_ERROR("20015","登录密码和支付密码不能相同"),
     LOGIN_INFO_INVALID("20016","登录信息失效"),
     PAY_PASSWORD_NOT_SETTING("20017","支付密码未设置"),
     MOBILE_NOT_SETTING("20018","手机号未设置"),
     REFERRER_NOT_SETTING("20019","推荐人未添加"),
     USER_LEVEL_NOT_EXIST("20020","不是VIP用户，无法操作"),
     CREATE_SHARE_AD_IMAGE_ERROR("20021","生成推广图片失败"),
     INVALID_REFERRER_CODE("20022","无效的推荐码"),
     USER_DLV_ADDRESS_NOT_SETTING("20023","请设置一个默认收货地址～"),
     IS_VIP_USER_NOT_UPGRADE("20024","你已经是VIP会员啦～"),
     IS_VIP_USER_UPGRADE("20025","需要升级为创客～"),
     IDENTYFY_NOT_INVALID("20026","未完成实名认证"),
     IDENTYFY_AUTH_FAIURE("20027","实名认证失败"),
     LOGIN_ACCOUNT_NOT_EXIST("200028","登陆账号不存在"),
     系统配置相关错误信息
     SYSTEM_CONFIG_FLOW_NOT_EXIST("30001","系统配置错误, 流水号配置在数据库中不存在!"),
     SYSTEM_CONFIG_FLOW_LENGTH_ERROR("30002","系统配置错误, 流水号长度不能小于1!"),
     WE_CHAT_CONFIG_NOT_EXIST("30003","微信尚未正确配置，请先配置"),
     WE_CHAT_LOGIN_FAIL("30004","微信接入登录失败"),
     AGENCY_DEFAULT_LEVEL_NOT_EXIST("30005","代理商初始化等级不存在"),
     ALI_YUN_OSS_NOT_CONFIG("30006","阿里云OSS尚未正确配置，请先配置"),
     USER_BASICS_SETTING_NOT_CONFIG("30
     */
    public static boolean Code(String value) {
        return value.equals("1000001") ||
                value.equals("10002") ||
                value.equals("10003") ||
                value.equals("10004") ||
                value.equals("020001") ||
                value.equals("10005") ||
                value.equals("10006") ||
                value.equals("10007") ||
                value.equals("10008") ||
                value.equals("10009") ||
                value.equals("10010") ||
                value.equals("20001") ||
                value.equals("20016") ||
                value.equals("20014") ||
                value.equals("20022")||
                value.equals("120001")||//不可重复报名
                value.equals("120009")||//活动已过期
                value.equals("120008")//不能自己参加自己发起的活动
                ;  //只要一个满足就是true
    }
}
