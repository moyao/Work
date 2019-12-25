package com.guansu.management.wigdet.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * @author: dongyaoyao
 */
public class TimeCount extends CountDownTimer {
    private Button btnGetcode;

    public TimeCount(long millisInFuture, long countDownInterval, Button btnGetcode) {
        super(millisInFuture, countDownInterval);
        btnGetcode = this.btnGetcode;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btnGetcode.setBackgroundColor(Color.parseColor("#B6B6D8"));
        btnGetcode.setClickable(false);
        btnGetcode.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
    }

    @Override
    public void onFinish() {
        btnGetcode.setText("重新获取验证码");
        btnGetcode.setClickable(true);
        btnGetcode.setBackgroundColor(Color.parseColor("#4EB84A"));

    }
}
