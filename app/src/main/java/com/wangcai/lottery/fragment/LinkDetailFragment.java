package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.data.GetLinkUserDetailCommand;
import com.wangcai.lottery.data.LinkUserBean;
import com.wangcai.lottery.data.LinkUserDetailResponse;
import com.wangcai.lottery.game.PromptManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Sakura on 2018/7/12.
 */

public class LinkDetailFragment extends BaseFragment
{
    private static final String TAG = "LinkDetailFragment";
    private static final int ID_SHOW_DETAIL = 1;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.qq)
    TextView qq;
    @BindView(R.id.valid_days)
    TextView validDays;
    @BindView(R.id.link)
    TextView link;
    @BindView(R.id.num_prize_group)
    TextView numPrizeGroup;
    @BindView(R.id.jcdg)
    TextView jcdg;
    @BindView(R.id.jchg)
    TextView jchg;
    @BindView(R.id.ag)
    TextView ag;
    @BindView(R.id.ga)
    TextView ga;
    @BindView(R.id.copy)
    Button copy;
    Unbinder unbinder;
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflateView(inflater, container, true, "链接详情", R.layout.link_detail, true, true);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }
    
    private void initData()
    {
        LinkUserBean linkUserBean = (LinkUserBean) getArguments().getSerializable("detail");
        GetLinkUserDetailCommand command = new GetLinkUserDetailCommand();
        command.setId(linkUserBean.getId());
        TypeToken typeToken = new TypeToken<RestResponse<LinkUserDetailResponse>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback,
                ID_SHOW_DETAIL, this);
        restRequest.execute();
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case ID_SHOW_DETAIL:
                    if (response != null && response.getData() != null)
                    {
                        LinkUserDetailResponse linkUserDetailResponse = (LinkUserDetailResponse) response.getData();
                        type.setText("账号类型：" + (linkUserDetailResponse.getIs_agent() == 1 ? "代理" : "玩家"));
                        qq.setText("推广QQ：" + linkUserDetailResponse.getAgent_qqs());
                        validDays.setText("链接有效期：" + linkUserDetailResponse.getValid_days() + "天");
                        link.setText("链接地址：" + linkUserDetailResponse.getUrl());
                        numPrizeGroup.setText("数字彩奖金组：" + linkUserDetailResponse.getPrizeGroup());
                        jcdg.setText("竞彩单关：" + linkUserDetailResponse.getSingle());
                        jchg.setText("竞彩混关：" + linkUserDetailResponse.getMulti());
                        ag.setText("AG游戏：" + linkUserDetailResponse.getAggame());
                        ga.setText("GA游戏：" + linkUserDetailResponse.getGagame());
                    }
                    break;
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 7003)
            {
                showToast("正在更新服务器请稍等", Toast.LENGTH_LONG);
                return true;
            } else if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, int state)
        {
        
        }
    };
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
    
    @OnClick({R.id.copy})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.copy:
                com.wangcai.lottery.util.ClipboardUtils.copy(getActivity(), link.getText().toString(), "代理链接");
                break;
        }
    }
}
