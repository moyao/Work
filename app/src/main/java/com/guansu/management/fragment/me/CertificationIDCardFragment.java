package com.guansu.management.fragment.me;
import android.app.Activity;
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
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.config.Constant;
import com.guansu.management.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import butterknife.BindView;
import static com.mob.tools.utils.DeviceHelper.getApplication;

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
                Intent intent = new Intent(getContext(), FaceLivenessExpActivity.class);
                startActivity(intent);
            }
        });
    }


    // 调用拍摄身份证正面（带本地质量控制）activity
    private void scanFrontWithNativeQuality(int idType) {
        Intent intent = new Intent(getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtils.getSaveFile(getApplication()).getAbsolutePath());
        //  intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtils.createFile(getDataDir()+"saomiao",".png"));
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
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
        /* mIvBtnBackgrod.setImageURI(Uri.fromFile(new File(filePath)));*/
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
        if ("0".equals(type)) {
            Glide.with(imageViewFront).load(file).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewFront);
        } else {
            Glide.with(imageViewBack).load(file).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewBack);
        }
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
        }

    }
}
