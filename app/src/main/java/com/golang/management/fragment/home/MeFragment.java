package com.golang.management.fragment.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
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
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.fragment.MainFragment;
import com.golang.management.fragment.me.AdviceFragment;
import com.golang.management.fragment.me.DistributionFragment;
import com.golang.management.fragment.me.DistributionHomepageFragment;
import com.golang.management.fragment.me.EditFragment;
import com.golang.management.fragment.me.InstallFragment;
import com.golang.management.fragment.me.MyActivityFragment;
import com.golang.management.fragment.payment.PaymentSuccessFragment;
import com.golang.management.utils.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }
    @Override
    public void bindEvent() {
        UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        tvName.setText(userSharedPreferencesUtils.getNickname());
        Glide.with(getContext()).load(userSharedPreferencesUtils.getProfileImageUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
        textViewGrade.setText("等级：" + userSharedPreferencesUtils.getLevelName());
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
                if ("普通用户".equals(textViewGrade.getText().toString())) {
                    ((MainFragment) getParentFragment()).start(DistributionFragment.newInstance());
                } else {
                    ((MainFragment) getParentFragment()).start(DistributionHomepageFragment.newInstance());
                }
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
                ((MainFragment) getParentFragment()).start(PaymentSuccessFragment.newInstance(""));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EvenData(MessageEvent messageEvent) {
        if ("发生改变".equals(messageEvent.getMessage())) {
            UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
            tvName.setText(userSharedPreferencesUtils.getNickname());
            Glide.with(getContext()).load(userSharedPreferencesUtils.getProfileImageUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
            textViewGrade.setText(userSharedPreferencesUtils.getLevelName());
        }
    }
}