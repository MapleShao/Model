package com.maple.model.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.maple.model.R;
import com.maple.model.ui.fragment.FragmentPage1;
import com.maple.model.ui.fragment.FragmentPage2;
import com.maple.model.ui.fragment.FragmentPage3;
import com.maple.model.ui.fragment.FragmentPage4;
import com.maple.model.ui.fragment.FragmentPage5;
import com.nineoldandroids.view.ViewHelper;


public class MainActivity extends FragmentActivity {
    @ViewInject(R.id.id_drawerLayout)
    private DrawerLayout mDrawerLayout;// 控件对象 总界面
    @ViewInject(R.id.bt_openLeftMenu)
    private Button bt_openLeftMenu;// 左侧按钮
    @ViewInject(R.id.tv_title)
    private TextView mTitle;// 标题
    @ViewInject(R.id.bt_openRightMenu)
    private Button bt_openRightMenu;// 右侧按钮

    // 定义FragmentTabHost对象
    @ViewInject(R.id.tabhost)
    private FragmentTabHost mTabHost;
    // 定义一个布局
    private LayoutInflater layoutInflater;
    // 定义数组来存放Fragment界面
    private Class fragmentArray[] = {FragmentPage1.class, FragmentPage2.class,
            FragmentPage3.class, FragmentPage4.class, FragmentPage5.class};
    /**
     * Tab选项卡的图片
     */
    private int mImageViewArray[] = {R.drawable.tab_home_btn,
            R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
            R.drawable.tab_square_btn, R.drawable.tab_more_btn};
    /**
     * Tab选项卡的文字
     */
    private String mTextViewArray[] = {"首页", "消息", "好友", "广场", "更多"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);// 注入view 和事件

        initView();
        initEvents();
        addClickListener();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i])// 设置Tag  此时tag和tab的名字相同
                    .setIndicator(getTabItemView(i)); // 设置Tab的说明文字和图表
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
        // Tab选项卡选择监听
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tag) {
                mTitle.setText(tag);
            }
        });
        mTabHost.setCurrentTab(0);// 默认指向第一个
        mTitle.setText(mTextViewArray[0]);// 设置默认标题
        //------------------抽屉菜单-------------------
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);// 关闭右侧菜单的滑动出现效果
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextViewArray[index]);

        return view;
    }

    /**
     * 初始化DrawerLayout的抽屉事件监听
     */
    private void initEvents() {
        // 设置抽屉监听（左右菜单栏打开与关闭）
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 抽屉状态改变
             */
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            /**
             * 抽屉滑动
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals(getResources().getString(R.string.left_tag))) {// 展开左侧菜单
                    float leftScale = 1 - 0.3f * scale;
                    // 设置左侧菜单缩放效果
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    // 设置中间View缩放效果
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else {// 展开右侧菜单
                    // 设置中间View缩放效果
                    ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            /**
             * 抽屉打开
             */
            @Override
            public void onDrawerOpened(View drawerView) {
            }

            /**
             * 抽屉关闭
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                // 抽屉上锁——关闭右侧菜单的滑动出现效果
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }

    /**
     * 添加点击监听
     */
    private void addClickListener() {
        // 左侧按钮点击事件
        bt_openLeftMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDrawerLayout.openDrawer(Gravity.LEFT);// 展开侧边的菜单
            }
        });
        // 右侧按钮点击事件
        bt_openRightMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);// 展开侧边的菜单
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);// 打开手势滑动
            }
        });
    }

}