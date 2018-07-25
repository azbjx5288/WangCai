package com.wangcai.lottery.component;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.data.ActivityData;
import com.wangcai.lottery.fragment.AdvertisementFragment;
import com.wangcai.lottery.material.ConstantInformation;


public class AdvertisementView extends Dialog implements View.OnClickListener{
    private ActivityData activityData = null;
    private OnAccedingListener onAccedingListener;
    private BaseFragment fragment;
    private ImageView imageView=null;
    private int visibility=View.GONE;
    private int strokeWidth = 1; // 3dp 边框宽度
    private int roundRadius = 8; // 8dp 圆角半径
    private int strokeColor = Color.parseColor("#ab0101");//边框颜色
    private int fillColor = Color.parseColor("#ab0101");//内部填充颜色
    private int bleft = 10, btop = 0, bright = 10, bbottom = 0;

    public AdvertisementView(BaseFragment fragment, ActivityData activityData) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.activityData=activityData;
        init();
    }

    public AdvertisementView(BaseFragment fragment, int theme) {
        super(fragment.getActivity(), theme);
        this.fragment = fragment;
        init();
    }

    public AdvertisementView(BaseFragment fragment, ActivityData activityData, int theme) {
        super(fragment.getActivity(), theme);
        this.fragment = fragment;
        this.activityData=activityData;
        init();
    }

    private void init(){
        View contentView = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.view_advertisement, null);
        setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setCanceledOnTouchOutside(true);
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setRoundRadius(int roundRadius) {
        this.roundRadius = roundRadius;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = Color.parseColor(strokeColor.length()==7?strokeColor:"#D22A2A");
    }

    public void setFillColor(String fillColor) {
        this.fillColor = Color.parseColor(fillColor.length()==7?fillColor:"#D22A2A");
    }

    public void setButtonPadding(int left, int top, int right, int bottom) {
        if (bleft != left + 10 || bright != right + 10 || btop != top || bbottom != bottom) {
            this.bleft = left + 10;
            this.btop = top;
            this.bright = right + 10;
            this.bbottom = bottom;
        }
    }

    public void setActivityData(ActivityData activityData) {
        this.activityData = activityData;
    }

    private ActivityData getActivityData() {
        return activityData;
    }

    public void setOnAccedingListener(OnAccedingListener onAccedingListener) {
        this.onAccedingListener = onAccedingListener;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showDialog() {
        if(getActivityData()==null){
            return;
        }

        Window window = getWindow();
        window.setWindowAnimations(R.style.style_dialog_anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);

        Button button =  findViewById(R.id.partake_button);
        button.setOnClickListener(this);
        imageView= findViewById(R.id.advertisement);
        Glide.with(getContext()).load(getActivityData().getLargeIcon()).asBitmap().listener(listener).into(imageView);

        ImageView closeView=findViewById(R.id.close);
        closeView.setOnClickListener(this);
        closeView.setVisibility(visibility);

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        button.setBackground(gd);
        button.setPadding(bleft, btop, bright, bbottom);

        show();
    }

    //监听图片加载
    RequestListener listener=new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //加载异常
            hide();
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            //加载成功
            imageView.setImageDrawable(resource);
            return false;
        }
    };

    public void setCloseVisibility(int visibility){
        this.visibility=visibility;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                if (onAccedingListener!=null){
                    onAccedingListener.onAccedingClick();
                }
                dismiss();
                break;
            case R.id.partake_button:
                if(ConstantInformation.isFastClick()) {
                    if (getActivityData() != null || getActivityData().getUrl().length() > 0) {
                        if (onAccedingListener!=null){
                            onAccedingListener.onAccedingClick();
                        }
                        AdvertisementFragment.launch(fragment, activityData);
                    }
                }
                dismiss();
                break;
        }
    }

    /**
     * 选中监听器
     */
    public interface OnAccedingListener {
        void onAccedingClick();
    }
}
