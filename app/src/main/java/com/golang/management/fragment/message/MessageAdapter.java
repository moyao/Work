package com.golang.management.fragment.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.golang.management.R;
import com.golang.management.bean.MessageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: dongyaoyao
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<MessageBean> messageBeansList;
    private int HTTP_URL=1;

    public MessageAdapter(Context mcontext, List<MessageBean> messageBeans, int HTTP_URL) {
        this.mcontext = mcontext;
        this.messageBeansList = messageBeans;
        this.HTTP_URL = HTTP_URL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (1 == HTTP_URL) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragemnet_comment_item, parent, false));
        } else if (2 == HTTP_URL) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_item, parent, false));
        } else {
            return new SystemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragement_message_system, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageBean messageBean = messageBeansList.get(position);
        if (holder instanceof MyViewHolder) {
            Glide.with(mcontext).load(messageBean.getProfileImageUrl()).centerCrop().error(R.mipmap.load_icon)
                    .into(((MyViewHolder) holder).imageViewPhoto);
            ((MyViewHolder) holder).textViewName.setText(messageBean.getNickname());
            ((MyViewHolder) holder).textViewTime.setText(messageBean.getCreatedAt());
            ((MyViewHolder) holder).textViewComment.setText(messageBean.getTargetUserNickname()+"回复你");

        } else if (holder instanceof ItemViewHolder) {
            Glide.with(mcontext).load(messageBean.getProfileImageUrl()).centerCrop()
                    .into(((ItemViewHolder) holder).imageViewPhoto);
            ((ItemViewHolder) holder).textViewName.setText(messageBean.getNickname());
            ((ItemViewHolder) holder).textViewTime.setText(messageBean.getCreatedAt());
            ((ItemViewHolder) holder).textViewParticipate.setText(messageBean.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageBeansList != null ? messageBeansList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewComment)
        TextView textViewComment;
        @BindView(R.id.textViewParticipate)
        TextView textViewParticipate;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.textViewDetails)
        TextView textViewDetails;
        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewParticipate)
        TextView textViewParticipate;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.textViewDetails)
        TextView textViewDetails;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class SystemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewContext)
        TextView textViewContext;
        @BindView(R.id.textViewTime)
        TextView textViewTime;

        SystemViewHolder(View view) {
            super(view);
        }
    }
}
