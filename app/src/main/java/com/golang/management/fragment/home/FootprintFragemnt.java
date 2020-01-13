package com.golang.management.fragment.home;

import android.app.Dialog;
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
import com.golang.management.fragment.foot.FootAdapter;
import com.golang.management.api.MyObserve;
import com.golang.management.model.FootModellml;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

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

    public static FootprintFragemnt newInstance() {
        Bundle args = new Bundle();
        FootprintFragemnt fragment = new FootprintFragemnt();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void locationResult(String longitude, String latitude, String address,
                                  String city, String province, String district) {
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
                bindEvent();
                dialog.dismiss();
            }
        });
    }

    private void initDialogData(String address) {
        showLoadingDialog("上传中……");
        new FootModellml().getPostVerify(userSharedPreferencesUtils.getUserid(),
                mTextViewTitle.getText().toString() + "," + address, longitude, latitude)
                .safeSubscribe(new MyObserve(this) {
                    @Override
                    protected void onSuccess(Object o) {
                        showPage();
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
                dialog.show();
            }
        });

        showLoadingDialog("加载中。。。。");
        new FootModellml().getVerify(userSharedPreferencesUtils.getUserid())
                .safeSubscribe(new MyObserve<List<FootBean>>(this) {
                    @Override
                    protected void onSuccess(List<FootBean> footBean) {
                        showPage();
                        if (null != footBean && footBean.size() > 0) {
                            view1.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            BgimageView.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            footAdapter = new FootAdapter(getContext(), footBean);
                            recyclerFoot.setAdapter(footAdapter);
                        } else {
                            view1.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            BgimageView.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                        }
                    }
                });
        view1.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                Log.e("view","点击数据");
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
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
