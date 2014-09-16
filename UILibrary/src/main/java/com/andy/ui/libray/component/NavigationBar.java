package com.andy.ui.libray.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.andy.ui.libray.R;
import com.andy.ui.libray.common.Dimension;

import java.util.ArrayList;

/**
 * 导航栏
 * <p/>
 * +--------------------------+
 * |  back    title     action|
 * +--------------------------+
 */
public class NavigationBar extends LinearLayout implements View.OnClickListener {
    /**	间隔时间击时间	*/
    private static long intervalTime =800;
    /**	最后点击时间	*/
    private static long lastClickTime;
    private static ArrayList<View> views = new ArrayList<View>();


    private int background;         //整体背景
    private int bottomBg;           //底部背景条
    private int backBg;             //返回按钮背景
    private String backText;        //返回按钮文字
    private String actionText;      //操作按钮文字
    private int actionBg;           //操作按钮背景
    private int textColor;          //文字颜色，包括title
    private float btnTextSize;      //按钮文字大小
    private float titleTextSize;    //title文字大小
    private int titleMaxLength;    //title最大长度
    private TextView backButton;     //返回按钮
    private TextView actionButton;        //操作按钮
    private TextView titleText;         //title文本
    private ProgressBar mProgress;      //右侧进度提示bar
    private OnNavBarClickListener onNavBarClickListener;    //导航栏点击事件监听器
    private RelativeLayout mRootLayout; //根布局

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initAttrs(context, attrs);
        initView();
    }

    /**
     * 设置导航栏事件监听器
     *
     * @param onNavBarClickListener 点击监听器
     */
    public void setOnNavBarClickListener(OnNavBarClickListener onNavBarClickListener) {
        this.onNavBarClickListener = onNavBarClickListener;
    }

    /**
     * 获取标题对象titleText
     *
     * @return
     */
    public TextView getNavbarTitleTextView() {
        return titleText;
    }

    /**
     * 配置事件监听器
     *
     * @param view 被点击的view
     */
    @Override
    public void onClick(View view) {
        if (isFastDoubleClick(view))
            return;

        if (onNavBarClickListener == null) return;

        if (view.equals(backButton)) {
            onNavBarClickListener.onNavItemClick(NavigationBarItem.back);
        } else if (view.equals(titleText)) {
            onNavBarClickListener.onNavItemClick(NavigationBarItem.title);
        } else if (view.equals(actionButton)) {
            onNavBarClickListener.onNavItemClick(NavigationBarItem.action);
        }
    }

    /**
     * 是否重复点击
     *
     * @return
     * @view 被点击view，如果前后是同一个view，则进行双击校验
     */
    private boolean isFastDoubleClick(View view) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (views.size() == 0) {
            views.add(view);
        }
        if (0 < timeD && timeD < intervalTime && views.get(0).getId() == view.getId()) {
            return true;
        }
        lastClickTime = time;
        views.clear();
        views.add(view);
        return false;
    }

    /**
     * 设置返回按钮文字
     *
     * @param backText 按钮文字
     */
    public void setBackText(String backText) {
        backButton.setText(backText);
    }

    /**
     * 设置返回按钮文字
     *
     * @param resId 按钮文字id
     */
    public void setBackText(int resId) {
        String text = getContext().getString(resId);
        setBackText(text);
    }

    /**
     * 给textview添加图标
     *
     * @param left   左侧图标id
     * @param top    上侧图标id
     * @param right  右侧图标id
     * @param bottom 底侧图标id
     */
    public void setBackCompoundDrawables(int left, int top, int right, int bottom) {
        titleText.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        titleText.setCompoundDrawablePadding(Dimension.dip2px(5, getContext()));
    }

    /**
     * 设置标题
     *
     * @param title 标题文字
     */
    public void setTitle(String title) {
        titleText.setText(title);
    }

    /**
     * 获取标题名称
     *
     * @return 标题字符串
     */
    public String getTitle() {
        return titleText.getText().toString();
    }

    /**
     * 得到titleView
     * @return
     */
    public View getTitleView(){
        return this.titleText;
    }

    /**
     * 设置标题
     *
     * @param resId 标题文字id
     */
    public void setTitle(int resId) {
        String text = getContext().getString(resId);
        setTitle(text);
    }

    /**
     * 设置标题文字大小
     * @param sizePx
     */
    public void setTitleTextSize(int sizePx){
        int sizeSp = Dimension.px2sp(sizePx, getContext());
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP,sizeSp);
    }

    /**
     * 给textview添加图标
     *
     * @param left   左侧图标id
     * @param top    上侧图标id
     * @param right  右侧图标id
     * @param bottom 底侧图标id
     */
    public void setTitleCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        titleText.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        titleText.setCompoundDrawablePadding(Dimension.dip2px(5,getContext()));
    }

    /**
     * 设置操作按钮文字
     *
     * @param actionText
     */
    public void setActionBtnText(String actionText) {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setText(actionText);
    }

    /**
     * 获取操作按钮的文字
     *
     * @return
     */
    public String getActionBtnText() {
        return actionButton.getText().toString();
    }

    /**
     * 设置操作按钮文字
     *
     * @param resId 操作按钮文字id
     */
    public void setActionBtnText(int resId) {
        setActionBtnText(getContext().getString(resId));
    }

    /**
     * 设置操作按钮字体颜色
     *
     * @param color
     */
    public void setActionBtnTextColor(int color) {
        actionButton.setTextColor(color);
    }

    /**
     * 设置操作按钮是否可用
     *
     * @param isEnable
     */
    public void setActionBtnEnabled(boolean isEnable) {
        actionButton.setEnabled(isEnable);
    }

    /**
     * 设置返回按钮是否可见
     *
     * @param visibility
     */
    public void setBackBtnVisibility(int visibility) {
        backButton.setVisibility(visibility);
    }

    /**
     * 设置操作按钮是否可见
     *
     * @param visibility
     */
    public void setActionBtnVisibility(int visibility) {
        actionButton.setVisibility(visibility);
    }

    /**
     * 设置操作按钮背景
     *
     * @param resourceId 资源id
     */
    public void setActionBtnBackground(int resourceId) {
        actionButton.setBackgroundResource(resourceId);
        actionButton.setVisibility(VISIBLE);
    }

    /**
     * 设置右边button图片
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setActionCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom){
        actionButton.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        actionButton.setCompoundDrawablePadding(Dimension.dip2px(5,getContext()));
    }

    /**
     * 设置操作按钮背景
     *
     * @param drawable
     */
    public void setActionBtnBackground(Drawable drawable){
        actionButton.setBackgroundDrawable(drawable);
        actionButton.setVisibility(VISIBLE);
    }

    /**
     * 设置返回按钮背景
     *
     * @param resourceId 资源id
     */
    public void setBackButtonBackground(int resourceId) {
        backButton.setBackgroundResource(resourceId);
    }

    /**
     * 设置标题背景
     *
     * @param resourceId 资源id
     */
    public void setTitleBackground(int resourceId) {
        titleText.setBackgroundResource(resourceId);
    }

   /**
     * 设置标题背景
     *
     * @param resourceId 资源id
     */
    public void setNavBackground(int resourceId) {
        mRootLayout.setBackgroundResource(resourceId);
    }

    /**
     * 显示右侧进度提示bar
     */
    public void showRightProgress() {
        actionButton.setVisibility(GONE);
        mProgress.setVisibility(VISIBLE);
    }

    /**
     * 隐藏右侧进度提示bar
     */
    public void hideRightProgress() {
        mProgress.setVisibility(GONE);
    }

    /**
     * 删掉所有元素
     */
    public void clear(){
        removeSubMenu();
        mProgress.setVisibility(GONE);
        titleText.setText(null);
        titleText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        titleText.setBackgroundResource(0);
        backButton.setText(null);
        backButton.setBackgroundResource(0);
        actionButton.setText(null);
        actionButton.setBackgroundResource(0);
    }

    /**
     * 初始化属性值
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //获取xml中配置的属性资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        try {
            background      = typedArray.getResourceId(R.styleable.NavigationBar_navBg, 0);
            bottomBg        = typedArray.getResourceId(R.styleable.NavigationBar_bottomBg, 0);
            backBg          = typedArray.getResourceId(R.styleable.NavigationBar_backBg, 0);
            actionText      = typedArray.getString(R.styleable.NavigationBar_actionText);
            backText        = typedArray.getString(R.styleable.NavigationBar_backText);
            actionBg        = typedArray.getResourceId(R.styleable.NavigationBar_actionBg, 0);

            textColor       = typedArray.getColor(R.styleable.NavigationBar_textColor, 0xFFFFFFFF);
            btnTextSize     = typedArray.getDimension(R.styleable.NavigationBar_btnTextSize, 0.0f);
            titleTextSize   = typedArray.getDimension(R.styleable.NavigationBar_titleTextSize, 0.0f);
            titleMaxLength  = typedArray.getInt(R.styleable.NavigationBar_titleMaxLength, 9);

        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 初始化导航条中的相关元素
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.l_navigation_bar, this, true);

        backButton      = (TextView) findViewById(R.id.nav_back);
        actionButton    = (TextView) findViewById(R.id.nav_right_btn);
        titleText       = (TextView) findViewById(R.id.nav_center_text);
        mProgress       = (ProgressBar) findViewById(R.id.nav_right_progress);

        //背景
        mRootLayout = (RelativeLayout) backButton.getParent();
        mRootLayout.setBackgroundResource(background);

        //返回按钮
        backButton.setBackgroundResource(backBg);
        backButton.setText(backText);
        backButton.setTextColor(textColor);

        //操作按钮
        actionButton.setBackgroundResource(actionBg);
        actionButton.setText(actionText);

        //标题文字
        titleText.setTextColor(textColor);
        InputFilter.LengthFilter lengthFilter = new InputFilter.LengthFilter(titleMaxLength);
        InputFilter[] filters = {lengthFilter};
        titleText.setFilters(filters);

        //字体大小
        if (Float.compare(btnTextSize, 0.0f) > 0) {
            backButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            actionButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        if (Float.compare(titleTextSize, 0.0f) > 0)
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);

        //设置事件监听器
        backButton.setOnClickListener(this);
        titleText.setOnClickListener(this);
        actionButton.setOnClickListener(this);
    }

    /**
     * 去掉SubMenu
     */
    public void removeSubMenu() {
        backButton.setTag(null);
        titleText.setTag(null);
        actionButton.setTag(null);
    }

    public enum NavigationBarItem {
        back,   //返回按钮
        title,  //title文字
        action  //操作按钮，即最右边的按钮
    }

    /**
     * 导航点击事件监听接口
     */
    public interface OnNavBarClickListener {
        public void onNavItemClick(NavigationBarItem navBarItem);
    }

}
