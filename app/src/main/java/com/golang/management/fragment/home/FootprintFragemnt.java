package com.golang.management.fragment.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golang.management.R;
import com.golang.management.activity.CheckPermissionsActivity;
import com.golang.management.bean.FootBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.foot.FootAdapter;
import com.golang.management.api.MyObserve;
import com.golang.management.model.FootModellml;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class FootprintFragemnt extends CheckPermissionsActivity {
    @BindView(R.id.view)
    View view;
    @BindView(R.id.recyclerFoot)
    RecyclerView recyclerFoot;
    @BindView(R.id.textViewRelease)
    TextView textViewRelease;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.imageView)
    ImageView BgimageView;
    @BindView(R.id.textViewGps)
    TextView textViewGps;
    private FootAdapter footAdapter;
    private Dialog dialog;
    private ImageView mImageView;
    private TextView mTextViewTitle;
    private TextView mTextViewContext;
    private Button mButCancel;
    private Button mButDetermine;
    String longitude, latitude, city, province, district;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    List<FootBean> footBeanList;

    public static FootprintFragemnt newInstance() {
        Bundle args = new Bundle();
        FootprintFragemnt fragment = new FootprintFragemnt();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void locationResult(String longitude, String latitude, String address,
                                  String city, String province, String district, String poiName) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.province = province;
        this.city = city;
        this.district = district;
        mTextViewTitle.setText(province + "," + city);
        if (StringUtils.isEmpty(address)) {
            textViewGps.setText("定位失败，请点击重试");
        } else {
            textViewGps.setText(city);
        }
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_footprint;
    }

    @Override
    public void initView(View view) {
        hideTitle();
        initApi();
        initDialog();

    }

    private void initDialog() {
        dialog = new Dialog(getContext(), R.style.BaseDialogStyle);
        dialog.setContentView(R.layout.dialog_foot_success);
        mImageView = dialog.findViewById(R.id.imageView);
        mTextViewTitle = dialog.findViewById(R.id.textViewTitle);
        mTextViewContext = dialog.findViewById(R.id.textViewContext);
        mButCancel = dialog.findViewById(R.id.butCancel);
        mButDetermine = dialog.findViewById(R.id.butDetermine);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(lp);
        mButCancel.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                dialog.dismiss();
            }
        });
        mButDetermine.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                initDialogData(mTextViewContext.getText().toString());
                dialog.dismiss();
            }
        });
    }

    private void initDialogData(String address) {
        HashMap<String, Object> httpParams = new HashMap<>();
        httpParams.put("userId", userSharedPreferencesUtils.getUserid());
        httpParams.put("address", mTextViewTitle.getText().toString() + "," + address);
        httpParams.put("longitude", longitude);
        httpParams.put("latitude", latitude);
        JSONObject jsonObject = new JSONObject(httpParams);
        OkGo.<String>post(HttpConstants.BASE_URL + FootModellml.URL_TRACE)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        FootBean footBean = new FootBean();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        footBean.setAddress(mTextViewTitle.getText().toString() + "," + address);
                        footBean.setUserId(Long.parseLong(userSharedPreferencesUtils.getUserid()));
                        footBean.setCreatedAt(formatter.format(curDate));
                        footBean.setLatitude(Double.parseDouble(latitude));
                        footBean.setLongitude(Double.parseDouble(longitude));
                        if (footBeanList.size()==0){
                            footAdapter = new FootAdapter(getContext(), footBeanList);
                            recyclerFoot.setAdapter(footAdapter);
                            footAdapter.addData(footBean, 0);
                            view1.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            BgimageView.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                        }else {
                            footAdapter.addData(footBean, footBeanList.size());
                        }
                    }
                });
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerFoot.setLayoutManager(layoutManager);
        textViewRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                startLocation();
                dialog.show();
            }
        });
        view1.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                Log.e("view", "点击数据");
                startLocation();
                dialog.show();
            }
        });
        textViewGps.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                startLocation();
                textViewGps.setText("定位中……");
            }
        });
        initFootData();
    }

    private void initFootData() {
        showLoadingDialog("加载中。。。。");
        new FootModellml().getVerify(userSharedPreferencesUtils.getUserid())
                .safeSubscribe(new MyObserve<List<FootBean>>(this) {
                    @Override
                    protected void onSuccess(List<FootBean> footBean) {
                        showPage();
                        footBeanList = footBean;
                        if (null != footBeanList && footBeanList.size() > 0) {
                            view1.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            BgimageView.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            footAdapter = new FootAdapter(getContext(), footBeanList);
                            recyclerFoot.setAdapter(footAdapter);
                        } else {
                            view1.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            BgimageView.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
