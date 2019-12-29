package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;

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
        hideTitle();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        mTabLayoutPeople.addTab(mTabLayoutPeople.newTab().setText("发起"));
        mTabLayoutPeople.addTab(mTabLayoutPeople.newTab().setText("参加"));
        mTabLayoutPeople.addTab(mTabLayoutPeople.newTab().setText("活动"));
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
    }

    private void mTabLayoutPeople(int position) {

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
