package com.guansu.management.fragment.me;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.guansu.management.R;
import com.guansu.management.api.ApiWrapper;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.EditBean;
import com.guansu.management.bean.FileBean;
import com.guansu.management.bean.PaymentBean;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.config.HttpConstants;
import com.guansu.management.config.Payment;
import com.guansu.management.fragment.payment.PaymentSuccessFragment;
import com.guansu.management.model.HomeModellml;
import com.guansu.management.model.MeModellml;
import com.guansu.management.paymentmoney.AuthResult;
import com.guansu.management.paymentmoney.PayResult;
import com.guansu.management.utils.OrderInfoUtil2_0;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;

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
                showToast(getString(R.string.pay_success) + payResult);
                start(PaymentSuccessFragment.newInstance());
                getActivity().onBackPressed();//销毁自己
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
        setTitle("分销");
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        checkGolden.isChecked();
        checkOperator.setChecked(false);
        checkOperator.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                checkOperator.isChecked();
                checkGolden.setChecked(false);
            }
        });
        checkGolden.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                checkGolden.isChecked();
                checkOperator.setChecked(false);
            }
        });
        imageViewJoin.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        String body,amount;
        if (checkGolden.isChecked()){
            body="金卡会员";
            amount="399";
        }else {
            body="运营商";
            amount="19999";
        }
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
                            }else {
                                showToast(jsonObject.getString("code"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
