package com.guansu.management.api;

import com.guansu.management.base.BaseInterface;
import com.guansu.management.config.HttpConstants;
import com.lzy.okgo.exception.HttpException;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by dongyaoyao on 2017/8/8.
 *
 */

public abstract class MyObserve<T> implements Observer<T> {
    private WeakReference<BaseInterface> baseInterface;

    public MyObserve(BaseInterface baseInterface) {
        this.baseInterface = new WeakReference<BaseInterface>(baseInterface);
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (null != baseInterface.get())
            baseInterface.get().addDisposable(d);
    }

    @Override
    public void onNext(T t) {
        if (null != baseInterface.get())
            baseInterface.get().hideLoadingDialog();
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (null != baseInterface.get())
            baseInterface.get().hideLoadingDialog();
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            baseInterface.get().showToast("无法连接到网络");
        } else if (e instanceof SocketTimeoutException) {
            baseInterface.get().showToast("网络连接超时");
        } else if (e instanceof NullPointerException) {
            baseInterface.get().showToast("服务器异常，请稍后再试");
        } else if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            if (serviceException.code.equals(HttpConstants.SESSION_TIMEOUT)) {
                baseInterface.get().toLogin(serviceException.message);
            }else if(serviceException.code.equals(HttpConstants.ERROR_SYSTEM)){
                baseInterface.get().showToast(serviceException.message);
            }else{
                baseInterface.get().showToast("服务器异常，请稍后再试");
            }
        } else if (e instanceof APIException) {
            baseInterface.get().showToast(e.getMessage());
        } else if (e instanceof HttpException) {
            // 其他各种http错误
        }

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);

}
