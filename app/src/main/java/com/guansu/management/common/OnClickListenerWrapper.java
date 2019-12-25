package com.guansu.management.common;

import android.view.View;
/**
 *
 * Created by dongyaoyao
 */
public abstract class OnClickListenerWrapper implements View.OnClickListener {

    private static long lastClickTime;

    protected abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        if (isFastDuplicateClick()) {
            return;
        }
        onSingleClick(v);
    }

    /**
     * 是否为重复的快速点击
     *
     * @return
     */
    protected boolean isFastDuplicateClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            lastClickTime = time;
            return true;
        }
        return false;
    }
}
