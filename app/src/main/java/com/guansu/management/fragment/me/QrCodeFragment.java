package com.guansu.management.fragment.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.api.MyObserve;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.orcode;
import com.guansu.management.common.OnClickListenerWrapper;
import com.guansu.management.common.UserSharedPreferencesUtils;
import com.guansu.management.model.MeModellml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

import static com.guansu.management.R.drawable;
import static com.guansu.management.R.id;

/**
 * @author: dongyaoyao
 */
public class QrCodeFragment extends BaseFragment {
    @BindView(id.imageViewQrCode)
    ImageView imageViewQrCode;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    @BindView(id.textViewCode)
    TextView textViewCode;
    @BindView(id.butRelease)
    Button butRelease;
    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败
    private static final int SAVE_BEGIN = 2;//开始保存图片
    String ImageUrl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    showToast("开始保存图片...");
                    butRelease.setClickable(false);
                    break;
                case SAVE_SUCCESS:
                    showToast("图片保存成功,请到相册查找");
                    butRelease.setClickable(true);
                    break;
                case SAVE_FAILURE:
                    showToast("图片保存失败,请稍后再试...");
                    butRelease.setClickable(true);
                    break;
            }
        }
    };

    public static QrCodeFragment newInstance() {
        Bundle args = new Bundle();
        QrCodeFragment fragment = new QrCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_qrcode;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView(View view) {
        mTitlebar.showStatusBar(true);
        initApi();
        mTitlebar.setBackgroundResource(drawable.but_release);
        setTitle("我的二维码");
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        new MeModellml().find_activity_byuserid(userSharedPreferencesUtils.getUserid())
                .safeSubscribe(new MyObserve<orcode>(this) {
                    @Override
                    protected void onSuccess(orcode orcode) {
                        ImageUrl = orcode.getQR_URI();
                        Glide.with(getContext()).load(orcode.getQR_URI()).into(imageViewQrCode);
                        textViewCode.setText("推荐码：" + orcode.getRecommendCode());
                    }
                });
        butRelease.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
               //保存图片必须在子线程中操作，是耗时操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.obtainMessage(SAVE_BEGIN).sendToTarget();
                        Bitmap bp = returnBitMap(ImageUrl);
                        saveImageToPhotos(getContext(), bp);
                    }
                }).start();
            }
        });
    }

    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Go 浪");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mHandler.obtainMessage(SAVE_FAILURE).sendToTarget();
            return;
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
    }

    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
