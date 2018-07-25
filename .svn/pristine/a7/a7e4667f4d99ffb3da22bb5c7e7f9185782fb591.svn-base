package com.wangcai.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.wangcai.lottery.R;
import com.wangcai.lottery.material.Calculation;

/**
 * 走势图
 * <p>
 * Created by ACE-PC on 2016/3/11.
 */
public class DrawTrendView extends View {

    private static final String TAG = DrawTrendView.class.getSimpleName();

    private Drawable checkedDrawable;
    private Drawable titleBackground;
    private int itemSize;
    private int horizontalGap;
    private int verticalGap;
    /**
     * false 0 数字显示成“1”，true 1 数字显示成“01” null 2 显示文字
     */
    private int numberStyle;
    /**
     * X 轴最大
     */
    private int maxcopiesx;
    /**
     * X 轴最小
     */
    private int mincopiesx;
    /**
     * y 轴分多少份
     */
    private int copies_y = 10;
    /**
     * x 轴每个坐标间的跨度,单位像素,根据实际view大小计算
     */
    private float delt_x;
    /**
     * y 轴每个坐标间的跨度,单位像素，根据实际view大小计算
     */
    private float delt_y;

    /**
     * 连线的颜色
     */
    private int lineColor;
    /**
     * 是否需要在ball之间连线
     */
    private boolean isNeedLink = false;
    private String[] displayText;
    private int column;
    private DisplayMethod method = DisplayMethod.SINGLE;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;// 毛边过滤
    private Paint linePaint;
    private int linkBallLineColor;
    private float textSize;
    private float padding_top;
    private int textColor;
    private int displayStyle;
    private int width;
    private int height;
    private String[] solelinkData;
    private String[][] morelinkData;

    public DrawTrendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DrawTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.TrendView);
        itemSize = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_itemsize, 48);
        verticalGap = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_verticalgap, 16);
        horizontalGap = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_horizontalgap, 0);
        textColor = attribute.getColor(R.styleable.TrendView_tr_textcolor, Color.BLACK);
        textSize = attribute.getDimension(R.styleable.TrendView_tr_textsize, 25);
        checkedDrawable = attribute.getDrawable(R.styleable.TrendView_tr_checkeddrawable);
        titleBackground = attribute.getDrawable(R.styleable.TrendView_tr_titlebackground);

        displayStyle = attribute.getInt(R.styleable.TrendView_tr_displaystyle, 0);
        numberStyle = attribute.getInt(R.styleable.TrendView_tr_numberstyle, 0);
        maxcopiesx = attribute.getInt(R.styleable.TrendView_tr_maxcopies_x, 9);
        mincopiesx = attribute.getInt(R.styleable.TrendView_tr_mincopies_x, 0);
        column = attribute.getInt(R.styleable.TrendView_tr_column, 0);
        lineColor = attribute.getColor(R.styleable.TrendView_tr_linecolor, Color.GRAY);
        isNeedLink = attribute.getBoolean(R.styleable.TrendView_tr_isNeedLinkEveryBall, false);
        linkBallLineColor = attribute.getColor(R.styleable.TrendView_tr_linkEveryBallLineColor, Color.GRAY);

        attribute.recycle();
        this.linePaint = new Paint();
        this.paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setColumn(maxcopiesx - mincopiesx + 1);
    }

    /**
     * 设置可以选中的数字的范围[copiesy, copiesx]
     */
    public void setNumber(int mincopiesx, int maxcopiesx) {
        if (this.mincopiesx == mincopiesx && this.maxcopiesx == maxcopiesx) {
            return;
        }
        this.mincopiesx = mincopiesx;
        this.maxcopiesx = maxcopiesx;
        requestLayout();
    }

    /**
     * 设置需要显示的文字集合
     **/
    public void setDisplayText(String[] displayText) {
        this.displayText = displayText;
    }

    /**
     * 设置数字显示的尺寸大小，单位像素
     */
    public void setItemSize(int itemSize) {
        if (this.itemSize == itemSize) {
            return;
        }
        this.itemSize = itemSize;
        requestLayout();
    }

    /**
     * 设置一行显示多少个数字
     */
    private void setColumn(int column) {
        if (this.column == column) {
            return;
        }
        this.column = column;
        requestLayout();
    }

    /**
     * 设置选中的数字显示的背景图
     */
    public void setCheckedDrawable(Drawable checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
    }

    /**
     * 行间垂直间隔，单位像素
     */
    public void setVerticalGap(int verticalGap) {
        if (this.verticalGap == verticalGap) {
            return;
        }
        this.verticalGap = verticalGap;
        requestLayout();
    }

    /**
     * 设置显示连线
     *
     * @param isNeedLink
     */
    public void setNeedLinkLine(boolean isNeedLink) {
        this.isNeedLink = isNeedLink;
        requestLayout();
    }

    public boolean isNeedLinkLine() {
        return this.isNeedLink;
    }

    /**
     * 数据设置
     *
     * @param array
     */
    public void setData(Object array) {
        if (array == null) {
            return;
        }

        if (array instanceof String[]) {
            this.solelinkData = (String[]) array;
            this.copies_y = solelinkData.length;
        } else if (array instanceof String[][]) {
            this.morelinkData = (String[][]) array;
            this.copies_y = morelinkData.length;
        }
        invalidate();
    }

    /**
     * 数字显示的风格，false 0 数字显示成“1”，true 1 数字显示成“01” null 2显示文字
     */
    public void setNumberStyle(Boolean numStyle) {
        if (numStyle == null) {
            this.numberStyle = 2;
        } else {
            this.numberStyle = numStyle ? 1 : 0;
        }
    }

    /**
     * 数字显示尺寸
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * 数字显示颜色
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setDisplayMethod(DisplayMethod method) {
        this.method = method;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = measureHeight((verticalGap + itemSize) * copies_y + verticalGap + itemSize);
        int measuredWidth = measureWidth((horizontalGap + itemSize) * (maxcopiesx - mincopiesx + 1));
        setMeasuredDimension(measuredWidth, measuredHeight);
        this.setViewSize(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawColor(Color.WHITE);
        drawTopNum(canvas);
        drawXLine(canvas);
        drawYLine(canvas);
        drawRightVerticalLine(canvas);
        if (displayStyle == 0 && isNeedLink && !(morelinkData != null && morelinkData.length > 0)) {
            drawBallLinkLine(canvas);
        }
        drawBall(canvas);
        drawBottomNum(canvas);
    }

    /**
     * 画顶部数字和开始横线轴
     */
    private void drawTopNum(Canvas canvas) {
        if (padding_top <= 0) {
            return;
        }
        linePaint.setStrokeWidth(1);
        linePaint.setColor(Color.GRAY);
        linePaint.setTextSize(textSize);
        float textX, textY;
        for (int i = 0, count = maxcopiesx - mincopiesx + 1; i < count; i++) {
            float[] xy = this.translateRowIndex2XY(i, i);
            String text = String.format(numberStyle == 0 ? "%d" : "%02d", i + mincopiesx);
            if (numberStyle == 2) {
                if (displayText != null) {
                    if (displayText.length >= maxcopiesx) {
                        text = moreDisplay(i);
                    } else {
                        Log.e(TAG, "Less than the array size required to display text");
                        text = "";
                    }
                }
            }

            titleBackground.setBounds((int) this.delt_x * i, 0, (int) (this.delt_x * i + this.delt_x), (int) (0 + this.delt_y));
            titleBackground.draw(canvas);
            textX = xy[0] + 0.5f * (itemSize - linePaint.measureText(String.valueOf(text)));
            textY = 0.5f * (this.delt_y + linePaint.getTextSize());
            canvas.drawText(text, textX, textY, linePaint);
        }

    }

    /**
     * 画y轴,--->
     *
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        linePaint.setStrokeWidth(1);
        linePaint.setColor(lineColor);
        for (int i = 0; i < copies_y + 1; i++) {
            canvas.drawLine(0, this.delt_y * i, this.width, this.delt_y * i, linePaint);
        }
    }

    /**
     * 画X轴,    ^|
     *
     * @param canvas
     */
    void drawYLine(Canvas canvas) {
        if ((solelinkData != null && solelinkData.length > 0) || (morelinkData != null && morelinkData.length > 0)) {
            linePaint.setStrokeWidth(1);
            linePaint.setColor(lineColor);
            for (int i = 0, count = maxcopiesx - mincopiesx + 1; i < count; i++) {
                canvas.drawLine(this.delt_x * i, i, this.delt_x * i, this.height, linePaint);
            }
        }
    }

    /**
     * 画最右边的横线
     *
     * @param canvas
     */
    private void drawRightVerticalLine(Canvas canvas) {
        linePaint.setStrokeWidth(2);
        linePaint.setColor(lineColor);
        canvas.drawLine(this.width, 0, this.width, this.height, linePaint);
    }

    private void drawBottomNum(Canvas canvas) {
        linePaint.setStrokeWidth(2);
        linePaint.setColor(lineColor);
        linePaint.setTextSize(textSize);
        canvas.drawLine(0, this.height, this.width, this.height, linePaint);
    }

    /**
     * 画两球连接线
     *
     * @param canvas
     */
    private void drawBallLinkLine(Canvas canvas) {
        canvas.setDrawFilter(paintFlagsDrawFilter);
        linePaint.setStrokeWidth(2);
        linePaint.setColor(linkBallLineColor);
        if (isNeedLink) {
            if (solelinkData != null && solelinkData.length > 0) {
                int firstIndex = Integer.parseInt(solelinkData[0]);
                float[] lastXy = this.translateRowIndex2XY(0, mincopiesx != 0 ? firstIndex - mincopiesx : firstIndex);
                for (int i = 0; i < solelinkData.length; i++) {
                    String value = solelinkData[i];
                    int xIndex = Integer.parseInt(value);
                    float[] xy = this.translateRowIndex2XY(i, mincopiesx != 0 ? xIndex - mincopiesx : xIndex);
                    canvas.drawLine(lastXy[0] + delt_x * 0.5f, lastXy[1] + delt_y * 0.5f, xy[0] + delt_x * 0.5f, xy[1] + delt_y * 0.5f, linePaint);

                    lastXy[0] = xy[0];
                    lastXy[1] = xy[1];
                }
            }
        }
    }

    private void drawBall(Canvas canvas) {
        linePaint.setColor(textColor);
        linePaint.setTextSize(textSize - 4);//比顶部字体小点

        if (displayStyle == 0 && solelinkData != null && solelinkData.length > 0) {
            single(canvas);
        } else if (displayStyle == 1 && morelinkData != null && morelinkData.length > 0) {
            more(canvas);
        }
    }

    private void single(Canvas canvas) {

        float textX, textY;
        for (int i = 0; i < solelinkData.length; i++) {
            int value = Integer.parseInt(solelinkData[i]);
            float[] xy = this.translateRowIndex2XY(i, mincopiesx != 0 ? value - mincopiesx : value);

            checkedDrawable.setBounds((int) xy[0], (int) xy[1], (int) xy[0] + itemSize, (int) xy[1] + itemSize);
            checkedDrawable.draw(canvas);

            textX = xy[0] + 0.5f * (itemSize - linePaint.measureText(String.valueOf(solelinkData[i])));
            textY = xy[1] + 0.5f * itemSize + 0.25f * linePaint.getTextSize();
            String text = String.format(numberStyle == 0 ? "%d" : "%02d", value);
            if (numberStyle == 2) {
                if (displayText != null) {
                    if (displayText.length >= maxcopiesx) {
                        text = moreDisplay(i);
                    } else {
                        Log.e(TAG, "Less than the array size required to display text");
                        text = "";
                    }
                }
            }
            canvas.drawText(text, textX, textY, linePaint);
        }
    }

    private void more(Canvas canvas) {
        float textX, textY;
        for (int i = 0; i < morelinkData.length; i++) {
            String[] testRow = morelinkData[i];
            for (int j = 0; j < testRow.length; j++) {
                int xIndex = Integer.parseInt(testRow[j]);
                float[] xy = this.translateRowIndex2XY(i, mincopiesx != 0 ? xIndex - mincopiesx : xIndex);

                checkedDrawable.setBounds((int) xy[0], (int) xy[1], (int) xy[0] + itemSize, (int) xy[1] + itemSize);
                checkedDrawable.draw(canvas);

                textX = xy[0] + 0.5f * (itemSize - linePaint.measureText(String.valueOf(testRow[j])));
                textY = xy[1] + 0.5f * itemSize + 0.25f * linePaint.getTextSize();
                String text = String.format(numberStyle == 0 ? "%d" : "%02d", xIndex);
                if (numberStyle == 2) {
                    if (displayText != null) {
                        if (displayText.length >= maxcopiesx) {
                            text = moreDisplay(i);
                        } else {
                            Log.e(TAG, "Less than the array size required to display text");
                            text = "";
                        }
                    } else {
                        text = testRow[j];
                    }
                }
                canvas.drawText(text, textX, textY, linePaint);
            }
        }
    }

    /**
     * 根据view的宽高，初始化部分尺寸
     *
     * @param w
     * @param h
     */
    void setViewSize(int w, int h) {
        this.width = w;
        this.height = h;
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        this.padding_top = (verticalGap + itemSize) * metrics.density;
        this.delt_x = horizontalGap + itemSize;
        this.delt_y = verticalGap + itemSize;    //减去顶部的预留空白后，再均分
        invalidate();
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 100;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.UNSPECIFIED) {
            result = specSize;
        }
        return result;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.UNSPECIFIED) {
            result = specSize;
        }
        return result;
    }

    /**
     * 根据位置行和列，转成对应的x,y坐标,不做校正，即都返回左上角坐标
     *
     * @param rowIndex 所在行，从0开始
     * @param xIndex   所在列，从0开始
     * @return [x0, y0]
     */
    private float[] translateRowIndex2XY(int rowIndex, int xIndex) {
        float[] xy = new float[2];
        xy[0] = this.delt_x * xIndex + 0.5f * horizontalGap;
        xy[1] = this.delt_y + this.delt_y * rowIndex + 0.5f * verticalGap;
        return xy;
    }

    private String moreDisplay(int iteration) {
        String text = "";
        switch (method) {
            case SINGLE:
                text = displayText[iteration];
                break;
            case TWIN:
                String twin = "";
                for (int i = 0; i < 2; i++) {
                    twin += displayText[iteration];
                }
                text = twin;
                break;
            case THREE:
                String three = "";
                for (int i = 0; i < 2; i++) {
                    three += displayText[iteration];
                }
                text = three;
                break;
            case JUNKO:
                Calculation.getInstance().combine(displayText, 3);

                break;
            case MORE:

                break;
            default:
                text = displayText[iteration];
        }
        return text;
    }

    private enum DisplayMethod {
        SINGLE, TWIN, THREE, JUNKO, MORE
    }

    public int getMinCopiesX() {
        return mincopiesx;
    }

    public int getMaxCopiesX() {
        return maxcopiesx;
    }
}
