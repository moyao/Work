package com.guansu.management.ui.welcome;
import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
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
    private int time=3;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            butNext.setText(time+"s");
            if (time==0){

                handler.removeMessages(0);
            }
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };
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
            createPresenter(new WelcomePresenter(this, 3 * 1000));
            handler.sendEmptyMessageDelayed(0,1000);
            butNext.setOnClickListener(new OnClickListenerWrapper() {
                @Override
                protected void onSingleClick(View v) {
                    EndTime();

                }
            });
    }

    private void EndTime() {
        createPresenter(new WelcomePresenter(this, 0));
    }
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
            startWithPop(LoginFragment.newInstance("0"));
    }
}
