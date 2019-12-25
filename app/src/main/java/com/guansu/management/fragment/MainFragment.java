package com.guansu.management.fragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import androidx.annotation.Nullable;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.config.Constant;
import com.guansu.management.fragment.home.FootprintFragemnt;
import com.guansu.management.fragment.home.MeFragment;
import com.guansu.management.fragment.home.MessageFragment;
import com.guansu.management.fragment.home.NewHomeFragment;
import com.guansu.management.fragment.home.ReleaseFragment;
import com.guansu.management.wigdet.bottombar.BottomBar;
import com.guansu.management.wigdet.bottombar.BottomBarTab;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by dongyaoyao
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.radioRelease)
    RadioButton radioRelease;
    private String lat, lng, address;
    private SupportFragment[] mFragments = new SupportFragment[5];
    private BottomBar mNavigation;
    private View viewCircle, viewConstraints;
    private Dialog dia;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_mian;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(NewHomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = NewHomeFragment.newInstance();
            mFragments[1] = MessageFragment.newInstance();
            mFragments[2] = MessageFragment.newInstance();
            mFragments[3] = FootprintFragemnt.newInstance();
            mFragments[4] = MeFragment.newInstance();
            loadMultipleRootFragment(R.id.frameLayout, 0,
                    mFragments[0], mFragments[1], mFragments[2], mFragments[3], mFragments[4]);
        } else {
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(MessageFragment.class);
            mFragments[2] = findChildFragment(MessageFragment.class);
            mFragments[3] = findChildFragment(FootprintFragemnt.class);
            mFragments[4] = findChildFragment(MeFragment.class);
        }
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
        mNavigation = view.findViewById(R.id.navigation);
        mNavigation
                .addItem(new BottomBarTab(_mActivity, R.mipmap.home_no, getString(R.string.navHome)))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.message_no, getString(R.string.navStatistics)))
                .addItem(new BottomBarTab(_mActivity, R.color.transparent, ""))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.footprint_no, getString(R.string.navMessage)))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.my_no, getString(R.string.navMe)));
        dia = new Dialog(getContext(), R.style.BaseDialogStyle);
        dia.setContentView(R.layout.dialog_release);
        viewCircle = dia.findViewById(R.id.view);
        viewConstraints = dia.findViewById(R.id.view1);
        dia.setCanceledOnTouchOutside(true);
        dia.getWindow().setGravity(Gravity.BOTTOM);
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        dia.onWindowAttributesChanged(lp);
    }

    @Override
    public void bindEvent() {
        initApi();
        mNavigation.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        radioRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (!dia.isShowing()) {
                    dia.show();
                }
            }
        });
        viewCircle.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(ReleaseFragment.newInstance(Constant.VIEW_CIRCLE));
                dia.dismiss();
            }
        });
        viewConstraints.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(ReleaseFragment.newInstance(Constant.VIEW_CONSTRAINTS));
                dia.dismiss();
            }
        });
    }

    private long exitTime = 0;

    @Override
    public boolean onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            //如果大于2000毫秒,说明误操作
            showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            _mActivity.finish();
            return super.onBackPressedSupport();
        }
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
