package com.guansu.management.fragment.me;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.EditBean;
import com.guansu.management.bean.FileBean;
import com.guansu.management.bean.ImagePhoto;
import com.guansu.management.bean.orcode;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.config.Constant;
import com.guansu.management.config.HttpConstants;
import com.guansu.management.model.HomeModellml;
import com.guansu.management.model.MeModellml;
import com.guansu.management.model.ReleaseModellml;
import com.guansu.management.wigdet.CommonTitleBar;
import com.guansu.management.wigdet.datepicker.CustomDatePicker;
import com.guansu.management.wigdet.datepicker.DateFormatUtils;
import com.guansu.management.wigdet.datepicker.PickValueView;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class EditFragment extends BaseFragment implements PickValueView.onSelectedChangeListener {
    private static CustomDatePicker mDatePicker;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textViewBirthday)
    TextView textViewBirthday;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textViewAddress)
    TextView textViewAddress;
    @BindView(R.id.textViewEducation)
    TextView textViewEducation;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageViewNext)
    ImageView imageViewNext;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.textViewHint)
    TextView textViewHint;
    @BindView(R.id.editTextAutograph)
    EditText editTextAutograph;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.textViewAuthentication)
    TextView textViewAuthentication;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textViewName)
    EditText textViewName;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.radioButMale)
    RadioButton radioButMale;
    @BindView(R.id.radioButFemale)
    RadioButton radioButFemale;
    @BindView(R.id.radioAge)
    RadioGroup radioAge;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.view7)
    View view7;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textViewOccupation)
    EditText textViewOccupation;
    @BindView(R.id.view8)
    View view8;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.radioButYes)
    RadioButton radioButYes;
    @BindView(R.id.radioButNo)
    RadioButton radioButNo;
    @BindView(R.id.radioSingle)
    RadioGroup radioSingle;
    @BindView(R.id.view9)
    View view9;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textViewInterest)
    EditText textViewInterest;
    @BindView(R.id.view10)
    View view10;
    @BindView(R.id.view11)
    View view11;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private Dialog dialog;
    private TextView tv_cancel, tv_send;
    private PickValueView pickString;
    String selectedStr;
    String[] valueStr = new String[]{"其他", "大专", "本科", "研究生", "博士"};
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    String profileImageUrl;
    String birthday = "", education = "", occupation = "", message = "", sex = "";
    String single = "";
    String auth = "", wantToGo = "", nickname = "", hobby = "";
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片

    public static EditFragment newInstance() {
        Bundle args = new Bundle();
        EditFragment fragment = new EditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_personal_center;
    }

    @Override
    public void initView(View view) {
        initApi();
        setTitle("我的主页");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mTitlebar.setRightType(CommonTitleBar.TYPE_IMAGEBUTTON_SEARCHVIEW);
        mTitlebar.getRightImageButton().setImageResource(R.mipmap.icon_my_preservation);
        mTitlebar.getRightImageButton().setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                editData();

            }
        });
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date(System.currentTimeMillis());
        initDatePicker();
    }


    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        textView4.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                mDatePicker.show(textViewBirthday.getText().toString());
            }
        });
        textView9.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                selectAddress();
            }
        });
        textView5.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                initDialog();
            }
        });

        textView1.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(CertificationIDCardFragment.newInstance());
            }
        });
        imageViewPhoto.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setSelectLimit(1);
                imagePicker.setCrop(false);
                imagePicker.setShowCamera(true);
                Intent intent1 = new Intent(getContext(), ImageGridActivity.class);
                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, selImageList);
                startActivityForResult(intent1, Constant.REQUEST_CODE_SELECT);
            }
        });
        showLoadingDialog("加载中...");
        new MeModellml().find_info_userbyid(userSharedPreferencesUtils.getUserid()).
                safeSubscribe(new MyObserve<EditBean>(this) {
                    @Override
                    protected void onSuccess(EditBean editBean) {
                        showPage();
                        Glide.with(getContext()).load(editBean.getProfileImageUrl())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageViewPhoto);
                        profileImageUrl = editBean.getProfileImageUrl();
                        editTextAutograph.setText(editBean.getMessage() + "");
                        textViewAuthentication.setText("");
                        textViewName.setText(editBean.getNickname());
                        if ("MALE".equals(editBean.getSex())) {
                            radioButMale.setChecked(true);
                        } else {
                            radioButFemale.setChecked(true);
                        }
                        if ("SINGLE".equals(editBean.getSingle())) {
                            radioButYes.setChecked(true);
                        } else {
                            radioButNo.setChecked(true);
                        }
                        if ("Auth_F".equals(editBean.getAuth())) {
                            textViewAuthentication.setText("未认证");
                        } else if ("Auth_".equals(editBean.getAuth())) {
                            textViewAuthentication.setText("已认证");
                        }
                        textViewBirthday.setText(editBean.getBirthday() + "");
                        textViewEducation.setText(editBean.getEducation());
                        textViewOccupation.setText(editBean.getOccupation());
                        textViewInterest.setText(editBean.getHobby() + "");
                        textViewAddress.setText(editBean.getWantToGo() + "");
                    }
                });
    }

    private void initDialog() {
        //选择对话框
        dialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cj_num, null);
        //获取组件
        tv_cancel = contentView.findViewById(R.id.tv_cancel);
        tv_send = contentView.findViewById(R.id.tv_send);
        pickString = contentView.findViewById(R.id.pickString);
        //获取Dialog的监听
        tv_cancel.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                textViewEducation.setText(selectedStr);
                dialog.dismiss();
            }
        });
        tv_send.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {

                dialog.dismiss();
            }
        });
        pickString.setOnSelectedChangeListener(this);

        pickString.setValueData(valueStr, valueStr[1]);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);//弹窗位置
        dialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogStyle);//弹窗样式
        dialog.show();//显示弹窗
    }

    private void editData() {
        birthday = textViewBirthday.getText().toString();
        education = textViewEducation.getText().toString();
        occupation = textViewOccupation.getText().toString();
        message = editTextAutograph.getText().toString();
        sex = "";
        single = "";
        auth = "";
        if (radioButMale.isChecked()) {
            sex = "MALE";
        } else {
            sex = "FEMALE";
        }
        if (radioButYes.isChecked()) {
            single = "SINGLE";
        } else {
            single = "COUPLE";
        }
        wantToGo = textViewAddress.getText().toString();
        nickname = textViewName.getText().toString();
        hobby = textViewInterest.getText().toString();
        if ("已认证".equals(textViewAuthentication.getText().toString())) {
            auth = "Auth_";
        } else {
            auth = "Auth_F";
        }
        showLoadingDialog("上传中……");
        final List<File> list = new ArrayList();
        if (null != list && null!=selImageList) {
            list.add(new File(selImageList.get(0).path));
            HttpParams params = new HttpParams();
            params.put("uid", userSharedPreferencesUtils.getUserid());
            OkGo.<String>post(HttpConstants.BASE_URL + MeModellml.ICON_UPLOAD)
                    .tag(this)
                    .isMultipart(true)
                    .params(params)
                    .addFileParams("file", list)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String body = response.body();
                            Gson gson = new Gson();
                            ImagePhoto user = gson.fromJson(body, ImagePhoto.class);
                            getDataRelease(user.getData());
                        }
                    });
        } else {
            getDataRelease(profileImageUrl);
        }
    }

    private void getDataRelease(String imageList) {
        new MeModellml().user_infoedit(userSharedPreferencesUtils.getUserid(), birthday, education, occupation, message,
                sex, single, wantToGo, auth, nickname, hobby, imageList).safeSubscribe(new MyObserve<String>(this) {
            @Override
            protected void onSuccess(String orcode) {
                UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
                userSharedPreferencesUtils.setNickname(nickname);
                userSharedPreferencesUtils.setProfileImageUrl(imageList);
                userSharedPreferencesUtils.saveSharedPreferences();
                showPage();
                getActivity().onBackPressed();//销毁自己
            }
        });
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(getContext())
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
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

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2020-01-01", false);
        long endTimestamp = DateFormatUtils.str2Long("1990-01-01", false);
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(getContext(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                textViewBirthday.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, endTimestamp, beginTimestamp);
        mDatePicker.setCancelable(true);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 允许滚动动画
        mDatePicker.setCanShowAnim(true);
    }

    @Override
    public void onSelected(PickValueView view, Object leftValue, Object middleValue, Object rightValue) {
        selectedStr = (String) leftValue;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加图片返回
        if (data != null && requestCode == Constant.REQUEST_CODE_SELECT) {
            selImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (selImageList != null) {
                Glide.with(getContext()).load(selImageList.get(0).path)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageViewPhoto);
            }
        }
    }
}
