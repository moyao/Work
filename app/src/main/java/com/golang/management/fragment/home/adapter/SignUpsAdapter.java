package com.golang.management.fragment.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.golang.management.R;
import com.golang.management.bean.ActivitySignUpsBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.HttpConstants;
import com.golang.management.model.FriendModellml;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: dongyaoyao
 */
public class SignUpsAdapter extends RecyclerView.Adapter<SignUpsAdapter.MyViewHolder> {
    private Context mcontext;
    private List<ActivitySignUpsBean> signUpsBeans;
    private String activityid;
    private UserSharedPreferencesUtils userSharedPreferencesUtils;

    public SignUpsAdapter(List<ActivitySignUpsBean> signUpsBeans, String activityid, Context context) {
        this.signUpsBeans = signUpsBeans;
        this.mcontext = context;
        this.activityid = activityid;
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
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(mcontext);
        ActivitySignUpsBean signUpsBean = signUpsBeans.get(position);
        Glide.with(mcontext).load(signUpsBean.getProfileImageUrl())
                .error(R.mipmap.photo).centerCrop().into(holder.imageViewPhoto);
        holder.textViewName.setText(signUpsBean.getNickname());
        if ("HAS_SIGN_UP".equals(signUpsBean.getStatus())) {
            if (activityid.equals(userSharedPreferencesUtils.getUserid())) {
                holder.textViewAgree.setVisibility(View.VISIBLE);
                holder.textViewRefuse.setVisibility(View.VISIBLE);
                holder.textViewStatus.setVisibility(View.GONE);
            } else {
                holder.textViewAgree.setVisibility(View.GONE);
                holder.textViewRefuse.setVisibility(View.GONE);
                holder.textViewStatus.setVisibility(View.VISIBLE);
                holder.textViewStatus.setText("申请中");
            }
        } else if ("HAS_AGREE".equals(signUpsBean.getStatus())) {
            holder.textViewStatus.setText("已加入");
            holder.textViewRefuse.setVisibility(View.GONE);
            holder.textViewAgree.setVisibility(View.GONE);
        } else if ("HAS_REFUND".equals(signUpsBean.getStatus())) {
            holder.textViewStatus.setText("已拒绝");
            holder.textViewRefuse.setVisibility(View.GONE);
            holder.textViewAgree.setVisibility(View.GONE);
        }
        holder.textViewAgree.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                Map<String, Object> httpParams = new HashMap<>();
                httpParams.put("userId", userSharedPreferencesUtils.getUserid());
                httpParams.put("SignUpStatusEnum", "HAS_AGREE");
                httpParams.put("id", signUpsBean.getId());
                httpParams.put("signUpUserId", signUpsBean.getUserId());
                httpParams.put("activityId", signUpsBean.getActivityId());
                JSONObject jsonObject = new JSONObject(httpParams);
                OkGo.<String>post(HttpConstants.BASE_URL + FriendModellml.USER_EDITSIGNUPUSER)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    if ("0000000".equals(jsonObject.getString("code"))) {
                                        holder.textViewStatus.setVisibility(View.VISIBLE);
                                        holder.textViewStatus.setText("已加入");
                                        holder.textViewRefuse.setVisibility(View.GONE);
                                        holder.textViewAgree.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(mcontext, jsonObject.getString("msg"), 0);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
            }
        });
        holder.textViewRefuse.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                Map<String, Object> httpParams = new HashMap<>();
                httpParams.put("userId", userSharedPreferencesUtils.getUserid());
                httpParams.put("SignUpStatusEnum", "HAS_REFUND");
                httpParams.put("id", signUpsBean.getId());
                httpParams.put("signUpUserId", signUpsBean.getUserId());
                httpParams.put("activityId", signUpsBean.getActivityId());
                JSONObject jsonObject = new JSONObject(httpParams);
                OkGo.<String>post(HttpConstants.BASE_URL + FriendModellml.USER_EDITSIGNUPUSER)
                        .upJson(jsonObject)
                        .execute(new StringCallback() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    if ("0000000".equals(jsonObject.getString("code"))) {
                                        holder.textViewStatus.setVisibility(View.VISIBLE);
                                        holder.textViewStatus.setText("已拒绝");
                                        holder.textViewRefuse.setVisibility(View.GONE);
                                        holder.textViewAgree.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(mcontext, jsonObject.getString("msg"), 0);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public int getItemCount() {
        return signUpsBeans != null ? signUpsBeans.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

