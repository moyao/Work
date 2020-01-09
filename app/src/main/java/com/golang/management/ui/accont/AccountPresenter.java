package com.golang.management.ui.accont;//package com.wisdom.regulatory.ui.welcome;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseCommonPresenter;
import com.golang.management.bean.UserInfo;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constants;
import com.golang.management.helper.GenerateTestUserSig;
import com.golang.management.model.AccountModelIml;
import com.golang.management.model.bean.LoginResult;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
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
                String userSig = GenerateTestUserSig.genTestUserSig(account);
                // identifier 为用户名，userSig 为用户登录凭证
                TUIKit.login(account, userSig, new IUIKitCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(context);
                        userSharedPreferencesUtils.setAccount(account);
                        userSharedPreferencesUtils.setPwd(pwd);
                        userSharedPreferencesUtils.setUserid(userInfo.getUserId());
                        userSharedPreferencesUtils.setNickname(userInfo.getNickname());
                        userSharedPreferencesUtils.setProfileImageUrl(userInfo.getProfileImageUrl());
                        userSharedPreferencesUtils.setLevelName(userInfo.getUserLevel().getLevelName());
                        userSharedPreferencesUtils.saveSharedPreferences();
                        SharedPreferences shareInfo = context.getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shareInfo.edit();
                        editor.putBoolean(Constants.AUTO_LOGIN, true);
                        editor.commit();
                        view.loginSuccessed();
                    }
                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        view.showToast("login failed. code: " + errCode + " errmsg: " + errMsg);
                    }
                });
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
}
