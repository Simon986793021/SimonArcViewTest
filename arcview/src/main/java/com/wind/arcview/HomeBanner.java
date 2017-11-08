package com.wind.arcview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangcong on 2017/11/7.
 */

public class HomeBanner extends FrameLayout  {
    private static final int DELAY_SHORT = 3000;
    private static final int DELAY_LONG = 5000;
    private Context context;
    private boolean isAutoPlay;
    private int currentItem;
    private int count;
    private Handler handler = new Handler();
    private ViewPager viewPager;
    private LinearLayout ll_dot;
    private List<View> views;
    private List<ImageView> iv_dots;


    public HomeBanner(Context context) {
        this(context, null);
    }

    public HomeBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();

    }



    private void initView() {
        views = new ArrayList<>();
        iv_dots = new ArrayList<>();
    }





    public void setImagesRes(int[] imagesRes) {
        initLayout();
        initImgFromRes(imagesRes);

    }


    private void initImgFromRes(int[] imagesRes) {
        count = imagesRes.length;
        for (int i = 0; i < count; i++) {
            ImageView iv_dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            iv_dot.setImageResource(R.drawable.dot_blur);
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);
        }
        iv_dots.get(0).setImageResource(R.drawable.dot_focus);

        for (int i = 0; i <= count + 1; i++) {
            View banner_view = LayoutInflater.from(context).inflate(R.layout.banner_content_layout, null);
            ArcImageView imageView_banner_title = (ArcImageView) banner_view.findViewById(R.id.iv_img);
            if (i == 0) {
                imageView_banner_title.setImageResource(imagesRes[count - 1]);
            } else if (i == count + 1) {
                imageView_banner_title.setImageResource(imagesRes[0]);
            } else {
                imageView_banner_title.setImageResource(imagesRes[i - 1]);
            }
            views.add(banner_view);
        }
        setAtt();
    }



    private void initLayout() {
        views.clear();
        View view = LayoutInflater.from(context).inflate(
                R.layout.banner_layout, this, true);
        viewPager = (ViewPager) view.findViewById(R.id.vp_banner);
        ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        ll_dot.removeAllViews();
    }



    private void setAtt() {
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        currentItem = 1;
        viewPager.addOnPageChangeListener(new MyOnPagerChangeListener());
        startAutoPlay();
    }


    private void startAutoPlay() {
        isAutoPlay = true;
        handler.postDelayed(runnable, DELAY_SHORT);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                //currentItem = currentItem % (topBannerEntities.size() + 1) + 1;
                currentItem = currentItem % (count + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(runnable);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(runnable, DELAY_LONG);
                }
            } else {
                handler.postDelayed(runnable, DELAY_LONG);
            }
        }
    };

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < iv_dots.size(); i++) {
                if (i == position - 1) {
                    iv_dots.get(i).setImageResource(R.drawable.dot_focus);
                } else {
                    iv_dots.get(i).setImageResource(R.drawable.dot_blur);
                }

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case 0:
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(count, false);
                    } else if (viewPager.getCurrentItem() == count + 1) {
                        viewPager.setCurrentItem(1, false);
                    }
                    currentItem = viewPager.getCurrentItem();
                    isAutoPlay = true;
                    break;
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                default:
                    break;
            }
        }
    }

}