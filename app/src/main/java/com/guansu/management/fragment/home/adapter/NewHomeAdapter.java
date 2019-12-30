package com.guansu.management.fragment.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.guansu.management.R;
import com.guansu.management.bean.HomeBean;
import com.guansu.management.bean.ImagesListBean;
import com.guansu.management.model.bean.HomeBannerBean;
import com.guansu.management.wigdet.banner.ConvenientBanner;
import com.guansu.management.wigdet.banner.holder.CBViewHolderCreator;
import com.guansu.management.wigdet.banner.holder.Holder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date:
 * @author: dongyaoyao
 */
public class NewHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<HomeBean> homeBeanList = new ArrayList<>();
    private final int STATUS_UNVERIFIED = 1;
    private int status = STATUS_UNVERIFIED;
    private ItemClickListener mItemClickListener;
    private int tag;
    private List<HomeBean> list;

    public interface ItemClickListener {
        void OnItemClick(String id, int tag);
    }

    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public NewHomeAdapter(List<HomeBean> homeBeans, Context context, int page, int tag) {
        this.mcontext = context;
        this.tag = tag;
    }

    @Override
    public int getItemViewType(int position) {
        return homeBeanList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case -1:
                return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragement_new_home_title, parent, false));
            case -2:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragement_new_home_two, parent, false));
            default:
                return new ContextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false));
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeBean homeBean = homeBeanList.get(position);
        if (holder instanceof MyViewHolder) {
            ArrayList arrayList = new ArrayList<HomeBannerBean>();
            arrayList.add(new HomeBannerBean(R.mipmap.banner_1, "《真爱-梁祝》音乐剧场见面会"));
            arrayList.add(new HomeBannerBean(R.mipmap.banner_2, "《真爱-梁祝》话剧见面会"));
            arrayList.add(new HomeBannerBean(R.mipmap.banner_3, "肖战，王一博见面会"));
            ((MyViewHolder) holder).banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, arrayList)
                    .startTurning(3000)
                    .setCanLoop(false);
        } else if (holder instanceof ItemViewHolder) {
            switch (tag) {
                case 0:
                    ((ItemViewHolder) holder).radioGroup.check(((ItemViewHolder) holder).radioNewest.getId());
                    break;
                case 1:
                    ((ItemViewHolder) holder).radioGroup.check(((ItemViewHolder) holder).radioMove.getId());
                    break;
                case 2:
                    ((ItemViewHolder) holder).radioGroup.check(((ItemViewHolder) holder).radioCircle.getId());
                    break;
            }

            ((ItemViewHolder) holder).radioMove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick("-1", -1);
                }
            });
            ((ItemViewHolder) holder).radioCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick("0", -1);
                }
            });

        } else if (holder instanceof ContextViewHolder) {
            Glide.with(mcontext).load(homeBean.getProfileImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ContextViewHolder) holder).imageViewPhoto);
            ((ContextViewHolder) holder).textViewName.setText(homeBean.getNickName());
//            ((ContextViewHolder) holder).textViewAddress.setText(homeBean.getDistance());
            ((ContextViewHolder) holder).textViewTime.setText(homeBean.getStartTime());
            ((ContextViewHolder) holder).textViewAge.setText(homeBean.getAge() + "岁");
            ((ContextViewHolder) holder).rButComment.setText(homeBean.getCommentCount() + "");
            ((ContextViewHolder) holder).rButWatch.setText(homeBean.getTraficCount() + "");
            ((ContextViewHolder) holder).rButJoin.setText(homeBean.getMaxPeopleNumber() + "");
            ((ContextViewHolder) holder).textViewDistance.setText("距离：" + Math.round(homeBean.getDistance() / 100d) / 10d + "km");
            if (homeBean.getSignUpCondition() != null) {
                String imglist = homeBean.getSignUpCondition().replace(",,",",");
                String[] split = imglist.split(",");
                for (String spit : split) {
                    if (!spit.equals("")) {
                        layoutFilterItem(((ContextViewHolder) holder).gridLayoutLevel, spit.replace("[", "").replace("]", ""));
                    }
                }
            }
            switch (tag) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    ((ContextViewHolder) holder).rButJoin.setVisibility(View.GONE);
                    ((ContextViewHolder) holder).textViewCondition.setVisibility(View.GONE);
                    ((ContextViewHolder) holder).view1.setVisibility(View.GONE);
                    ((ContextViewHolder) holder).textViewSo.setText("来自圈子");
                    ((ContextViewHolder) holder).textViewSo.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            mcontext.getResources().getDrawable(R.mipmap.home_circle, null), null);
                    break;
            }
            if ("MALE".equals(homeBean.getSex())) {
                ((ContextViewHolder) holder).view.setBackground(mcontext.getResources().getDrawable(R.drawable.but_item_distance));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ContextViewHolder) holder).textViewAge.setCompoundDrawablesWithIntrinsicBounds(mcontext.getResources().getDrawable(R.mipmap.male, null),
                            null, null, null);
                }
            } else {
                ((ContextViewHolder) holder).view.setBackground(mcontext.getResources().getDrawable(R.drawable.but_item_age));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((ContextViewHolder) holder).textViewAge.setCompoundDrawablesWithIntrinsicBounds(mcontext.getResources().getDrawable(R.mipmap.female, null),
                            null, null, null);
                }
            }
            ((ContextViewHolder) holder).textViewContext.setText(homeBean.getContent());
            List<ImagesListBean> img_list = homeBean.getImagesList();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mcontext, 3);
            ((ContextViewHolder) holder).rvPics.setLayoutManager(gridLayoutManager);
            ImageAdapter imageAdapter = new ImageAdapter(img_list,mcontext);
            ((ContextViewHolder) holder).rvPics.setAdapter(imageAdapter);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(homeBean.getId() + "", tag);
                }
            });
        }
    }

    private void layoutFilterItem(GridLayout gridLayoutLevel, String replace) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_home_laber, gridLayoutLevel, false);
        TextView mCheckBoxFilter = view.findViewById(R.id.textViewLaber);
        mCheckBoxFilter.setText(replace);
        gridLayoutLevel.addView(view);
    }

    @Override
    public int getItemCount() {
        return homeBeanList != null ? homeBeanList.size() : 0;
    }

    public void setmList(List<HomeBean> homeBeans, int page) {
        if (homeBeans != null && page == 1) {
            initData(homeBeans);
        } else {
        }
        notifyDataSetChanged();
    }

    public void addmList(List<HomeBean> homeBeans, int page) {
        if (homeBeans != null && page == 1) {
            initData(homeBeans);
        } else {
            homeBeanList.addAll(homeBeans);
        }
        notifyDataSetChanged();
    }

    private void initData(List<HomeBean> homeBeans) {
        for (int i = 0; i < homeBeans.size() + 2; i++) {
            HomeBean homeBean = new HomeBean();
            if (i == 0) {
                homeBean.setType(-1);
                homeBean.setId(12);
                homeBean.setAge(12);
                homeBean.setCommentCount(12);
                homeBean.setContent("");
                homeBean.setDistance(0.1);
                homeBean.setMaxPeopleNumber(0);
                homeBean.setNickName("");
                homeBean.setProfileImage("");
                homeBean.setSignUpCondition("");
                homeBean.setSignUpPeopleNumber(1);
                homeBean.setStartTime("");
                homeBean.setTraficCount(1);
                homeBean.setVisible(1);
                homeBean.setSex("");
                homeBean.setImagesList(null);
            } else if (i == 1) {
                homeBean.setType(-2);
                homeBean.setId(12);
                homeBean.setAge(12);
                homeBean.setCommentCount(12);
                homeBean.setContent("");
                homeBean.setDistance(0.1);
                homeBean.setMaxPeopleNumber(0);
                homeBean.setNickName("");
                homeBean.setProfileImage("");
                homeBean.setSignUpCondition("");
                homeBean.setSignUpPeopleNumber(1);
                homeBean.setStartTime("");
                homeBean.setTraficCount(1);
                homeBean.setVisible(1);
                homeBean.setSex("");
                homeBean.setImagesList(null);
            } else {
                homeBean.setType(0);
                homeBean.setId(homeBeans.get(i - 2).getId());
                homeBean.setAge(homeBeans.get(i - 2).getAge());
                homeBean.setCommentCount(homeBeans.get(i - 2).getCommentCount());
                homeBean.setContent(homeBeans.get(i - 2).getContent());
                homeBean.setDistance(homeBeans.get(i - 2).getDistance());
                homeBean.setMaxPeopleNumber(homeBeans.get(i - 2).getMaxPeopleNumber());
                homeBean.setNickName(homeBeans.get(i - 2).getNickName());
                homeBean.setProfileImage(homeBeans.get(i - 2).getProfileImage());
                homeBean.setSignUpCondition(homeBeans.get(i - 2).getSignUpCondition());
                homeBean.setSignUpPeopleNumber(homeBeans.get(i - 2).getSignUpPeopleNumber());
                homeBean.setStartTime(homeBeans.get(i - 2).getStartTime());
                homeBean.setTraficCount(homeBeans.get(i - 2).getTraficCount());
                homeBean.setVisible(homeBeans.get(i - 2).getVisible());
                homeBean.setSex(homeBeans.get(i - 2).getSex());
                homeBean.setImagesList(homeBeans.get(i - 2).getImagesList());
            }
            this.homeBeanList.add(homeBean);
        }
    }

    class LocalImageHolderView implements Holder<HomeBannerBean> {
        private View view;
        private ImageView textViewBg;
        private TextView textViewTitle;

        @Override
        public View createView(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.home_view_title, null);
            textViewBg = view.findViewById(R.id.textViewBg);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, HomeBannerBean data) {
            Glide.with(mcontext).load(data.getBackground()).centerCrop().thumbnail(0.1f).into(textViewBg);
            textViewTitle.setText(data.getTitle());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view2)
        View view2;
        @BindView(R.id.imageBlack)
        TextView imageBlack;
        @BindView(R.id.imagePreservation)
        ImageButton imagePreservation;
        @BindView(R.id.banner)
        ConvenientBanner banner;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radioNewest)
        RadioButton radioNewest;
        @BindView(R.id.radioMove)
        RadioButton radioMove;
        @BindView(R.id.radioCircle)
        RadioButton radioCircle;
        @BindView(R.id.radioGroup)
        RadioGroup radioGroup;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.view1)
        View view1;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ContextViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.rButComment)
        RadioButton rButComment;
        @BindView(R.id.rButWatch)
        RadioButton rButWatch;
        @BindView(R.id.rButJoin)
        RadioButton rButJoin;
        @BindView(R.id.textViewSo)
        TextView textViewSo;
        @BindView(R.id.gridLayoutLevel)
        GridLayout gridLayoutLevel;

        public ContextViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }
}
