package com.maple.model.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.maple.model.R;
import com.maple.model.ui.fragment.FragmentPage1;
import com.maple.model.ui.fragment.FragmentPage2;
import com.maple.model.ui.fragment.FragmentPage3;
import com.maple.model.ui.fragment.FragmentPage4;
import com.maple.model.ui.fragment.FragmentPage5;


public class MainActivity extends FragmentActivity {
    @ViewInject(R.id.bt_openLeftMenu)
    private Button bt_openLeftMenu;// 左侧按钮
    @ViewInject(R.id.tv_title)
    private TextView mTitle;// 标题
    @ViewInject(R.id.bt_openRightMenu)
    private Button bt_openRightMenu;// 右侧按钮
    @ViewInject(R.id.tabhost)
    private FragmentTabHost mTabHost;

    // 定义数组来存放Fragment界面
    private Class[] fragmentArray = {FragmentPage1.class, FragmentPage2.class,
            FragmentPage3.class, FragmentPage4.class, FragmentPage5.class};
    //Tab选项卡的图片
    private int[] mImageViewArray = {R.drawable.tab_home_btn, R.drawable.tab_message_btn,
            R.drawable.tab_selfinfo_btn, R.drawable.tab_square_btn, R.drawable.tab_more_btn};
    //Tab选项卡的文字
    @ResInject(id = R.array.tab_fun_array, type = ResType.StringArray)
    private String[] mTextViewArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);// 注入view 和事件

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化TabHost对象，得到TabHost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 填充fragment
        for (int i = 0; i < fragmentArray.length; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i])// 设置Tag  此时tag和tab的名字相同
                    .setIndicator(getTabItemView(i)); // 设置Tab的说明文字和图表
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
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
    }


    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextViewArray[index]);

        return view;
    }
}