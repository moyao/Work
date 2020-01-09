package com.golang.management.config;
/**
 *
 * Created by dongyaoyao
 */
public class Constant {
    /**
     * 使用常量
     */
    public final static int PROGRESS_ERROR_CODE = -1;//进度条不显示文本
    /**
     * BaseDialog 按钮下标
     */
    public final static int DIALOG_WHICH_OK = 0;
    public final static int DIALOG_WHICH_CANCEL = 1;
    public final static int DIALOG_TITLE_DEFAULT = -1;

    public static final String INTENT_BUSINESS_CATEGORY = "business_type";
    public static final String INTENT_ID_NUMBER = "idCardNumber";
    public static final String INTENT_ARCHIVE_ID = "archiveId";
    public static final String INTENT_FULL_NAME="fullName";

    public static final int PAGE_SIZE = 40;

    //选择图片
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int REQUEST_CODE_TWO = 102;
    //扫描身份证
    public static int SCANNER_REQUESTCODE = 200;

    public static String ACTION_BROADCAST_PUSH = "com.wisdom.push";
    public static String INTENT_FROMPUSH = "fromPush";

    public static String NAMESPACE_JSSDK = "WISDOM_JS";

    public static String REPORT_NAME_LIST="report_name";
    public static String REPORT_LIST="report_list";

    //圈子，活动，最新
    public static String VIEW_CIRCLE="viewCircle";
    public static String VIEW_CONSTRAINTS="viewConstraints";
    public static String VIEW_BLEND="viewBlend";

    //
    public static String PARTICIPATE="participate";
}
