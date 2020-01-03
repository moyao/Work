package com.guansu.management.base;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.guansu.management.R;
import com.guansu.management.api.APIException;
import com.guansu.management.api.ServiceException;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.ReplaceViewHelper;
import com.guansu.management.wigdet.CommonTitleBar;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by dongyaoayo
 */

public abstract class BaseFragment<T extends BasePresenter> extends SwipeBackFragment implements BaseInterface,
        CommonTitleBar.OnTitleBarListener {
    private BaseActivity mContext;

    protected CommonTitleBar mTitlebar;

    protected ReplaceViewHelper defaultReplaceViewHelper;
    public View mContentView;
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    private CompositeDisposable mCompositeDisposable;
    public T presenter;
    private View childView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setParallaxOffset(0.5f);
        mContext = getBaseActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_base, container, false);
        defaultReplaceViewHelper = new ReplaceViewHelper(getContext());
        setContentChildView(view);
        initTitleView(view);
        initView(view);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return canSwipeBack() ? attachToSwipeBack(view) : view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        bindEvent();
    }

    public void setContentChildView(View view) {
        LinearLayout llBaseLayout = view.findViewById(R.id.llBaseLayout);
        childView = LayoutInflater.from(mContext).inflate(onSetLayoutId(), llBaseLayout, true);
    }

    public void initTitleView(View view) {
        mTitlebar = view.findViewById(R.id.titlebar);
        mTitlebar.showStatusBar(false);
        mTitlebar.setListener(this);
    }

    public void setTitle(CharSequence title) {
        if (null != title) {
            mTitlebar.getCenterTextView().setText(title);
        }
    }

    public void removeTitleAndStatusBar() {
        mTitlebar.setVisibility(View.GONE);
    }

    public void hideTitle() {
        mTitlebar.getTitleView().setVisibility(View.GONE);
    }

    public void setLoadingContentView(View mContentView) {
        this.mContentView = mContentView;
        showLoadingPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mCompositeDisposable) {
            mCompositeDisposable.dispose();
        }
        //解绑 presenter
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }

    /**
     * 创建相应的 presenter
     */
    public void createPresenter(T presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }

    }


    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }

    /**
     * 初始化 API  不使用mvp需要初始化
     */
    public void initApi() {
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 设置布局文件
     *
     * @return 返回布局文件资源Id
     */
    public abstract int onSetLayoutId();

    public abstract void initView(View view);

    public abstract void bindEvent();

    public abstract boolean canSwipeBack();

    public void loginDialog(Activity activity) {
        Dialog dia = new Dialog(activity, R.style.BaseDialogStyle);
        dia.setContentView(R.layout.dialog_register);
        Button butLater = dia.findViewById(R.id.butLater);
        Button butRegister = dia.findViewById(R.id.butRegister);
        dia.setCanceledOnTouchOutside(false);
        dia.getWindow().setGravity(Gravity.CENTER);
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dia.onWindowAttributesChanged(lp);
        dia.show();
        butLater.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                LateronLogin();
                dia.dismiss();
            }
        });
        butRegister.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                immediatelyLogin();
                dia.dismiss();
            }
        });
    }

    protected void immediatelyLogin() {
    }

    protected void LateronLogin() {

    }
/*
    public <T> Subscriber newMySubscriber(final SimpleMyCallBack onNext) {
        return mContext.newMySubscriber(onNext);
    }*/

    public void showToast(String content) {
        mContext.showToast(content);
    }

    public void showLoadingDialog() {
        mContext.showLoadingDialog();
    }

    public void showLoadingDialog(String message) {
        mContext.showLoadingDialog(message);
    }

    public void hideLoadingDialog() {
        mContext.hideLoadingDialog();
    }

    public void addDisposable(Disposable d) {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.add(d);
        } else {
            presenter.addDisposable(d);
        }
    }

    public void toLogin(String message) {
        mContext.toLogin(message);
    }

    public void skipAct(Class clazz) {
        mContext.skipAct(clazz);
    }

    public void skipAct(Class clazz, Bundle bundle) {
        mContext.skipAct(clazz, bundle);
    }

    public void skipAct(Class clazz, Bundle bundle, int flags) {
        mContext.skipAct(clazz, bundle, flags);
    }

    @Override
    public boolean isLogin() {
        return mContext.isLogin();
    }


    public void showPage() {
        defaultReplaceViewHelper.removeView();
    }

    public void showError(Throwable e) {
        if (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketTimeoutException) {
            showNetError();
        } else if (e instanceof ServiceException || e instanceof APIException) {
            showServiceError();
        } else {
            showServiceError();
        }
    }

    @Override
    public void showServiceError() {
        View view;
        if (mContentView.getParent() instanceof ViewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_status_error, (ViewGroup) mContentView.getParent(), false);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_status_error, (ViewGroup) childView, false);
        }
        ImageView iv_status = view.findViewById(R.id.iv_status);
        iv_status.setImageResource(R.drawable.fragmentation_help);
        TextView tv_status = view.findViewById(R.id.tv_status);
        tv_status.setText("内容获取失败，请点击按钮重试");
        Button btn_retry = view.findViewById(R.id.btn_retry);
        btn_retry.setVisibility(View.VISIBLE);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryloading();
            }
        });
        defaultReplaceViewHelper.toReplaceView(mContentView, view);
    }

    public void showNoData() {
        View view;
        if (mContentView.getParent() instanceof ViewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_status_error, (ViewGroup) mContentView.getParent(), false);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_status_error, (ViewGroup) childView, false);
        }
        ImageView iv_status = view.findViewById(R.id.iv_status);
        iv_status.setImageResource(R.mipmap.icon_empty_data);
        TextView tv_status = view.findViewById(R.id.tv_status);
        tv_status.setText("这里什么也没有...");
        defaultReplaceViewHelper.toReplaceView(mContentView, view);
    }

    @Override
    public void showLoadingPage() {
        View view;
        if (mContentView.getParent() instanceof ViewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading, (ViewGroup) mContentView.getParent(), false);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading, (ViewGroup) childView, false);
        }
        defaultReplaceViewHelper.toReplaceView(mContentView, view);
    }

    @Override
    public void showNetError() {
        View view;
        if (mContentView.getParent() instanceof ViewGroup) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_status_error, (ViewGroup) mContentView.getParent(), false);
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_status_error, (ViewGroup) childView, false);
        }
        ImageView iv_status = view.findViewById(R.id.iv_status);
        iv_status.setImageResource(R.mipmap.icon_out_of_contact);
        TextView tv_status = view.findViewById(R.id.tv_status);
        tv_status.setText("您的网络失联了");
        Button btn_retry = view.findViewById(R.id.btn_retry);
        btn_retry.setVisibility(View.VISIBLE);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryloading();
            }
        });
        defaultReplaceViewHelper.toReplaceView(mContentView, view);
    }

    protected void retryloading() {

    }

    @Override
    public void onTitleClick(View v, int action, String extra) {
        if (action == CommonTitleBar.ACTION_LEFT_BUTTON
                || action == CommonTitleBar.ACTION_LEFT_TEXT) {
            _mActivity.onBackPressed();
        }
    }
}
