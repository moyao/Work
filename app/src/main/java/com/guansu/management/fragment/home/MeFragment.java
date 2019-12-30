package com.guansu.management.fragment.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.ShareItem;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.fragment.MainFragment;
import com.guansu.management.fragment.me.AdviceFragment;
import com.guansu.management.fragment.me.DistributionHomepageFragment;
import com.guansu.management.fragment.me.EditFragment;
import com.guansu.management.fragment.me.InstallFragment;
import com.guansu.management.fragment.me.MyActivityFragment;
import com.guansu.management.fragment.payment.PaymentSuccessFragment;

import java.util.ArrayList;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author: dongyaoyao
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.textViewGrade)
    TextView textViewGrade;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_wallet)
    TextView tvWallet;
    @BindView(R.id.ll_layout)
    FrameLayout llLayout;
    @BindView(R.id.tv_userinfo)
    TextView tvUserinfo;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_advice)
    TextView tvAdvice;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.textViewExtension)
    TextView textViewExtension;
    @BindView(R.id.tv_Order)
    TextView tv_Order;
    @BindView(R.id.textView_order)
    TextView textView_order;
    private TextView mTvMyTeam;
    private Dialog dia;
    private TextView mTextView;
    private ImageView mIvClose;
    private GridLayout mGridLayoutLevel;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_me;
    }

    @Override
    public void initView(View view) {
        hideTitle();
        initDialog();
    }

    @Override
    public void bindEvent() {
        UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        tvName.setText(userSharedPreferencesUtils.getNickname());
        Glide.with(getContext()).load(userSharedPreferencesUtils.getProfileImageUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
        textViewGrade.setText(userSharedPreferencesUtils.getLevelName());
        ivHead.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(EditFragment.newInstance());
            }
        });
        tvUserinfo.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(EditFragment.newInstance());
            }
        });
        tvAdvice.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(AdviceFragment.newInstance());
            }
        });
        tvShare.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                showShare();
            }
        });
        textViewExtension.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(DistributionHomepageFragment.newInstance());
            }
        });
        tvSetting.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(InstallFragment.newInstance());
            }
        });
        tv_Order.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(PaymentSuccessFragment.newInstance());
            }
        });
        tvWallet.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(MyActivityFragment.newInstance());
            }
        });
        textView_order.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                showToast("开发中，敬请期待。。。");
            }
        });
    }

    private void initDialog() {
        dia = new Dialog(getContext(), R.style.BaseDialogStyle);
        dia.setContentView(R.layout.dialog_share);
        dia.setCanceledOnTouchOutside(true);
        dia.getWindow().setGravity(Gravity.BOTTOM);
        mTextView = dia.findViewById(R.id.textView);
        mIvClose = dia.findViewById(R.id.iv_close);
        mGridLayoutLevel = dia.findViewById(R.id.gridLayoutLevel);
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dia.onWindowAttributesChanged(lp);
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    //java
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，确保SDcard下面存在此张图片
        oks.setImagePath("/sdcard/test.jpg");
        // url在微信、Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getContext());
    }
}