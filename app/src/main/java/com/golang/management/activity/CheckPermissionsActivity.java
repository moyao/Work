package com.golang.management.activity;

import android.view.View;
import com.amap.api.location.AMapLocation;
import com.golang.management.base.BaseFragment;
import com.golang.management.servise.LocationManager;
/**
 * @date:
 * @author: dongyaoyao
 */
public abstract class CheckPermissionsActivity extends BaseFragment {


    protected abstract void locationResult(String longitude, String latitude, String address,String city,String province,String district);

    protected void startLocation() {
        LocationManager.getInstance().start(getContext(), new LocationManager.OnLocationResultListener() {
            @Override
            public void onLocationResult(AMapLocation aMapLocation) {
                locationResult(String.valueOf(aMapLocation.getLongitude()),
                        String.valueOf(aMapLocation.getLatitude()),
                        aMapLocation.getAddress(),String.valueOf(aMapLocation.getCity())
                        ,String.valueOf(aMapLocation.getProvince()),String.valueOf(aMapLocation.getDistrict()));
            }
        });
    }
    @Override
    public int onSetLayoutId() {
        return 0;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}