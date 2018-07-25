package com.wangcai.lottery.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wangcai.lottery.R;

public class FragmentDelayer extends BaseFragment {
    private static final String TAG = FragmentDelayer.class.getSimpleName();

    private static String KEY_FRAGMENT_ID = "key_fragment_id";
    private static String KEY_FRAGMENT_NAME = "key_fragment_name";
    private static String KEY_FRAGMENT_ARGUMENTS = "key_fragment_arguments";
    
    private FrameLayout frameLayout;
    private int frameLayoutId;
    private Fragment fragment;
    private String fragmentName;
    private boolean isSwitched = false;
    private Bundle arguments;
    
    /**
     * 
     * @param id 同一个activity里面使用多个FragmentDelayer时，必须保证id不相同，并且不与layout里使用的ID相同。建议使用R.drawable.xxx
     * @param fragmentName 需要延时加载的fragment的class name
     * @param args
     * @return
     */
    public static FragmentDelayer newInstance(int id, String fragmentName, Bundle args) {
        FragmentDelayer fragment = new FragmentDelayer();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_FRAGMENT_ID, id);
        bundle.putString(KEY_FRAGMENT_NAME, fragmentName);
        bundle.putBundle(KEY_FRAGMENT_ARGUMENTS, args);
        fragment.setArguments(bundle);
        
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        this.fragmentName = bundle.getString(KEY_FRAGMENT_NAME);
        this.arguments = bundle.getBundle(KEY_FRAGMENT_ARGUMENTS);
        frameLayoutId = bundle.getInt(KEY_FRAGMENT_ID);

        frameLayout = new FrameLayout(getActivity());
        frameLayout.setId(frameLayoutId);
        if (!getUserVisibleHint()) {
            inflater.inflate(R.layout.fragment_delayer, frameLayout, true);
        }
        return frameLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try2Replace2Target();
    }

    private void try2Replace2Target() {
        if (!isSwitched && frameLayout != null && getUserVisibleHint()) {
            isSwitched = true;
            frameLayout.removeAllViews();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            Log.d(TAG, "try2Replace2Target: replace to " + fragmentName);
            fragment = Fragment.instantiate(getActivity(), fragmentName, arguments);

            ft.replace(frameLayoutId, fragment, fragmentName);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try2Replace2Target();

        if (fragment != null) {
            fragment.setUserVisibleHint(isVisibleToUser);
        }
    }
}
