package com.guansu.management.fragment.me;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.orcode;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.model.MeModellml;
import com.guansu.management.wigdet.CommonTitleBar;

import butterknife.BindView;

import static com.guansu.management.R.*;
/**
 * @author: dongyaoyao
 */
public class QrCodeFragment extends BaseFragment {
    @BindView(id.imageViewQrCode)
    ImageView imageViewQrCode;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    @BindView(id.textViewCode)
    TextView textViewCode;
    public static QrCodeFragment newInstance() {
        Bundle args = new Bundle();
        QrCodeFragment fragment = new QrCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return layout.fragement_qrcode;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView(View view) {
        mTitlebar.showStatusBar(true);
        initApi();
        mTitlebar.setBackgroundResource(drawable.but_release);
        setTitle("我的二维码");
    }
    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        new MeModellml().find_activity_byuserid(userSharedPreferencesUtils.getUserid())
                .safeSubscribe(new MyObserve<orcode>(this) {
                    @Override
                    protected void onSuccess(orcode orcode) {
                        Glide.with(getContext()).load(orcode.getQR_URI()).into(imageViewQrCode);
                        textViewCode.setText("推荐码："+orcode.getRecommendCode());
                    }
                });
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
