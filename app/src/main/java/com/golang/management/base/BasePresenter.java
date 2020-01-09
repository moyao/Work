package com.golang.management.base;

import io.reactivex.disposables.Disposable;

/**
 *
 * Created by dongyaoyao
 */

public interface BasePresenter {
    void addDisposable(Disposable d);

    void unsubscribe();

}