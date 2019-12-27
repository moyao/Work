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
import com.guansu.management.fragment.me.DistributionFragment;
import com.guansu.management.fragment.me.DistributionHomepageFragment;
import com.guansu.management.fragment.me.EditFragment;
import com.guansu.management.fragment.payment.PaymentSuccessFragment;

import java.util.ArrayList;

import butterknife.BindView;
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
    @BindView(R.id.tv_order)
    TextView tvOrder;
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
    TextView textViewExtension;   @BindView(R.id.tv_Order)
    TextView tv_Order;
    private TextView mTvMyTeam;
    private Dialog dia;
    private TextView mTextView;
    private ImageView mIvClose;
    private GridLayout mGridLayoutLevel;
    public static ArrayList<ShareItem> shareItems = new ArrayList<ShareItem>() {{
        add(new ShareItem("微信", R.mipmap.weixin));
        add(new ShareItem("朋友圈", R.mipmap.pengyouquan));
        add(new ShareItem("微博", R.mipmap.weibo));
        add(new ShareItem("QQ好友", R.mipmap.qq));
        add(new ShareItem("支付宝", R.mipmap.zhifubao));
        add(new ShareItem("复制链接", R.mipmap.lianjie));
        add(new ShareItem("二维码", R.mipmap.erweima));
    }};
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
        Glide.with(getContext()).load(userSharedPreferencesUtils.getProfileImageUrl()) .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
        textViewGrade.setText(userSharedPreferencesUtils.getLevelName());

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
                dia.show();
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
                ((MainFragment) getParentFragment()).start(DistributionFragment.newInstance());
            }
        });
        tv_Order.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(PaymentSuccessFragment.newInstance());
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
        lp.width= WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        dia.onWindowAttributesChanged(lp);
        for (ShareItem b : shareItems) {
            layoutFilterItem(mGridLayoutLevel, b);
        }
    }
    private void layoutFilterItem(GridLayout mGridLayoutLevel, ShareItem b) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gride_share, mGridLayoutLevel, false);
        ImageView imageView = view.findViewById(R.id.iv_img);
        TextView iv_title = view.findViewById(R.id.iv_title);
        imageView.setImageResource(b.id);
        iv_title.setText(b.titme);
        mGridLayoutLevel.addView(view);
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }

}