package com.guansu.management.ui.accont;//package com.wisdom.regulatory.ui.welcome;


import android.content.Context;

import com.guansu.management.base.BaseInterface;
import com.guansu.management.base.BasePresenter;

/**
 * Created by dongyaoyao
 */

public class AccountContract {
    public interface LoginInterface extends BaseInterface {
        void loginSuccessed();

        void resetPWDSuccessed();
    }

    interface Presenter extends BasePresenter {
        void login(Context context, String account, String pwd);

        void getVerify(String account);

        void findPWD(String account, String verify, String pwd);

        void updatePWD(String user, String pass, String newpassone);

        void Eixt();
    }

}
