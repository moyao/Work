package com.golang.management.fragment.release;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.golang.management.R;
import com.golang.management.activity.CheckPermissionsActivity;
import com.golang.management.api.MyObserve;
import com.golang.management.bean.FileBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constants;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.release.data.CalendarList;
import com.golang.management.model.HomeModellml;
import com.golang.management.model.ReleaseModellml;
import com.golang.management.wigdet.datepicker.PickValueView;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class DetailsNextFragment extends CheckPermissionsActivity implements PickValueView.onSelectedChangeListener {
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.imageBlack)
    ImageButton imageBlack;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.tvPhoto)
    TextView tvPhoto;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.textViewAddress)
    TextView textViewAddress;
    @BindView(R.id.textViewSetAddress)
    EditText textViewSetAddress;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.textViewSo)
    TextView textViewSo;
    @BindView(R.id.checkBoxSo)
    CheckBox checkBoxSo;
    @BindView(R.id.tvLabel)
    TextView tvLabel;
    @BindView(R.id.editTextLabel)
    EditText editTextLabel;
    @BindView(R.id.butAddition)
    Button butAddition;
    @BindView(R.id.gridLayoutLevel)
    GridLayout gridLayoutLevel;
    @BindView(R.id.butRelease)
    Button butRelease;
    @BindView(R.id.textViewStartEndTime)
    TextView textViewStartEndTime;
    @BindView(R.id.textViewNumber)
    TextView textViewNumber;
    @BindView(R.id.tvSetAddress)
    TextView tvSetAddress;
    private Dialog dialog, ExemptionDialog;
    private CalendarList calendarList;
    private String selectedStr;
    String userId, visible="0", startTime, endTime;
    List<String> tage = new ArrayList<>();
    private String lat, lng, province, city, district;
    private CheckBox checkbox;
    private Button butDetermine, butCancel;
    private WebView webView;

    public static DetailsNextFragment newInstance(ArrayList<ImageItem> selImageList, String context) {
        Bundle args = new Bundle();
        DetailsNextFragment fragment = new DetailsNextFragment();
        args.putString(Constants.KEY_TITLE, context);
        args.putParcelableArrayList(Constants.KEY_URL, selImageList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void locationResult(String longitude, String latitude, String address
            , String city, String province, String district) {
        this.lng = longitude;
        this.lat = latitude;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_details;
    }

    @Override
    public void initView(View view) {
        hideTitle();
        initApi();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mTitlebar.getLeftImageButton().setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "0");
                setFragmentResult(RESULT_OK, bundle);
            }
        });
        showDialogTwo();
        showDialogExemption();
        startLocation();
    }

    @Override
    public void bindEvent() {
        UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        userId = userSharedPreferencesUtils.getUserid();
        checkBoxSo.setChecked(false);
        //将adapter 添加到spinner中
        textViewAddress.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                selectAddress();
            }
        });
        tvTime.setOnClickListener(new OnClickListenerWrapper() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void onSingleClick(View v) {
                dialog.show();
            }
        });
        butAddition.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if ("".equals(editTextLabel.getText().toString())) {
                    showToast("请输入您要添加的标签");
                    return;
                } else {
                    if (tage.size() < 3) {
                        layoutFilterItem(gridLayoutLevel, editTextLabel.getText().toString());
                        tage.add(editTextLabel.getText().toString());

                        editTextLabel.setText("");
                    } else {
                        showToast("不能超过最大标签数");
                    }
                }
            }
        });
        calendarList.setOnDateSelected(new CalendarList.OnDateSelected() {
            @Override
            public void selected(String startDate, String endDate) {
                startTime = startDate;
                endTime = endDate;
                textViewStartEndTime.setText(startDate + "-" + endDate);
                dialog.dismiss();
            }
        });
        imageBlack.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                getActivity().onBackPressed();//销毁自己
            }
        });

        checkBoxSo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    textViewSo.setText(buttonView.getText() + "可见");
                } else {
                    textViewSo.setText(buttonView.getText() + "隐藏");
                }
            }
        });
        butRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if ("".equals(textViewStartEndTime.getText().toString())) {
                    showToast("请选择活动的开始/结束时间");
                    return;
                }
                if ("".equals(textViewSetAddress.getText().toString())) {
                    showToast("请填写活动的详细地址");
                    return;
                }
                if ("".equals(textViewNumber.getText().toString())) {
                    showToast("请选择参加活动的人数");
                    return;
                }
                if ("".equals(tage.toString())) {
                    showToast("请增加活动标签");
                    return;
                }
                UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
                if ("true".equals(userSharedPreferencesUtils.getDetails())){
                    getRelease();
                }else {
                    ExemptionDialog.show();
                }

            }
        });
    }

    private void getRelease() {
        showLoadingDialog("上传中……");
        ArrayList<ImageItem> imageItems = getArguments().getParcelableArrayList(Constants.KEY_URL);
        //圈子
        final List<File> list = new ArrayList();
        for (ImageItem imageItem : imageItems) {
            if (!imageItem.path.startsWith("http"))
                list.add(new File(imageItem.path));
        }
        HttpParams params = new HttpParams();
        params.put("uid", userId);
        OkGo.<String>post(HttpConstants.BASE_URL + HomeModellml.IMAGEUPLOADLIST)
                .tag(this)
                .isMultipart(true)
                .params(params)
                .addFileParams("file", list)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Gson gson = new Gson();
                        FileBean user = gson.fromJson(body, FileBean.class);
                        getDataRelease(user.getData().getImageList());
                    }
                });
    }


    private void getDataRelease(List<FileBean.DataBean.ImageListBean> imageList) {
        Gson gson = new Gson();
        String s = gson.toJson(imageList);
        if (checkBoxSo.isChecked()) {
            visible = "1";
        } else {
            visible = "0";
        }
        try {
            JSONArray jsonObject = new JSONArray(s);
            new ReleaseModellml().user_activity_infosave(userId, getArguments().getString(Constants.KEY_TITLE),
                    startTime, endTime
                    , textViewAddress.getText().toString(), textViewSetAddress.getText().toString(),
                    textViewNumber.getText().toString(), visible, tage.toString(), lat, lng, jsonObject)
                    .subscribe(new MyObserve<String>(this) {
                        @Override
                        protected void onSuccess(String activityDtoInfo) {
                            Bundle bundle = new Bundle();
                            bundle.putString("title", "1");
                            setFragmentResult(RESULT_OK, bundle);
                            getActivity().onBackPressed();//销毁自己
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void layoutFilterItem(GridLayout gridLayoutLevel, String toString) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_laber, gridLayoutLevel, false);
        TextView mCheckBoxFilter = view.findViewById(R.id.textViewLaber);
        mCheckBoxFilter.setText(toString);
        gridLayoutLevel.addView(view);
    }

    private void showDialogTwo() {
        dialog = new Dialog(getContext(), R.style.BaseDialogStyle);
        dialog.setContentView(R.layout.dialog_date);
        calendarList = dialog.findViewById(R.id.calendarList);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.onWindowAttributesChanged(lp);
    }


    @Override
    public boolean canSwipeBack() {
        return false;
    }

    @Override
    public void onSelected(PickValueView view, Object leftValue, Object middleValue, Object rightValue) {
        selectedStr = (String) leftValue;
    }

    private void selectAddress() {

        CityPicker cityPicker = new CityPicker.Builder(getContext())
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province(province)
                .city(city)
                .district(district)
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)//false为三级联动，true为二级联动
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                textViewAddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
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
        webView.loadUrl("http://47.104.88.151/Golang/page2.html");
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
                    UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
                    userSharedPreferencesUtils.setDetails("true");
                    userSharedPreferencesUtils.saveSharedPreferences();
                    getRelease();
                    dialog.dismiss();
                    ExemptionDialog.dismiss();
                } else {
                    showToast("同意遵守本声明，以后每次默认都同意");
                }
            }
        });
    }
}
