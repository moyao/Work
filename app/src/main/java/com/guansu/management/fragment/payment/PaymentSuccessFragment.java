package com.guansu.management.fragment.payment;

import android.os.Bundle;
import android.view.View;

import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;

/**
 * @date:
 * @author: dongyaoyao
 */
public class PaymentSuccessFragment extends BaseFragment {
    public static PaymentSuccessFragment newInstance() {
        Bundle args = new Bundle();
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
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

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
