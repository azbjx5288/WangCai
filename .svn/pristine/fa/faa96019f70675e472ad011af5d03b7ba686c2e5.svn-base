package com.wangcai.lottery.game;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.component.TagCloudView;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.data.MethodList;
import com.wangcai.lottery.view.TableMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于显示弹出菜单，选择玩法
 * Created by Alashi on 2016/2/17.
 */
public class MenuController {
    private static final String TAG = MenuController.class.getSimpleName();

    @BindView(R.id.tableMenu)
    TableMenu tableMenu;
    @BindView(R.id.preference)
    TagCloudView preference;


    /*@BindView(R.id.game_preference_1) TextView preference1;
    @BindView(R.id.game_preference_2) TextView preference2;
    @BindView(R.id.game_preference_3) TextView preference3;
    @BindView(R.id.game_preference_4) TextView preference4;*/

    private PopupWindow popupWindow;
    private Activity activity;

    private TableMenu.OnClickMethodListener onClickMethodListener;
    private Method currentMethod;
    private ArrayList<MethodList> methodList;
    private ArrayList<Method> commonMethods = new ArrayList<>();
    private ChooserModel chooserModel;
    private int lotteryId;
    private boolean dataChanged;
    private List<String> tags = new ArrayList<>(0);
    private HashMap<Integer, Object[]> map = new HashMap<>(0);//记录选择的位置

    public MenuController(FragmentActivity activity, Lottery lottery) {
        this.activity = activity;
        this.lotteryId = lottery.getId();
    }

    private ChooserModel getChooserModel() {
        if (chooserModel == null) {
            chooserModel = ChooserModel.get(activity, "model_history_"
                    + WangCaiApp.getUserCentre().getUserID() + "_" + lotteryId);
        }
        return chooserModel;
    }

    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
        if (tableMenu != null) {
            tableMenu.setCurrentMethod(currentMethod);
        }
    }

    public void setMethodList(ArrayList<MethodList> methodList) {
        this.methodList = methodList;
        dataChanged = true;
    }

    public boolean isShowing() {
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }

    public void show(View anchor) {
        if (methodList == null || methodList.size() == 0) {
            return;
        }
        if (popupWindow == null) {
            View topView = LayoutInflater.from(activity).inflate(R.layout.game_menu_layout, null);
            tableMenu = topView.findViewById(R.id.tableMenu);
            ButterKnife.bind(this, topView);
            tableMenu.setOnClickMethodListener(onClickMethodListener);

            popupWindow = new PopupWindow(activity);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            //应用区域
            Rect outRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
            int height = outRect.height() - anchor.getHeight();
            popupWindow.setHeight(height);
            popupWindow.setContentView(topView);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setAnimationStyle(R.style.pulldown_in_out);
        }

        if (dataChanged) {
            dataChanged = false;
            getChooserModel().setMethodList(methodList);
            tableMenu.setMethodList(methodList);
            tableMenu.setCurrentMethod(currentMethod);
        }

        usePreference();

        preference.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                Method method = commonMethods.get(position);
                if (method != null && onClickMethodListener != null) {
                    if (method.getId() != currentMethod.getId() && currentMethod.getPid() != method.getId()) {
                        currentMethod = method;
                        onClickMethodListener.onClickMethod(currentMethod);
                    }
                }
            }
        });

        popupWindow.showAsDropDown(anchor);
    }

    //常用玩法点击
    /*@OnClick({R.id.game_preference_1, R.id.game_preference_2, R.id.game_preference_3, R.id.game_preference_4})
    public void onClickPreferenceTextView(TextView textView) {
        if (onClickMethodListener != null) {
            onClickMethodListener.onClickMethod((Method) textView.getTag());
        }
    }*/
    //保存常用玩法
    public void addPreference(Method method) {
        getChooserModel().addChoosedMethod(method);
    }

    // 定点标签记录和view变化
    private void bindPositionView(int position) {
        for (int i = 0; i < tags.size(); i++) {
            if (i == position) {
                map.put(i, new Object[]{tags.get(i), true});
            } else {
                map.put(i, new Object[]{tags.get(i), false});
            }
        }
        preference.setTagsByPosition(map, tags);
    }

    //引用常用玩法
    private void usePreference() {
        tags.clear();
        commonMethods.clear();
        map.clear();
        if (chooserModel.getLotteryInfos().size() >= 6) {
            for (int i = 0; i < 6; i++) {
                if (chooserModel.getMethodInfo(i) != null) {
                    tags.add(chooserModel.getMethodInfo(i).method.getNameCn());
                    commonMethods.add(chooserModel.getMethodInfo(i).method);
                    map.put(i, new Object[]{tags.get(i), false});
                }
            }
        } else {
            for (int i = 0; i < chooserModel.getLotteryInfos().size(); i++) {
                if (chooserModel.getMethodInfo(i) != null) {
                    tags.add(chooserModel.getMethodInfo(i).method.getNameCn());
                    commonMethods.add(chooserModel.getMethodInfo(i).method);
                    map.put(i, new Object[]{tags.get(i), false});
                }
            }
        }

        int common = 0;
        for (Integer in : map.keySet()) {
            if (currentMethod != null
                    && currentMethod.getPid() == chooserModel.getMethodInfo(in).method.getPid()
                    && currentMethod.getId() == chooserModel.getMethodInfo(in).method.getId()) {
                common = in;
            }
        }

        bindPositionView(common);
    }

    public void hide() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public void setOnClickMethodListener(TableMenu.OnClickMethodListener onClickMethodListener) {
        this.onClickMethodListener = onClickMethodListener;
        if (tableMenu != null) {
            tableMenu.setOnClickMethodListener(onClickMethodListener);
        }
    }
}
