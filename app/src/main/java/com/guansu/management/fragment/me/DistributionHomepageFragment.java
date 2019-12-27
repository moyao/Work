package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.DistributiopnHomepageBean;
import com.guansu.management.bean.orcode;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.model.MeModellml;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class DistributionHomepageFragment extends BaseFragment {
    ImageButton imageButton;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @BindView(R.id.textViewGrade)
    TextView textViewGrade;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewTotal)
    TextView textViewTotal;
    @BindView(R.id.textViewCash)
    TextView textViewCash;
    @BindView(R.id.tvSettlement)
    TextView tvSettlement;
    @BindView(R.id.textViewSettlement)
    TextView textViewSettlement;
    @BindView(R.id.imageButtonQRCode)
    View imageButtonQRCode;
    @BindView(R.id.imageButtonCustomer)
    View imageButtonCustomer;

    public static DistributionHomepageFragment newInstance() {
        Bundle args = new Bundle();
        DistributionHomepageFragment fragment = new DistributionHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_my_distribution;
    }

    @Override
    public void initView(View view) {
        initApi();
        setTitle("我的分销");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        new MeModellml().query_Point_Account_Page(userSharedPreferencesUtils.getUserid()).
                safeSubscribe(new MyObserve<DistributiopnHomepageBean>(this) {
                    @Override
                    protected void onSuccess(DistributiopnHomepageBean distributiopnHomepageBean) {
                        Glide.with(getContext()).load(distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getProfileImageUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageViewPhoto);
                        textViewGrade.setText("等级："+distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getUserLevel().getLevelName());
                        textViewName.setText("昵称："+distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getNickname());
                        textViewTotal.setText(distributiopnHomepageBean.getContent().get(0).getAccountBalance()+"");
                    }
                });
        imageButtonQRCode.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(QrCodeFragment.newInstance());
            }
        });
        imageButtonCustomer.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(MyDistributionFragment.newInstance());
            }
        });
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
