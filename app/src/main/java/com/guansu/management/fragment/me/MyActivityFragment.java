package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.HomeBean;
import com.guansu.management.bean.MyActivityBean;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.config.Constant;
import com.guansu.management.fragment.me.adapter.MyActivityAdapter;
import com.guansu.management.model.HomeModellml;
import com.guansu.management.model.MyActivityModellml;

import java.util.List;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MyActivityFragment extends BaseFragment {
    @BindView(R.id.tabLayoutPeople)
    TabLayout mTabLayoutPeople;
    @BindView(R.id.Recycler)
    RecyclerView Recycler;
    @BindView(R.id.imageViewBlack)
    ImageView imageViewBlack;
    private String status = Constant.VIEW_CONSTRAINTS;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    MyActivityAdapter myActivityAdapter;
    List<MyActivityBean> homeBeanList;

    public static MyActivityFragment newInstance() {
        Bundle args = new Bundle();
        MyActivityFragment fragment = new MyActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_my_activity;
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        imageViewBlack.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                getActivity().onBackPressed();//销毁自己
            }
        });
        mTabLayoutPeople.addTab(mTabLayoutPeople.newTab().setText("发起"));
        mTabLayoutPeople.addTab(mTabLayoutPeople.newTab().setText("参加"));
        mTabLayoutPeople.addTab(mTabLayoutPeople.newTab().setText("圈子"));
        mTabLayoutPeople.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mTabLayoutPeople(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(layoutManager);

        activityData(0);
    }

    private void activityData(int position) {
        new MyActivityModellml().query_activity_infopage(userSharedPreferencesUtils.getUserid(), position)
                .safeSubscribe(new MyObserve<List<MyActivityBean>>(this) {
                    @Override
                    protected void onSuccess(List<MyActivityBean> homeBeans) {
                        myActivityAdapter = new MyActivityAdapter(homeBeans, getContext(),position);
                        Recycler.setAdapter(myActivityAdapter);
                    }
                });
    }

    private void mTabLayoutPeople(int position) {
        switch (position) {
            case 0:
                status = Constant.VIEW_CONSTRAINTS;
                break;
            case 1:
                status = Constant.PARTICIPATE;
                break;
            case 2:
                status = Constant.VIEW_CIRCLE;
                break;
        }
        activityData(position);

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
