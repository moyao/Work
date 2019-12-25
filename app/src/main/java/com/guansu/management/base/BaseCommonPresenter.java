package com.guansu.management.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * Created by dongyaoyao
 */

public class BaseCommonPresenter<T extends BaseInterface> {
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeDisposable mCompositedDisposable;

    public T view;

    public BaseCommonPresenter(T view) {
        //创建 CompositeSubscription 对象 使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
        mCompositedDisposable = new CompositeDisposable();
        // 构建 ApiWrapper 对象
        this.view = view;
    }

    public void addDisposable(Disposable d) {
        if (mCompositedDisposable != null) {
            mCompositedDisposable.add(d);
        }
    }

    /**
     * 解绑 CompositeSubscription
     */
    public void unsubscribe() {
        if (mCompositedDisposable != null) {
            mCompositedDisposable.dispose();
        }
        view = null;
    }

}
