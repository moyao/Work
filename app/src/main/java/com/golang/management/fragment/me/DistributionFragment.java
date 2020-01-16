package com.golang.management.fragment.me;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.PayResultBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.HttpConstants;
import com.golang.management.config.Payment;
import com.golang.management.fragment.payment.PaymentSuccessFragment;
import com.golang.management.model.MeModellml;
import com.golang.management.paymentmoney.PayResult;
import com.golang.management.utils.MessageEvent;
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
public class DistributionFragment extends BaseFragment {
    @BindView(R.id.imageViewTitle)
    ImageView imageViewTitle;
    @BindView(R.id.imageViewGolden)
    TextView imageViewGolden;
    @BindView(R.id.imageViewOperator)
    TextView imageViewOperator;
    @BindView(R.id.imageViewJoin)
    ImageView imageViewJoin;
    @BindView(R.id.checkGolden)
    CheckBox checkGolden;
    @BindView(R.id.checkOperator)
    CheckBox checkOperator;
    @BindView(R.id.textViewContext)
    TextView textViewContext;
    @BindView(R.id.textViewActivity)
    TextView textViewActivity;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    String LevelName;
    private Dialog ExemptionDialog;
    private CheckBox checkbox;
    private Button butDetermine, butCancel;
    private WebView webView;
    String body, amount;
    public static DistributionFragment newInstance() {
        Bundle args = new Bundle();
        DistributionFragment fragment = new DistributionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
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
                userSharedPreferencesUtil.setLevelName(LevelName);
                userSharedPreferencesUtil.saveSharedPreferences();
                EventBus.getDefault().post(new MessageEvent("发生改变",2));
                Gson gson = new Gson();
                PayResultBean user = gson.fromJson(resultInfo, PayResultBean.class);
                startWithPop(PaymentSuccessFragment.newInstance(user.getAlipay_trade_app_pay_response().getOut_trade_no()));
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showToast(getString(R.string.pay_failed) + payResult);
            }
        }
    };

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_distribution;
    }

    @Override
    public void initView(View view) {
        mTitlebar.showStatusBar(true);
        initApi();
        mTitlebar.setBackgroundResource(R.color.white);
        setTitle("会员充值");

    }
    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        checkGolden.isChecked();
        checkOperator.setChecked(false);

        imageViewGolden.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                checkData();

            }
        });
        imageViewOperator.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                checkData();
            }
        });
        checkGolden.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                checkData();
            }
        });
        checkOperator.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                checkData();
            }
        });
        imageViewJoin.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (checkGolden.isChecked()) {
                    body = "GOLD_MEMBER";
                    amount = "399";
                    LevelName="金卡会员";

                } else {
                    body = "MERCHANT";
                    amount = "19999";
                    LevelName="运营商";
                }
                showDialogExemption();
                ExemptionDialog.show();
            }
        });
    }
    private void checkData() {
        if (checkGolden.isChecked()){
            checkOperator.setChecked(true);
            checkGolden.setChecked(false);
        }else if (checkOperator.isChecked()){
            checkGolden.setChecked(true);
            checkOperator.setChecked(false);
        }

    }

    private void getData() {
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("body", body);
        httpParams.put("amount", amount);
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
                                        msg.what = Payment.SDK_PAY_FLAG;
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
    @Override
    public boolean canSwipeBack() {
        return false;
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
        if (checkGolden.isChecked()){
            webView.loadUrl("http://www.golangkeji.com/Golang/page6.html");
        }else {
            webView.loadUrl("http://www.golangkeji.com/Golang/page7.html");
        }
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
                    getData();
                    ExemptionDialog.dismiss();
                } else {
                    showToast("同意遵守本声明，以后每次默认都同意");
                }
            }
        });
    }
}
