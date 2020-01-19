package com.golang.management.fragment.me;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.golang.management.utils.MessageEvent;
import com.google.gson.Gson;
import com.golang.management.R;
import com.golang.management.activity.FaceLivenessExpActivity;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.ImagePhoto;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constant;
import com.golang.management.config.HttpConstants;
import com.golang.management.model.MeModellml;
import com.golang.management.utils.FileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class CertificationIDCardFragment extends BaseFragment {
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.image_view1)
    ImageView imageView1;
    @BindView(R.id.tv_face)
    TextView tvFace;
    @BindView(R.id.image_view2)
    ImageView imageView2;
    @BindView(R.id.tv_finsh)
    TextView tvFinsh;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewIDCardNumber)
    TextView textViewIDCardNumber;
    @BindView(R.id.imageViewFront)
    ImageView imageViewFront;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.textViewNext)
    TextView textViewNext;
    private String name;
    private String idnum;
    private String addressDetail;
    private String beginDate;
    private String endDate;
    private String authority;
    private String front;
    private String reverse;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    private ProgressDialog dialog;
    public static CertificationIDCardFragment newInstance() {
        Bundle args = new Bundle();
        CertificationIDCardFragment fragment = new CertificationIDCardFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_certification_idcard;
    }

    @Override
    public void initView(View view) {
        setTitle("实名认证");
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        imageViewFront.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                scanFrontWithNativeQuality(0);
            }
        });
        imageViewBack.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(idnum)) {
                    showToast("请您先扫描身份证正面");
                } else {
                    scanFrontWithNativeQuality(1);
                }
            }
        });
        textViewNext.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                if (TextUtils.isEmpty(idnum)) {
                    showToast("请上传身份证照片");
                    return;
                }
                if (StringUtils.isEmpty(reverse)) {
                    showToast("请上传身份证国徽面照片");
                    return;
                }
                Intent intent = new Intent(getContext(), FaceLivenessExpActivity.class);
                intent.putExtra("name", name);
                intent.putExtra(Constant.INTENT_ID_NUMBER, idnum);
                intent.putExtra("date", beginDate+"-"+endDate);
                intent.putExtra("url", front);
                intent.putExtra("idCardReverseSide", reverse);
                startActivityForResult(intent,4);

            }
        });
    }

    // 调用拍摄身份证正面（带本地质量控制）activity
    private void scanFrontWithNativeQuality(int idType) {
        Intent intent = new Intent(getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtils.getSaveFile(getContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        //使用本地质量控制能力需要授权
        intent.putExtra(CameraActivity.KEY_NATIVE_TOKEN, OCR.getInstance(getContext()).getLicense());
        //设置扫描的身份证的类型（正面front还是反面back）
        if (idType == 0) {
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        } else {
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        }
        startActivityForResult(intent, Constant.SCANNER_REQUESTCODE);
    }

    private void recIDCard(final String idCardSide, final File file) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(file);
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);
        OCR.getInstance(getContext()).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    String type = "";
                    if (IDCardParams.ID_CARD_SIDE_FRONT.equals(result.getIdCardSide())) {
                        if (result.getName() != null && result.getIdNumber() != null && result.getAddress() != null) {
                            name = result.getName().getWords();
                            idnum = result.getIdNumber().getWords();
                            addressDetail = result.getAddress().getWords();
                            beginDate = "";
                            endDate = "";
                            authority = "";
                            type = "0";
                            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idnum)) {
                                showToast("身份证信息未识别完整，请重新扫描");
                                return;
                            }
                            textViewName.setText("姓名：" + name);
                            textViewIDCardNumber.setText("身份证号：" + idnum);
                        } else {
                            showToast("身份证未识别到,请重新扫描");
                            return;
                        }

                    } else if (IDCardParams.ID_CARD_SIDE_BACK.equals(result.getIdCardSide())) {
                        if (result.getSignDate() != null && result.getExpiryDate() != null && result.getIssueAuthority() != null) {
                            beginDate = result.getSignDate().getWords();
                            endDate = result.getExpiryDate().getWords();
                            authority = result.getIssueAuthority().getWords();

                            type = "1";
                        } else {
                            showToast("身份证未识别到,请重新上扫描");
                            return;
                        }
                    }
                    upload(file, type);
                } else {
                    showToast("请重新扫描身份证");
                    return;
                }
            }

            @Override
            public void onError(OCRError error) {
                showToast(error.getMessage());
            }
        });
    }
    private void upload(File file, String type) {
        showLoadingDialog("加载中...");
        List<File> files = new ArrayList<>();
        files.add(file);
        HttpParams params = new HttpParams();
        params.put("uid", userSharedPreferencesUtils.getUserid());
        OkGo.<String>post(HttpConstants.BASE_URL + MeModellml.ICON_UPLOAD)
                .tag(this)
                .isMultipart(true)
                .params(params)
                .addFileParams("file", files)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dialog.dismiss();
                        String body = response.body();
                        Gson gson = new Gson();
                        ImagePhoto user = gson.fromJson(body, ImagePhoto.class);
                        if ("0".equals(type)) {
                            front = user.getData();
                            Glide.with(imageViewFront).load(file).skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewFront);
                        } else {
                            reverse = user.getData();
                            Glide.with(imageViewBack).load(file).skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewBack);
                        }

                    }
                });
    }
    @Override
    public void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.SCANNER_REQUESTCODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                File file = FileUtils.getSaveFile(getContext());
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, file);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, file);
                    }
                }
            }
        }else if (requestCode==4){
            getActivity().onBackPressed();//销毁自己
        }
    }
    public void showLoadingDialog(String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(getContext());
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (dialog.isShowing()) return;
        dialog.setMessage(message);
        dialog.show();
    }
}
