package com.wangcai.lottery.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.internal.util.Predicate;
import com.wangcai.lottery.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 测试专用
 * Created by Alashi on 2015/12/18.
 */
public class TestFragment extends BaseFragment{
    private static final String TAG = TestFragment.class.getSimpleName();

    @BindView(R.id.button)
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new XX(){}.D();
        Log.i(TAG, "onViewCreated: " + button);

        button.setOnClickListener(this::testTo);

        File dir = new File("/sdcard/");
        File[] dirs = dir.listFiles(f -> f.isDirectory());
        Log.i(TAG, "onViewCreated: " + dirs.length);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Log.i(TAG, "onViewCreated:sumAll1  " + sumAll(numbers, n -> true));
        Log.i(TAG, "onViewCreated:sumAll2  " + sumAll(numbers, n -> n % 2 == 0));
        Log.i(TAG, "onViewCreated:sumAll3  " + sumAll(numbers, n -> n > 3));

        String[] stringArray = {"IntelliJ IDEA", "AppCode", "CLion", "0xDBE", "Upsource"};
        Arrays.sort(stringArray, String::compareToIgnoreCase);
        Log.i(TAG, "onViewCreated: " + Arrays.deepToString(stringArray));
    }

    private void testTo(View v) {
        Toast.makeText(getActivity(), "xx 调用button xx", Toast.LENGTH_SHORT).show();
    }

    public int sumAll(List<Integer> numbers, Predicate<Integer> p) {
        int total = 0;
        for (int number : numbers) {
            if (p.apply(number)) {
                total += number;
            }
        }
        return total;
    }

    public interface XX {
        //接口默认函数
        @TargetApi(24)
        default void D() {
            Log.i(TAG, "XX.D: default interface run");
        }
    }
}
