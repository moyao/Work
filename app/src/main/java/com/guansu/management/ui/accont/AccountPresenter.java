package com.guansu.management.ui.accont;//package com.wisdom.regulatory.ui.welcome;

import android.content.Context;
import android.text.TextUtils;

import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseCommonPresenter;
import com.guansu.management.bean.UserInfo;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.model.AccountModelIml;
import com.guansu.management.model.bean.LoginResult;

/**
 *
 * Created by dongyaoyao
 */
public class AccountPresenter extends BaseCommonPresenter<AccountContract.LoginInterface> implements AccountContract.Presenter {

    public AccountPresenter(AccountContract.LoginInterface view) {
        super(view);
    }

    @Override
    public void login(final Context context, final String account, final String pwd) {
        if (TextUtils.isEmpty(account)) {
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            return;
        }
        view.showLoadingDialog("登录中……");
        new AccountModelIml().login(account,pwd).subscribe(new MyObserve<UserInfo>(view) {
            @Override
            protected void onSuccess(UserInfo userInfo) {
                UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(context);
                userSharedPreferencesUtils.setAccount(account);
                userSharedPreferencesUtils.setPwd(pwd);
                userSharedPreferencesUtils.setUserid(userInfo.getUserId());
                userSharedPreferencesUtils.setNickname(userInfo.getNickname());
                userSharedPreferencesUtils.setProfileImageUrl(userInfo.getProfileImageUrl());
                userSharedPreferencesUtils.setLevelName(userInfo.getUserLevel().getLevelName());
                userSharedPreferencesUtils.saveSharedPreferences();
                view.loginSuccessed();
            }
        });
    }

    @Override
    public void getVerify(String account) {
        if (TextUtils.isEmpty(account)) {
            return;
        }
        new AccountModelIml().getVerify(account).subscribe(new MyObserve<LoginResult>(view) {
            @Override
            protected void onSuccess(LoginResult list) {
                view.showToast("验证码已发送");
            }
        });
    }

    @Override
    public void findPWD(String account, String verify, String pwd) {

    }

    @Override
    public void updatePWD(String user, String pass, String newpassone) {

    }

    @Override
    public void Eixt() {

    }

   /* @Override
    public void getVerify(String account) {

    }

    @Override
    public void findPWD(String account, String verify, String pwd) {
    }
    @Override
    public void updatePWD(String user,String pass, String newpassone){
        new AccountModelIml().getUpdate(user,pass,newpassone).subscribe(new MyObserve<CommonResponse>(view) {
            @Override
            protected void onSuccess(CommonResponse v) {
                view.loginSuccessed();
            }
        });
    }
    @Override
    public void Eixt() {
        new AccountModelIml().getExit().safeSubscribe(new MyObserve<CommonResponse>(view) {
            @Override
            protected void onSuccess(CommonResponse o) {
                view.loginSuccessed();
            }
        });
    }*/
    //    private StartModelimp mStartModelimp;
//    private CountDownTimer countDownTimer;
//
//    public AccountPresenter(WelcomeContract.WelcomeInterface view, int time) {
//        super(view);
//        mStartModelimp = new StartModelimp();
//        countDownTimer = new TimeCount(time, 1000);
//        countDownTimer.start();
//    }
//
//
//    @Override
//    public void getAdMessage() {
//        mStartModelimp.getLaucherMessage().subscribe(new MyObserve<LaucherEntity>(view) {
//            @Override
//            protected void onSuccess(LaucherEntity laucherEntity) {
//                if (laucherEntity.getAd() != null) {
//                    downloadAdPic(laucherEntity.getAd().getImage());
//                }
//                view.saveAD(laucherEntity);
//
//            }
//        });
//    }
//
//    //先进行缓存，然后下次加载出来
//    private void downloadAdPic(String url) {
//        GlideApp.with(YiXianTongApplication.getInstance()).download(url);
//    }
//
//    @Override
//    public boolean validateFirst(int version_code) {
//        int localVersion = Tools.getVersionCode();
//        // 判断当前版本号不一致则显示引导页
//        if (version_code != localVersion) {
//            Observable.timer(1, TimeUnit.SECONDS).subscribe(new MyObserve<Long>(view) {
//                @Override
//                protected void onSuccess(Long aLong) {
//                    view.toGuide();
//                }
//            });
//
////            new Handler().postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    view.toGuide();
////                }
////            }, 1000);
//            countDownTimer.cancel();
//        }
//        return version_code != localVersion;
//    }
//
//
//    class TimeCount extends CountDownTimer {
//
//        /**
//         * @param millisInFuture    The number of millis in the future from the call
//         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//         *                          is called.
//         * @param countDownInterval The interval along the way to receive
//         *                          {@link #onTick(long)} callbacks.
//         */
//        public TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        @Override
//        public void onFinish() {
//            if (view.isLogin()) {
//                view.toHome();
//            } else {
//                view.toLogin(null);
//            }
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//        }
//
//    }
//
//    @Override
//    public void unsubscribe() {
//        countDownTimer.cancel();
//        super.unsubscribe();
//    }
}
