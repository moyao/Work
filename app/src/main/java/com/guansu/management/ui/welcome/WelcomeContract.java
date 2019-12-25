package com.guansu.management.ui.welcome;


import com.guansu.management.base.BaseInterface;
import com.guansu.management.base.BasePresenter;

/**
 *
 * Created by dongyaoyao
 */
public class WelcomeContract {
    interface WelcomeInterface extends BaseInterface {
        void toHome();
        void toGuide();

    }
    interface Presenter extends BasePresenter {
         /**判断首次打开
          * @param version_code*/
         boolean validateFirst(int version_code);
    }

}
