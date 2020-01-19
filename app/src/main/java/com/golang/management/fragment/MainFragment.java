package com.golang.management.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.golang.management.BuildConfig;
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
import com.golang.management.wigdet.bottombar.BottomBar;
import com.golang.management.wigdet.bottombar.BottomBarTab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

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
    @BindView(R.id.checkView)
    View checkView;
    @BindView(R.id.checktextView)
    View checktextView;
    @BindView(R.id.radioRelease)
    FloatingActionButton radioRelease;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textViewVisibility)
    TextView textViewVisibility;
    @BindView(R.id.iamgefabu)
    ImageButton iamgefabu;
    @BindView(R.id.imagequnzi)
    ImageButton imagequnzi;
    private SupportFragment[] mFragments = new SupportFragment[5];
    private BottomBar mNavigation;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    private AppUpdateProgressDialog appUpdateProgressDialog;
    boolean fabOpened = false;
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
        OkGo.<String>get("http://api.golangkeji.com/apk/version")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                    String newVersionCode = jsonObject.getString("newVersion");
                                    if (newVersionCode.equals(WisdomMyTool.getWisdomVersion(getContext()))) {
                                    }else {
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
        OkGo.<File>get("http://api.golangkeji.com/apk/version")
                .execute(new FileCallback("golang.apk") {
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

    /***
     * APP 安装
     * @param file
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
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
                    } else if (StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
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
        radioRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
                    loginDialog((Activity) getContext());
                } else if (!fabOpened) {
                    openMenu();
                    textViewVisibility.setVisibility(View.VISIBLE);
                } else {
                    closeMenu();
                }
            }
        });
        checktextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHandler.hasNull(userSharedPreferencesUtils.getUserid())) {
                    loginDialog((Activity) getContext());
                } else if (!fabOpened) {
                    openMenu();
                    textViewVisibility.setVisibility(View.VISIBLE);
                } else {
                    closeMenu();
                }
            }
        });
        textViewVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabOpened) {
                    closeMenu();
                }
            }
        });
        imagequnzi.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(ReleaseFragment.newInstance(Constant.VIEW_CONSTRAINTS));
                closeMenu();
            }
        });
        iamgefabu.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                start(ReleaseFragment.newInstance(Constant.VIEW_CIRCLE));

                closeMenu();
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

    private void closeMenu() {
        textViewVisibility.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 0);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        textView.startAnimation(alphaAnimation);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_false);
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.translate_yfalse);
        iamgefabu.startAnimation(animation);
        imagequnzi.startAnimation(animation1);
        imagequnzi.setVisibility(View.GONE);
        iamgefabu.setVisibility(View.GONE);
        fabOpened = false;
    }

    private void openMenu() {
        textView.setVisibility(View.VISIBLE);
        imagequnzi.setVisibility(View.VISIBLE);
        iamgefabu.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.translate_y);
        iamgefabu.startAnimation(animation);
        imagequnzi.startAnimation(animation1);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.7f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        textView.startAnimation(alphaAnimation);
        fabOpened = true;
    }
}
