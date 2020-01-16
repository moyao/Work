package com.golang.management.ui.welcome;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constants;
import com.golang.management.fragment.LoginFragment;
import com.golang.management.fragment.MainFragment;
import com.golang.management.helper.GenerateTestUserSig;
import com.golang.management.utils.StringHandler;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

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
            UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
            if (!StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
                String userSig = GenerateTestUserSig.genTestUserSig(userSharedPreferencesUtils.getAccount());
                // identifier 为用户名，userSig 为用户登录凭证
                TUIKit.login(userSharedPreferencesUtils.getAccount(), userSig, new IUIKitCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        Log.e("login failed. code: ","");
                    }
                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        Log.e("login failed. code: " + errCode ," errmsg: " + errMsg);
                    }
                });
            }
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
