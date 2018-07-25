package com.wangcai.lottery.pattern;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.data.City;
import com.wangcai.lottery.data.County;
import com.wangcai.lottery.data.Province;
import com.wangcai.lottery.util.ConvertUtils;

import java.util.ArrayList;

/**
 * 获取地址数据并显示地址选择器
 * Created by ACE-PC on 2016/10/24.
 */
public class AddressInitTask extends AsyncTask<String, Void, ArrayList<Province>> {
    private static final String TAG = AddressInitTask.class.getSimpleName();
    private Activity activity;
    private OnAddressPickListener onAddressPickListener;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideCounty = false;

    /**
     * 初始化为不显示区县的模式
     *
     * @param activity
     * @param hideCounty is hide County
     */
    public AddressInitTask(Activity activity, boolean hideCounty) {
        this.activity = activity;
        this.hideCounty = hideCounty;
    }

    public AddressInitTask(Activity activity) {
        this.activity = activity;
    }

    public void setOnAddressPickListener(OnAddressPickListener onAddressPickListener) {
        this.onAddressPickListener = onAddressPickListener;
    }

    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    selectedCounty = params[2];
                    break;
                default:
                    break;
            }
        }
        ArrayList<Province> data = new ArrayList<>();
        try {
            String json = ConvertUtils.toString(activity.getAssets().open("city.json"));
            data.addAll(GsonHelper.toJsonArray(json, Province.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<Province> result) {
        if (result.size() > 0 ) {
            AddressPicker picker = new AddressPicker(activity, result);
            picker.setHideCounty(hideCounty);
            if (hideCounty) {
                picker.setColumnWeight(1 / 3.0, 2 / 3.0);//将屏幕分为3份，省级和地级的比例为1:2
            } else {
                picker.setColumnWeight(2 / 8.0, 3 / 8.0, 3 / 8.0);//省级、地级和县级的比例为2:3:3
            }
            picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener(){

                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    if (onAddressPickListener != null) {
                        if (county == null) {
                            onAddressPickListener.onAddressPickClick(province, city, county);
                        } else {
                            onAddressPickListener.onAddressPickClick(province, city, county);
                        }
                    }
                }

                @Override
                public void onPickedStatus(){
                    if (onAddressPickListener != null) {
                        onAddressPickListener.onPickedStatus();
                    }
                }
            });

            /*(Province province, City city, County county) -> {

            }*/
            picker.show();
        } else {
            Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选中监听器
     */
    public interface OnAddressPickListener {
        void onAddressPickClick(Province province, City city, County county);
        void onPickedStatus();
    }
}
