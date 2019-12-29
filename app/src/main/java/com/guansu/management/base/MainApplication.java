package com.guansu.management.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.guansu.management.BuildConfig;
import com.guansu.management.common.ActivityPageManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import okhttp3.OkHttpClient;
/**
 *
 * Created by dongyaoyao
 */
public class MainApplication extends Application {
    private static MainApplication context;
    private final int CONNCET_TIMEOUT = 8;
    public static MainApplication getInstance() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initOkGo();
        initFragmentation();
        initImagePicker();
        initCloudChannel(this);
        MultiDex.install(this);
    }


    private void initImagePicker() {
        ImagePicker.getInstance().setImageLoader(new GlideImageLoader());
        ImagePicker.getInstance().setShowCamera(true);
    }


    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
            loggingInterceptor.setColorLevel(Level.WARNING);                               //log颜色级别，决定了log在控制台显示的颜色
            builder.addInterceptor(loggingInterceptor);
        }

        //超时时间设置，默认60秒
        builder.readTimeout(120000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(120000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(CONNCET_TIMEOUT, TimeUnit.SECONDS);

        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
//        OkDownload.getInstance().getThreadPool().setCorePoolSize(1);

    }

    private void initFragmentation() {
        Fragmentation.builder()
                .debug(BuildConfig.DEBUG)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                    }
                })
                .install();
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
//        PushServiceFactory.init(applicationContext);
//        CloudPushService pushService = PushServiceFactory.getCloudPushService();
//        pushService.register(applicationContext, new CommonCallback() {
//            @Override
//            public void onSuccess(String response) {
//            }
//            @Override
//            public void onFailed(String errorCode, String errorMessage) {
//            }
//        });
//        pushService.setLogLevel(CloudPushService.LOG_OFF);
    }

    /**
     * 退出应用
     */
    public void exit() {
        ActivityPageManager.getInstance().exit(this);
    }
}
