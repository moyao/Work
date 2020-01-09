package com.golang.management.wigdet.banner.holder;

import android.content.Context;
import android.view.View;
/**
 *
 * Created by dongyaoyao
 */
public interface Holder<T>{
    View createView(Context context);
    void UpdateUI(Context context, int position, T data);
}