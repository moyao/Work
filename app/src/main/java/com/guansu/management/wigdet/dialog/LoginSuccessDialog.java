package com.guansu.management.wigdet.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.guansu.management.R;
import com.guansu.management.base.BaseActivity;
import com.guansu.management.bean.updateTextEvent;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;

public final class LoginSuccessDialog {

    public static final class Builder extends BaseDialogFragment.Builder<Builder> {
        private Button mButLater;
        private Button mButRegister;

        public Builder(FragmentActivity activity) {
            super(activity);
            setAnimStyle(BaseDialog.AnimStyle.SCALE);
            setWidth(MATCH_PARENT);
            setContentView(R.layout.dialog_register);
            mButLater =  findViewById(R.id.butLater);
            mButRegister =  findViewById(R.id.butRegister);
            setGravity(Gravity.CENTER);
            setCancelable(true);
            mButLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            mButRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public BaseDialog create() {
            // 如果内容为空就设置隐藏
            return super.create();
        }
    }

}