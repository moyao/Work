package com.guansu.management.fragment.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.bean.HomeBean;
import com.guansu.management.bean.ImagesListBean;

import java.util.List;

/**
 * @author: dongyaoyao
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SelectedPicViewHolder> {
    private List<ImagesListBean> imagesListBeans;

    public ImageAdapter(List<ImagesListBean> img_list) {
        this.imagesListBeans=img_list;
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, null));
    }
    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, final int position) {
        Glide.with(holder.itemView).load(imagesListBeans.get(position).getImage()).into(holder.iv_img);
    }
    @Override
    public int getItemCount() {
        return imagesListBeans.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_img;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);

        }
    }
}
