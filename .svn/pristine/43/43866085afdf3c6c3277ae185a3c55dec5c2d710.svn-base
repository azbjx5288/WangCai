package com.wangcai.lottery.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.app.LazyBaseFragment;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.data.ChangePrizeGroupCommand;
import com.wangcai.lottery.data.GetChildInfoBean;
import com.wangcai.lottery.data.GetChildInfoCommand;
import com.wangcai.lottery.data.GetUserAccurateInfoCommand;
import com.wangcai.lottery.data.PrizeGroupChild;
import com.wangcai.lottery.data.QuotaBean;
import com.wangcai.lottery.data.UserAccurateInfo;
import com.wangcai.lottery.view.adapter.QuotaAdapter;
import com.wangcai.lottery.view.adapter.QuotaAdapter2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 精准开户
 */

public class UserListEditAccountAccurateFragment extends LazyBaseFragment
{
    private static final String TAG = UserListEditAccountAccurateFragment.class.getSimpleName();
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.bonus_counts)
    EditText bonusCounts;
    @BindView(R.id.bonus_rebate)
    TextView bonusRebate;
    @BindView(R.id.bonus_danguan)
    EditText bonusDanguan;
    @BindView(R.id.bonus_hunhe)
    EditText bonusHunhe;
    @BindView(R.id.bonus_ag)
    EditText bonusAg;
    @BindView(R.id.bonus_game)
    EditText bonusGame;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.rebates_setting_btn)
    Button rebatesSettingBtn;
    Unbinder unbinder;
    @BindView(R.id.bonus_danguan_tv)
    TextView bonus_danguan_tv;
    @BindView(R.id.bonus_hunhe_tv)
    TextView bonus_hunhe_tv;
    @BindView(R.id.bonus_ag_tv)
    TextView bonus_ag_tv;
    @BindView(R.id.bonus_game_tv)
    TextView bonus_game_tv;
    
    private static final int GET_USER_INFO_COMMAND = 1;
    private static final int SUBMIT_COMMAND = 2;
    private static final int  GET_CHILD_INFO=3;
    
    private QuotaAdapter quotaAdapter;
    private QuotaAdapter2 mQuotaAdapter2;
    private ArrayList<QuotaBean> mQuotaList;

    private int mId;
    private String mUserName;
    private  boolean is_agent;
    private  GetChildInfoBean bean;
    private  UserAccurateInfo userAccurateInfo;

    private boolean mShowDialog=true;


    public static void launch(BaseFragment fragment, int id,String username,boolean is_agent) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("username",username);
        bundle.putBoolean("is_agent",is_agent);
        FragmentLauncher.launch(fragment.getActivity(), UserListEditAccountAccurateFragment.class, bundle);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflateView(inflater, container, true, "调整奖金组", R.layout.fragment_edit_account_accurate, true, true);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    
    private void init()
    {
        mId=getArguments().getInt("id");
        mUserName=getArguments().getString("username");
        is_agent=getArguments().getBoolean("is_agent");
        userName.setText(mUserName);

        quotaAdapter = new QuotaAdapter();
        mQuotaAdapter2 = new QuotaAdapter2();

        GetUserAccurateInfoCommand command = new GetUserAccurateInfoCommand();
        TypeToken typeToken = new TypeToken<RestResponse<UserAccurateInfo>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback,
                GET_USER_INFO_COMMAND, this);
        restRequest.execute();


        GetChildInfoCommand command2 = new GetChildInfoCommand();
        command2.setId(mId);
        TypeToken typeToken2 = new TypeToken<RestResponse<GetChildInfoBean>>() {};
        RestRequest restRequest2 = RestRequestManager.createRequest(getActivity(), command2, typeToken2, restCallback,
                GET_CHILD_INFO, this);
        restRequest2.execute();

    }

    //DECIMAL_DIGITS = 1;//小数的位数
    private void  initEditText(EditText editText,float  MaxValue,int DECIMAL_DIGITS){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2){
                //这里需要注意，必须先判断mEtEndTimeRepeatTimes.getText()是否为空才能使用Integer.parseInt，否则会报异常。
                if((editText.getText()!=null) &&
                        !("".equals(editText.getText().toString()))){

                    if(Double.parseDouble(String.valueOf(editText.getText())) > MaxValue){
                        editText.setText(String.valueOf(MaxValue));
                    }


                    //限制小数的位数 start
                    if (DECIMAL_DIGITS>0) {
                        if (s.toString().contains(".")) {
                            if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                                s = s.toString().subSequence(0,
                                        s.toString().indexOf(".") + DECIMAL_DIGITS+1);
                                editText.setText(s);
                                editText.setSelection(s.length());
                            }
                        }
                        if (s.toString().trim().substring(0).equals(".")) {
                            s = "0" + s;
                            editText.setText(s);
                            editText.setSelection(2);
                        }
                        if (s.toString().startsWith("0")
                                && s.toString().trim().length() > 1) {
                            if (!s.toString().substring(1, 2).equals(".")) {
                                editText.setText(s.subSequence(0, 1));
                                editText.setSelection(1);
                                return;
                            }
                        }
                    }
                }
                //限制小数的位数  end

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    
    @OnClick(R.id.rebates_setting_btn)
    public void onViewClicked()
    {
        if (verifyInput())
        {
            String type;
            if (is_agent)
                type = "代理";
            else
                type = "玩家";

            /*stringBuilder.append("用户类型：" + type + "   ");
            stringBuilder.append("登录账号:" + userName.getText().toString() + "\n");
            stringBuilder.append("登录密码:" + userPassword.getText().toString() + "   ");
            stringBuilder.append("用户昵称:" + nickName.getText().toString() + "\n");
            stringBuilder.append("数字彩奖金组:" + bonusCounts.getText().toString() + "\n");
            stringBuilder.append("竞彩单关：" + bonusDanguan.getText().toString() + "%   ");
            stringBuilder.append("竞彩混关:" + bonusHunhe.getText().toString() + "%\n");
            stringBuilder.append("AG游戏：" + bonusAg.getText().toString() + "%   ");
            stringBuilder.append("GA游戏：" + bonusAg.getText().toString() + "%\n");*/
            
//            CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            View displayView = LayoutInflater.from(getContext()).inflate(R.layout.alert_dialog_reg_confirm_layout2,
                    null);
//            builder.setDisplayLayout(displayView);
            TextView user_name = (TextView) displayView.findViewById(R.id.user_name);
            TextView numPrizeGroup = (TextView) displayView.findViewById(R.id.num_prize_group);
            TextView jcdg = (TextView) displayView.findViewById(R.id.jcdg);
            TextView jchg = (TextView) displayView.findViewById(R.id.jchg);
            TextView ag = (TextView) displayView.findViewById(R.id.ag);
            TextView ga = (TextView) displayView.findViewById(R.id.ga);
            Button positiveButton = (Button) displayView.findViewById(R.id.positiveButton);

            user_name.setText("用户 "+mUserName+" 的奖金组设置成功，新的奖金组是");

//            if(Integer.parseInt(bonusCounts.getText().toString()) < 1950){
//                numPrizeGroup.setVisibility(View.GONE);
//            }else{
                numPrizeGroup.setText("数字彩奖金组:" + bonusCounts.getText().toString());
                numPrizeGroup.setVisibility(View.VISIBLE);
//            }


            jcdg.setText("竞彩单关:" + bonusDanguan.getText().toString()+"%");
            jchg.setText("竞彩混关:" + bonusHunhe.getText().toString()+"%");
            ag.setText("AG游戏:" + bonusAg.getText().toString()+"%");
            ga.setText("GA游戏:" + bonusGame.getText().toString()+"%");
            //builder.setMessage(stringBuilder.toString());
//            builder.setLayoutSet(DialogLayout.SINGLE);
//            positiveButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            GridView grid_view=(GridView)displayView.findViewById(R.id.grid_view);
            grid_view.setAdapter(mQuotaAdapter2);



            TextView peie_id = (TextView) displayView.findViewById(R.id.peie_id);
            if(Integer.parseInt(bonusCounts.getText().toString()) < 1950){
                peie_id.setVisibility(View.GONE);
                grid_view.setVisibility(View.GONE);
            }else{
                peie_id.setVisibility(View.VISIBLE);
                grid_view.setVisibility(View.VISIBLE);
            }


            final Dialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setContentView(displayView);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                public void onClick(View v) {
                    ChangePrizeGroupCommand accurateUserCommand = new ChangePrizeGroupCommand();
                    accurateUserCommand.setId(mId);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("1", bonusCounts.getText().toString());
                    accurateUserCommand.setSeries_prize_group_json(GsonHelper.toJson(map));
                    accurateUserCommand.setAgent_prize_set_quota(Integer.parseInt(bonusCounts.getText().toString()) <
                            1950 ? null : GsonHelper.toJson(quotaAdapter.getResultMap()));
                    accurateUserCommand.setFb_single(Float.parseFloat(bonusDanguan.getText().toString()));
                    accurateUserCommand.setFb_all(Float.parseFloat(bonusHunhe.getText().toString()));
                    accurateUserCommand.setAg_percent(Float.parseFloat(bonusAg.getText().toString()));
                    accurateUserCommand.setGa_percent(Float.parseFloat(bonusGame.getText().toString()));

                    executeCommand(accurateUserCommand, restCallback, SUBMIT_COMMAND);
                                    dialog.dismiss();
                }
            });
//            builder.create().show();
        }
    }
    
    private boolean verifyInput()
    {
        if (TextUtils.isEmpty(userName.getText().toString()))
        {
            Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_LONG).show();
            return false;
        }
        
        if (!(userName.getText().toString().charAt(0) <= 'Z' && userName.getText().toString().charAt(0) >= 'A' ||
                userName.getText().toString().charAt(0) <= 'z' && userName.getText().toString().charAt(0) >= 'a'))
        {
            Toast.makeText(getContext(), "用户名必须以字母开头", Toast.LENGTH_LONG).show();
            return false;
        }
        
        String userNameReg = "^[A-Za-z0-9_]+$";//英文和数字
        Pattern pAll = Pattern.compile(userNameReg);
        Matcher mAll = pAll.matcher(userName.getText().toString());
        if (!mAll.matches())
        {
            Toast.makeText(getContext(), "用户名只能是字母或者数字或者下划线", Toast.LENGTH_LONG).show();
            return false;
        }
        
        if (userName.getText().toString().length() > 16 || userName.getText().toString().length() < 5)
        {
            Toast.makeText(getContext(), "用户名只能是长度为5-16位", Toast.LENGTH_LONG).show();
            return false;
        }
        
        //// 清除掉所有特殊字符
        //        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        //        Pattern p = Pattern.compile(regEx);
        //        Matcher matcher = p.matcher(nickname.getText().toString());
        //        if(matcher .find()){
        //            showToast("昵称不能包含特殊字符", Toast.LENGTH_SHORT);
        //            return false;
        //        }
        

        if (TextUtils.isEmpty(bonusCounts.getText().toString()))
        {
            Toast.makeText(getContext(), "请输入奖金组", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(bonusCounts.getText().toString()) < 1500){
            Toast.makeText(getContext(), "奖金组不能低于1500", Toast.LENGTH_LONG).show();
            return false;
        }

        if(Integer.parseInt(bonusCounts.getText().toString()) < Integer.parseInt(bean.getCurrentUserPrizeGroup())){
            Toast.makeText(getContext(), "奖金组不能调低", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(bonusDanguan.getText().toString()) || TextUtils.isEmpty(bonusHunhe.getText().toString()
        ) || TextUtils.isEmpty(bonusAg.getText().toString()) || TextUtils.isEmpty(bonusGame.getText().toString()))
        {
            Toast.makeText(getContext(), "请输入返点", Toast.LENGTH_LONG).show();
            return false;
        }
        
        return true;
    }
    
    //判断字符串中是否含Emoji表情正则表达式
    public boolean hasEmoji(String content)
    {
        //过滤Emoji表情
        //        Pattern p = Pattern.compile("[^\\u0000-\\uFFFF]");
        //过滤Emoji表情和颜文字
        //        Pattern p = Pattern.compile
        // ("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]|[\\ud83e\\udd00
        // -\\ud83e\\uddff]|[\\u2300-\\u23ff]|[\\u2500-\\u25ff]|[\\u2100-\\u21ff]|[\\u0000-\\u00ff]|[\\u2b00-\\u2bff
        // ]|[\\u2d06]|[\\u3030]");
        //禁用emoji表情和颜文字
        Pattern p = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()" +
                "<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` " +
                "，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");
        //判断字符串中是否含Emoji表情正则表达式
        //        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff
        // ]");
        Matcher matcher = p.matcher(content);
        if (matcher.find())
        {
            return true;
        }
        return false;
    }

    private void setReturnPoint(){
        if(bean==null){
            return;
        }
        if(userAccurateInfo==null){
            return;
        }

        if (bean.getIsAgent()==1)
        {
            float returnPoint = ((float) userAccurateInfo
                                .getPossibleAgentPrizeGroup()
                        - Float.parseFloat(bean.getCurrentUserPrizeGroup())) / 2000l;
            returnPoint=returnPoint*100;
            bonusRebate.setText("对应返点" + returnPoint + "%");
        } else
        {
            float  returnPoint = ((float) userAccurateInfo
                                .getPossiblePlayerPrizeGroup() -
             Float.parseFloat(bean.getCurrentUserPrizeGroup())) / 2000l;

            returnPoint=returnPoint*100;
            bonusRebate.setText("对应返点" + returnPoint + "%");

        }
    }


    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case GET_USER_INFO_COMMAND:
                    if (response.getData() != null && response.getData() instanceof UserAccurateInfo)
                    {
                        userAccurateInfo= (UserAccurateInfo) response.getData();
//                        if (userAccurateInfo.isIs_top_agent() == 0){
//                            user.setVisibility(View.VISIBLE);
//                        }

                       mQuotaList = getQuotaBeans(userAccurateInfo);

                        bonusCounts.addTextChangedListener(new TextWatcher()
                        {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                            { }
                            
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                            {
                                if (!TextUtils.isEmpty(charSequence))
                                {
                                    int currentPrizeInt=0;
                                    if (is_agent)
                                    {
                                        currentPrizeInt= userAccurateInfo.getAgentCurrentPrize();
                                    }else{
                                        currentPrizeInt=userAccurateInfo.getCurrentPrize();
                                    }
                                    if(Integer.parseInt(charSequence.toString())>currentPrizeInt){
                                        bonusCounts.setText(currentPrizeInt+"");
                                        return;
                                    }

                                    if (Integer.parseInt(charSequence.toString()) < 1950)
                                    {
                                        if (is_agent)
                                        {
                                            ArrayList<PrizeGroupChild> allList = userAccurateInfo
                                                    .getAllPossibleAgentPrizeGroups();
                                            if (allList != null && allList.size() != 0)
                                                for (PrizeGroupChild prizeGroupChild : allList)
                                                {
                                                    if (prizeGroupChild.getName().equals(charSequence.toString()))
                                                    {
                                                        float returnPoint = ((float) userAccurateInfo
                                                                .getPossibleAgentPrizeGroup() - Float.parseFloat
                                                                (charSequence.toString())) / 2000l;
                                                        //returnPoint = (float) (Math.round((returnPoint * 1000 /
                                                        // 1000)));
                                                        returnPoint=returnPoint*100;
                                                        bonusRebate.setText("对应返点" + returnPoint + "%");
                                                    }
                                                }
                                        } else
                                        {
                                            ArrayList<PrizeGroupChild> allList = userAccurateInfo
                                                    .getAllPossiblePrizeGroups();
                                            if (allList != null && allList.size() != 0)
                                                for (PrizeGroupChild prizeGroupChild : allList)
                                                {
                                                    if (prizeGroupChild.getName().equals(charSequence.toString()))
                                                    {
                                                        float returnPoint = ((float) userAccurateInfo
                                                                .getPossiblePlayerPrizeGroup() - Float.parseFloat
                                                                (charSequence.toString())) / 2000l;
                                                        //returnPoint = (float) (Math.round((returnPoint * 1000 /
                                                        // 1000)));
                                                        returnPoint=returnPoint*100;
                                                        bonusRebate.setText("对应返点" + returnPoint + "%");
                                                    }
                                                }
                                        }
                                    } else
                                    {
                                        if (!is_agent) return;

                                        if (mQuotaList == null) return;
                                        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                                        View displayView = LayoutInflater.from(getContext()).inflate(R.layout
                                                .alert_dialog_prize_groups_layout, null);
                                        ListView listView = displayView.findViewById(R.id.list_view);
                                        listView.setAdapter(quotaAdapter);
                                        quotaAdapter.setData(mQuotaList,charSequence.toString());
                                        builder.setDisplayLayout(displayView);
                                        builder.setTitle("设置开户配额");
                                        builder.setLayoutSet(DialogLayout.SINGLE);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i)
                                            {
                                                mQuotaAdapter2.setData(quotaAdapter.getData());
                                                dialogInterface.dismiss();
                                                String currentPrize="";

                                                if (is_agent)
                                                {
                                                    currentPrize= userAccurateInfo.getAgentCurrentPrize()+"";
                                                }else{
                                                    currentPrize=userAccurateInfo.getCurrentPrize()+"";
                                                }

//                                                if(bonusCounts.getText().toString().equals(currentPrize)){
//                                                    mShowDialog=false;
//                                                }else{
//                                                    bonusCounts.setText(currentPrize);
//                                                }
                                            }
                                        });
//                                        if(mShowDialog){
                                            builder.create().show();
//                                        }
//                                        mShowDialog=true;
                                    
                                    }
                                }
                            }
                            
                            @Override
                            public void afterTextChanged(Editable editable)
                            {
                            
                            }
                        });

                        bonus_danguan_tv.setText("% （共有"+userAccurateInfo.getUserSingle()+"%可以分配）");
                        bonus_hunhe_tv.setText("% （共有"+userAccurateInfo.getUserMulti()+"%可以分配）");
                        bonus_ag_tv.setText("% （共有"+userAccurateInfo.getUserAG()+"%可以分配）");
                        bonus_game_tv.setText("% （共有"+userAccurateInfo.getUserGA()+"%可以分配）");


                        initEditText(bonusDanguan,userAccurateInfo.getUserSingle(),0);
                        initEditText(bonusHunhe,userAccurateInfo.getUserMulti(),0);
                        initEditText(bonusAg,userAccurateInfo.getUserAG(),1);
                        initEditText(bonusGame,userAccurateInfo.getUserGA(),1);


                        mQuotaAdapter2.setData(mQuotaList);

                        setReturnPoint();
                    }
                    break;
                case SUBMIT_COMMAND:
                    showToast("调整奖金组成功", Toast.LENGTH_LONG);
                    break;
                case GET_CHILD_INFO:
                   bean = (GetChildInfoBean) response.getData();
                     bonusCounts.setText(bean.getCurrentUserPrizeGroup());

                     bonusDanguan.setText(bean.getUserSingle()+"");
                     bonusHunhe.setText(bean.getUserMulti()+"");
                     bonusAg.setText(bean.getUserAg()+"");
                     bonusGame.setText(bean.getUserGa()+"");

                    setReturnPoint();
                    break;
            }
            return true;
        }

        @Nullable
        private ArrayList<QuotaBean> getQuotaBeans(UserAccurateInfo userAccurateInfo) {
            LinkedHashMap<Integer, Integer> quotaMap = userAccurateInfo
                    .getUserAllPrizeSetQuota();
            if (quotaMap == null || quotaMap.size() == 0)
            {
                return null;
            }
            ArrayList<QuotaBean> mQuotaList = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : quotaMap.entrySet())
            {
                QuotaBean quotaBean = new QuotaBean();
                quotaBean.setQuota(entry.getKey());
                quotaBean.setMax(entry.getValue());
                mQuotaList.add(quotaBean);
            }
            return mQuotaList;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 3004 || errCode == 2016)
            {
                signOutDialog(getActivity(), errCode);
                return true;
            } else
            {
                showToast(errDesc);
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {
        }
    };
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}