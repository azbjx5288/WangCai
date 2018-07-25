package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.MyTextWatcher;
import com.wangcai.lottery.component.OptionPicker;
import com.wangcai.lottery.data.BankOpenCommand;
import com.wangcai.lottery.data.BindCardCommand;
import com.wangcai.lottery.data.BindCardDetail;
import com.wangcai.lottery.data.ChangeCardCommand;
import com.wangcai.lottery.data.City;
import com.wangcai.lottery.data.County;
import com.wangcai.lottery.data.Province;
import com.wangcai.lottery.pattern.AddressInitTask;
import com.wangcai.lottery.user.UserCentre;
import com.wangcai.lottery.util.ConvertUtils;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/10/24.
 */
public class AddBankCard extends BaseFragment {
    private static final String TAG = AddBankCard.class.getSimpleName();

    private static final int BINDCARD_TRACE_ID = 1;
    private static final int BANKNAME_TRACE_ID = 2;
    private static final int CHANGECARD_TRACE_ID = 3;

    @BindView(android.R.id.title)
    TextView titleView;
    @BindView(R.id.bank_area)
    RelativeLayout bankArea;
    @BindView(R.id.choose_bank)
    TextView chooseBank;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.province_layout)
    LinearLayout provinceLayout;
    @BindView(R.id.province)
    TextView provinceText;
    @BindView(R.id.city_layout)
    LinearLayout cityLayout;
    @BindView(R.id.city)
    TextView cityText;
    @BindView(R.id.detailed)
    EditText detailed;
    @BindView(R.id.submitbank)
    Button submitbank;

    private Map<Integer, String> bankOpens = new LinkedHashMap<>();
    private String[] bankname = null;
    private UserCentre userCentre;
    private boolean revise = true;
    private Province provincePicker;
    private City cityPicker;
    private BindCardDetail bindCard = null;
    private String verify = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        revise = getArguments().getBoolean("revise");
        return inflater.inflate(R.layout.addbankcard_setting, container, false);
//        return inflateView(inflater, container, revise ? "修改银行卡" : "添加银行卡", R.layout.addbankcard_setting, true, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this)
//                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                  .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //软键盘自动弹出
                .init();

        userCentre = WangCaiApp.getUserCentre();
        titleView.setText(revise ? "修改银行卡" : "添加银行卡");
        submitbank.setText(revise ? "修改银行卡" : "添加银行卡");
        //添加　false 修改　true
        if (revise) {
            bindCard = GsonHelper.fromJson(getArguments().getString("bindCard"), BindCardDetail.class);
            verify = getArguments().getString("verify");
            initRevise();
        } else {
            verify = getArguments().getString("verify");
        }

        loadBankData();
        cardNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        MyTextWatcher cardnumberWatcher = new MyTextWatcher(cardNumber);
        cardnumberWatcher.setTextWatcherChanging((boolean textbool) -> {

        });
        cardNumber.addTextChangedListener(cardnumberWatcher);

        bankArea.setOnClickListener((v) -> {
            getBankView();
        });
        provinceLayout.setOnClickListener((v) -> {
            getBankAddressView();
        });
        cityLayout.setOnClickListener((v) -> {
            getBankAddressView();
        });
    }

    private void initRevise() {
        if (bindCard != null) {
            chooseBank.setText(bindCard.getBank());
            accountName.setText(bindCard.getAccountName());
            provinceText.setText(bindCard.getProvince());
            cityText.setText(bindCard.getCity());
            detailed.setText(bindCard.getBranch());
            ArrayList<Province> data = new ArrayList<>();
            try {
                String json = ConvertUtils.toString(getContext().getAssets().open("city.json"));
                data.addAll(GsonHelper.toJsonArray(json, Province.class));
                for (Province pr : data) {
                    if (pr.getAreaName().indexOf(bindCard.getProvince()) != -1) {
                        provincePicker = pr;
                        for (City ci : pr.getCities()) {
                            if (ci.getAreaName().indexOf(bindCard.getCity()) != -1) {
                                cityPicker = ci;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                MobclickAgent.reportError(getActivity(), e.getMessage());
            }
        }
    }

    /**
     * 平台银行地址选择
     */
    private void getBankAddressView() {
        AddressInitTask address = new AddressInitTask(getActivity(), true);
        if ((TextUtils.isEmpty(provinceText.getText().toString()) || provinceText.getText().toString().equals("省份"))
                && (TextUtils.isEmpty(cityText.getText().toString()) || cityText.getText().toString().equals("城市"))) {
            address.execute("北京", "北京市");
        } else {
            address.execute(provinceText.getText().toString(), cityText.getText().toString());
        }
        address.setOnAddressPickListener(new AddressInitTask.OnAddressPickListener() {
            @Override
            public void onAddressPickClick(Province province, City city, County county) {
                provincePicker = province;
                cityPicker = city;
                provinceText.setText(province.getAreaName());
                cityText.setText(city.getAreaName());
            }

            @Override
            public void onPickedStatus() {
            }
        });
    }

    /**
     * 平台银行选择
     */
    private void getBankView() {
        if (bankname == null || bankname.length == 0) {
            return;
        }
        OptionPicker picker = new OptionPicker(getActivity(), bankname);
        picker.setOffset(1);
        picker.setSelectedIndex(0);
        picker.setTextSize(18);
        picker.setOnOptionPickListener((int position, String option) -> {
            chooseBank.setText(option);
        });
        picker.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        initImmersionBarEnabled();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(android.R.id.home)
    public void finishFragment() {
        getActivity().finish();
    }

    @OnClick(R.id.submitbank)
    public void submitbank() {
        if (verification()) {
            submitbank.setEnabled(false);
            int bankid = 0;
            Iterator<Map.Entry<Integer, String>> iterator = bankOpens.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                if (entry.getValue().equals(chooseBank.getText().toString())) {
                    bankid = (int) entry.getKey();
                }
            }
            try {
                String cardNo = cardNumber.getText().toString().replace(" ", "");
                if (revise) {
                    ChangeCardCommand changeCommand = new ChangeCardCommand();
                    changeCommand.setCardId(bindCard.getId());
                    changeCommand.setBankId(bankid);
                    changeCommand.setProvinceId(provincePicker.getAreaId());
                    changeCommand.setCityId(cityPicker.getAreaId());
                    changeCommand.setAccountName(accountName.getText().toString());
                    changeCommand.setAccount(cardNo);
                    changeCommand.setBranch(detailed.getText().toString());
                    changeCommand.setCheckedToken(verify);
                    String requestCommand = GsonHelper.toJson(changeCommand);
                    requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
                    changeCommand.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=changeBankCard", "UTF-8") + userCentre.getKeyApiKey()));
                    executeCommand(changeCommand, restCallback, CHANGECARD_TRACE_ID);
                } else {
                    BindCardCommand command = new BindCardCommand();
                    command.setBankId(bankid);
                    command.setProvinceId(provincePicker.getAreaId());
                    command.setCityId(cityPicker.getAreaId());
                    command.setAccount(cardNo);
                    command.setBranch(detailed.getText().toString());
                    if (!TextUtils.isEmpty(verify)) {
                        command.setCheckedToken(verify);
                    }
                    command.setAccountName(accountName.getText().toString());
                    String requestCommand = GsonHelper.toJson(command);
                    requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
                    command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=bindBankCard", "UTF-8") + userCentre.getKeyApiKey()));
                    executeCommand(command, restCallback, BINDCARD_TRACE_ID);
                }
            } catch (UnsupportedEncodingException e) {
                MobclickAgent.reportError(getActivity(), e.getMessage());
            }
        }
    }

    private boolean verification() {
        if (chooseBank.getText().toString().equals("请选开户行")) {
            tipDialog("请选开户银行");
            return false;
        }

        if (TextUtils.isEmpty(accountName.getText().toString())) {
            tipDialog("请选开户姓名");
            return false;
        }

        if (TextUtils.isEmpty(cardNumber.getText().toString())) {
            tipDialog("请输入银行卡号");
            return false;
        }
        String cardNo = cardNumber.getText().toString().replace(" ", "");
//        String isBankCard = CheckBankNumber.luhmCheck(cardNo);

        if (cardNo.length() < 16 || cardNo.length() > 19) {
            tipDialog("银行卡格式不正确");
            return false;
        }

       /* if (!isBankCard.equals("true")) {
            tipDialog("银行卡格式不正确");
            return false;
        }*/

        if (provinceText.getText().toString().equals("省份")) {
            tipDialog("请选择省份");
            return false;
        }

        if (cityText.getText().toString().equals("城市")) {
            tipDialog("请选择城市");
            return false;
        }

        if (TextUtils.isEmpty(detailed.getText().toString())) {
            tipDialog("请输入开户支行");
            return false;
        }

        return true;
    }

    private void loadBankData() {
        BankOpenCommand command = new BankOpenCommand();
        TypeToken typeToken = new TypeToken<RestResponse<LinkedHashMap<Integer, String>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BANKNAME_TRACE_ID, this);
        restRequest.execute();
    }

    private void bankData() {
        bankname = new String[bankOpens.size()];
        int i = 0;
        Iterator<Map.Entry<Integer, String>> iterator = bankOpens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            bankname[i] = (String) entry.getValue();
            i++;
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    private Map<Integer, String> sortMapByKey(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Integer, String> sortMap = new TreeMap<Integer, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case BINDCARD_TRACE_ID:
                case CHANGECARD_TRACE_ID: //添加银行卡 　修改银行卡
                    submitbank.setEnabled(true);
                    AddBankCard.this.getActivity().finish();
                    break;
                case BANKNAME_TRACE_ID: //获取银行信息
                    if (response.getData() instanceof Map) {
                        bankOpens = (Map<Integer, String>) response.getData();
                        if (bankOpens.size() > 0) {
                            bankOpens = sortMapByKey(bankOpens);
                            bankData();
                        }
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            submitbank.setEnabled(true);
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }else {
                showToast(errDesc);
            }
            return true;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {

        }
    };

    class MapKeyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer key1, Integer key2) {
            return key1.compareTo(key2);
        }
    }
}
