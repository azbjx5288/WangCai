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
 * 绘制开奖号码
 *
 * Created by ACE-PC on 2016/3/11.
 */
public class DrawCodeView extends View {

    private static final String TAG = DrawCodeView.class.getSimpleName();

    private Drawable checkedDrawable;
    private Drawable background;
    private int itemSize;
    private int itemHeight;
    private int horizontalGap;
    private int verticalGap;
    private Boolean numberStyle;
    private int maxcopiesx;
    private int copies_y = 10;
    private float delt_x;
    private float delt_y;
    private int lineColor;
    private String[] displayText;
    private int column;
    private DisplayMethod method = DisplayMethod.SINGLE;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;// 毛边过滤
    private Paint linePaint;
    private float textSize;
    private float padding_top;
    private int textColor;
    private int width;
    private int height;
    private String[][] morelinkData;

    public DrawCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DrawCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.TrendView);
        itemSize = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_itemsize, 48);
        itemHeight = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_itemheight, 48);
        verticalGap = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_verticalgap, 16);
        horizontalGap = attribute.getDimensionPixelSize(R.styleable.TrendView_tr_horizontalgap, 0);
        textColor = attribute.getColor(R.styleable.TrendView_tr_textcolor, Color.BLACK);
        textSize = attribute.getDimension(R.styleable.TrendView_tr_textsize, 25);
        checkedDrawable = attribute.getDrawable(R.styleable.TrendView_tr_checkeddrawable);
        background = attribute.getDrawable(R.styleable.TrendView_tr_titlebackground);
        numberStyle = attribute.getBoolean(R.styleable.TrendView_tr_numberstyle, true);
        maxcopiesx = attribute.getInt(R.styleable.TrendView_tr_maxcopies_x, 5);
        column = attribute.getInt(R.styleable.TrendView_tr_column, 0);
        lineColor = attribute.getColor(R.styleable.TrendView_tr_linecolor, Color.GRAY);

        attribute.recycle();
        this.linePaint = new Paint();
        this.paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setColumn(maxcopiesx);
    }

    /**
     * 设置可以选中的数字的范围[copiesy, copiesx]
     */
    public void setNumber(int maxcopiesx) {
        if (this.maxcopiesx == maxcopiesx) {
            return;
        }
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
     * 数据设置
     *
     * @param array
     */
    public void setData(Object array) {
        if (array == null || !(array instanceof String[][])) {
            Log.e(TAG, "please check the data type is correct.");
            return;
        }

        this.morelinkData = (String[][]) array;
        this.maxcopiesx = morelinkData[0].length;
        this.copies_y = morelinkData.length;

        invalidate();
    }

    /**
     * 数字显示的风格，true 数字显示成“1”，false 数字显示成“01” null显示文字
     */
    public void setNumberStyle(Boolean numberStyle) {
        this.numberStyle = numberStyle;
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
        int measuredHeight = measureHeight((verticalGap + itemHeight) * copies_y);
        int measuredWidth = measureWidth(maxcopiesx > 1 ? (horizontalGap + itemSize) * (maxcopiesx) : (horizontalGap + itemSize) * (maxcopiesx) + (horizontalGap + itemSize));
        setMeasuredDimension(measuredWidth, measuredHeight);
        this.setViewSize(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawColor(Color.WHITE);
        drawBall(canvas);
        drawXLine(canvas);
        drawYLine(canvas);
        drawRightVerticalLine(canvas);
        drawBottomNum(canvas);
    }

    /**
     * 画y轴,--->
     *
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        linePaint.setStrokeWidth(1);
        linePaint.setColor(lineColor);
        for (int i = 0; i < copies_y; i++) {
            canvas.drawLine(0, this.delt_y * i, this.width, this.delt_y * i, linePaint);
        }
    }

    /**
     * 画X轴,    ^|
     *
     * @param canvas
     */
    void drawYLine(Canvas canvas) {
        if (morelinkData != null && morelinkData.length > 0) {
            linePaint.setStrokeWidth(1);
            linePaint.setColor(lineColor);
            for (int i = 0, count = maxcopiesx; i < count; i++) {
                canvas.drawLine(this.delt_x * i, i, this.delt_x * i, this.height, linePaint);
            }
        }
    }

    private void drawBottomNum(Canvas canvas) {
        linePaint.setStrokeWidth(2);
        linePaint.setColor(lineColor);
        linePaint.setTextSize(textSize);
        canvas.drawLine(0, this.height, this.width, this.height, linePaint);
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

    private void drawBall(Canvas canvas) {
        if (morelinkData != null && morelinkData.length > 0) {
            code(canvas);
        }
    }

    private void code(Canvas canvas) {

        if (padding_top <= 0) {
            return;
        }

        float textX, textY;
        for (int i = 0; i < morelinkData.length; i++) {
            String[] testRow = morelinkData[i];
            for (int j = 0; j < testRow.length; j++) {
                float[] xy = this.translateRowIndex2XY(i, j);
                String text = null;
                if (numberStyle == null) {
                    if (displayText.length >= maxcopiesx) {
                        text = moreDisplay(j);
                    } else {
                        Log.e(TAG, "Less than the array size required to display text");
                        text = "";
                    }
                } else {
                    text = testRow[j];
                }
                if (testRow.length > 1) {
                    linePaint.setColor(textColor);
                    linePaint.setTextSize(textSize - 4);//比顶部字体小点
                    checkedDrawable.setBounds((int) xy[0], (int) xy[1], (int) (xy[0] + this.delt_x), (int) (xy[1] + this.delt_y));
                    checkedDrawable.draw(canvas);
                    textX = xy[0] + (this.delt_x / 2 - linePaint.measureText(String.valueOf(text)) / 2);
                    textY = xy[1] + (this.delt_y + linePaint.getTextSize() / 2) / 2;
                } else {
                    linePaint.setColor(Color.GRAY);
                    linePaint.setTextSize(textSize - 4);//比顶部字体小点
                    background.setBounds((int) xy[0], (int) xy[1], (int) (xy[0] + this.delt_x * 2), (int) (xy[1] + this.delt_y));
                    background.draw(canvas);
                    textX = xy[0] + (this.delt_x - linePaint.measureText(String.valueOf(text)) / 2);
                    textY = xy[1] + (this.delt_y + linePaint.getTextSize() / 2) / 2;
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
        this.padding_top = (verticalGap + itemHeight) * metrics.density;
        this.delt_x = horizontalGap + itemSize;
        this.delt_y = verticalGap + itemHeight;    //减去顶部的预留空白后，再均分
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
        xy[0] = this.delt_x * xIndex;
        xy[1] = this.delt_y * rowIndex;
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

    public int getMaxCopiesX() {
        return maxcopiesx;
    }
}
