package com.wangcai.lottery.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.LazyBaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.AdvertisementView;
import com.wangcai.lottery.component.AppBarLayoutOverScrollViewBehavior;
import com.wangcai.lottery.component.CommonTabLayout;
import com.wangcai.lottery.component.CustomTabEntity;
import com.wangcai.lottery.component.CycleViewPager;
import com.wangcai.lottery.component.FragImageView;
import com.wangcai.lottery.component.HomeFragmentPagerAdapter;
import com.wangcai.lottery.component.OnSortClickListener;
import com.wangcai.lottery.component.OnTabSelectListener;
import com.wangcai.lottery.component.RoundProgressBar;
import com.wangcai.lottery.component.ViewFactory;
import com.wangcai.lottery.data.ActivityCommand;
import com.wangcai.lottery.data.ActivityData;
import com.wangcai.lottery.data.BannerListCommand;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryListCommand;
import com.wangcai.lottery.data.Notice;
import com.wangcai.lottery.data.Series;
import com.wangcai.lottery.data.SeriesCommand;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordType;
import com.wangcai.lottery.pattern.TabEntity;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 首页
 */

public class FragmentHome extends LazyBaseFragment {
    private static final String TAG = FragmentHome.class.getSimpleName();
    private static final int ACTIVITY_ID = 1;
    private static final int BANNER_LIST_ID = 2;
    private static final int LOTTERY_TRACE_ID = 3;
    private static final int ACTIVITY_CLICK_ID = 4;
    private static final int SERIES_ID = 5;

    @BindView(R.id.container)
    CoordinatorLayout container; //拖拽反应区域
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.uc_progressbar)
    RoundProgressBar progressBar;
    @BindView(R.id.uc_kefu_iv)
    ImageView mKefuIv;
    @BindView(R.id.uc_msg_iv)
    ImageView mMsgIv;
    @BindView(R.id.uc_tablayout)
    CommonTabLayout mTablayout;
    @BindView(R.id.tag_ocreate_ll)
    LinearLayout tagOcreate;
    @BindView(R.id.tagcontainerLayout)
    TagContainerLayout mTagContainerLayout;
    @BindView(R.id.uc_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.imageView)
    FragImageView imageView;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private CycleViewPager cycleViewPager;
    private ArrayList<Notice> notices;
    private ActivityData activityData;
    private AdvertisementView advertisementView;
    private List<Lottery> item = new ArrayList<>();
    private RecordType recordType;
    private int site = 0;
    private boolean sortFlag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, getActivity().getResources().getString(R.string.app_name), R.layout.fragment_home, true, false);
    }

    @Override
    public void onFirstUserVisible() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //显示加载进度对话框
                dialogShow("正在加载...");
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                    //在这里添加调用接口获取数据的代码
                    recordType = ConstantInformation.getLotteryModel(getContext());
                    lotteryListLoad();
                    getSeries();
                    //doSomething()
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    // 加载成功
                    initTab();
                    initListener();
                    // 渲染页面
                } else {
                    // 加载失败
                }
                //关闭对话框
                dialogHide();
            }
        }.execute();
    }

    public void onUserVisible() {
        if (recordType.getLotteryInfos() == null || recordType.getLotteryInfos().size() == 0) {
            lotteryListLoad();
        }
        //活动显示
        activityLoad();
        if (imageView != null) {
            imageView.setDrag(getActivity(), imageView);
        }
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        fragments = getFragments();
        HomeFragmentPagerAdapter myFragmentPagerAdapter = new HomeFragmentPagerAdapter(getFragmentManager(), fragments, getNames());
        mTablayout.setIconGravity(Gravity.LEFT);
        mTablayout.setTabData(mTabEntities);
        mViewPager.setAdapter(myFragmentPagerAdapter);
    }

    /**
     * 绑定事件
     */
    private void initListener() {
        //Banner
        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.uc_zoomiv);
        //活动控键
        advertisementView = new AdvertisementView(this, R.style.Dialog);
        mAppBarLayout.addOnOffsetChangedListener((AppBarLayout appBarLayout, int verticalOffset) -> {
            /*float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
            if (mKefuIv != null && mMsgIv != null) {
                StatusBarUtil.setTranslucentForImageView(getActivity(), (int) (255f * percent), null);
            }*/
        });
        AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior) ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();
        myAppBarLayoutBehavoir.setOnProgressChangeListener((float progress, boolean isRelease) -> {
            progressBar.setProgress((int) (progress * 360));
            if (progress == 1 && !progressBar.isSpinning && isRelease) {
                // 刷新viewpager里的fragment
            }
            if (mMsgIv != null) {
                if (progress == 0 && !progressBar.isSpinning) {
                    mMsgIv.setVisibility(View.VISIBLE);
                } else if (progress > 0 && mKefuIv.getVisibility() == View.VISIBLE) {
                    mMsgIv.setVisibility(View.INVISIBLE);
                }
            }
        });
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                selectPage(position);
            }

            @Override
            public void onTabReselect(int position) {
                SortingLayer(position);
            }
        });
        //大厅选择
        mTablayout.setOnSortClickListener(new OnSortClickListener() {
            @Override
            public void onSortClick(View v, boolean dupo) {
                SortingLayer(site);
            }
        });

        //选择分类
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                if (site == 0) {
                    List<Series> seriess = WangCaiApp.getUserCentre().getSeriesList();
                    if (seriess.size() > 0) {
                        if (position != 0) {
                            Series series = seriess.get(--position);
                            ((LottoFragment) fragments.get(0)).notifyData(series.getId(), false);
                        } else {
                            ((LottoFragment) fragments.get(0)).notifyData(0, true);
                        }
                    }
                }
            }

            @Override
            public void onTagLongClick(final int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);
                selectPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public String[] getNames() {
        String[] mNames = new String[]{"彩票大厅", "GA游戏"};
        mTabEntities.add(new TabEntity(mNames[0], R.drawable.ic_tap_lottery_pressed, R.drawable.ic_tap_lottery, true, R.drawable.arrow_down));
        mTabEntities.add(new TabEntity(mNames[1], R.drawable.ic_tap_game_pressed, R.drawable.ic_tap_game, false, R.drawable.arrow_down));
        return mNames;
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LottoFragment());
        fragments.add(new GaFragment());
        return fragments;
    }

    /**
     * 选择某页
     *
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        mTablayout.setSortIcon(site != position ? true : false); //tab 图标动画归位
        tagOcreate.setVisibility(View.GONE);
        if (position == 0) {
            ((LottoFragment) fragments.get(position)).notifyData(item);
        } else {
            ((GaFragment) fragments.get(position)).notifyData(item);
        }
        site = position;
        mViewPager.setCurrentItem(position);
    }

    /**
     * 分类层操作
     *
     * @param position
     */
    private void SortingLayer(int position) {
        if (position == 0 && sortFlag) {
            tagOcreate.setVisibility(View.VISIBLE);
            sortFlag = false;
        } else {
            tagOcreate.setVisibility(View.GONE);
            sortFlag = true;
        }
        mTablayout.sortIconAnimate(sortFlag);
    }

    /**
     * 更新Banner
     */
    private void updateBanner() {
        if (notices == null || notices.size() == 0) {
            return;
        }
        initialize();
    }

    private void initialize() {
        String[] imageUris = new String[notices.size()];
        for (int i = 0, size = notices.size(); i < size; i++) {
            imageUris[i] = notices.get(i).getPath();
        }
        List<View> views = new ArrayList<>();
        views.add(ViewFactory.getImageView(getActivity(), notices.get(notices.size() - 1).getPath()));
        for (int i = 0, size = notices.size(); i < size; i++) {
            views.add(ViewFactory.getImageView(getActivity(), notices.get(i).getPath()));
        }
        // 将第一个view添加进来
        views.add(ViewFactory.getImageView(getActivity(), notices.get(0).getPath()));

        cycleViewPager.setCycle(true);
        cycleViewPager.setData(views, mAdCycleViewListener);
        cycleViewPager.setWheel(true);
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            if (TextUtils.isEmpty(notices.get(position).getUrl())) {
                NoticeDetailsFragment.launch(FragmentHome.this, notices.get(position));
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notices.get(position).getUrl()));
                startActivity(browserIntent);
            }
        }
    };

    private void initActivity() {
        if (activityData == null) {
            hideFunction();
            return;
        }
        if (!activityData.isStatus() || ConstantInformation.afterDate(activityData.getEndTime())) { //状态 fales 时间结束
            hideFunction();
            return;
        } else {
            if (advertisementView.isShowing()) {
                advertisementView.dismiss();
            }

            String url = activityData.getIcon();
            if (!TextUtils.isEmpty(url)) {
                Glide.with(this).load(url).asBitmap().priority(Priority.HIGH).listener(listener).into(imageView);
            }else{
                hideFunction();
            }

            if (!activityData.isPartIn()) {
                imageView.setVisibility(View.GONE);
                imageView.setClickable(false);
                advertisementView.setActivityData(activityData);
                advertisementView.setCloseVisibility(View.GONE);
                advertisementView.setStrokeColor(activityData.getColor());
                advertisementView.setFillColor(activityData.getColor());
                advertisementView.setButtonPadding(20, 0, 20, 0);
                advertisementView.setOnAccedingListener(() -> {
                    showImageView();
                });
                advertisementView.setOnCancelListener((DialogInterface dialog) -> {
                    showImageView();
                });
                advertisementView.showDialog();
            } else {
                showImageView();
            }

            imageView.setOnActivityClickListener(() -> {
                if (ConstantInformation.isFastClick()) {
                    activityClick();
                }
            });
        }
    }

    private void hideFunction() {
        imageView.clearAnimation();
        imageView.setClickable(false);
        imageView.setImageResource(R.color.colorTransparent);
        imageView.setVisibility(View.GONE);
    }

    private void showImageView() {
        imageView.setClickable(true);
        imageView.setVisibility(View.VISIBLE);
    }

    private void activitylaunch() {
        if (activityData == null) {
            if (imageView != null) {
                imageView.clearAnimation();
                imageView.setImageResource(R.color.colorTransparent);
                imageView.setVisibility(View.GONE);
                imageView.setClickable(false);
                showToast("活动已结束!谢谢参与");
            }
            return;
        }
        if (activityData.isPartIn()) {
            AdvertisementFragment.launch(this, activityData);//fragment.getActivity(), AdvertisementFragment.class, bundle
        } else {
            initActivity();
        }
    }

    //监听图片加载
    RequestListener listener=new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //加载异常
            hideFunction();
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            //加载成功
            imageView.setImageDrawable(resource);
            showImageView();
            return false;
        }
    };

    /**
     * 加载Banner数据
     */
    private void loadBanner() {
        if (WangCaiApp.getUserCentre().getUserInfo() == null) {
            return;
        }
        BannerListCommand command = new BannerListCommand();
        command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Notice>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BANNER_LIST_ID, this);
        restRequest.execute();
    }

    /**
     * 加载彩种
     */
    private void lotteryListLoad() {
        if (WangCaiApp.getUserCentre().getUserInfo() == null) {
            return;
        }
        LotteryListCommand lotteryListCommand = new LotteryListCommand();
        lotteryListCommand.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Lottery>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), lotteryListCommand, typeToken, restCallback, LOTTERY_TRACE_ID, this);
        restRequest.execute();
    }

    /**
     * 加载分类
     */
    private void getSeries() {
        if (WangCaiApp.getUserCentre().getUserInfo() == null) {
            return;
        }
        SeriesCommand command = new SeriesCommand();
        command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<List<Series>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, SERIES_ID, this);
        restRequest.execute();
    }

    /**
     * 活动数据加载
     */
    private void activityLoad() {
        if (WangCaiApp.getUserCentre().getUserInfo() == null) {
            return;
        }
        ActivityCommand command = new ActivityCommand();
        command.setUserdId(WangCaiApp.getUserCentre().getUserInfo().getId());
        command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ActivityData>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, ACTIVITY_ID, this);
        restRequest.execute();
    }

    /**
     * 点击活动加载数据
     */
    private void activityClick() {
        if (WangCaiApp.getUserCentre().getUserInfo() == null) {
            return;
        }
        ActivityCommand command = new ActivityCommand();
        command.setUserdId(WangCaiApp.getUserCentre().getUserInfo().getId());
        TypeToken typeToken = new TypeToken<RestResponse<ActivityData>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, ACTIVITY_CLICK_ID, this);
        restRequest.execute();
    }

    @OnClick(R.id.uc_msg_iv)
    public void onMsgClick() {
        launchFragment(NoticeListFragment.class);
    }

    @OnClick(R.id.uc_kefu_iv)
    public void onKefuClick() {
        launchFragment(ServiceCenterFragment.class);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case BANNER_LIST_ID:
                    notices = (ArrayList<Notice>) response.getData();
                    updateBanner();
                    break;
                case LOTTERY_TRACE_ID:
                    item = (ArrayList<Lottery>) response.getData();
                    recordType.setLotteryList(item);
                    activityLoad();
                    loadBanner();
                    selectPage(0);
                    break;
                case ACTIVITY_ID:
                    activityData = (ActivityData) response.getData();
                    initActivity();
                    break;
                case ACTIVITY_CLICK_ID:
                    activityData = (ActivityData) response.getData();
                    activitylaunch();
                    break;
                case SERIES_ID: //获取彩种信息
                    List<Series> series = (List<Series>) response.getData();
                    if (series.size() > 0) {
                        WangCaiApp.getUserCentre().setSeriesList(series);
                        mTagContainerLayout.setTags(ConstantInformation.getSort());
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.DONE) {
                if (activityData == null && request.getId() == ACTIVITY_ID) {
                    imageView.setVisibility(View.GONE);
                    imageView.setEnabled(false);
                }
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cycleViewPager != null) {
            cycleViewPager.onDestroyView();
        }
    }
}
