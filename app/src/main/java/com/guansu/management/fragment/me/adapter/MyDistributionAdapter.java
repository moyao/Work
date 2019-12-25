package com.guansu.management.fragment.me.adapter;
import android.annotation.SuppressLint;
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
import com.guansu.management.bean.MyDistributionBean;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @date:
 * @author: dongyaoyao
 */
public class MyDistributionAdapter extends RecyclerView.Adapter<MyDistributionAdapter.MyDistributionViewHolder> {
    private Context mcontext;
    private List<MyDistributionBean> myDistributionBeans;

    public MyDistributionAdapter(Context mcontext,List<MyDistributionBean> myDistributionBeans) {
        this.mcontext = mcontext;
        this.myDistributionBeans = myDistributionBeans;
    }
    @NonNull
    @Override
    public MyDistributionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_distribution, parent, false);
        MyDistributionViewHolder myViewHolder = new MyDistributionViewHolder(view);
        return myViewHolder;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyDistributionViewHolder holder, int position) {
        MyDistributionBean myDistributionBean = myDistributionBeans.get(position);
        Glide.with(mcontext).load(myDistributionBean.getProfileImageUrl()).into(holder.imageViewPhoto);
        holder.textViewName.setText(myDistributionBean.getNickname());
        holder.textViewTime.setText(myDistributionBean.getCreatedAt());
        if ("MERCHANT".equals(myDistributionBean.getDisLevel())){
            holder.textViewGrade.setText("运营商");
            holder.textViewGrade.setTextColor(R.color.color_51A375);
        }else if ("PUKA_MEMBER".equals(myDistributionBean.getDisLevel())){
            holder.textViewGrade.setText("用户");
            holder.textViewGrade.setTextColor(R.color.color_6B6B6B);
        }else {
            holder.textViewGrade.setText("创客");
        }
    }
    @Override
    public int getItemCount() {
        return myDistributionBeans.size();
    }

    public class MyDistributionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.textViewGrade)
        TextView textViewGrade;

        public MyDistributionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
