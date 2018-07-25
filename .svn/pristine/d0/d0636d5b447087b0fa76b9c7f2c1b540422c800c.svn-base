package com.wangcai.lottery.base.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * 设备联网状态监听.
 */
public class NetStateHelper {

    private final Context mContext;
    private boolean mIsRegisterReceiver = false;
    private boolean mIsConnected;
    private boolean mIsMobileConnected;
    private boolean mIsWifiConnected;
    private ArrayList<NetStateListener> mListeners = new ArrayList<NetStateListener>();
    private WeakHashMap<NetStateListener, Void> mWeakListeners = new WeakHashMap<NetStateListener, Void>();

    public interface NetStateListener{
        void onStateChange(boolean isConnected);
    }

    public NetStateHelper(Context context) {
        mContext = context;
    }

    public synchronized void pause() {
        if (mIsRegisterReceiver) {
            mContext.unregisterReceiver(mNetworkStateReceiver);
            mIsRegisterReceiver = false;
        }
    }

    public synchronized void resume() {
        if (!mIsRegisterReceiver) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(mNetworkStateReceiver, filter);
            mIsRegisterReceiver = true;
        }

        updateState();
    }

    private void notifyNetStateChange() {
        synchronized (mListeners) {
            for (NetStateListener listener : mListeners) {
                listener.onStateChange(mIsConnected);
            }
        }

        if (!mWeakListeners.isEmpty()) {
            Iterator<NetStateListener> listeners = mWeakListeners.keySet().iterator();
            while (listeners.hasNext()) {
                listeners.next().onStateChange(mIsConnected);
            }
        }
    }

    /** 弱引用，无需removeListener */
    public void addWeakListener(NetStateListener listener) {
        mWeakListeners.put(listener, null);
    }

    public void removeWeakListener(NetStateListener listener) {
        if (mWeakListeners.containsKey(listener)) {
            mWeakListeners.remove(listener);
        }
    }

    /** 强引用，记得removeListener */
    public void addListener(NetStateListener listener) {
        synchronized (mListeners) {
            if (!mListeners.contains(listener)) {
                mListeners.add(listener);
            }
        }
    }

    public void removeListener(NetStateListener listener) {
        synchronized (mListeners) {
            if (mListeners.contains(listener)) {
                mListeners.remove(listener);
            }
        }
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public boolean isWifiConnected() {
        return mIsWifiConnected;
    }

    public boolean isMobileConnected() {
        return mIsMobileConnected;
    }

    private final BroadcastReceiver mNetworkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateState();
        }
    };

    private void updateState() {
        ConnectivityManager connectivity = (ConnectivityManager)mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //wifi
        NetworkInfo wifiInfo = connectivity.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            mIsWifiConnected = wifiInfo.isAvailable() && wifiInfo.isConnected();
        }

        //Mobile
        NetworkInfo mobileInfo = connectivity.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {
            mIsMobileConnected = mobileInfo.isAvailable() && mobileInfo.isConnected();
        }

        mIsConnected = mIsWifiConnected || mIsMobileConnected;
        if (!mIsConnected) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                mIsConnected = info.isAvailable() && info.isConnected();
            }
        }
        notifyNetStateChange();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //wifi
        NetworkInfo wifiInfo = connectivity.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isAvailable() && wifiInfo.isConnected();
    }

    public static boolean isMobileNetworkConnected(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //Mobile
        NetworkInfo mobileInfo = connectivity.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE);
        return mobileInfo.isAvailable() && mobileInfo.isConnected();
    }

    /**
     * 获取Ip地址
     * @param context
     * @return
     */
    public static String getIpAddress(Context context){
        if(isWifiConnected(context)){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return intToIp(ipAddress);
        }else if(isMobileNetworkConnected(context)){
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {
            }
        }
        return "";
    }

    public static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
}
