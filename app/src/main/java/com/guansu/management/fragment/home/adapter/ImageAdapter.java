package com.guansu.management.fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.bean.ImagesListBean;
import com.guansu.management.wigdet.dialog.ImageDialog;

import java.util.List;

/**
 * @author: dongyaoyao
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SelectedPicViewHolder> {
    private List<ImagesListBean> imagesListBeans;
    private Context mcontext;
    public ImageAdapter(List<ImagesListBean> img_list,Context context) {
        this.imagesListBeans=img_list;
        this.mcontext=context;
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, null));
    }
    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, final int position) {
        Glide.with(holder.itemView).load(imagesListBeans.get(position).getImage()).placeholder(R.mipmap.load_icon).into(holder.iv_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ImageDialog.Builder((FragmentActivity) mcontext,imagesListBeans,position).show();
            }
        });
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
