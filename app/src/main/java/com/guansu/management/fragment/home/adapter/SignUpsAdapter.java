package com.guansu.management.fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.guansu.management.R;
import com.guansu.management.bean.ActivitySignUpsBean;
import com.guansu.management.common.OnClickListenerWrapper;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: dongyaoyao
 */
public class SignUpsAdapter extends RecyclerView.Adapter<SignUpsAdapter.MyViewHolder> {
    private Context mcontext;
    private List<ActivitySignUpsBean> signUpsBeans;
    public SignUpsAdapter(List<ActivitySignUpsBean> signUpsBeans,Context context) {
        this.signUpsBeans=signUpsBeans;
        this.mcontext=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signups, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActivitySignUpsBean signUpsBean=signUpsBeans.get(position);
        Glide.with(mcontext).load(signUpsBean.getProfileImageUrl()).error(R.mipmap.photo).into(holder.imageViewPhoto);
        holder.textViewName.setText(signUpsBean.getNickname());
        if ("HAS_SIGN_UP".equals(signUpsBean.getStatus())){
            holder.textViewStatus.setText("申请中");
        }else if ("HAS_AGREE".equals(signUpsBean.getStatus())){
            holder.textViewStatus.setText("已加入");
            holder.textViewRefuse.setVisibility(View.GONE);
            holder.textViewAgree.setVisibility(View.GONE);
        }else if ("HAS_REFUND".equals(signUpsBean.getStatus())){
            holder.textViewStatus.setText("已拒绝");
            holder.textViewRefuse.setVisibility(View.GONE);
            holder.textViewAgree.setVisibility(View.GONE);
        }
        holder.textViewAgree.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
            }
        });
    }
    @Override
    public int getItemCount() {
        return signUpsBeans != null ? signUpsBeans.size() : 0;
    }
    public class  MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.textViewAge)
        TextView textViewAge;
        @BindView(R.id.textViewRefuse)
        TextView textViewRefuse;
        @BindView(R.id.textViewAgree)
        TextView textViewAgree;
        @BindView(R.id.textViewStatus)
        TextView textViewStatus;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

