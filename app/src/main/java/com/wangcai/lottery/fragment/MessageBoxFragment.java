package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.JsonString;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.data.GetLetterInfoCommand;
import com.wangcai.lottery.game.PromptManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;


public class MessageBoxFragment extends BaseFragment {
    private static final String TAG = MessageBoxFragment.class.getSimpleName();

    private int LIST = 0;
    @BindView(R.id.in_box_fragment)
    RelativeLayout in_box_fragment;

    @BindView(R.id.in_box_text)
    TextView in_box_text;

    @BindView(R.id.in_box_badge)
    TextView in_box_badge;

    @BindView(R.id.out_box_fragment)
    View out_box_fragment;

    @BindView(R.id.write_email_fragment)
    View write_email_fragment;

    @BindView(R.id.in_box_text_img)
    ImageView in_box_text_img;
    @BindView(R.id.tabUserItemTextArr)
    ImageView tabUserItemTextArr;

    private  String MAIL="mail";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "站内信", R.layout.message_box_fragment,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void GetLetterInfo() {
        GetLetterInfoCommand command = new GetLetterInfoCommand();

        executeCommand(command, restCallback, LIST);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        GetLetterInfo();
    }

    @OnClick({R.id.in_box_fragment, R.id.out_box_fragment, R.id.write_email_fragment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.in_box_fragment:
                launchFragment(MessageInFragment.class);
                break;
            case R.id.out_box_fragment:
                launchFragment(OutBoxFragment.class);
                break;
            case R.id.write_email_fragment:
                launchFragment(WriteEmailFragment.class);
                break;

            default:
                break;
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {

            if (request.getId() == LIST) {
                String jsonString= ((JsonString) response.getData()).getJson();

                boolean  isShow=false;
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    isShow=jsonObject.getInt("is_show")==1?true:false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int totalCount;

                if(isShow){
                    totalCount =-1;
                }else{
                    totalCount =0;
                }

                Object  tag=in_box_badge.getTag();

                if(tag==null){
                    QBadgeView qBadgeView=new QBadgeView(getActivity());
                    qBadgeView.bindTarget(in_box_badge);
                    qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
                    qBadgeView.setBadgeNumber(totalCount);
                    in_box_badge.setTag(qBadgeView);
                }else{
                    QBadgeView qQBadgeView=(QBadgeView)tag;
                    qQBadgeView.setBadgeNumber(totalCount);
                }
            }

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

}