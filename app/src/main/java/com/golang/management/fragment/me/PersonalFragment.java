package com.golang.management.fragment.me;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.golang.management.R;
import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.PersonalBean;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constants;
import com.golang.management.fragment.home.adapter.ImageHorizontalAdapter;
import com.golang.management.model.MeModellml;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerPhoto.setLayoutManager(layoutManager);
        new MeModellml().user_webpage(userSharedPreferencesUtils.getUserid(),
                getArguments().getString(Constants.KEY_TITLE)).safeSubscribe(new MyObserve<PersonalBean>(this) {
            @Override
            protected void onSuccess(PersonalBean personalBean) {
                Glide.with(getContext()).load(personalBean.getProfileImageUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageViewPhoto);
                String str="主人寄语：<font color='#6B6B6B'><small>"+ personalBean.getMessage()+"</small></font>";
                textViewMessage.setTextSize(18);
                textViewMessage.setText(Html.fromHtml(str));
                if ("Auth_T".equals(personalBean.getAuth())) {
                    textViewRealName.setText("已认证");
                } else {
                    textViewRealName.setText("未认证");
                }
                if ("MALE".equals(personalBean.getSex())) {
                    textViewSex.setText("性别：" + "男");
                } else {
                    textViewSex.setText("性别：" + "女");
                }

                textViewAge.setText("年龄：" + "");
                textViewNumber.setText("" + "人看过");
                textViewEducation.setText(personalBean.getEducation());
                if ("SINGLE".equals(personalBean.getSingle())) {
                    textViewSingle.setText("是");
                } else {
                    textViewSingle.setText("否");
                }
                textViewInterest.setText(personalBean.getHobby());
                textViewLocal.setText(personalBean.getWantToGo());
                textViewTime.setText("注册时间");
                ImageHorizontalAdapter imageAdapter = new ImageHorizontalAdapter(personalBean.getPhotoAlbum(),getContext());
                recyclerPhoto.setAdapter(imageAdapter);
            }
        });
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
