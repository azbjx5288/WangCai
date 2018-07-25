package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.Notice;
import com.wangcai.lottery.data.NoticeDetail;
import com.wangcai.lottery.data.NoticeDetailCommand;
import com.wangcai.lottery.data.NoticeList;
import com.wangcai.lottery.user.UserCentre;

import butterknife.BindView;

/**
 * 公告或banner的详情页
 * Created by Alashi on 2016/1/19.
 */
public class NoticeDetailsFragment extends BaseFragment {

    private static final int PLACARD_TRACE_ID = 1;
    private static final int NOTICE_TRACE_ID = 2;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;

    private UserCentre userCentre;
    private NoticeList noticeList;
    private Notice notice;

    public static void launch(BaseFragment fragment, NoticeList noticeList) {
        Bundle bundle = new Bundle();
        bundle.putString("noticelist", GsonHelper.toJson(noticeList));
        FragmentLauncher.launch(fragment.getActivity(), NoticeDetailsFragment.class, bundle);
    }

    public static void launch(BaseFragment fragment, Notice notice) {
        Bundle bundle = new Bundle();
        bundle.putString("notice", GsonHelper.toJson(notice));
        FragmentLauncher.launch(fragment.getActivity(), NoticeDetailsFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "详情", R.layout.notice_details,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
        if(getArguments().containsKey("noticelist")){
            noticeList = GsonHelper.fromJson(getArguments().getString("noticelist"), NoticeList.class);
            loadNoticeListDetail(noticeList);
        }else{
            notice = GsonHelper.fromJson(getArguments().getString("notice"), Notice.class);
            loadNoticeDetail(notice);
        }

    }

    private void loadNoticeDetail(Notice notice) {
        NoticeDetailCommand command = new NoticeDetailCommand(); //4:公告；3:banner
        command.setId(notice.getNoticeId());
        executeCommand(command, restCallback,NOTICE_TRACE_ID);
    }

    private void loadNoticeListDetail(NoticeList notice) {
        NoticeDetailCommand command = new NoticeDetailCommand(); //4:公告；3:banner
        command.setId(notice.getId());
        executeCommand(command, restCallback,PLACARD_TRACE_ID);
    }

    private RestCallback restCallback = new RestCallback<NoticeDetail>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<NoticeDetail> response) {
            if(request.getId()==NOTICE_TRACE_ID){
                NoticeDetail noticeDetail = response.getData();
                title.setText(noticeDetail.getTitle());
                time.setText(noticeDetail.getCreatedAt());
                content.setText(Html.fromHtml(noticeDetail.getContent()));
                content.setMovementMethod(LinkMovementMethod.getInstance());
            }else if(request.getId()==PLACARD_TRACE_ID){
                NoticeDetail noticeDetail = response.getData();
                title.setText(noticeDetail.getTitle());
                time.setText(noticeDetail.getCreatedAt());
                content.setText(Html.fromHtml(noticeDetail.getContent()));
                content.setMovementMethod(LinkMovementMethod.getInstance());
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                dialogShow("加载中...");
            } else {
                dialogHide();
            }
        }
    };
}
