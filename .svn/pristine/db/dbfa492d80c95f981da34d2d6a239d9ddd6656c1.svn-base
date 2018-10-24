package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.reflect.TypeToken;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.GetLetterDetailBean;
import com.wangcai.lottery.data.GetLetterDetailCommand;
import com.wangcai.lottery.data.GetSendLetterDetailBean;
import com.wangcai.lottery.data.GetSendLetterDetailCommand;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 收件箱  发件箱的详情页
 * Created by gan on 2017/11/20.
 */
public class BoxDetailsFragment extends BaseFragment {

    @BindView(R.id.user)
    TextView user;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;
    private int msg_id;
    private String msgType;

    private final int RECEIVE=0;
    private final int SEND=1;

    public static void launch(BaseFragment fragment, int msg_id, String msgType) {
        Bundle bundle = new Bundle();
        bundle.putInt("msg_id", msg_id);
        bundle.putString("msgType", msgType);
        FragmentLauncher.launch(fragment.getActivity(), BoxDetailsFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        msg_id = getArguments().getInt("msg_id");
        msgType = getArguments().getString("msgType");//receive:收件箱，send:发件箱
        if ("receive".equals(msgType)) {
            return inflateView(inflater, container, "收件箱", R.layout.box_details, true, true);
        } else {
            return inflateView(inflater, container, "发件箱", R.layout.box_details, true, true);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if ("receive".equals(msgType)) {//收件箱
            GetLetterDetailCommand command = new GetLetterDetailCommand();
            command.setId(msg_id);
            TypeToken typeToken = new TypeToken<RestResponse<GetLetterDetailBean>>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, RECEIVE, this);
            restRequest.execute();
        } else {//发件箱
            GetSendLetterDetailCommand command = new GetSendLetterDetailCommand();
            command.setId(msg_id);
            TypeToken typeToken = new TypeToken<RestResponse<GetSendLetterDetailBean>>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, SEND, this);
            restRequest.execute();

        }
    }


    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case RECEIVE:
                    GetLetterDetailBean detail = (GetLetterDetailBean) response.getData();

                    user_name.setText(detail.getSender());
                    title.setText(detail.getMsg_title());
                    time.setText(detail.getCreated_at());
                    content.setText(detail.getMsg_content());

                    user.setText("发件人");
                    break;
                case SEND:
                    GetSendLetterDetailBean bean = (GetSendLetterDetailBean) response.getData();

                    user_name.setText(bean.getReceiver());
                    title.setText(bean.getMsg_title());
                    time.setText(bean.getCreated_at());
                    content.setText(bean.getMsg_content());

                    user.setText("收件人");

                    break;
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
        }
    };
}
