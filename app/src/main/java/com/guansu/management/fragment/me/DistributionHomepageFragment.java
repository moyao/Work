package com.guansu.management.fragment.me;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.DistributiopnHomepageBean;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.config.HttpConstants;
import com.guansu.management.model.MeModellml;
import com.guansu.management.paymentmoney.AuthResult;
import com.guansu.management.wigdet.CommonTitleBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class DistributionHomepageFragment extends BaseFragment {
    ImageButton imageButton;
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
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
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
        OkGo.<String>get(HttpConstants.BASE_URL+MeModellml.USER_ALIPAYAUTH)
                .tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject=new JSONObject(response.body());
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        new MeModellml().query_Point_Account_Page(userSharedPreferencesUtils.getUserid()).
                safeSubscribe(new MyObserve<DistributiopnHomepageBean>(this) {
                    @Override
                    protected void onSuccess(DistributiopnHomepageBean distributiopnHomepageBean) {
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
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    private void MorePopShow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwind_distribution, null, false);
        TextView CashWithdrawal = (TextView) view.findViewById(R.id.CashWithdrawal);
        TextView BindingAlipay = (TextView) view.findViewById(R.id.BindingAlipay);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        PopupWindow popWindow = new PopupWindow(view,
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
                getData();
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
    class poponDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            setBackgroundAlpha(1f);
        }
    }
}
