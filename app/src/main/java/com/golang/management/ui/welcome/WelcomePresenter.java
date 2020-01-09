package com.golang.management.ui.welcome;

import android.os.CountDownTimer;

import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseCommonPresenter;
import com.golang.management.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by dongyaoyao
 */

public class WelcomePresenter extends BaseCommonPresenter<WelcomeContract.WelcomeInterface>
        implements WelcomeContract.Presenter {
    private CountDownTimer countDownTimer;

    public WelcomePresenter(WelcomeContract.WelcomeInterface view, int time) {
        super(view);
        countDownTimer = new TimeCount(time, 1000);
        countDownTimer.start();
    }

    @Override
    public boolean validateFirst(int version_code) {
        int localVersion = CommonUtils.getVersionCode();
        // 判断当前版本号不一致则显示引导页
        if (version_code != localVersion) {
            Observable.timer(1, TimeUnit.SECONDS).subscribe(new MyObserve<Long>(view) {
                @Override
                protected void onSuccess(Long aLong) {
                    view.toGuide();
                }
            });

            countDownTimer.cancel();
        }
        return version_code != localVersion;
    }


    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (view.isLogin()) {
                view.toHome();
            } else {
                view.toLogin(null);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

    }

    @Override
    public void unsubscribe() {
        countDownTimer.cancel();
        super.unsubscribe();
    }
}
