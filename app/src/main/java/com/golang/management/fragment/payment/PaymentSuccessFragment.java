package com.golang.management.fragment.payment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.golang.management.R;
import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.PaymentBean;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constants;
import com.golang.management.model.MeModellml;

import java.util.List;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class PaymentSuccessFragment extends BaseFragment {
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    @BindView(R.id.textViewOrderNumber)
    TextView textViewOrderNumber;
    @BindView(R.id.textViewMoney)
    TextView textViewMoney;
    @BindView(R.id.textViewAccountNumber)
    TextView textViewAccountNumber;
    @BindView(R.id.textViewType)
    TextView textViewType;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.textViewContext)
    TextView textViewContext;
    public static PaymentSuccessFragment newInstance(String out_trade_no) {
        Bundle args = new Bundle();
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        args.putString(Constants.KEY_TITLE,out_trade_no);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_payment_success;
    }
    @Override
    public void initView(View view) {
        mTitlebar.showStatusBar(true);
        initApi();
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        setTitle("支付订单");
    }
    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        if ("".equals(getArguments().getString(Constants.KEY_TITLE))){
            textViewContext.setVisibility(View.GONE);
        }else {
            textViewContext.setVisibility(View.VISIBLE);
        }
        showLoadingDialog("加载中。。。");
        new MeModellml().user_mybill(userSharedPreferencesUtils.getUserid(),
                getArguments().getString(Constants.KEY_TITLE))
                .safeSubscribe(new MyObserve<List<PaymentBean>>(this) {
                    @Override
                    protected void onSuccess(List<PaymentBean> paymentBean) {
                        showPage();
                        PaymentBean paymentBean1=paymentBean.get(0);
                        textViewOrderNumber.setText(paymentBean1.getOrderNo());
                        textViewMoney.setText(paymentBean1.getPaymentFee() + "元");
                        textViewType.setText(paymentBean1.getPaymentChannel());
                        textViewTime.setText(paymentBean1.getPaymentTime());
                    }
                });
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
