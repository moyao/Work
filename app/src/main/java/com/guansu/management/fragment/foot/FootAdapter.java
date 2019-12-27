package com.guansu.management.fragment.foot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guansu.management.R;
import com.guansu.management.bean.FootBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: dongyaoyao
 */
public class FootAdapter extends RecyclerView.Adapter<FootAdapter.FootViewHolder> {
    private Context mcontext;
    private List<FootBean> footBean;

    public FootAdapter(Context mcontext, List<FootBean> footBean) {
        this.mcontext = mcontext;
        this.footBean = footBean;
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
        FootBean footBeana = footBean.get(position);
        holder.textViewAddress.setText(footBeana.getAddress());
        holder.textViewTime.setText(footBeana.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return footBean.size();
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
