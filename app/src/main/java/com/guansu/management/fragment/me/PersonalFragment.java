package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.EditBean;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.config.Constants;
import com.guansu.management.model.MeModellml;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class PersonalFragment extends BaseFragment {
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @BindView(R.id.textViewMessage)
    TextView textViewMessage;
    @BindView(R.id.textViewSex)
    TextView textViewSex;
    @BindView(R.id.textViewAge)
    TextView textViewAge;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.textViewNumber)
    TextView textViewNumber;
    @BindView(R.id.recyclerPhoto)
    RecyclerView recyclerPhoto;
    @BindView(R.id.textViewRealName)
    TextView textViewRealName;
    @BindView(R.id.textViewEducation)
    TextView textViewEducation;
    @BindView(R.id.textViewOccupation)
    TextView textViewOccupation;
    @BindView(R.id.textViewSingle)
    TextView textViewSingle;
    @BindView(R.id.textViewInterest)
    TextView textViewInterest;
    @BindView(R.id.textViewLocal)
    TextView textViewLocal;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    public static PersonalFragment newInstance(String visitorId) {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        args.putString(Constants.KEY_TITLE, visitorId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_personal_homepage;
    }

    @Override
    public void initView(View view) {
        initApi();
        setTitle("个人主页");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }
    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        new MeModellml().user_webpage(userSharedPreferencesUtils.getUserid(),
                getArguments().getString(Constants.KEY_TITLE)).safeSubscribe(new MyObserve<EditBean>(this) {
            @Override
            protected void onSuccess(EditBean editBean) {

            }
        });
        Glide.with(getContext()).load("").into(imageViewPhoto);
        textViewMessage.setText("主人寄语："+"");
        textViewSex.setText("性别："+"");
        textViewAge.setText("年龄："+"");
        textViewNumber.setText(""+"人看过");
        textViewRealName.setText("认证");
        textViewEducation.setText("教育");
        textViewSingle.setText("是否单身");
        textViewInterest.setText("兴趣爱好");
        textViewLocal.setText("想去的地方");
        textViewTime.setText("注册时间");

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
