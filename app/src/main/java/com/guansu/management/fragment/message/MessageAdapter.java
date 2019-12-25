package com.guansu.management.fragment.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.guansu.management.R;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
   /* private int getGroupBeans() {
        if (groupBeans != null) {
            return groupBeans;
        }
        return null;
    }*/

    private int groupBeans;
    private int HTTP_URL;
    public MessageAdapter(Context mcontext, int groupBeans, int HTTP_URL) {
        this.mcontext = mcontext;
        this.groupBeans = groupBeans;
        this.HTTP_URL = HTTP_URL;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (1 == HTTP_URL) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragemnet_comment_item, parent, false));
        } else if (2==HTTP_URL){
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_item, parent, false));
        }else {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragement_message_system, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return groupBeans;
    }

    /*  @Override
      public int getItemCount() {
          return groupBeans != null ? groupBeans.size() : 0;
      }

      public void setmList(List<UserInfo> rows) {
          this.groupBeans = rows;
          notifyDataSetChanged();
      }
      public void addmList(List<UserInfo> rows) {
          groupBeans.addAll(rows);
          notifyDataSetChanged();
      }*/
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    public class ItemViewHolder extends MyViewHolder {
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
//            ButterKnife.bind(this, view);
        }
    }
}
