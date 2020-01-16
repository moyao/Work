package com.golang.management.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.golang.management.base.MainApplication;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dongyaoyao
 */
public class CommonUtils {

    public static String formatPersonCount(String personCount) {
        if (!TextUtils.isEmpty(personCount)) {
            return formatPersonCount(Integer.valueOf(personCount));
        }
        return "0";
    }
    public static String formatPersonCount(int personCount) {
        float num = Float.valueOf(personCount);
        if (num < 10000) {
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(num);
        } else {
            DecimalFormat df = new DecimalFormat("#,###.##万");
            return df.format(num / 10000);
        }
    }
    /**
     * 获取app versioncode
     *
     * @return
     */
    public static int getVersionCode() {
        PackageManager pm = MainApplication.getInstance().getPackageManager();// 获取包管理器
        try {
            PackageInfo packageInfo = pm.getPackageInfo(MainApplication.getInstance().getPackageName(), 0);// 根据包名获取应用版本信息
            int versionCode = packageInfo.versionCode;// 获取版本号
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }
    /**
     * 获取app versionname
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager pm = MainApplication.getInstance().getPackageManager();// 获取包管理器
        try {
            PackageInfo packageInfo = pm.getPackageInfo(MainApplication.getInstance().getPackageName(), 0);// 根据包名获取应用版本信息
            String versionName = packageInfo.versionName;// 获取版本号
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }
}
