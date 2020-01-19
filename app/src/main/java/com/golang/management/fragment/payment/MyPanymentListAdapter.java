package com.golang.management.fragment.payment;

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
import com.golang.management.bean.PaymentBean;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author: dongyaoyao
 */
public class MyPanymentListAdapter extends RecyclerView.Adapter<MyPanymentListAdapter.MyViewHolder> {
    private Context mcontext;
    private List<PaymentBean> paymentBeans;
    public MyPanymentListAdapter(Context mcontext, List<PaymentBean> paymentBeans) {
        this.mcontext = mcontext;
        this.paymentBeans = paymentBeans;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_panyent_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PaymentBean paymentBean = paymentBeans.get(position);
        if ("金卡会员".equals(paymentBean.getExplanation())
                ||"创客".equals(paymentBean.getExplanation())){
            Glide.with(mcontext).load(R.mipmap.image_maker).into(holder.imageViewPhoto);
            holder.textViewTitle.setText("创客会员认证");
        }else {
            Glide.with(mcontext).load(R.mipmap.image_operator).into(holder.imageViewPhoto);
            holder.textViewTitle.setText("运营商资格认证");
        }
        holder.textViewOrderNumber.setText("订单号："+paymentBean.getOrderNo());
        holder.textViewTime.setText("日期："+paymentBean.getPaymentTime());
        holder.textViewMoney.setText("￥"+paymentBean.getPaymentFee());
        holder.textViewPayment.setText(paymentBean.getPaymentStatus());
    }
    @Override
    public int getItemCount() {
        return paymentBeans != null ? paymentBeans.size() : 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewOrderNumber)
        TextView textViewOrderNumber;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.textViewMoney)
        TextView textViewMoney;
        @BindView(R.id.textViewPayment)
        TextView textViewPayment;
        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
