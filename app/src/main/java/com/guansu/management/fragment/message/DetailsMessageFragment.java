package com.guansu.management.fragment.message;

import android.os.Bundle;
import android.view.View;

import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;

/**
 * @author: dongyaoyao
 */
public class DetailsMessageFragment extends BaseFragment {

    public static DetailsMessageFragment newInstance() {
        Bundle args = new Bundle();
        DetailsMessageFragment fragment = new DetailsMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_details_message;
    }

    @Override
    public void initView(View view) {
        setTitle("消息详情");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);

    }

    @Override
    public void bindEvent() {


    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
