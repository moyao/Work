package com.guansu.management.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.api.ServiceException;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.HomeBean;
import com.guansu.management.config.Constant;
import com.guansu.management.config.HttpConstants;
import com.guansu.management.fragment.MainFragment;
import com.guansu.management.fragment.details.DetailsFragment;
import com.guansu.management.fragment.home.adapter.NewHomeAdapter;
import com.guansu.management.model.HomeModellml;
import com.guansu.management.wigdet.recyclerview.EndLessOnScrollListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dongyaoyao
 */
public class NewHomeFragment extends BaseFragment implements NewHomeAdapter.ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rgStatus)
    RadioGroup rgStatus;
    @BindView(R.id.rvListMessage)
    RecyclerView rvListMessage;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    private String status = Constant.VIEW_BLEND;
    NewHomeAdapter newHomeAdapter;
    private List<HomeBean> homeBeanList;
    private EndLessOnScrollListener endLessOnScrollListener;
    int page = 1;
    int tag;

    public static NewHomeFragment newInstance() {
        Bundle args = new Bundle();
        NewHomeFragment fragment = new NewHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_message;
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    private void inithomeData(String status) {
        if (status == Constant.VIEW_BLEND) {
            tag = 0;
        } else if (status == Constant.VIEW_CONSTRAINTS) {
            tag = 1;
        } else if (status == Constant.VIEW_CIRCLE) {
            tag = 2;
        }
        newHomeAdapter = new NewHomeAdapter(homeBeanList, getContext(), page, tag);
        rvListMessage.setAdapter(newHomeAdapter);
        ReporteNameData(1, tag);
    }
    @Override
    public void bindEvent() {
        rgStatus.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setLayoutManager(layoutManager);
        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                ReporteNameData(++page, tag);
            }
        };
        rvListMessage.addOnScrollListener(endLessOnScrollListener);
        layoutSwipeRefresh.setOnRefreshListener(this);
        setLoadingContentView(layoutSwipeRefresh);
        inithomeData(status);
    }

    private void ReporteNameData(int currentPage, int tag) {
        showLoadingPage();
        new HomeModellml().queryactivityinfopage(currentPage,tag).subscribe(new MyObserve<List<HomeBean>>(this) {
            @Override
            protected void onSuccess(List<HomeBean> homeBeans) {
                showPage();
                if (1 == currentPage) {
                    endLessOnScrollListener.refresh();
                    newHomeAdapter.setmList(homeBeans, 1);
                } else {
                    newHomeAdapter.addmList(homeBeans, currentPage);
                }
                initOnclick();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (e instanceof ServiceException && ((ServiceException) e).code.equals(HttpConstants.SUCCESS_CODE)) {
                    showNoData();
                } else {
                    showError(e);
                }
            }
        });
    }

    private void initOnclick() {
        newHomeAdapter.setItemClickListener(this);
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    @Override
    public void OnItemClick(String id, int tag) {
        if ("-1".equals(id)) {
            status = Constant.VIEW_CONSTRAINTS;
            inithomeData(status);
        } else if ("0".equals(id)) {
            status = Constant.VIEW_CIRCLE;
            inithomeData(status);
        } else {
            if (2 == tag) {
                ((MainFragment) getParentFragment()).start(DetailsFragment.newInstance(id, Constant.VIEW_CIRCLE));
            } else {
                ((MainFragment) getParentFragment()).start(DetailsFragment.newInstance(id, Constant.VIEW_CONSTRAINTS));
            }
        }
    }

    @Override
    public void onRefresh() {
        inithomeData(status);
        layoutSwipeRefresh.setRefreshing(false);
    }
    @Override
    protected void retryloading() {
        ReporteNameData(1, tag);
    }
}
