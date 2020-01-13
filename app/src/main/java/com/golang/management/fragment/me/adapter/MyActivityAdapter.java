package com.golang.management.fragment.me.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.golang.management.R;
import com.golang.management.bean.ImagesListBean;
import com.golang.management.bean.MyActivityBean;
import com.golang.management.fragment.home.adapter.ImageAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @date:
 * @author: dongyaoyao
 */
public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityAdapter.MyActivityViewHolder> {
    private List<MyActivityBean> myActivityBeans;
    private Context mcontext;
    int tage;
    public MyActivityAdapter(List<MyActivityBean> myActivityBeans, Context context,int tage) {
        this.myActivityBeans = myActivityBeans;
        this.mcontext = context;
        this.tage=tage;
    }
    @NonNull
    @Override
    public MyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        MyActivityViewHolder myViewHolder = new MyActivityViewHolder(view);
        return myViewHolder;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyActivityViewHolder holder, int position) {
        MyActivityBean homeBean = myActivityBeans.get(position);
        switch (tage) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                (holder).rButJoin.setVisibility(View.GONE);
                (holder).textViewCondition.setVisibility(View.GONE);
                ( holder).view1.setVisibility(View.GONE);
                (holder).textViewSo.setText("来自圈子");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    (holder).textViewSo.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            mcontext.getResources().getDrawable(R.mipmap.home_circle, null), null);
                }
                break;
        }
        Glide.with(mcontext).load(homeBean.getProfileImage()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into((holder).imageViewPhoto);
        (holder).textViewName.setText(homeBean.getNickName());
        (holder).textViewTime.setText(homeBean.getStartTime());
        (holder).textViewAge.setText(homeBean.getAge() + "岁");
        (holder).rButComment.setText(homeBean.getCommentCount() + "");
        (holder).rButWatch.setText(homeBean.getTraficCount() + "");
        (holder).rButJoin.setText(homeBean.getMaxPeopleNumber() + "");
        (holder).textViewDistance.setText("距离：" + Math.round(homeBean.getDistance() / 100d) / 10d + "km");
        if ("0".equals(homeBean.getVisible()+"")) {
            (holder).textViewSo.setText("活动成员:对外不可见");
        } else if ("1".equals(homeBean.getVisible()+"")) {
            (holder).textViewSo.setText("活动成员:对外可见");
        }
        if (homeBean.getSignUpCondition() != null) {
            String imglist = homeBean.getSignUpCondition().replace(",,", ",");
            String[] split = imglist.split(",");
            for (String spit : split) {
                if (!spit.equals("")) {
                    layoutFilterItem((holder).gridLayoutLevel, spit.replace("[", "").replace("]", ""));
                }
            }
        }
        if ("MALE".equals(homeBean.getSex())) {
            (holder).view.setBackground(mcontext.getResources().getDrawable(R.drawable.but_item_distance));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                (holder).textViewAge.setCompoundDrawablesWithIntrinsicBounds(mcontext.getResources().getDrawable(R.mipmap.male, null),
                        null, null, null);
            }
        } else {
            (holder).view.setBackground(mcontext.getResources().getDrawable(R.drawable.but_item_age));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                (holder).textViewAge.setCompoundDrawablesWithIntrinsicBounds(mcontext.getResources().getDrawable(R.mipmap.female, null),
                        null, null, null);
            }
        }
        (holder).textViewContext.setText(homeBean.getContent());
        List<ImagesListBean> img_list = homeBean.getImagesList();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mcontext, 3);
        (holder).rvPics.setLayoutManager(gridLayoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(img_list, mcontext);
        (holder).rvPics.setAdapter(imageAdapter);

    }

    private void layoutFilterItem(GridLayout gridLayoutLevel, String replace) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_home_laber, gridLayoutLevel, false);
        TextView mCheckBoxFilter = view.findViewById(R.id.textViewLaber);
        mCheckBoxFilter.setText(replace);
        gridLayoutLevel.addView(view);
    }

    @Override
    public int getItemCount() {
         return myActivityBeans != null ? myActivityBeans.size() : 0;
    }
    class MyActivityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPhoto)
        ImageView imageViewPhoto;
        @BindView(R.id.textViewName)
        TextView textViewName;
        @BindView(R.id.textViewAddress)
        TextView textViewAddress;
        @BindView(R.id.textViewTime)
        TextView textViewTime;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.textViewAge)
        TextView textViewAge;
        @BindView(R.id.textViewDistance)
        TextView textViewDistance;
        @BindView(R.id.rvPics)
        RecyclerView rvPics;
        @BindView(R.id.textViewContext)
        TextView textViewContext;
        @BindView(R.id.view1)
        View view1;
        @BindView(R.id.textViewCondition)
        TextView textViewCondition;
        @BindView(R.id.gridLayoutLevel)
        GridLayout gridLayoutLevel;
        @BindView(R.id.rButComment)
        RadioButton rButComment;
        @BindView(R.id.rButWatch)
        RadioButton rButWatch;
        @BindView(R.id.rButJoin)
        RadioButton rButJoin;
        @BindView(R.id.textViewSo)
        TextView textViewSo;
        MyActivityViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
