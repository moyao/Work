package com.golang.management.activity;

import android.view.View;
import android.widget.TextView;

import com.golang.management.R;
import com.golang.management.base.BaseActivity;
import com.golang.management.config.Constant;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 13992
 */
public class CertificationFinishActivity extends BaseActivity {
    private TextView mTvNameContext;
    private TextView mTvIdcardContext;
    private TextView mTvValidityContext;
    private TextView mTvFinish;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_certification_finish;
    }
    @Override
    public void initView() {
        mTvNameContext = findViewById(R.id.tv_name_context);
        mTvIdcardContext = findViewById(R.id.tv_idcard_context);
        mTvValidityContext = findViewById(R.id.tv_validity_context);
        mTvFinish = findViewById(R.id.tv_finish);
        mTvFinish.setOnClickListener(this);
    }
    @Override
    public void bindEvent() {
        mTvNameContext.setText(getIntent().getStringExtra("name"));
        mTvIdcardContext.setText(getIntent().getStringExtra(Constant.INTENT_ID_NUMBER));
        /*UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(this);
        userSharedPreferencesUtils.getUserInfo().setHasAuthentication("1");
        userSharedPreferencesUtils.saveSharedPreferences();*/
        try {
            mTvValidityContext.setText(StringToDate(getIntent().getStringExtra("date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String StringToDate(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date;
        date = format.parse(time);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String s = format1.format(date);
        return s;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                finish();
                break;
        }
    }
}
