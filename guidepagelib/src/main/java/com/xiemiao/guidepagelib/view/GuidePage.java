package com.xiemiao.guidepagelib.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiemiao.guidepagelib.R;
import com.xiemiao.guidepagelib.utils.DensityUtils;
import com.xiemiao.guidepagelib.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: xiemiao
 * Date: 2017-08-06
 * Time: 11:10
 * Desc: app启动引导页
 */
public class GuidePage extends FrameLayout implements ViewPager.OnPageChangeListener {
    public String tag = "GuidePage";

    private Context context;
    private List imageUrls = new ArrayList<>();//图片地址集合
    private List<View> imageViews = new ArrayList<>();//通过图片地址生成的view集合

    private ViewPager viewPager;
    private LinearLayout llIndicateContainer;
    private Button btnEntry;

    private ImageLoaderInterface imageLoader;//图片加载接口实现
    private GradientDrawable selectedDrawable;//选中圆点drawable
    private GradientDrawable unSelectedDrawable;//未选中圆点drawable
    private GuidePageAdapter adapter;

    public GuidePage(Context context) {
        this(context, null);
    }

    public GuidePage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidePage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //填充布局作为它的根部局
        View.inflate(context, R.layout.guide_page, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        llIndicateContainer = (LinearLayout) findViewById(R.id.llIndicateContainer);
        btnEntry = (Button) findViewById(R.id.btnEntry);

    }

    /**
     * 设置图片加载监听，接口实现自己的图片加载方法
     *
     * @param imageLoader
     * @return
     */
    public GuidePage setImageLoader(ImageLoaderInterface imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    /**
     * 设置本地资源图片集合(无需设置imageLoader)
     *
     * @param imageUrls
     */
    public GuidePage setLocalImageResList(List<Integer> imageUrls) {
        if (imageUrls == null || imageUrls.size() <= 0) {
            Log.e(tag, "请设置图片数据");
        } else {
            //遍历集合生成imageview集合
            for (int i = 0; i < imageUrls.size(); i++) {
                View imageView = null;
                if (imageView == null) {
                    imageView = new ImageView(context);
                }
                Integer url = imageUrls.get(i);
                imageView.setBackgroundResource(url);
                imageViews.add(imageView);
            }

            //设置适配器显示viewpager
            if (adapter == null) {
                adapter = new GuidePageAdapter();
            }
            viewPager.setAdapter(adapter);
            viewPager.setFocusable(true);
            viewPager.addOnPageChangeListener(this);
        }
        return this;
    }

    /**
     * 设置图片集合（可传递任意类型，在ImageLoader里实现转换，从而显示图片）
     *
     * @param imageUrls
     */
    public GuidePage setImageList(List<?> imageUrls) {
        if (imageUrls == null || imageUrls.size() <= 0) {
            Log.e(tag, "请设置图片数据");
        } else {
            this.imageUrls = imageUrls;
            //遍历集合生成imageview集合
            for (int i = 0; i < imageUrls.size(); i++) {
                View imageView = null;
                if (imageView == null) {
                    imageView = new ImageView(context);
                }
                Object url = imageUrls.get(i);
                imageViews.add(imageView);
                if (imageLoader != null)
                    imageLoader.displayImage(context, url, imageView);
                else
                    Log.e(tag, "请设置图片加载器ImageLoader");
            }

            //设置适配器显示viewpager
            if (adapter == null) {
                adapter = new GuidePageAdapter();
            }
            viewPager.setAdapter(adapter);
            viewPager.setFocusable(true);

            viewPager.addOnPageChangeListener(this);
        }
        return this;
    }

    /**
     * 设置圆点指示器
     *
     * @param selectedColorId
     * @param unselectedColorId
     * @return
     */
    public GuidePage setOvalIndicator(int selectedColorId, int unselectedColorId, float
            radius) {
        //初始化圆点shape
        selectedDrawable = DrawableUtils.getOvalShapeDrawable(selectedColorId, radius);
        unSelectedDrawable = DrawableUtils.getOvalShapeDrawable(unselectedColorId, radius);

        //清空所有指示点
        llIndicateContainer.removeAllViews();
        //根据图片数量，添加小圆点
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                params.leftMargin = 0;
                imageView.setImageDrawable(selectedDrawable);
            } else {
                params.leftMargin = DensityUtils.dp2px(context, 5);
                imageView.setImageDrawable(unSelectedDrawable);
            }
            imageView.setLayoutParams(params);
            llIndicateContainer.addView(imageView);
        }

        return this;
    }

    private boolean isShowEntry;

    /**
     * 设置进入按钮点击监听
     *
     * @param listener
     * @return
     */
    public GuidePage setOnEntryClickListener(String text, int textColor, int textSize, int
            backgroundResId, OnClickListener listener) {
        this.isShowEntry = true;
        btnEntry.setText(text);
        btnEntry.setTextColor(textColor);
        btnEntry.setTextSize(textSize);
        btnEntry.setBackgroundResource(backgroundResId);
        btnEntry.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置进入按钮的padding
     *
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public void setBtnEntryPadding(int l, int t, int r, int b) {
        btnEntry.setPadding(l, t, r, b);
    }

    /**
     * 引导图片显示适配器
     */
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (isShowEntry) {
            //最后一页显示进入按钮
            if (position == imageViews.size() - 1) {
                btnEntry.setVisibility(VISIBLE);
            } else {
                btnEntry.setVisibility(GONE);
            }
        }
        //遍历指示点容器，设置小圆点选中效果
        for (int i = 0; i < llIndicateContainer.getChildCount(); i++) {
            ImageView childView = (ImageView) llIndicateContainer.getChildAt(i);
            if (i == position) {
                childView.setImageDrawable(selectedDrawable);
            } else {
                childView.setImageDrawable(unSelectedDrawable);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
