package com.wangcai.lottery.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.base.Preferences;
import com.wangcai.lottery.base.net.NetStateHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.base.thread.Future;
import com.wangcai.lottery.base.thread.FutureListener;
import com.wangcai.lottery.base.thread.ThreadPool;
import com.wangcai.lottery.data.BetListCommand;
import com.wangcai.lottery.data.LoginCommand;
import com.wangcai.lottery.data.LotteriesHistoryCommand;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryListCommand;
import com.wangcai.lottery.data.MethodList;
import com.wangcai.lottery.data.MethodListCommand;
import com.wangcai.lottery.fragment.BetOrTraceListTagFragment;
import com.wangcai.lottery.fragment.GoldenLogin;
import com.wangcai.lottery.fragment.NoticeListFragment;
import com.wangcai.lottery.fragment.ShoppingFragment;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;

/**
 * Created by Alashi on 2015/12/22.
 */
public class DebugActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DebugActivity.class.getSimpleName();

    private TextView logView;
    private SparseArray<Object> array = new SparseArray<>();
    private UserCentre userCentre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logView = (TextView)findViewById(R.id.text);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                launch(array.keyAt(arg2));
            }
        });
        loadData();
        findViewById(R.id.fab).setOnClickListener(this);
        WangCaiApp.getNetStateHelper().addWeakListener(new NetStateHelper.NetStateListener() {
            @Override
            public void onStateChange(boolean isConnected) {
                String connect = WangCaiApp.getNetStateHelper().isConnected()?
                        "网络连接：可以": "网络连接：无网络";
                logView.setText(connect);
            }
        });
        String connect = WangCaiApp.getNetStateHelper().isConnected()?
                "网络连接：可以": "网络连接：无网络";
        logView.setText(connect);
    }

    private void loadData() {
        array.append(0, "登录");
        array.append(1, "清除session");
        array.append(2, "清除session&用户名&密码");
        array.append(3, "访问lotteryList接口");
        array.append(4, "访问玩法接口");
        array.append(5, "开奖号码列表");
        array.append(6, "注单列表");

        addItem("登录页", GoldenLogin.class);
        addItem("测试Fragment页面", TestFragment.class);
        addItem("公告", NoticeListFragment.class);
        addItem("购物车", ShoppingFragment.class);
        addItem("游戏记录", BetOrTraceListTagFragment.class);

    }

    private void launch(int key) {
        switch (key) {
            case 0:
                testLogin();
                break;
            case 1:
                WangCaiApp.getUserCentre().saveSession(null);
                break;
            case 2:
                WangCaiApp.getUserCentre().saveSession(null);
                WangCaiApp.getUserCentre().saveLoginInfo(null, null);
                WangCaiApp.getUserCentre().setUserInfo(null);
                break;
            case 3:
                testGetLotteryListCommand();
                break;
            case 4:
                testGetMethodListCommand();
                break;
            case 5:
                LotteriesHistoryLoad();
                break;
            case 6:
                loadBetList();
                break;
            default: {
                Object object = array.get(key);
                if (object instanceof DataItem) {
                    FragmentLauncher.launch(DebugActivity.this, ((DataItem)object).fragment);
                } else {
                    Log.e(TAG, "launch: 未处理的列表项, key=" + key + ", v=" + object);
                }
                break;
            }
        }
    }

    private void testThreadPool() {
        WangCaiApp.getThreadPool().submit(new ThreadPool.Job<Void>() {
            @Override
            public Void run(ThreadPool.JobContext jc) {
                Log.i(TAG, "run: ThreadPool Job running!");
                return null;
            }
        }, new FutureListener<Void>() {
            @Override
            public void onFutureDone(Future<Void> future) {
                Toast.makeText(DebugActivity.this, "线程池，回调到UI线程", Toast.LENGTH_SHORT).show();
            }
        }, true);
    }

    private void testLogin() {
        LoginCommand command = new LoginCommand();
        command.setUsername("toptop01p02");
        command.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex("toptop01p02".toLowerCase()+"123qwe"))));
        command.setTerminalId(2);
        RestRequestManager.executeCommand(this, command, restCallback, 0 , this);
    }

    private void testGetLotteryListCommand() {
        LotteryListCommand command = new LotteryListCommand();
        command.setToken(userCentre.getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Lottery>>>(){};
        RestRequestManager.executeCommand(this, command,typeToken,
                restCallback, 0 , this);
    }

    private void testGetMethodListCommand() {
        MethodListCommand methodListCommand = new MethodListCommand();
        methodListCommand.setLotteryId(1);
        methodListCommand.setToken(userCentre.getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MethodList>>>() {
        };
        RestRequestManager.executeCommand(this, methodListCommand, typeToken, restCallback, 1, this);
    }

    private void LotteriesHistoryLoad() {
        LotteriesHistoryCommand listCommand = new LotteriesHistoryCommand();
        listCommand.setToken(userCentre.getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(this, listCommand, typeToken, restCallback, 3, this);
        restRequest.execute();
    }

    private void loadBetList() {
        BetListCommand command = new BetListCommand();
        command.setPage(1);
        command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        RestRequest restRequest = RestRequestManager.createRequest(this, command,restCallback, 4, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        private String[] stateString = {"IDLE", "RUNNING", "DONE", "QUIT"};
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            //网络请求调用成功，处理结果
            if(request.getId()==0){
                userCentre = WangCaiApp.getUserCentre();
            }else if(request.getId()==1) {
                ArrayList<MethodList> methodList = (ArrayList<MethodList>) response.getData();
                for (MethodList methods : methodList) {
                    Log.i(TAG, methods.getNameCn());
                }
            }
           /* Log.i(TAG, "onRestComplete: " + response);
            //Log.i(TAG, "onRestComplete: isHasMore.getData = "
            //        + response.getData().getClass() + ", " + response.getData());
            if (response.getData() instanceof UserInfo) {
                UserInfo info = (UserInfo) response.getData();
                Log.d(TAG, "onRestComplete: "+ GsonHelper.toJson(info));
                Log.d(TAG, "onRestComplete: "+ info.getSigninAt());
            }*/
            return true;
        }
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            //请求失败，异常情况处理
            Log.w(TAG, "onRestError() called with: errCode = [" + errCode + "], errDesc = [" + errDesc + "]");
            //logView.setText("出错了：/n errCode=" + errCode + ", errDesc=" + errDesc);
            return false;
        }
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            //状态响应，如开始时弹等待对话框，结束时隐藏对话框
            logView.setText(stateString[state]);
            Log.d(TAG, "onRestStateChanged: " + stateString[state]);
        }
    };

    private void addItem(String text, Class<? extends Fragment> fragment) {
        array.append(array.size(), new DataItem(text, fragment));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            String name = Preferences.getString(this, "debug_last_launch_fragment", null);
            try {
                if (name != null && getClassLoader().loadClass(name) != null) {
                    FragmentLauncher.launch(this, name);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DataItem {
        String text;
        Class<? extends Fragment> fragment;

        public DataItem(String text, Class<? extends Fragment> fragment) {
            this.text = text;
            this.fragment = fragment;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(DebugActivity.this).inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView view = (TextView) convertView.findViewById(android.R.id.text1);
            Object data = array.get(array.keyAt(position));
            view.setText(position +": "+ data.toString());

            return convertView;
        }
    }
}
