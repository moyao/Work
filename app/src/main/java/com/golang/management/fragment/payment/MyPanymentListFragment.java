package com.golang.management.fragment.payment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.golang.management.R;
import com.golang.management.api.MyObserve;
import com.golang.management.api.ServiceException;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.PaymentBean;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.HttpConstants;
import com.golang.management.model.MeModellml;
import com.golang.management.wigdet.recyclerview.OnItemClickListener;
import com.golang.management.wigdet.recyclerview.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
public class MyPanymentListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rgStatus)
    RadioGroup rgStatus;
    @BindView(R.id.rvListMessage)
    RecyclerView rvListMessage;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    MyPanymentListAdapter myPanymentListAdapter;
    List<PaymentBean> paymentBeans;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    public static MyPanymentListFragment newInstance() {
        Bundle args = new Bundle();
        MyPanymentListFragment fragment = new MyPanymentListFragment();
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
        setTitle("订单列表");
    }

    @Override
    public void bindEvent() {
        rgStatus.setVisibility(View.GONE);
         userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setBackgroundColor(getContext().getResources().getColor(R.color.color_F2F2F2));
        rvListMessage.setLayoutManager(layoutManager);
        rvListMessage.addOnItemTouchListener(new RecyclerItemClickListener(
                new MyPanymentListFragment.ListOnItemClickListener()));
        setLoadingContentView(layoutSwipeRefresh);
        layoutSwipeRefresh.setOnRefreshListener(this);
        initData();
    }

    private void initData() {
        new MeModellml().user_mybill(userSharedPreferencesUtils.getUserid(),
               "")
                .safeSubscribe(new MyObserve<List<PaymentBean>>(this) {
                    @Override
                    protected void onSuccess(List<PaymentBean> paymentBean) {
                        showPage();
                        if (0 == paymentBean.size()) {
                            showNoData();
                        } else {
                            paymentBeans = paymentBean;
                            myPanymentListAdapter = new MyPanymentListAdapter(getContext(), paymentBeans);
                            rvListMessage.setAdapter(myPanymentListAdapter);
                        }
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

    @Override
    public boolean canSwipeBack() {
        return false;
    }
    @Override
    public void onRefresh() {
        layoutSwipeRefresh.setRefreshing(false);
        initData();
    }

    @Override
    protected void retryloading() {
        initData();
    }
    public class ListOnItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            start(PaymentSuccessFragment.newInstance(paymentBeans.get(position).getOrderNo()));
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }
}
