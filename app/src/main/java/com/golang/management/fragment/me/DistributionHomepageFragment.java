package com.golang.management.fragment.me;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.golang.management.R;
import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.DistributiopnHomepageBean;
import com.golang.management.bean.PayResultBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.payment.PaymentSuccessFragment;
import com.golang.management.model.MeModellml;
import com.golang.management.paymentmoney.AuthResult;
import com.golang.management.paymentmoney.PayResult;
import com.golang.management.utils.MessageEvent;
import com.golang.management.utils.StringHandler;
import com.golang.management.wigdet.CommonTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class DistributionHomepageFragment extends BaseFragment {
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @BindView(R.id.textViewGrade)
    TextView textViewGrade;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewTotal)
    TextView textViewTotal;
    @BindView(R.id.textViewCash)
    TextView textViewCash;
    @BindView(R.id.tvSettlement)
    TextView tvSettlement;
    @BindView(R.id.textViewSettlement)
    TextView textViewSettlement;
    @BindView(R.id.imageButtonQRCode)
    View imageButtonQRCode;
    @BindView(R.id.imageButtonCustomer)
    View imageButtonCustomer;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String Operator;
    PopupWindow popWindow;
    private Dialog ExemptionDialog;
    private CheckBox checkbox;
    private Button butDetermine, butCancel;
    private WebView webView;
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        UserSharedPreferencesUtils userSharedPreferencesUtil = new UserSharedPreferencesUtils(getContext());
                        userSharedPreferencesUtil.setLevelName("运营商");
                        userSharedPreferencesUtil.saveSharedPreferences();
                        EventBus.getDefault().post(new MessageEvent("发生改变", 2));
                        popWindow.dismiss();
                        Gson gson = new Gson();
                        PayResultBean user = gson.fromJson(resultInfo, PayResultBean.class);
                        startWithPop(PaymentSuccessFragment.newInstance(user.getAlipay_trade_app_pay_response().getOut_trade_no()));
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast(getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showToast(getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showToast(getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public static DistributionHomepageFragment newInstance() {
        Bundle args = new Bundle();
        DistributionHomepageFragment fragment = new DistributionHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_my_distribution;
    }

    @Override
    public void initView(View view) {
        initApi();
        setTitle("我的分销");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mTitlebar.setRightType(CommonTitleBar.TYPE_IMAGEBUTTON_SEARCHVIEW);
        mTitlebar.getRightTextView().setText("...");
        mTitlebar.getRightTextView().setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                MorePopShow();
            }
        });
    }

    private void getData() {
        OkGo.<String>get(HttpConstants.BASE_URL + MeModellml.USER_ALIPAYAUTH)
                .tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body());
                    if ("404".equals(jsonObject.getString("status"))) {
                        showToast(jsonObject.getString("status"));
                    } else {
                        if ("0000000".equals(jsonObject.getString("code"))) {
                            String data = jsonObject.getString("data");
                            Runnable authRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    // 构造AuthTask 对象
                                    AuthTask authTask = new AuthTask(getActivity());
                                    // 调用授权接口，获取授权结果
                                    Map<String, String> result = authTask.authV2(data, true);
                                    Message msg = new Message();
                                    msg.what = SDK_AUTH_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread authThread = new Thread(authRunnable);
                            authThread.start();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                showToast(response.message());
            }
        });
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        showLoadingDialog("加载中。。。");
        new MeModellml().query_Point_Account_Page(userSharedPreferencesUtils.getUserid()).
                safeSubscribe(new MyObserve<DistributiopnHomepageBean>(this) {
                    @Override
                    protected void onSuccess(DistributiopnHomepageBean distributiopnHomepageBean) {
                        showPage();
                        Operator = distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getUserLevel().getLevelName();
                        Glide.with(getContext()).load(distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getProfileImageUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageViewPhoto);
                        textViewGrade.setText("等级：" + distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getUserLevel().getLevelName());
                        textViewName.setText("昵称：" + distributiopnHomepageBean.getContent().get(0).getUserInfoDto().getNickname());
                        textViewTotal.setText(distributiopnHomepageBean.getContent().get(0).getAccountBalance() + "");
                    }
                });
        imageButtonQRCode.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(QrCodeFragment.newInstance());
            }
        });
        imageButtonCustomer.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(MyDistributionFragment.newInstance());
            }
        });
      /*  HttpParams httpParams = new HttpParams();
        httpParams.put("userId", userSharedPreferencesUtils.getUserid());
        OkGo.<String>get(HttpConstants.BASE_URL + MeModellml.USER_ALIPAYAUTH)
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());
                            if ("0000000".equals(jsonObject.getString("code"))) {
                            } else {
                                showToast("请先绑定支付信息");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    private void MorePopShow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwind_distribution, null, false);
        TextView CashWithdrawal = view.findViewById(R.id.CashWithdrawal);
        TextView BindingAlipay = view.findViewById(R.id.BindingAlipay);
        TextView OperatorTextView = view.findViewById(R.id.OperatorTextView);
        if ("运营商".equals(Operator)) {
            OperatorTextView.setVisibility(View.GONE);
        }
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);
        setBackgroundAlpha(0.5f);//设置屏幕透明度
        //设置加载动画
        popWindow.setTouchable(true);
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(mTitlebar.getRightTextView(), 50, 0);
        CashWithdrawal.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(CashWithdrawalFragment.newInstance());
//                getData();
                popWindow.dismiss();
            }
        });
        BindingAlipay.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                getData();
            }
        });
        OperatorTextView.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                showDialogExemption();
                ExemptionDialog.show();
            }
        });
        popWindow.setOnDismissListener(new poponDismissListener());
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (getActivity()).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (getActivity()).getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(1f);
        }
    }

    private void showDialogExemption() {
        ExemptionDialog = new Dialog(getContext(), R.style.BaseDialogStyle);
        ExemptionDialog.setContentView(R.layout.dialog_login_exemption);
        checkbox = ExemptionDialog.findViewById(R.id.checkbox);
        webView = ExemptionDialog.findViewById(R.id.webView);
        butCancel = ExemptionDialog.findViewById(R.id.butCancel);
        butDetermine = ExemptionDialog.findViewById(R.id.butDetermine);
        checkbox.setChecked(false);
        ExemptionDialog.setCanceledOnTouchOutside(false);
        ExemptionDialog.getWindow().setGravity(Gravity.CENTER);
        Window w = ExemptionDialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        ExemptionDialog.onWindowAttributesChanged(lp);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.loadUrl("http://www.golangkeji.com/Golang/page7.html");
        butCancel.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ExemptionDialog.dismiss();
            }
        });
        butDetermine.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (checkbox.isChecked()) {
                    ExemptionData();
                    ExemptionDialog.dismiss();
                } else {
                    showToast("同意遵守本声明，以后每次默认都同意");
                }
            }
        });
    }

    private void ExemptionData() {
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("body", "MERCHANT");
        httpParams.put("amount", "19999");
        httpParams.put("userId", userSharedPreferencesUtils.getUserid());
        JSONObject jsonObject = new JSONObject(httpParams);
        OkGo.<String>post(HttpConstants.BASE_URL + MeModellml.USER_GENERATEORDERINFO)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body());

                            if ("0000000".equals(jsonObject.getString("code"))) {
                                String data = jsonObject.getString("data");
                                final Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                                        PayTask alipay = new PayTask(getActivity());
                                        Map<String, String> result = alipay.payV2(data, true);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                // 必须异步调用
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else {
                                showToast(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                showToast(jsonObject.getString("message"));
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("服务器异常，请稍后重试！！！！");
                    }
                });
    }
}
