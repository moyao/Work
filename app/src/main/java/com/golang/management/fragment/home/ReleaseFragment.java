package com.golang.management.fragment.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.golang.management.R;
import com.golang.management.activity.CheckPermissionsActivity;
import com.golang.management.api.MyObserve;
import com.golang.management.bean.FileBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constant;
import com.golang.management.config.Constants;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.release.DetailsNextFragment;
import com.golang.management.model.HomeModellml;
import com.golang.management.model.ReleaseModellml;
import com.golang.management.ui.release.ImagePickerAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
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
 * Created by dongyaoyao
 */
public class ReleaseFragment extends CheckPermissionsActivity implements
        ImagePickerAdapter.OnRecyclerViewItemClickListener, View.OnClickListener {
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.editTextContext)
    TextView editTextContext;
    private ImageButton mImageBlack;
    private ImageButton mImagePreservation;
    private RecyclerView mRvPics;
    private Button mButRelease;
    private ArrayList<ImageItem> selImageList  = new ArrayList<>(); //当前选择的所有图片
    private ImagePickerAdapter adapter;
    String userId;
    private String lat, lng;
    private Dialog ExemptionDialog;
    private CheckBox checkbox;
    private Button butDetermine, butCancel;
    private WebView webView;
    final List<File> list = new ArrayList();

    public static ReleaseFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_TITLE, title);
        ReleaseFragment fragment = new ReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void locationResult(String longitude, String latitude,
                                  String address, String city, String province, String district) {
        this.lng = longitude;
        this.lat = latitude;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_release;
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
        startLocation();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mImageBlack = view.findViewById(R.id.imageBlack);
        mImagePreservation = view.findViewById(R.id.imagePreservation);
        mRvPics = view.findViewById(R.id.rvPics);
        mButRelease = view.findViewById(R.id.butRelease);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRvPics.setLayoutManager(gridLayoutManager);
        adapter = new ImagePickerAdapter(getContext(), selImageList, 9);
        mRvPics.setAdapter(adapter);
        mRvPics.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(this);
        mRvPics.setOnClickListener(this);
        showDialogExemption();
    }

    @Override
    public void bindEvent() {
        UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        userId = userSharedPreferencesUtils.getUserid();
        if (getArguments().getString(Constants.KEY_TITLE).equals(Constant.VIEW_CIRCLE)) {
            textViewTitle.setText("发布圈子");
            mButRelease.setText("立即发布");
        } else {
            textViewTitle.setText("发布活动");
            mButRelease.setText("下一步");
        }
        mButRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (selImageList.size() < 1) {
                    showToast("请选择您要发布的图片");
                    return;
                }
                if ("".equals(editTextContext.getText().toString())) {
                    showToast("请输入您要发布的内容");
                    return;
                }
                if (getArguments().getString(Constants.KEY_TITLE).equals(Constant.VIEW_CIRCLE)) {
                    showLoadingDialog("上传中……");
                    //圈子

                    for (ImageItem imageItem : selImageList) {
                        if (!imageItem.path.startsWith("http"))
                            list.add(new File(imageItem.path));
                    }
                    if ("true".equals(userSharedPreferencesUtils.getDetails())) {
                        DataImage();
                    } else {
                        ExemptionDialog.show();
                    }

                } else {
                    //活动
                    final List<File> list = new ArrayList();
                    for (ImageItem imageItem : selImageList) {
                        if (!imageItem.path.startsWith("http"))
                            list.add(new File(imageItem.path));
                    }
                    startForResult(DetailsNextFragment.newInstance(selImageList, editTextContext.getText().toString()), 0);
                }
            }
        });
        mImageBlack.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                getActivity().onBackPressed();

            }
        });
    }

    private void DataImage() {
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

    private void getDataRelease(List<FileBean.DataBean.ImageListBean> jsonArray) {
        Gson gson = new Gson();
        String s = gson.toJson(jsonArray);
        try {
            JSONArray jsonObject = new JSONArray(s);
            new ReleaseModellml().user_imgand_textsave(userId, editTextContext.getText().toString(), lat, lng, jsonObject)
                    .subscribe(new MyObserve<String>(this) {
                        @Override
                        protected void onSuccess(String activityDtoInfo) {
                            showPage();
                            getActivity().onBackPressed();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    public void onItemClick(View view, int position) {
        switch (position) {
            case Constant.IMAGE_ITEM_ADD:
                view.requestFocus();
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setSelectLimit(9);
                imagePicker.setCrop(false);
                imagePicker.setShowCamera(true);
                imagePicker.setMultiMode(true);
                Intent intent1 = new Intent(getContext(), ImageGridActivity.class);
                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, (ArrayList<ImageItem>) adapter.getImages());
                startActivityForResult(intent1, Constant.REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(getContext(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, Constant.REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加图片返回
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == Constant.REQUEST_CODE_SELECT) {
                selImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (selImageList != null) {
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == Constant.REQUEST_CODE_PREVIEW) {
                selImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (selImageList != null) {
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            // 在此通过Bundle data 获取返回的数据
            if (data.getString("title").equals(0)) {

            } else {
                getActivity().onBackPressed();//销毁自己
            }
        }
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
                    DataImage();
                    ExemptionDialog.dismiss();
                } else {
                    showToast("同意遵守本声明，以后每次默认都同意");
                }
            }
        });
    }
}
