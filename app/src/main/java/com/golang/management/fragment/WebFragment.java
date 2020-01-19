package com.golang.management.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.golang.management.R;
import com.golang.management.base.BaseFragment;

import butterknife.BindView;

public class WebFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;

    public static WebFragment newInstance() {
        Bundle args = new Bundle();
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_sweep_code;
    }

    @Override
    public void initView(View view) {
        initApi();
        setTitle("注册用户领VIP啦");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.loadUrl("http://www.golangkeji.com/Golang/page11.html");
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
