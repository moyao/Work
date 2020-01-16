package com.golang.management.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.golang.management.R;
import com.golang.management.activity.CheckPermissionsActivity;
import com.golang.management.api.MyObserve;
import com.golang.management.api.ServiceException;
import com.golang.management.bean.HomeBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.config.Constant;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.MainFragment;
import com.golang.management.fragment.details.DetailsFragment;
import com.golang.management.fragment.home.adapter.NewHomeAdapter;
import com.golang.management.model.HomeModellml;
import com.golang.management.wigdet.recyclerview.EndLessOnScrollListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by dongyaoyao
 */
public class NewHomeFragment extends CheckPermissionsActivity implements NewHomeAdapter.ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.imageBlack)
    TextView imageBlack;
    @BindView(R.id.imagePreservation)
    ImageButton imagePreservation;
    @BindView(R.id.rvListMessage)
    RecyclerView rvListMessage;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    private String status = Constant.VIEW_CONSTRAINTS;
    NewHomeAdapter newHomeAdapter;
    private List<HomeBean> homeBeanList;
    private EndLessOnScrollListener endLessOnScrollListener;
    private String longitude,latitude;

    int page = 1;
    int tag;

    public static NewHomeFragment newInstance() {
        Bundle args = new Bundle();
        NewHomeFragment fragment = new NewHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void locationResult(String longitude, String latitude,
                                  String address, String city, String province, String district) {
        imageBlack.setText(city);
        this.latitude=latitude;
        this.longitude=longitude;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_home;
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        startLocation();
    }
    private void inithomeData(String status) {
        if (status == Constant.VIEW_BLEND) {
            tag = 0;
        } else if (status == Constant.VIEW_CONSTRAINTS) {
            tag = 1;
        } else if (status == Constant.VIEW_CIRCLE) {
            tag = 2;
        }
        page = 1;
        newHomeAdapter = new NewHomeAdapter(homeBeanList, getContext(), page, tag);
        rvListMessage.setAdapter(newHomeAdapter);
        ReporteNameData(1, tag);
    }
    @Override
    public void bindEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setLayoutManager(layoutManager);
        endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                page = page + 1;
                ReporteNameData(page, tag);

            }
        };
        rvListMessage.addOnScrollListener(endLessOnScrollListener);
        layoutSwipeRefresh.setOnRefreshListener(this);
        setLoadingContentView(layoutSwipeRefresh);
        inithomeData(status);
        imagePreservation.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ((MainFragment) getParentFragment()).start(SweepCodeFragment.newInstance());
            }
        });
    }
    private void ReporteNameData(int currentPage, int tag) {
        if (1 == currentPage) {
            showLoadingPage();
        }
        new HomeModellml().queryactivityinfopage(currentPage, tag,latitude,longitude).
                subscribe(new MyObserve<List<HomeBean>>(this) {
            @Override
            protected void onSuccess(List<HomeBean> homeBeans) {
                if (1 == currentPage) {
                    showPage();
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
        page = 1;
        ReporteNameData(1, tag);
    }


}
