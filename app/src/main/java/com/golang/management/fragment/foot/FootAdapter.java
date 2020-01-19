package com.golang.management.fragment.foot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.golang.management.R;
import com.golang.management.bean.FootBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: dongyaoyao
 */
public class FootAdapter extends RecyclerView.Adapter<FootAdapter.FootViewHolder> {
    private Context mcontext;
    private List<FootBean> footBeanList;

    public FootAdapter(Context mcontext, List<FootBean> footBean) {
        this.mcontext = mcontext;
        this.footBeanList = footBean;
    }

    @NonNull
    @Override
    public FootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
        FootViewHolder myViewHolder = new FootViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FootViewHolder holder, int position) {
        FootBean footBeana = footBeanList.get(position);
        holder.textViewAddress.setText(footBeana.getAddress());
        holder.textViewTime.setText(footBeana.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return footBeanList != null ? footBeanList.size() : 0;
    }

    public void addData(FootBean footBean, int position) {
//      在list中添加数据，并通知条目加入一条
        footBeanList.add(position, footBean);
        //添加动画
        notifyDataSetChanged();
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewAddress)
        TextView textViewAddress;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.textViewCircle)
        TextView textViewCircle;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
