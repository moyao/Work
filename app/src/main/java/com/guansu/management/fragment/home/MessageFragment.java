package com.guansu.management.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guansu.management.R;
import com.guansu.management.base.BaseFragment;
import com.guansu.management.bean.UserInfo;
import com.guansu.management.fragment.MainFragment;
import com.guansu.management.fragment.me.PersonalFragment;
import com.guansu.management.fragment.message.DetailsMessageFragment;
import com.guansu.management.fragment.message.MessageAdapter;
import com.guansu.management.wigdet.recyclerview.EndLessOnScrollListener;
import com.guansu.management.wigdet.recyclerview.OnItemClickListener;
import com.guansu.management.wigdet.recyclerview.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class MessageFragment extends BaseFragment {
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
    private int status = STATUS_UNVERIFIED;
    private MessageAdapter messageAdapter;
    private EndLessOnScrollListener endLessOnScrollListener;
    private List<UserInfo> userInfos;

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
        hideTitle();
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);

    }

    @Override
    public void bindEvent() {
        setLoadingContentView(rvListMessage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListMessage.setLayoutManager(layoutManager);
        rgStatus.setOnCheckedChangeListener(ChangeRadioGroup);
        rgStatus.check(rbComment.getId());
        Data();
      /*  rvListMessage.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL_LINE,
                10, R.color.color_B5B5B5));*/

        /*endLessOnScrollListener = new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getData(currentPage);
            }
        };
        rvListMessage.addOnScrollListener(endLessOnScrollListener);*/
//        getData(1);
        rvListMessage.addOnItemTouchListener(new RecyclerItemClickListener(
                new ListOnItemClickListener()));

    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }

    private void Data() {
        showLoadingPage();
        messageAdapter = new MessageAdapter(getContext(), 10, status);
        rvListMessage.setAdapter(messageAdapter);
        showPage();
    }

    private class ListOnItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {

            switch (status) {
                case 1:
                    ((MainFragment) getParentFragment()).start(PersonalFragment.newInstance());
                    break;
                case 2:
                    ((MainFragment)getParentFragment()).start(DetailsMessageFragment.newInstance());
                    break;
            }
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }
        /**
         * 数据请求
         */
        private void getData(int currentPage) {
           /* new MessageModellml().Comment("", "").safeSubscribe(new MyObserve<List<UserInfo>>(this) {
                @Override
                protected void onSuccess(List<UserInfo> loginResults) {
                    userInfos = loginResults;
//                messageAdapter.setmList(userInfos);//刷新
//                messageAdapter.addmList(userInfos);//加载更多
                }
            });*/
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
    }
