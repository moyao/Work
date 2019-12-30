package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.View;

import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;

/**
 * @date:
 * @author: dongyaoyao
 */
public class CashWithdrawalFragment extends BaseFragment {
    public static CashWithdrawalFragment newInstance() {
        Bundle args = new Bundle();
        CashWithdrawalFragment fragment = new CashWithdrawalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_cash_withdrawal;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
