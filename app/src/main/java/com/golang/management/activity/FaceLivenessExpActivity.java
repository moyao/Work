package com.golang.management.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.baidu.idl.face.platform.utils.FileUtils;
import com.baidu.idl.util.FileUtil;
import com.golang.management.utils.MessageEvent;
import com.google.gson.Gson;
import com.golang.management.bean.ImagePhoto;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constant;
import com.golang.management.config.HttpConstants;
import com.golang.management.config.config;
import com.golang.management.model.MeModellml;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FaceLivenessExpActivity extends FaceLivenessActivity {
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    private ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        FaceSDKManager.getInstance().initialize(this, config.licenseID, config.licenseFileName);
        setFaceConfig();
        super.onCreate(savedInstanceState);
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(this);
    }
    private void setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        List<LivenessTypeEnum> livenessList = new ArrayList<>();
        livenessList.add(LivenessTypeEnum.Eye);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
        config.setLivenessTypeList(livenessList);
        config.setLivenessRandom(false);
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);
        FaceSDKManager.getInstance().setFaceConfig(config);
    }
    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            upload(base64ImageMap.get("bestImage0"));
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
        }
    }
    private void upload(final String bestImage) {
        List<File> files = new ArrayList<>();
        File file = new File(getExternalFilesDir("img"), "face" + System.currentTimeMillis() + ".jpg");
        FileUtil.createFile(file);
        FileUtils.writeToFile(file, Base64Utils.decode(bestImage, Base64Utils.NO_WRAP));
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
                        String body = response.body();
                        Gson gson = new Gson();
                        ImagePhoto user = gson.fromJson(body, ImagePhoto.class);
                        certification(user.getData());
                    }
                });
    }
    private void certification(String path) {
        showLoadingDialog("加载中。。。。");
        Map<String, Object> httpParams = new HashMap<>();
        httpParams.put("userId", userSharedPreferencesUtils.getUserid());
        //生活照
        httpParams.put("faceImage", path);
        //身份证照片
        httpParams.put("identityImage", getIntent().getStringExtra("url"));
        //身份证号码
        httpParams.put("identity", getIntent().getStringExtra(Constant.INTENT_ID_NUMBER));
        //姓名
        httpParams.put("name", getIntent().getStringExtra("name"));
        JSONObject jsonObject = new JSONObject(httpParams);
        OkGo.<String>post(HttpConstants.BASE_URL + MeModellml.USER_FACERECOGNITION)
                .tag(this)
                .isMultipart(true)
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if ("0000000".equals(jsonObject.getString("code"))) {
                                Intent intent = getIntent();
                                intent.putExtra("name", getIntent().getStringExtra("name"));
                                intent.putExtra(Constant.INTENT_ID_NUMBER, getIntent().getStringExtra(Constant.INTENT_ID_NUMBER));
                                intent.putExtra("date", getIntent().getStringExtra("date"));
                                intent.setClass(FaceLivenessExpActivity.this, CertificationFinishActivity.class);
                                startActivity(intent);
                                EventBus.getDefault().post(new MessageEvent("已认证",1));
                                finish();
                            }else {
                                Toast.makeText(getApplication(), "认证失败，请来联系客服", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                Toast.makeText(getApplication(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dialog.dismiss();
                        Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void showLoadingDialog(String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (dialog.isShowing()) return;
        dialog.setMessage(message);
        dialog.show();
    }
}
