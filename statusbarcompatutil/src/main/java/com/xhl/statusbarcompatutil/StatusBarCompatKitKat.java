package com.xhl.statusbarcompatutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import static com.xhl.statusbarcompatutil.StatusBarCompat.TAG_FAKE_STATUS_BAR_VIEW;
import static com.xhl.statusbarcompatutil.StatusBarCompat.TAG_MARGIN_ADDED;
import static com.xhl.statusbarcompatutil.StatusBarCompat.TAG_TRANSLATE_STATUS_BAR_VIEW;
import static com.xhl.statusbarcompatutil.StatusBarCompat.getStatusBarHeight;
import static com.xhl.statusbarcompatutil.StatusBarCompat.removeTranslucentViewIfExist;


/**
 * After kitkat add fake status bar
 * Created by qiu on 8/27/16.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class StatusBarCompatKitKat {

    /**
     * set StatusBarColor
     *
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. removeFakeStatusBarViewIfExist
     * 3. addFakeStatusBarView
     * 4. addMarginTopToContentChild
     * 5. cancel ContentChild's fitsSystemWindow
     */
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏 全屏

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);//Activity内容区域FrameLayout
        View mContentChild = mContentView.getChildAt(0);//Activity主布局xml
        int statusBarHeight = getStatusBarHeight(activity);

        removeFakeStatusBarViewIfExist(activity);//取消添加的自定义区域 防止2次添加
//        removeTranslucentViewIfExist(activity);
        addFakeStatusBarView(activity, statusColor, statusBarHeight);//添加填充的自定义View
        addMarginTopToContentChild(mContentChild, statusBarHeight);//设置Activity xml最外层偏移状态栏高度

        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);//防止最外层添加了fitSystemWindow
        }
    }

    /**
     * 1. Add fake statusBarView.
     * 2. set tag to statusBarView.
     */
    private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View mStatusBarView = new View(activity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);//decorView继承FrameLayout
        layoutParams.gravity = Gravity.TOP;
        mStatusBarView.setLayoutParams(layoutParams);
        mStatusBarView.setBackgroundColor(statusBarColor);
        mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);

        mDecorView.addView(mStatusBarView);
        return mStatusBarView;
    }

    /**
     * use reserved order to remove is more quickly.
     */
     static void removeFakeStatusBarViewIfExist(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }


    /**
     * add marginTop to simulate set FitsSystemWindow true
     */
    private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (!TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);//Activity主布局xml最外层 偏移
            mContentChild.setTag(TAG_MARGIN_ADDED);
        }
    }

    /**
     * remove marginTop to simulate set FitsSystemWindow false
     */
    private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(null);
        }
    }


    /**
     * translucentStatusBar
     *
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. removeFakeStatusBarViewIfExist
     * 3. removeMarginTopOfContentChild
     * 4. cancel ContentChild's fitsSystemWindow
     * 内容会沉浸到状态栏
     */
    static void translucentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        removeFakeStatusBarViewIfExist(activity);
        removeTranslucentViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }
    }

    /**
     * compat for CollapsingToolbarLayout
     *
     * 1. set Window Flag : WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
     * 2. set FitsSystemWindows for views.
     * 3. add Toolbar's height, let it layout from top, then add paddingTop to layout normal.
     * 4. removeFakeStatusBarViewIfExist
     * 5. removeMarginTopOfContentChild
     * 6. add OnOffsetChangedListener to change statusBarView's alpha
     */
    static void setStatusBarColorForCollapsingToolbar(Activity activity, final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
                                                      Toolbar toolbar, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);

        View mContentChild = mContentView.getChildAt(0);
        mContentChild.setFitsSystemWindows(false);
        ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
        appBarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);

        toolbar.setFitsSystemWindows(false);
        if (toolbar.getTag() == null) {
            CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            int statusBarHeight = getStatusBarHeight(activity);
            lp.height += statusBarHeight;
            toolbar.setLayoutParams(lp);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(true);
        }

        int statusBarHeight = getStatusBarHeight(activity);
        removeFakeStatusBarViewIfExist(activity);
        removeTranslucentViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, statusBarHeight);
        final View statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight);

        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        if (behavior != null && behavior instanceof AppBarLayout.Behavior) {
            int verticalOffset = ((AppBarLayout.Behavior) behavior).getTopAndBottomOffset();
            if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                statusView.setAlpha(1f);
            } else {
                statusView.setAlpha(0f);
            }
        } else {
            statusView.setAlpha(0f);
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    if (statusView.getAlpha() == 0) {
                        statusView.animate().cancel();
                        statusView.animate().alpha(1f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
                    }
                } else {
                    if (statusView.getAlpha() == 1) {
                        statusView.animate().cancel();
                        statusView.animate().alpha(0f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
                    }
                }
            }
        });
    }
}
