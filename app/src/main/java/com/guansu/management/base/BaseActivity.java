package com.guansu.management.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.guansu.management.R;
import com.guansu.management.common.ActivityPageManager;
import com.guansu.management.common.ReplaceViewHelper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.fragment.LoginFragment;
import com.guansu.management.wigdet.CommonTitleBar;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportActivity;

/**
 *
 * Created by dongyaoyao
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity
        implements View.OnClickListener, BaseInterface {
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeDisposable mCompositeDisposable;
    /**
     * 子类页面布局的 根view
     */
    protected T presenter;

    private ProgressDialog dialog;

    public View mContentView;

    private ReplaceViewHelper defaultReplaceViewHelper;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onSetLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //Activity管理
        ActivityPageManager.getInstance().addActivity(this);
        init();
    }

    public abstract int onSetLayoutId();

    /**
     * 初始化页面
     */
    public void init() {
        defaultReplaceViewHelper = new ReplaceViewHelper(this);
        initView();
        bindEvent();
    }

    /**
     * 初始化view
     */
    public abstract void initView();


    /**
     * 绑定事件
     */
    public abstract void bindEvent();


    /**
     * 创建相应的 presenter
     */
    public void createPresenter(T presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    public void setLoadingContentView(View mContentView) {
        this.mContentView = mContentView;
    }

    /**
     * 显示一个Toast信息
     */
    public void showToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public void showLoadingDialog(String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (dialog.isShowing()) return;
        dialog.setMessage(message);
        dialog.show();
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog("网络正在连接");
    }

    public void hideLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showLoadingPage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
        defaultReplaceViewHelper.toReplaceView(mContentView, view);
    }

    public void showPage() {
        defaultReplaceViewHelper.removeView();
    }

    public void showNoData() {
    }

    @Override
    public void showError(Throwable e) {
    }

    @Override
    public void showServiceError() {
    }

    @Override
    public void showNetError() {
    }


    protected void retryloading() {

    }


    public void addDisposable(Disposable d) {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.add(d);
        } else {
            presenter.addDisposable(d);
        }

    }

    public void toLogin(String message) {
        new UserSharedPreferencesUtils(this).clearSharedPreferences();
        start(LoginFragment.newInstance("0"));
    }

    /**
     * 跳转页面
     *
     * @param clazz
     */
    public void skipAct(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("fromWhere", getClass().getSimpleName());
        startActivity(intent);
    }

    public void skipAct(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.putExtra("fromWhere", getClass().getSimpleName());
        startActivity(intent);
    }

    public void skipAct(Class clazz, Bundle bundle, int flags) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.putExtra("fromWhere", getClass().getSimpleName());
        intent.setFlags(flags);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityPageManager.getInstance().removeActivity(this);
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        //解绑 presenter
        if (presenter != null) {
            presenter.unsubscribe();
            presenter = null;
        }
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(new UserSharedPreferencesUtils(this).getUserid());
    }
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 在指定的时间执行
     */
    public final boolean postAtTime(Runnable r, long uptimeMillis) {
        return HANDLER.postAtTime(r, this, uptimeMillis);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}