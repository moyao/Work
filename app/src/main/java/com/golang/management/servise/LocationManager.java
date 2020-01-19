package com.golang.management.servise;

import android.content.Context;
import android.content.Intent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocationManager {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0)
                    onLocationResultListener.onLocationResult(location);
            }
        }
    };

    private OnLocationResultListener onLocationResultListener;
    private static final LocationManager ourInstance = new LocationManager();

    public static LocationManager getInstance() {
        return ourInstance;
    }

    public void start(Context context, OnLocationResultListener onLocationResultListener) {
        this.onLocationResultListener = onLocationResultListener;
        context.startService(new Intent(context, LocationService.class));
    }

    public void stop(Context context) {
        context.stopService(new Intent(context, LocationService.class));
    }

    public interface OnLocationResultListener {
        void onLocationResult(AMapLocation aMapLocation);
    }

}