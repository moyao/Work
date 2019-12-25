package com.guansu.management.base;

import io.reactivex.disposables.Disposable;

/**
 *
 * Created by dongyaoyao
 */

public interface BaseInterface {

    void showToast(String content);

    void hideLoadingDialog();

    void showLoadingDialog();

    void showLoadingDialog(String message);

    void showLoadingPage();

    void showPage();

    void showError(Throwable e);

    void showServiceError();

    void showNoData();

    void showNetError();

    void addDisposable(Disposable d);

    //401 重新鉴权
    void toLogin(String message);

    boolean isLogin();
}
