package com.guansu.management.ui.welcome;
import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.fragment.LoginFragment;
import com.guansu.management.fragment.MainFragment;
import butterknife.BindView;

/**
 * Created by dongyaoyao
 */

public class WelcomeFragment extends BaseFragment<WelcomeContract.Presenter>

        implements WelcomeContract.WelcomeInterface {
    @BindView(R.id.butNext)
    Button butNext;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_wecom;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void initView(View view) {
        hideTitle();
    }

        @Override
    public void bindEvent() {
            userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        createPresenter(new WelcomePresenter(this, 3 * 1000));
        timer.start();
    }

    CountDownTimer timer = new CountDownTimer(3 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            butNext.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            butNext.setClickable(true);
        }
    };

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    @Override
    public void toHome() {
        startWithPop(MainFragment.newInstance());
    }

    @Override
    public void toGuide() {
    }

    @Override
    public void toLogin(String message) {
            startWithPop(LoginFragment.newInstance());
    }
}
