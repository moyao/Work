package com.golang.management.fragment.details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.ActivityCommentsBeanX;
import com.golang.management.bean.ActivityDtoInfo;
import com.golang.management.bean.ActivitySignUpsBean;
import com.golang.management.bean.ImagesListBean;
import com.golang.management.common.OnClickListenerWrapper;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.config.Constant;
import com.golang.management.config.Constants;
import com.golang.management.api.MyObserve;
import com.golang.management.fragment.home.adapter.CommentAdapter;
import com.golang.management.fragment.home.adapter.ImageAdapter;
import com.golang.management.fragment.home.adapter.NewHomeAdapter;
import com.golang.management.fragment.home.adapter.SignUpsAdapter;
import com.golang.management.fragment.me.PersonalFragment;
import com.golang.management.model.FriendModellml;
import com.golang.management.model.MessageModellml;
import com.golang.management.utils.KeyboardStateObserver;
import com.golang.management.utils.StringHandler;
import com.golang.management.wigdet.CommonTitleBar;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendRequest;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMFriendStatus;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class DetailsFragment extends BaseFragment implements CommentAdapter.ItemClickListener {
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewAddress)
    TextView textViewAddress;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.view)
    View Ageview;
    @BindView(R.id.rvPics)
    RecyclerView rvPics;
    @BindView(R.id.textViewContext)
    TextView textViewContext;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.rButComment)
    RadioButton rButComment;
    @BindView(R.id.rButWatch)
    RadioButton rButWatch;
    @BindView(R.id.rButJoin)
    RadioButton rButJoin;
    @BindView(R.id.radioComment)
    RadioButton radioComment;
    @BindView(R.id.radioMember)
    RadioButton radioMember;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.textViewAge)
    TextView textViewAge;
    @BindView(R.id.textViewDistance)
    TextView textViewDistance;
    @BindView(R.id.textViewSo)
    TextView textViewSo;
    @BindView(R.id.butComment)
    Button butComment;
    @BindView(R.id.butActivity)
    Button butActivity;
    @BindView(R.id.editTextContext)
    EditText editTextContext;
    @BindView(R.id.textVIewSend)
    TextView textVIewSend;
    @BindView(R.id.textViewCondition)
    TextView textViewCondition;
    private final int STATUS_UNVERIFIED = 1;
    private final int STATUS_VERIFIED = 2;
    @BindView(R.id.gridLayoutLevel)
    GridLayout gridLayoutLevel;
    private int status = STATUS_UNVERIFIED;
    private int SEND = 0;
    ActivityDtoInfo activityDtoInfo;
    List<ActivityCommentsBeanX> commentsBeans;
    List<ActivitySignUpsBean> signUpsBeans;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    private String userId, objectId, parentId, targetUserNickname;
    ImageAdapter imageAdapter;

    public static DetailsFragment newInstance(String id, String title) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_TYPE, id);
        args.putString(Constants.KEY_TITLE, title);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_activity_details;
    }

    @Override
    public void initView(View view) {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        setTitle("详情列表");
        initApi();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
        mTitlebar.setRightType(CommonTitleBar.TYPE_IMAGEBUTTON_SEARCHVIEW);
        mTitlebar.getRightImageButton().setImageResource(R.mipmap.icon_add_friends);
        mTitlebar.getRightImageButton().setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                friendData();
            }
        });
    }

    private void friendData() {
        TIMFriendRequest timFriendRequest = new TIMFriendRequest(userSharedPreferencesUtils.getAccount());
        timFriendRequest.setAddSource("android");
        TIMFriendshipManager.getInstance().addFriend(timFriendRequest, new TIMValueCallBack<TIMFriendResult>() {
            @Override
            public void onError(int i, String s) {
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(TIMFriendResult timFriendResult) {
                switch (timFriendResult.getResultCode()) {
                    case TIMFriendStatus.TIM_FRIEND_STATUS_SUCC:
                        ToastUtil.toastShortMessage("成功");
                        successData();
                        break;
                    case TIMFriendStatus.TIM_FRIEND_PARAM_INVALID:
                        if (TextUtils.equals(timFriendResult.getResultInfo(), "Err_SNS_FriendAdd_Friend_Exist")) {
                            ToastUtil.toastShortMessage("对方已是您的好友");
                            successData();
                            break;
                        }
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_SELF_FRIEND_FULL:
                        ToastUtil.toastShortMessage("您的好友数已达系统上限");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_THEIR_FRIEND_FULL:
                        ToastUtil.toastShortMessage("对方的好友数已达系统上限");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                        ToastUtil.toastShortMessage("被加好友在自己的黑名单中");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                        ToastUtil.toastShortMessage("对方已禁止加好友");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                        ToastUtil.toastShortMessage("您已被被对方设置为黑名单");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_PENDING:
                        ToastUtil.toastShortMessage("等待好友审核同意");
                        break;
                    default:
                        ToastUtil.toastLongMessage(timFriendResult.getResultCode() + " " + timFriendResult.getResultInfo());
                        break;
                }

            }
        });
    }

    private void successData() {
        new FriendModellml().add_friend(userSharedPreferencesUtils.getUserid(),
                activityDtoInfo.getUserId())
                .safeSubscribe(new MyObserve<String>(this) {
                    @Override
                    protected void onSuccess(String s) {
                        showToast("添加成功");
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindEvent() {
        initDetails();
        radioGroup.check(radioComment.getId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.radioComment == checkedId) {
                    status = STATUS_UNVERIFIED;
                    getDataComment();
                } else {
                    status = STATUS_VERIFIED;
                    getDataSignUpps();
                }
            }
        });
        butActivity.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                initDatabut();
            }
        });
        butComment.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                initDataComment();
            }
        });
        textVIewSend.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                switch (SEND) {
                    case 0:
                        objectId = getArguments().get(Constants.KEY_TYPE) + "";
                        targetUserNickname = userSharedPreferencesUtils.getUserid();
                        parentId = activityDtoInfo.getUserId();
                        if (!StringHandler.hasNull(editTextContext.getText().toString())) {
                            initDataSend();
                        } else {
                            showToast("请输入您要评论的内容");
                        }

                        break;
                    case 1:
                        if (!StringHandler.hasNull(editTextContext.getText().toString())) {
                            initDataSend();
                        } else {
                            showToast("请输入您要评论的内容");
                        }
                        break;
                }
            }
        });
        KeyboardStateObserver.getKeyboardStateObserver((Activity) getContext()).
                setKeyboardVisibilityListener(new KeyboardStateObserver.OnKeyboardVisibilityListener() {
                    @Override
                    public void onKeyboardShow() {
                    }

                    @Override
                    public void onKeyboardHide() {
                        butComment.setVisibility(View.VISIBLE);
                        if (getArguments().getString(Constants.KEY_TITLE).equals(Constant.VIEW_CIRCLE)) {
                            butActivity.setVisibility(View.GONE);
                        } else {
                            butActivity.setVisibility(View.VISIBLE);
                        }
                        editTextContext.setVisibility(View.GONE);
                        textVIewSend.setVisibility(View.GONE);
                    }
                });
    }

    @SuppressLint("NewApi")
    private void initDetails() {
        if (getArguments().getString(Constants.KEY_TITLE).equals(Constant.VIEW_CIRCLE)) {
            view1.setVisibility(View.GONE);
            textViewCondition.setVisibility(View.GONE);
            rButJoin.setVisibility(View.GONE);
            radioMember.setVisibility(View.GONE);
            butActivity.setVisibility(View.GONE);
            textViewSo.setText("来自圈子");
            textViewSo.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(R.mipmap.home_circle, null), null);
        }
        showLoadingDialog("加载中...");
        new MessageModellml().find_activity_dtoinfo(userSharedPreferencesUtils.getUserid(),
                getArguments().getString(Constants.KEY_TYPE), getArguments().getString(Constants.KEY_TITLE))
                .safeSubscribe(new MyObserve<ActivityDtoInfo>(this) {
                    protected void onSuccess(ActivityDtoInfo userInfos) {
                        showPage();
                        activityDtoInfo = userInfos;
                        Glide.with(getContext()).load(userInfos.getProfileImage())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageViewPhoto);
                        textViewName.setText(userInfos.getNickName());
                        textViewTime.setText(userInfos.getStartTime());
                        textViewAge.setText(userInfos.getAge() + "岁");
                        List<ImagesListBean> imglist = userInfos.getImagesList();
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                        rvPics.setLayoutManager(gridLayoutManager);
                        imageAdapter = new ImageAdapter(imglist, getContext());
                        rvPics.setAdapter(imageAdapter);

                        textViewContext.setText(userInfos.getContent() + "");
                        rButComment.setText(userInfos.getCommentCount() + "");
                        rButWatch.setText(userInfos.getTraficCount() + "");
                        rButJoin.setText(userInfos.getSignUpPeopleNumber() + "/" + userInfos.getMaxPeopleNumber());
                        if (getArguments().getString(Constants.KEY_TITLE).equals(Constant.VIEW_CIRCLE)) {
                            textViewSo.setText("来自圈子");
                        } else {
                            if ("0".equals(userInfos.getVisible() + "")) {
                                textViewSo.setText("活动成员:对外不可见");
                            } else if ("1".equals(userInfos.getVisible() + "")) {
                                textViewSo.setText("活动成员:对外可见");
                            }
                            String imlist = userInfos.getSignUpCondition();
                            String[] split = imlist.split(",");
                            for (String spit : split) {
                                layoutFilterItem(gridLayoutLevel, spit.replace("[", "").replace("]", ""));
                            }
                        }
                        if ("MALE".equals(userInfos.getSex())) {
                            Ageview.setBackground(getResources().getDrawable(R.drawable.but_item_distance));
                            textViewAge.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.male, null),
                                    null, null, null);
                        } else {
                            Ageview.setBackground(getResources().getDrawable(R.drawable.but_item_age));
                            textViewAge.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.female, null),
                                    null, null, null);
                        }
                        commentsBeans = userInfos.getActivityComments();
                        getDataComment();
                        signUpsBeans = userInfos.getActivitySignUps();
                        imageViewPhoto.setOnClickListener(new OnClickListenerWrapper() {
                            @Override
                            protected void onSingleClick(View v) {
                                start(PersonalFragment.newInstance(userInfos.getUserId() + ""));
                            }
                        });
                    }
                });
    }

    private void layoutFilterItem(GridLayout gridLayoutLevel, String replace) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_laber, gridLayoutLevel, false);
        TextView mCheckBoxFilter = view.findViewById(R.id.textViewLaber);
        mCheckBoxFilter.setText(replace);
        gridLayoutLevel.addView(view);
    }

    /**
     * 发送评论
     */
    public void initDataSend() {
        showLoadingDialog("发表中……");
        new MessageModellml().find_activity_commentsave(userSharedPreferencesUtils.getUserid(),
                objectId, editTextContext.getText().toString(),
                parentId, targetUserNickname,
                getArguments().getString(Constants.KEY_TITLE))
                .safeSubscribe(new MyObserve<String>(this) {
                    @Override
                    protected void onSuccess(String s) {
                        if (getArguments().getString(Constants.KEY_TITLE).equals(Constant.VIEW_CIRCLE)) {
                            butActivity.setVisibility(View.GONE);
                        } else {
                            butActivity.setVisibility(View.VISIBLE);
                        }
                        butComment.setVisibility(View.VISIBLE);
                        editTextContext.setVisibility(View.GONE);
                        textVIewSend.setVisibility(View.GONE);
                        showPage();
                        SEND = 0;
                        editTextContext.setText("");
                        initDetails();
                    }
                });
    }

    /**
     * 底部控件显示与隐藏
     */
    private void initDataComment() {
        butComment.setVisibility(View.GONE);
        butActivity.setVisibility(View.GONE);
        editTextContext.setVisibility(View.VISIBLE);
        textVIewSend.setVisibility(View.VISIBLE);
    }

    /**
     * 活动报名
     */
    private void initDatabut() {
        showLoadingDialog("报名中……");
        new MessageModellml().find_activity_signupsave(userSharedPreferencesUtils.getUserid(),
                getArguments().getString(Constants.KEY_TYPE))
                .safeSubscribe(new MyObserve<String>(this) {
                    @Override
                    protected void onSuccess(String activityDtoInfo) {
                        showPage();
                        showToast("报名成功");
                        initDetails();
                    }
                });
    }

    /**
     * 报名列表
     */
    private void getDataSignUpps() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        SignUpsAdapter signUpsAdapter = new SignUpsAdapter(signUpsBeans, activityDtoInfo.getVisible() + "", activityDtoInfo.getUserId(), getContext());
        recycler.setAdapter(signUpsAdapter);
    }

    /**
     * 评论列表
     */
    private void getDataComment() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter = new CommentAdapter(activityDtoInfo, getContext());
        recycler.setAdapter(commentAdapter);
        commentAdapter.setItemClickListener(this);
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    @Override
    public void OnItemClick(ActivityCommentsBeanX comments, int tag) {
        switch (tag) {
            case 0:
                parentId = comments.getId();
                break;
            case 1:
                parentId = comments.getParentId();
                break;
        }
        SEND = 1;
        objectId = comments.getObjectId();
        targetUserNickname = comments.getTargetNickname();
        initDataComment();
    }
}
