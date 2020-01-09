package com.golang.management.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.golang.management.R;
import com.golang.management.api.MyObserve;
import com.golang.management.base.BaseFragment;
import com.golang.management.bean.MessageBean;
import com.golang.management.bean.UserInfo;
import com.golang.management.common.UserSharedPreferencesUtils;
import com.golang.management.api.ServiceException;
import com.golang.management.config.HttpConstants;
import com.golang.management.fragment.MainFragment;
import com.golang.management.fragment.me.PersonalFragment;
import com.golang.management.fragment.message.DetailsMessageFragment;
import com.golang.management.fragment.message.MessageAdapter;
import com.golang.management.model.FriendModellml;
import com.golang.management.wigdet.recyclerview.EndLessOnScrollListener;
import com.golang.management.wigdet.recyclerview.OnItemClickListener;
import com.golang.management.wigdet.recyclerview.RecyclerItemClickListener;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.util.List;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class MessageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rbComment)
    RadioButton rbComment;
    @BindView(R.id.rbFriend)
    RadioButton rbFriend;
    @BindView(R.id.rbSystem)
    RadioButton rbSystem;
    @BindView(R.id.rgStatus)
    RadioGroup rgStatus;
    @BindView(R.id.rvListMessage)
    RecyclerView rvListMessage;
    private final int STATUS_UNVERIFIED = 1;
    private final int STATUS_VERIFIED = 2;
    private final int STATUS_SYSTEM = 3;
    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout layoutSwipeRefresh;
    private int status = STATUS_UNVERIFIED;
    private MessageAdapter messageAdapter;
    private EndLessOnScrollListener endLessOnScrollListener;
    private List<UserInfo> userInfos;
    UserSharedPreferencesUtils userSharedPreferencesUtils;
    List<MessageBean> ListmessageBeans;

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_message;
    }

    @Override
    public void initView(View view) {
        initApi();
        hideTitle();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }

    @Override
    public void bindEvent() {
        userSharedPreferencesUtils = new UserSharedPreferencesUtils(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setLayoutManager(layoutManager);
        rvListMessage.addOnItemTouchListener(new RecyclerItemClickListener(
                new ListOnItemClickListener()));
        setLoadingContentView(layoutSwipeRefresh);
        layoutSwipeRefresh.setOnRefreshListener(this);
        rgStatus.setOnCheckedChangeListener(ChangeRadioGroup);
        rgStatus.check(rbComment.getId());
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    private void Data() {
        showLoadingPage();
        new FriendModellml().user_friend(userSharedPreferencesUtils.getUserid())
                .safeSubscribe(new MyObserve<List<MessageBean>>(this) {
                    @Override
                    protected void onSuccess(List<MessageBean> messageBeans) {
                        showPage();
                        if (0 == messageBeans.size()) {
                            showNoData();
                        } else {
                            ListmessageBeans = messageBeans;
                            messageAdapter = new MessageAdapter(getContext(), messageBeans, status);
                            rvListMessage.setAdapter(messageAdapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof ServiceException && ((ServiceException) e).code.equals(HttpConstants.SUCCESS_CODE)) {
                            showNoData();
                        } else {
                            showError(e);
                        }
                    }
                });
    }

    private class ListOnItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            switch (status) {
                case 1:
                    ((MainFragment) getParentFragment()).start(PersonalFragment.newInstance(""));
                    break;
                case 2:
                    ChatInfo chatInfo = new ChatInfo();
                    chatInfo.setType(TIMConversationType.C2C);
                    chatInfo.setId(ListmessageBeans.get(position).getMobileNumber() + "");
                    chatInfo.setChatName(ListmessageBeans.get(position).getMobileNumber() + "");
                    ((MainFragment) getParentFragment()).start(DetailsMessageFragment.newInstance(chatInfo, ListmessageBeans.get(position).getNickname()));
                    break;
            }
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }

    private RadioGroup.OnCheckedChangeListener ChangeRadioGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (R.id.rbComment == checkedId) {
                status = STATUS_UNVERIFIED;
            } else if (checkedId == rbFriend.getId() && rbFriend.isChecked()) {
                status = STATUS_VERIFIED;

            } else if (checkedId == rbSystem.getId() && rbSystem.isChecked()) {
                status = STATUS_SYSTEM;
            }
            Data();
        }
    };

    @Override
    protected void retryloading() {
        Data();
    }

    @Override
    public void onRefresh() {
        layoutSwipeRefresh.setRefreshing(false);
        Data();
    }
}
