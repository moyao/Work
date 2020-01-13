package com.golang.management.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constant;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.home.FootprintFragemnt;
import com.golang.management.fragment.home.MeFragment;
import com.golang.management.fragment.home.MessageFragment;
import com.golang.management.fragment.home.NewHomeFragment;
import com.golang.management.fragment.home.ReleaseFragment;
import com.golang.management.utils.StringHandler;
import com.golang.management.utils.WisdomMyTool;
import com.golang.management.wigdet.AppUpdateProgressDialog;
import com.golang.management.wigdet.animation.CircleList;
import com.golang.management.wigdet.bottombar.BottomBar;
import com.golang.management.wigdet.bottombar.BottomBarTab;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.qcloud.tim.uikit.component.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.yokeyword.fragmentation.SupportFragment;

import static com.baidu.idl.face.platform.ui.utils.VolumeUtils.TAG;

/**
 * Created by dongyaoyao
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.radioRelease)
    RadioButton radioRelease;
    @BindView(R.id.checkView)
    View checkView;
    private SupportFragment[] mFragments = new SupportFragment[5];
    private BottomBar mNavigation;
    private View viewCircle, viewConstraints;
    private Dialog dia;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    private String downUrl;
    private AppUpdateProgressDialog appUpdateProgressDialog;
    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_mian;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(NewHomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = NewHomeFragment.newInstance();
            mFragments[1] = MessageFragment.newInstance();
            mFragments[2] = MessageFragment.newInstance();
            mFragments[3] = FootprintFragemnt.newInstance();
            mFragments[4] = MeFragment.newInstance();
            loadMultipleRootFragment(R.id.frameLayout, 0,
                    mFragments[0], mFragments[1], mFragments[2], mFragments[3], mFragments[4]);
        } else {
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.findFragmentByTag自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(MessageFragment.class);
            mFragments[2] = findChildFragment(MessageFragment.class);
            mFragments[3] = findChildFragment(FootprintFragemnt.class);
            mFragments[4] = findChildFragment(MeFragment.class);
        }
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
//        initDownloadApk();
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        mNavigation = view.findViewById(R.id.navigation);
        mNavigation
                .addItem(new BottomBarTab(_mActivity, R.mipmap.home_no, getString(R.string.navHome)))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.message_no, getString(R.string.navStatistics)))
                .addItem(new BottomBarTab(_mActivity, R.color.transparent, ""))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.footprint_no, getString(R.string.navMessage)))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.my_no, getString(R.string.navMe)));
        dia = new Dialog(getContext(), R.style.BaseDialogStyle);
        dia.setContentView(R.layout.dialog_release);
        viewCircle = dia.findViewById(R.id.view);
        viewConstraints = dia.findViewById(R.id.view1);
        dia.setCanceledOnTouchOutside(true);
        dia.getWindow().setGravity(Gravity.BOTTOM);
        Window w = dia.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        dia.onWindowAttributesChanged(lp);
        if (!StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
            Set<String> tagSet = new HashSet<>();
            tagSet.add(userSharedPreferencesUtils.getUserid());
            JPushInterface.setTags(getContext(), tagSet, new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.e(TAG, "--设置标签returnCode:" + i + ",s:" + s);
                }
            });
        }

    }

    private void initDownloadApk() {
        OkGo.<String>post("").isSpliceUrl(true)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                final String result = jsonObject.getString("result");
                                if (HttpConstants.SUCCESS_CODE.equals(result)) {
                                    JSONObject jo = jsonObject.getJSONObject("data");
                                    downUrl = (String) jo.get("url");
                                    String newVersionCode = jo.getString("version");
                                    // int versionCode = WisdomMyTool.getVersionCode();
                                    if (Integer.parseInt(newVersionCode) > WisdomMyTool.getVersionCode()) {
                                        AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                                                .setMessage("检测到有新版本,您需要下载更新!")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        downloadAPP();
                                                        dialogInterface.dismiss();
                                                    }
                                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).create();
                                        alertDialog2.show();
                                    }
                                } else {
                                    showToast("网络请求失败,请稍后重试");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    /***
     * APP 下载
     * @param
     */
    private void downloadAPP() {
        if (!StringHandler.hasNull(downUrl))
            OkGo.<File>get(downUrl)
                    .execute(new FileCallback("wisdom_doctor.apk") {
                        @Override
                        public void onStart(Request<File, ? extends Request> request) {
                            super.onStart(request);
                            appUpdateProgressDialog = new AppUpdateProgressDialog(getContext());
                            appUpdateProgressDialog.show();
                        }

                        @Override
                        public void onSuccess(Response<File> response) {
                            appUpdateProgressDialog.dismiss();
                            File absoluteFile = response.body().getAbsoluteFile();
                            installApk(absoluteFile);
                        }

                        @Override
                        public void onError(Response<File> response) {
                            String message = response.message();
                            appUpdateProgressDialog.dismiss();
                           showToast("下载失败,请您稍后再试!");
                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            int currentSize = (int) progress.currentSize;
                            int totalSize = (int) progress.totalSize;
                            double result = new BigDecimal((float) currentSize / totalSize).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            int i = (int) (result * 100);
                            appUpdateProgressDialog.setProgress(i);
                            appUpdateProgressDialog.show();
                        }

                    });
    }

    private void installApk(File absoluteFile) {
        new Thread(){
            @Override
            public void run() {
                if (absoluteFile != null) {
                    if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
                        Uri apkUri = FileProvider.getUriForFile(getContext(), "com.csti.cetx.fileProvider", absoluteFile);//在AndroidManifest中的android:authorities值
                        Intent install = new Intent(Intent.ACTION_VIEW);
                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 一定要记得 先 setFlags 在 addFlags 否则 set 会覆盖 add
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(install);
                    } else{
                        Intent install = new Intent(Intent.ACTION_VIEW);
                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        install.setDataAndType(Uri.fromFile(absoluteFile), "application/vnd.android.package-archive");
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(install);
                    }
                }
            }
        }.start();
    }

    @Override
    public void bindEvent() {
        initApi();
        if (!StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
            checkView.setVisibility(View.GONE);
            mNavigation.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
                @Override
                public void onTabSelected(int position, int prePosition) {
                    if (position >= 0 && !StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
                        showHideFragment(mFragments[position], mFragments[prePosition]);
                    } else {

                        loginDialog((Activity) getContext());
                    }
                }

                @Override
                public void onTabUnselected(int position) {
                }

                @Override
                public void onTabReselected(int position) {
                }
            });
        } else {
            checkView.setVisibility(View.VISIBLE);
            checkView.setOnClickListener(new OnClickListenerWrapper() {
                @Override
                protected void onSingleClick(View v) {
                    loginDialog((Activity) getContext());
                }
            });
        }
        radioRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (!StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
                    if (!dia.isShowing()) {
                        dia.show();
                    }
                } else {
                    loginDialog((Activity) getContext());
                }
            }
        });
        viewCircle.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(ReleaseFragment.newInstance(Constant.VIEW_CIRCLE));
                dia.dismiss();
            }
        });
        viewConstraints.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(ReleaseFragment.newInstance(Constant.VIEW_CONSTRAINTS));
                dia.dismiss();
            }
        });
    }

    private long exitTime = 0;

    @Override
    public boolean onBackPressedSupport() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            //如果大于2000毫秒,说明误操作
            showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            _mActivity.finish();
            return super.onBackPressedSupport();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    protected void immediatelyLogin() {
        start(LoginFragment.newInstance("1"));
    }
}
