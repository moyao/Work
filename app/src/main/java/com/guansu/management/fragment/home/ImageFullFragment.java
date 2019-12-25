package com.guansu.management.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.ui.banner.ConvenientBanner;
import com.guansu.management.wigdet.banner.holder.CBViewHolderCreator;
import com.guansu.management.wigdet.banner.holder.Holder;

import butterknife.BindView;

/**
 * @date:
 * @author: dongyaoyao
 */
public class ImageFullFragment extends BaseFragment {
    @BindView(R.id.guide_viewpager)
    ConvenientBanner guideViewpager;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    public static ImageFullFragment newInstance() {
        Bundle args = new Bundle();
        ImageFullFragment fragment = new ImageFullFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_full_img;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void bindEvent() {
       /* guideViewpager.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, getContext().getStringArrayListExtra("imgs")).setPageIndicator(new int[]{R.drawable.img_circle_point_uncheck, R.drawable.img_circle_point_checked});
        guideViewpager.setcurrentitem(getIntent().getIntExtra("page", 0));*/
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
    class LocalImageHolderView implements Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(getContext()).load(data).into(imageView);
        }
    }
}
