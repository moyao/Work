package com.guansu.management.fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationManagerCompat;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.config.Constants;
import com.guansu.management.ui.accont.AccountContract;
import com.guansu.management.ui.accont.AccountPresenter;
import com.guansu.management.wigdet.utils.PhoneNumberValid;
import butterknife.BindView;

/**
 * Created by dongyaoyao
 */
public class LoginFragment extends BaseFragment<AccountPresenter> implements AccountContract.LoginInterface {
    @BindView(R.id.editTextAccount)
    AppCompatEditText editTextAccount;
    @BindView(R.id.editTextPWD)
    AppCompatEditText editTextPWD;
    @BindView(R.id.buttonCode)
    Button buttonCode;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    Button butLater;
    Button butRegister;
    @BindView(R.id.includeLogin)
    LinearLayout includeLogin;
    private TimeCount time;
    private Dialog dia,dialog;
    private Button butRelease;

    public static LoginFragment newInstance(String tage) {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        args.putString(Constants.KEY_TYPE, tage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        initApi();
        createPresenter(new AccountPresenter(this));
        hideTitle();
        if (!NotificationManagerCompat.from(getContext()).areNotificationsEnabled()) {
            toOpenNotification();
        }
        time = new TimeCount(60000, 1000);
        initDialog();
        initDia();

    }



    private void toOpenNotification() {
        new AlertDialog.Builder(getContext()).setMessage("您可能会错过我们的消息，请允许接受通知栏消息").setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                open(getContext());
            }
        }).setNegativeButton("残忍拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }
    public void open(Context context) {

        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if (appIntent != null) {
            context.startActivity(appIntent);
            return;
        }
        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
        //      点击权限隐私>自启动管理>我的app
        appIntent = context.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        if (appIntent != null) {
            context.startActivity(appIntent);
            return;
        }

        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }
    private void initDialog() {
        dialog = new Dialog(getContext(), R.style.BaseDialogStyle);
        dialog.setContentView(R.layout.dialog_loginsuccess);
        butRelease=dialog.findViewById(R.id.butRelease);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(lp);
    }
    private void initDia() {
        dia = new Dialog(getContext(), R.style.BaseDialogStyle);
        dia.setContentView(R.layout.dialog_register);
        butLater = dia.findViewById(R.id.butLater);
        butRegister = dia.findViewById(R.id.butRegister);
        dia.setCanceledOnTouchOutside(false);
        dia.getWindow().setGravity(Gravity.CENTER);
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dia.onWindowAttributesChanged(lp);
    }
    @Override
    public void bindEvent() {
        if ("1".equals(getArguments().getString(Constants.KEY_TYPE))){
            includeLogin.setVisibility(View.VISIBLE);
        }else {
            includeLogin.setVisibility(View.GONE);
            dia.show();
        }
        //验证码
        buttonCode.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                time.start();
                presenter.getVerify(editTextAccount.getText().toString());
            }
        });

        btnLogin.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (!PhoneNumberValid.isMobileNO(editTextAccount.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextPWD.getText().toString().length() < 6) {
                    Toast.makeText(getContext(), "请输入正确验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    presenter.login(getContext(),editTextAccount.getText().toString(),editTextPWD.getText().toString());
                }
            }
        });
        butLater.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                dia.dismiss();
                startWithPop(MainFragment.newInstance());
            }
        });
        butRegister.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                dia.dismiss();
                includeLogin.setVisibility(View.VISIBLE);
            }
        });
        butRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                dialog.dismiss();
                startWithPop(MainFragment.newInstance());
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            buttonCode.setBackgroundColor(Color.parseColor("#B6B6D8"));
            buttonCode.setClickable(false);
            buttonCode.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }
        @Override
        public void onFinish() {
            buttonCode.setText("重新获取验证码");
            buttonCode.setClickable(true);
            buttonCode.setBackgroundColor(Color.parseColor("#4EB84A"));
        }
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }

    @Override
    public void loginSuccessed() {
        startWithPop(MainFragment.newInstance());
    }
    @Override
    public void resetPWDSuccessed() {

    }
}
