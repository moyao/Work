package com.guansu.management.fragment.me;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.wigdet.datepicker.CustomDatePicker;
import com.guansu.management.wigdet.datepicker.DateFormatUtils;
import com.guansu.management.wigdet.datepicker.MyNumberPicker;
import com.guansu.management.wigdet.datepicker.PickValueView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private Dialog dialog;
    private TextView tv_cancel, tv_send;
    private PickValueView pickString;
    String selectedStr;
    String[] valueStr = new String[]{"其他", "大专", "本科", "研究生", "博士"};

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
        setTitle("我的主页");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date(System.currentTimeMillis());
        initDatePicker();
    }

    @Override
    public void bindEvent() {
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
        long endTimestamp = DateFormatUtils.str2Long("2019-01-01", false);
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
}
