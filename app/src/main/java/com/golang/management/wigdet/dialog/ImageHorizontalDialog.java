package com.golang.management.wigdet.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.golang.management.R;
import com.golang.management.bean.PersonalBean;
import com.golang.management.ui.banner.ConvenientBanner;
import com.golang.management.wigdet.banner.holder.CBViewHolderCreator;
import com.golang.management.wigdet.banner.holder.Holder;

import java.util.ArrayList;
import java.util.List;

public final class ImageHorizontalDialog {
    public static final class Builder extends BaseDialogFragment.Builder<Builder> {
        private ConvenientBanner mGuideViewpager;
        private Context context;

        public Builder(FragmentActivity activity, List<PersonalBean.PhotoAlbumBean> image, int page) {
            super(activity);
            this.context = activity;
            setAnimStyle(BaseDialog.AnimStyle.SCALE);
            setWidth(WRAP_CONTENT);
            setHeight(WRAP_CONTENT);
            setContentView(R.layout.fragement_full_img);
            List<String> images = new ArrayList<>();
            for (int i=0;i<image.size();i++){
                images.add(image.get(i).getImagePath());
            }
            mGuideViewpager = findViewById(R.id.guide_viewpager);
            setGravity(Gravity.CENTER);
            setCancelable(true);
            findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            mGuideViewpager.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, images).setPageIndicator(new int[]{R.drawable.img_circle_point_uncheck, R.drawable.img_circle_point_checked});
            mGuideViewpager.setcurrentitem(page);
        }
        @Override
        public BaseDialog create() {
            // 如果内容为空就设置隐藏
            return super.create();
        }
        class LocalImageHolderView implements Holder<String> {
            private ImageView imageView;
            @Override
            public View createView(Context context) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                return imageView;
            }

            @Override
            public void UpdateUI(Context context, int position, String data) {
                Glide.with(context).load(data).placeholder(R.mipmap.load_icon).into(imageView);
            }
        }
    }
}