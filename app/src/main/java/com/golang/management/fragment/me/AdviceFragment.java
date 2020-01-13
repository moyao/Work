package com.golang.management.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;

import com.golang.management.R;
import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseFragment;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.model.MeModellml;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class AdviceFragment extends BaseFragment {
    @BindView(R.id.gridLayoutLevel)
    GridLayout gridLayoutLevel;
    @BindView(R.id.editTextContext)
    EditText editTextContext;
    @BindView(R.id.editTextContact)
    EditText editTextContact;
    @BindView(R.id.butRelease)
    Button butRelease;
    private Button mButRelease;
    private static final List<String> n = Arrays.asList(new String[]{"系统出错", "信息不正确", "售后服务", "闪退", "不会使用\n某些功能", "改进意见", "其他"});
    String types = "";
    UserSharedPreferencesUtils userSharedPreferencesUtils;

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
        initApi();
        setTitle("意见反馈");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mButRelease = view.findViewById(R.id.butRelease);
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        mButRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if ("".equals(types)) {
                    showToast("请选择您的问题种类!!!");
                    return;
                }
                if ("".equals(editTextContext.getText().toString())) {
                    showToast("请选择描述您的问题/建议。。。");
                    return;
                }
                if ("".equals(editTextContact.getText().toString())) {
                    showToast("请留下您的联系方式，以便我们更好的处理！！！");
                    return;
                }
                AdviceData();
            }
        });
        for (String b : n) {
            layoutFilterItem(gridLayoutLevel, b);
        }
    }

    private void AdviceData() {
        showLoadingDialog("提交中。。。。");
        new MeModellml().user_suggest(userSharedPreferencesUtils.getUserid(),
                editTextContext.getText().toString(), editTextContact.getText().toString(), types)
                .safeSubscribe(new MyObserve<String>(this) {
                    @Override
                    protected void onSuccess(String editBean) {
                        showPage();
                        getActivity().onBackPressed();
                    }
                });
    }
    private void layoutFilterItem(GridLayout gridLayoutLevel, String n) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_filter, gridLayoutLevel, false);
        CheckBox mCheckBoxFilter = view.findViewById(R.id.checkBoxFilter);
        mCheckBoxFilter.setChecked(false);
        mCheckBoxFilter.setTag(n);
        mCheckBoxFilter.setText(n);
        if (n.equals(types)) {
            mCheckBoxFilter.setChecked(true);
        }
        gridLayoutLevel.addView(view);
        view.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                types = n;
                for (int i = 0; i < gridLayoutLevel.getChildCount(); i++) {
                    View view = gridLayoutLevel.getChildAt(i);
                    if (!view.getTag().equals(v.getTag())) {
                        ((CheckBox) view).setChecked(false);
                    } else {
                        ((CheckBox) view).setChecked(true);
                    }
                }
            }
        });
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
