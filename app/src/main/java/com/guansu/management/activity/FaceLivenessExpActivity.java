package com.guansu.management.activity;
import android.os.Bundle;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.guansu.management.config.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaceLivenessExpActivity extends FaceLivenessActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        FaceSDKManager.getInstance().initialize(this, config.licenseID, config.licenseFileName);
        setFaceConfig();
        super.onCreate(savedInstanceState);
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
       /* UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(this);
        final HttpParams params = new HttpParams();
        params.put("token", Base64.encode(userSharedPreferencesUtils.getToken()));
        params.put("appFlag", Base64.encode("1"));
        params.put("moduleFlag", Base64.encode("face"));
        OkGo.<FileBean>post(IpManager.getInstance().getIp(HttpConstant.FTP_UPLOAD)).retryCount(0).isSpliceUrl(true)
                .params(params).tag(this)
                .execute(new JsonCallback<FileBean>(FileBean.class, this) {
                    @Override
                    public void onStart(Request<FileBean, ? extends Request> request) {
                        super.onStart(request);
                        File file = new File(getExternalFilesDir("img"), "face" + System.currentTimeMillis() + ".jpg");
                        FileUtil.createFile(file);
                        FileUtils.writeToFile(file, Base64Utils.decode(bestImage, Base64Utils.NO_WRAP));
                        request.getParams().put("files", file);
                    }

                    @Override
                    public void onSuccess(Response<FileBean> response) {
                        String path = response.body().getData().getPath();
                        certification(path, getIntent().getStringExtra("url"));
                    }
                });
*/

    }

    private void certification(String path, String url) {
       /* UserSharedPreferencesUtils userSharedPreferencesUtils = new UserSharedPreferencesUtils(this);
        final HttpParams params = new HttpParams();
        params.put("token", Base64.encode(userSharedPreferencesUtils.getToken()));
        //生活照
        params.put("realNameUrl", Base64.encode(path));
        //身份证正面
        params.put("idCardFront", Base64.encode(url));
        params.put("idCardReverseSide", Base64.encode(getIntent().getStringExtra("idCardReverseSide")));
        //医护端  d   居民端 i
        params.put("cFlag", "d");
        OkGo.<FileBean>post(IpManager.getInstance().getIp(HttpConstant.URL_CERITIFICATION)).retryCount(0).isSpliceUrl(true)
                .params(params).tag(this)
                .execute(new JsonCallback<FileBean>(FileBean.class, this) {
                    @Override
                    public void onSuccess(Response<FileBean> response) {
                        FileBean fileBean = response.body();
                        if (fileBean.getResult().equals("000000")) {
                            ActivityUtils.getTopActivity().finish();
                            Intent intent = getIntent();
                            intent.putExtra("name", getIntent().getStringExtra("name"));
                            intent.putExtra(Constant.INTENT_ID_NUMBER, getIntent().getStringExtra(Constant.INTENT_ID_NUMBER));
                            intent.putExtra("date", getIntent().getStringExtra("date"));
                            intent.setClass(FaceLivenessExpActivity.this, CertificationFinishActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<FileBean> response) {
                        super.onError(response);
                        finish();
                    }
                });*/

    }
}
