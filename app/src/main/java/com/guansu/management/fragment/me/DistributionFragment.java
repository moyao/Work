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
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.config.Payment;
import com.guansu.management.paymentmoney.AuthResult;
import com.guansu.management.paymentmoney.PayResult;
import com.guansu.management.utils.OrderInfoUtil2_0;

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
            switch (msg.what) {
                case Payment.SDK_PAY_FLAG: {
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
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast(getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case Payment.SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
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

        ;
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
                if (TextUtils.isEmpty(Payment.APPID) || (TextUtils.isEmpty(Payment.RSA2_PRIVATE) && TextUtils.isEmpty(Payment.RSA_PRIVATE))) {
                    showToast(getString(R.string.error_missing_appid_rsa_private));
                    return;
                }
                /*
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * orderInfo 的获取必须来自服务端；
                 */
                boolean rsa2 = (Payment.RSA2_PRIVATE.length() > 0);
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Payment.APPID, rsa2);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                String privateKey = rsa2 ? Payment.RSA2_PRIVATE : Payment.RSA_PRIVATE;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;
                final Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                        PayTask alipay = new PayTask(getActivity());
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());
                        Message msg = new Message();
                        msg.what = Payment.SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
