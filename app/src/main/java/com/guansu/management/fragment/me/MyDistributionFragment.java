package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.MyDistributionBean;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.fragment.foot.FootAdapter;
import com.guansu.management.fragment.me.adapter.MyDistributionAdapter;
import com.guansu.management.model.MeModellml;

import java.util.List;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class MyDistributionFragment extends BaseFragment {
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    @BindView(R.id.rgStatus)
    RadioGroup rgStatus;
    @BindView(R.id.rvListMessage)
    RecyclerView rvListMessage;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    MyDistributionAdapter myDistributionAdapter;
    public static MyDistributionFragment newInstance() {
        Bundle args = new Bundle();
        MyDistributionFragment fragment = new MyDistributionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_message;
    }

    @Override
    public void initView(View view) {
        mTitlebar.showStatusBar(true);
        initApi();
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        setTitle("我的顾客");
    }

    @Override
    public void bindEvent() {
        rgStatus.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setLayoutManager(layoutManager);
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        setLoadingContentView(layoutSwipeRefresh);
        showLoadingPage();
        new MeModellml().my_distri_bution(userSharedPreferencesUtils.getUserid())
                .safeSubscribe(new MyObserve<List<MyDistributionBean>>(this) {
                    @Override
                    protected void onSuccess(List<MyDistributionBean> orcode) {
                        showPage();
                        myDistributionAdapter = new MyDistributionAdapter(getContext(), orcode);
                        rvListMessage.setAdapter(myDistributionAdapter);
                    }
                });

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
