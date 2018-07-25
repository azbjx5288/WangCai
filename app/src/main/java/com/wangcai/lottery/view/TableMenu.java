package com.wangcai.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.data.MethodList;
import com.wangcai.lottery.data.MethodType;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩法组的显示
 * Created by User on 2016/2/22.
 */
public class TableMenu extends ViewGroup implements View.OnClickListener {
    private Method currentMethod;
    private List<TextView> headmanView = new ArrayList<>();
    private SparseArray<TextView> itemView = new SparseArray<>();

    private int offsetX;
    private int offsetY;
    private float textSize;
    private int headmanTextColor;
    private int itemTextColor;
    private int currentTextColor;
    private Paint divideLinePaint;
    private int[] divideLineY;
    private int divideLineHigh;
    private List<MethodList> methodMenuList;
    private OnClickMethodListener onClickMethodListener;

    public interface OnClickMethodListener {
        void onClickMethod(Method method);
    }

    public TableMenu(Context context) {
        super(context);
    }

    public TableMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TableMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setWillNotDraw(false);//ViewGroup需要调用setWillNotDraw，否则不会触发onDraw

        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.TableMenu);
        offsetX = attribute.getDimensionPixelSize(R.styleable.TableMenu_tmHorizontalGap, 32);
        offsetY = attribute.getDimensionPixelSize(R.styleable.TableMenu_tmVerticalGap, 16);
        textSize = attribute.getDimensionPixelSize(R.styleable.TableMenu_tmTextSize, 24);
        headmanTextColor = attribute.getColor(R.styleable.TableMenu_tmHeadmanTextColor, Color.GRAY);
        itemTextColor = attribute.getColor(R.styleable.TableMenu_tmItemTextColor, Color.BLACK);
        currentTextColor = attribute.getColor(R.styleable.TableMenu_tmCurrentTextColor, Color.RED);
        int divideLineColor = attribute.getColor(R.styleable.TableMenu_tmDivideLineColor, Color.GRAY);
        attribute.recycle();

        divideLineHigh = (int) getContext().getResources().getDisplayMetrics().density;

        divideLinePaint = new Paint();
        divideLinePaint.setColor(divideLineColor);
    }

    public void setOnClickMethodListener(OnClickMethodListener onClickMethodListener) {
        this.onClickMethodListener = onClickMethodListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (itemView.size() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        calculateLayout(MeasureSpec.getSize(widthMeasureSpec), false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        calculateLayout(r - l, true);
    }

    private void calculateLayout(int maxWidth, boolean isLayout) {
        int headmanWidth = 0;//
        int headmanHeight = 0;//
        int widthMS = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
        int heightMS = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
        for (TextView view : headmanView) {
            view.measure(widthMS, heightMS);
            headmanWidth = Math.max(headmanWidth, view.getMeasuredWidth());
            headmanHeight = view.getMeasuredHeight();
        }

        int childCount = getChildCount();
        int cX = 0;
        int cY = -headmanHeight - offsetY;
        int divideLineIndex = -1;
        int lastY = 0; //上一组，最后一个按钮的右下角的Y值
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view.getTag() == null) {
                //组名的
                cX = 0;
                cY += headmanHeight + offsetY;
                if (isLayout) {
                    divideLineY[++divideLineIndex] = (int) ((cY - lastY) * 0.5 + lastY);
                    view.layout(cX, cY, cX + view.getMeasuredWidth(), cY + view.getMeasuredHeight());
                    lastY = cY + view.getMeasuredHeight();
                }
                cX += headmanWidth + offsetX;
            } else {
                if (!try2Layout(cX, maxWidth, view)) {
                    cX = headmanWidth + offsetX;
                    cY += headmanHeight + offsetY;
                }

                if (isLayout) {
                    view.layout(cX, cY, cX + view.getMeasuredWidth(), cY + view.getMeasuredHeight());
                    lastY = cY + view.getMeasuredHeight();
                } else {
                    if (childCount - 1 == i) {
                        cY += view.getMeasuredHeight();
                    }
                }
                cX += view.getMeasuredWidth() + offsetX;
            }
        }

        if (!isLayout) {
            //setMeasuredDimension(maxWidth, cY);
            setMeasuredDimension(maxWidth, Math.max(headmanHeight + offsetY, cY));
        }
    }

    private boolean try2Layout(int cX, int maxW, View view) {
        int widthMS = MeasureSpec.makeMeasureSpec(maxW, MeasureSpec.AT_MOST);
        int heightMS = MeasureSpec.makeMeasureSpec(1080, MeasureSpec.AT_MOST);
        view.measure(widthMS, heightMS);
        return view.getMeasuredWidth() < maxW - cX;
    }

    public void setCurrentMethod(Method currentMethod) {
        if (this.currentMethod != null) {
            if (this.currentMethod.getId() == currentMethod.getId()) {
                return;
            }

            TextView textView = itemView.get(this.currentMethod.getId());
            if (textView != null) {
                textView.setTextColor(itemTextColor);
            }
        }

        this.currentMethod = currentMethod;
        TextView textView = itemView.get(currentMethod.getId());
        if (textView != null) {
            textView.setTextColor(currentTextColor);
        }
    }

    public void setMethodList(List<MethodList> methodList) {
        removeAllViews();
        this.methodMenuList = methodList;
        expandView(methodList);
        requestLayout();
    }

    private void expandView(List<MethodList> methodList) {
        headmanView.clear();
        itemView.clear();
        divideLineY = null;
        if (methodList == null || methodList.size() == 0) {
            return;
        }
        divideLineY = new int[methodList.size()];
        int index = 0;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView textView;
        for (MethodList methods : methodList) {
            textView = createHeadman(methods.getNameCn());
            headmanView.add(textView);
            addViewInLayout(textView, index++, layoutParams, true);
            for (MethodType methodGroup : methods.getChildren()) {
                if (methodGroup.getChildren() != null) {
                    for (Method method : methodGroup.getChildren()) {
                        addViewInLayout(createItem(method), index++, layoutParams, true);
                    }
                }
            }
        }
    }

    private TextView createHeadman(String name) {
        TextView textView = new TextView(getContext());
        textView.setText(name);
        textView.setTextColor(headmanTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setMaxLines(1);
        textView.setTag(null);
        return textView;
    }

    //菜单列表　
    private TextView createItem(Method method) {
        TextView textView = new TextView(getContext());
        textView.setText(method.getNameCn());
        textView.setTextColor(itemTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setMaxLines(1);
        textView.setTag(method);
        textView.setOnClickListener(this);
        itemView.put(method.getId(), textView);
        return textView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        if (divideLineY != null && divideLineY.length > 0) {
            for (int i = 1; i < divideLineY.length - 1; i++) {
                canvas.drawRect(0, divideLineY[i], width, divideLineY[i] + divideLineHigh, divideLinePaint);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (onClickMethodListener != null) {
            onClickMethodListener.onClickMethod(((Method) v.getTag()));
        }
    }
}
