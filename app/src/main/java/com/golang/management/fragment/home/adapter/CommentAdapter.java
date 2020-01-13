package com.golang.management.fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.golang.management.R;
import com.golang.management.bean.ActivityCommentsBeanX;
import com.golang.management.bean.ActivityDtoInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author: dongyaoyao
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ActivityCommentsBeanX> commentsBeans= new ArrayList<ActivityCommentsBeanX>();
    private Context context;
    private ItemClickListener mItemClickListener;
    public interface ItemClickListener {
        void OnItemClick(ActivityCommentsBeanX comments,int tag);
    }

    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public CommentAdapter(ActivityDtoInfo activityComments, Context context) {
        this.context = context;
            for (ActivityCommentsBeanX commentsBeanX : activityComments.getActivityComments()) {
                ActivityCommentsBeanX commentsBean = new ActivityCommentsBeanX();
                commentsBean.setType(-1);
                commentsBean.setId(commentsBeanX.getId());
                commentsBean.setContent(commentsBeanX.getContent());
                commentsBean.setNickname(commentsBeanX.getNickname());
                commentsBean.setCreatedAt(commentsBeanX.getCreatedAt());
                commentsBean.setObjectId(commentsBeanX.getObjectId());
                commentsBean.setParentId(commentsBeanX.getParentId());
                commentsBean.setUserId(commentsBeanX.getUserId());
                commentsBean.setTargetNickname(commentsBeanX.getTargetNickname());
                commentsBean.setProfileImageUrl(commentsBeanX.getProfileImageUrl());
                this.commentsBeans.add(commentsBean);
                for (ActivityCommentsBeanX commentsBeanX1 : commentsBeanX.getContentComments()) {
                    ActivityCommentsBeanX commentsBean1 = new ActivityCommentsBeanX();
                    commentsBean1.setType(1);
                    commentsBean1.setId(commentsBeanX1.getId());
                    commentsBean1.setContent(commentsBeanX1.getContent());
                    commentsBean1.setNickname(commentsBeanX1.getNickname());
                    commentsBean1.setCreatedAt(commentsBeanX1.getCreatedAt());
                    commentsBean1.setParentId(commentsBeanX1.getParentId());
                    commentsBean1.setUserId(commentsBeanX1.getUserId());
                    commentsBean1.setObjectId(commentsBeanX1.getObjectId());
                    commentsBean1.setTargetNickname(commentsBeanX1.getTargetNickname());
                    commentsBean1.setProfileImageUrl(commentsBeanX1.getProfileImageUrl());
                    this.commentsBeans.add(commentsBean1);
                }
        }
    }
    @Override
    public int getItemViewType(int position) {
        return commentsBeans.get(position).getType();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case -1:
                return new TopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
            case 1:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_reply, parent, false));
            default:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_reply, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ActivityCommentsBeanX commentsBean = commentsBeans.get(position);
        if (holder instanceof TopViewHolder) {
            Glide.with(context).load(commentsBean.getProfileImageUrl()+"")
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).error(R.mipmap.load_icon).into(((TopViewHolder) holder).textViewPhoto);
            ((TopViewHolder) holder).textViewName.setText(commentsBean.getNickname() + "");
            ((TopViewHolder) holder).textViewTime.setText(commentsBean.getCreatedAt());
            ((TopViewHolder) holder).textViewContent.setText(commentsBean.getContent());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(commentsBean,0);
                }
            });
        } else if (holder instanceof ItemViewHolder) {
            Glide.with(context).load(commentsBean.getProfileImageUrl()+"")
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).error(R.mipmap.load_icon).into(((ItemViewHolder) holder).textViewPhoto);
            ((ItemViewHolder) holder).textViewName.setText(commentsBean.getNickname() + "@"+commentsBean.getTargetNickname());
            ((ItemViewHolder) holder).textViewTime.setText(commentsBean.getCreatedAt());
            ((ItemViewHolder) holder).textViewContent.setText(commentsBean.getContent());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(commentsBean,1);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return commentsBeans != null ? commentsBeans.size() : 0;
    }
    class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewPhoto)
        ImageView textViewPhoto;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.textViewContent)
        TextView textViewContent;
        @BindView(R.id.imageViewReply)
        ImageView textViewReply;
        TopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
        class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.textViewPhoto)
            ImageView textViewPhoto;
            @BindView(R.id.textViewName)
            TextView textViewName;
            @BindView(R.id.textViewContent)
            TextView textViewContent;
            @BindView(R.id.textViewTime)
            TextView textViewTime;
            @BindView(R.id.imageViewReply)
            ImageView textViewReply;
            ItemViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

}
