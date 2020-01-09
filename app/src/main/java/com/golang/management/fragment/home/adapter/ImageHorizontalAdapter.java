package com.golang.management.fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.golang.management.R;
import com.golang.management.bean.PersonalBean;
import com.golang.management.wigdet.dialog.ImageHorizontalDialog;
import java.util.List;
/**
 * @author: dongyaoyao
 */
public class ImageHorizontalAdapter extends RecyclerView.Adapter<ImageHorizontalAdapter.SelectedPicViewHolder> {
    private List<PersonalBean.PhotoAlbumBean> imagesListBeans;
    private Context mcontext;
    public ImageHorizontalAdapter(List<PersonalBean.PhotoAlbumBean> img_list, Context context) {
        this.imagesListBeans=img_list;
        this.mcontext=context;
    }
    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, null));
    }
    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, final int position) {
        Glide.with(holder.itemView).load(imagesListBeans.get(position).getImagePath()).placeholder(R.mipmap.load_icon).into(holder.iv_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageHorizontalDialog.Builder((FragmentActivity) mcontext,imagesListBeans,position).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return imagesListBeans != null ? imagesListBeans.size() : 0;
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_img;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);

        }
    }
}
