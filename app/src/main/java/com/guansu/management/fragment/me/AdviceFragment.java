package com.guansu.management.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;

import androidx.fragment.app.FragmentActivity;

import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.wigdet.dialog.PaySuccessDialog;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class AdviceFragment extends BaseFragment {

    @BindView(R.id.gridLayoutLevel)
    GridLayout gridLayoutLevel;
    private Button mButRelease;
    private static final String[] n = {"系统出错", "信息不正确", "售后服务", "闪退", "不会使用\n某些功能", "改进意见", "其他"};

    public static AdviceFragment newInstance() {
        Bundle args = new Bundle();
        AdviceFragment fragment = new AdviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_advice;
    }

    @Override
    public void initView(View view) {
        setTitle("意见反馈");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mButRelease = view.findViewById(R.id.butRelease);
    }
    @Override
    public void bindEvent() {
        mButRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                new PaySuccessDialog.Builder((FragmentActivity) getContext()).show();
            }
        });
        for (String b:n) {
            layoutFilterItem(gridLayoutLevel, b);
        }
    }

    private void layoutFilterItem(GridLayout gridLayoutLevel, String n) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_filter, gridLayoutLevel, false);
        CheckBox mCheckBoxFilter = view.findViewById(R.id.checkBoxFilter);
        mCheckBoxFilter.setChecked(false);
        mCheckBoxFilter.setText(n);
        gridLayoutLevel.addView(view);
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
