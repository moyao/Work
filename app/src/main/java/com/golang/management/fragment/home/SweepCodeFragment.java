package com.golang.management.fragment.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import butterknife.BindView;
import io.github.xudaojie.qrcodelib.CaptureActivity;
public class SweepCodeFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView webView;
    private int REQUEST_CODE_SCAN = 0;
    public static SweepCodeFragment newInstance() {
        Bundle args = new Bundle();
        SweepCodeFragment fragment = new SweepCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_sweep_code;
    }
    @Override
    public void initView(View view) {
        setTitle("扫码");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SCAN && data != null) {
            String result = data.getStringExtra("result");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            webView.loadUrl(result);
        }
    }
}
