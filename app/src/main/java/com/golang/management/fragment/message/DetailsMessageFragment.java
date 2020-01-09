package com.golang.management.fragment.message;
import android.os.Bundle;
import android.view.View;
import com.golang.management.R;
import com.golang.management.base.BaseFragment;
import com.golang.management.config.Constants;
import com.golang.management.helper.ChatLayoutHelper;
import com.tencent.qcloud.tim.uikit.component.AudioPlayer;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;

import butterknife.BindView;

/**
 * @author: dongyaoyao
 */
public class DetailsMessageFragment extends BaseFragment {
    @BindView(R.id.chat_layout)
    ChatLayout mChatLayout;
    private TitleBarLayout mTitleBar;
    private ChatInfo mChatInfo;
    public static DetailsMessageFragment newInstance(ChatInfo chatInfo,String NickName) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.CHAT_INFO, chatInfo);
        args.putString(Constants.KEY_TITLE,NickName);
        DetailsMessageFragment fragment = new DetailsMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int onSetLayoutId() {
        return R.layout.fragement_details_message;
    }

    @Override
    public void initView(View view) {
        setTitle(getArguments().getString(Constants.KEY_TITLE));
        mTitlebar.showStatusBar(true);
        mTitlebar.setBackgroundResource(R.drawable.but_release);
    }
    @Override
    public void bindEvent() {
        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mChatLayout.getMessageLayout().setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }
            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo) {
                    return;
                }
                ChatInfo info = new ChatInfo();
                info.setId(messageInfo.getFromUser());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // TODO 通过api设置ChatLayout各种属性的样例
        mChatInfo = (ChatInfo) getArguments().getSerializable(Constants.CHAT_INFO);
        if (mChatInfo == null) {
            return;
        }
        initMessageData();
        ChatLayoutHelper helper = new ChatLayoutHelper(getActivity());
        helper.customizeChatLayout(mChatLayout);
        //从布局文件中获取聊天面板组件
    }
    private void initMessageData() {
        mChatLayout.initDefault();
        mChatLayout.setChatInfo(mChatInfo);
        mTitleBar = mChatLayout.getTitleBar();
        mTitleBar.setVisibility(View.GONE);
    }
    @Override
    public void onPause() {
        super.onPause();
        AudioPlayer.getInstance().stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatLayout != null) {
            mChatLayout.exitChat();
        }
    }
    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
