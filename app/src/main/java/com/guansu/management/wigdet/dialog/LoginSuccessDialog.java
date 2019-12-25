package com.guansu.management.wigdet.dialog;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.guansu.management.R;

public final class LoginSuccessDialog {


    public static final class Builder extends BaseDialogFragment.Builder<Builder> {


        public Builder(FragmentActivity activity) {
            super(activity);
            setAnimStyle(BaseDialog.AnimStyle.SCALE);
            setWidth(WRAP_CONTENT);
            setContentView(R.layout.dialog_paysuccess);
            //  setAnimStyle(BaseDialog.AnimStyle.TOAST);
            setGravity(Gravity.CENTER);
            setCancelable(true);
            ((TextView) findViewById(R.id.tv_cofirm)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
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