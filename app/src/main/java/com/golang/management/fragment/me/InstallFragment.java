package com.golang.management.fragment.me;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.fragment.LoginFragment;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class InstallFragment extends BaseFragment {
    @BindView(R.id.tv_sign_out)
    TextView tvSignOut;
    @BindView(R.id.tv_replace_phone)
    TextView tvReplacePhone;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    public static InstallFragment newInstance() {
        Bundle args = new Bundle();
        InstallFragment fragment = new InstallFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_install;
    }

    @Override
    public void initView(View view) {
        setTitle("设置");
        initApi();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils=new UserSharedPreferencesUtils(getContext());
        tvSignOut.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                        .setMessage("退出后评论、消息、参加\n活动等功能将无法使用\n确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                userSharedPreferencesUtils.clearSharedPreferences();
                                startWithPop(LoginFragment.newInstance("1"));
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                alertDialog2.show();
            }
        });
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
