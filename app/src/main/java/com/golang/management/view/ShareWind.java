package com.golang.management.view;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.golang.management.R;
import com.golang.management.base.MainApplication;
import com.golang.management.config.HttpConstants;
import com.golang.management.config.Payment;
import com.golang.management.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
/**
 * Created by Administrator on 2016/12/23.
 */
public class ShareWind {
    /**
     * context:当前的类
     * wid：分享的id
     * text：分享的内容
     * image：分享的图片
     * hlsPullUrl: 直播拉流地址
     * type: 分享的类型（0 分享APK，1 分享说说，2 分享新闻,3 直播）
     */
    public static void Sharepartake(final Context context, final String wid, final String text,
                                    final String image, final String hlsPullUrl, final int type) {
        final IWXAPI wxApi;
        wxApi = WXAPIFactory.createWXAPI(context, Payment.APP_ID);
        wxApi.registerApp(Payment.APP_ID);
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        ConstraintLayout layout = (ConstraintLayout) inflaterDl.inflate(R.layout.dialog_share, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(lp);
        RadioButton out_weixin = layout.findViewById(R.id.image_weixin);
        RadioButton out_pengyouquan = layout.findViewById(R.id.iamge_weixinpengyouquan);
        RadioButton image_black = layout.findViewById(R.id.image_black);
        ImageView imageBlack = layout.findViewById(R.id.imageViewBlack);
        out_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatShare(0, context, wxApi);//分享到微信好友
                dialog.dismiss();
            }
        });
        out_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatShare(1, context, wxApi);//分享到微信朋友圈
                dialog.dismiss();
            }
        });
        image_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ToastUtils.copy(HttpConstants.WXShare_app, context);
                dialog.dismiss();
            }
        });
        imageBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    /**
     * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    private static void wechatShare(int flag, Context context, IWXAPI wxApi) {
        if (!wxApi.isWXAppInstalled()) {
            ToastUtils.showToast(context, context.getResources().getString(R.string.meiyouweixin));
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = context.getResources().getString(R.string.share_title);
        webpage.webpageUrl = HttpConstants.WXShare_app;
        msg.description = context.getResources().getString(R.string.share_cotent);
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(MainApplication.getInstance().getResources(), R.mipmap.image_share);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);

    }
}
