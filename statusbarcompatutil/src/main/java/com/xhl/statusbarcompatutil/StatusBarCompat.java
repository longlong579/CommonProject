package com.xhl.statusbarcompatutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.xhl.statusbarcompatutil.StatusBarCompatKitKat.removeFakeStatusBarViewIfExist;

/**
*  AlertBy xhl
* 1：状态栏改变颜色   2：状态栏透明（沉浸 若不想沉浸 Fragment布局添加StatusView） 3:状态栏字体颜色黑白切换
 * 添加自定义布局针对的是Activity->PhoneWindow 在Fragment中应用，一般先设置activity为透明，若不想沉浸 Fragment布局添加StatusView
*  2018/9/27 12:20
*/
public class StatusBarCompat {

    private static final int DEFAULT_STATUS_BAR_ALPHA = 112;//默认半透明
    private static final int TAG_KEY_HAVE_SET_OFFSET_MARGIN = -123;
    private static final int TAG_KEY_HAVE_SET_OFFSET_PADDING = -124;
    static final String TAG_TRANSLATE_STATUS_BAR_VIEW = "tag_translate_status_bar_view";//半透明View Tag
    static final String TAG_FAKE_STATUS_BAR_VIEW = "tag_fake_status_bar_view";
    static final String TAG_MARGIN_ADDED = "marginAdded";


    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    public static void hideFakeStatusBarView(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setVisibility(View.GONE);
        }
        View fakeTranslucentView = decorView.findViewWithTag(TAG_TRANSLATE_STATUS_BAR_VIEW);
        if (fakeTranslucentView != null) {
            fakeTranslucentView.setVisibility(View.GONE);
        }
    }

    //设置控件偏移状态栏高度Margin
    public static void  setOffsetMarginView(Activity activity,View needOffsetView)
    {
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET_MARGIN);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
                    layoutParams.rightMargin, layoutParams.bottomMargin);
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET_MARGIN, true);
            needOffsetView.requestLayout();
        }
    }

    ////设置控件取消偏移状态栏高度Margin
    public static void clearOffsetMarginView(Activity activity,View needOffsetView)
    {
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET_MARGIN);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin-getStatusBarHeight(activity),
                        layoutParams.rightMargin, layoutParams.bottomMargin);
                needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET_MARGIN, false);
                needOffsetView.requestLayout();
            }

        }
    }

    //设置控件偏移状态栏高度Padding
    public static void  setOffsetPaddingView(Activity activity,View needOffsetView)
    {
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET_PADDING);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                return;
            }
            needOffsetView.setPadding(needOffsetView.getPaddingLeft(), needOffsetView.getPaddingTop()+getStatusBarHeight(activity) ,
                    needOffsetView.getPaddingRight(), needOffsetView.getPaddingBottom());
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET_PADDING, true);
            needOffsetView.requestLayout();
        }
    }

    ////设置控件取消偏移状态栏高度Padding
    public static void clearOffsetPaddingView(Activity activity,View needOffsetView)
    {
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET_PADDING);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                needOffsetView.setPadding(needOffsetView.getPaddingLeft(), needOffsetView.getPaddingTop()-getStatusBarHeight(activity) ,
                        needOffsetView.getPaddingRight(), needOffsetView.getPaddingBottom());
                needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET_PADDING, false);
                needOffsetView.requestLayout();
            }
          
        }
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusBarColor(color, alpha));
        statusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
        return statusBarView;
    }

    /**-----------------------------------------------状态栏颜色-----------------------------------------**/

    // 设置Acticity状态栏颜色 默认半透明
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor) {
        setStatusBarColor(activity, statusColor, DEFAULT_STATUS_BAR_ALPHA);
    }
    /**
     * set Acticity statusBarColor
     * @param statusColor color
     * @param alpha       0 - 255
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, @IntRange(from = 0, to = 255)int alpha) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            removeTranslucentViewIfExist(activity);
            StatusBarCompatLollipop.setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            removeTranslucentViewIfExist(activity);
            StatusBarCompatKitKat.setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
        }
    }

    /**
     * 为滑动返回界面Acticity设置状态栏颜色  原理 设置状态栏透明 内容侵入 然后设置Activity主布局的padding为状态栏高度
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColorForSwipeBack(Activity activity, int color) {
        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setColorForSwipeBack(Activity activity, @ColorInt int color,
                                            @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
            View rootView = contentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight(activity);
            if (rootView != null && rootView instanceof CoordinatorLayout) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(calculateStatusBarColor(color, statusBarAlpha));
                    boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0);
                        coordinatorLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                coordinatorLayout.requestLayout();
                            }
                        });
                    }
                } else {
                    coordinatorLayout.setStatusBarBackgroundColor(calculateStatusBarColor(color, statusBarAlpha));
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0);
                contentView.setBackgroundColor(calculateStatusBarColor(color, statusBarAlpha));
            }
            translucentStatusBar(activity);
        }
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色 内容布局不能是CoordinatorLayout 布局参考DrawerFragment2 所有内容放在一个ViewGroup中
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
                                               @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 生成一个状态栏大小的矩形 设置颜色
        // 添加 statusBarView 到布局中
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
                contentLayout.addView(createStatusBarView(activity, color,0));
        }
        // 内容布局不是 LinearLayout(所有内容放在contentLayout.getChildAt(1)里面) 时,设置padding top
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }
        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */
    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }




/**----------------------------------------透明------------------------------------------*/

    public static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * change to full screen mode  全屏模式
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            removeFakeStatusBarViewIfExist(activity);
            removeTranslucentViewIfExist(activity);//伪透明状态栏在5.0以上也添加 切换时注意移除
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }

    /**
     *  为头部是 ImageView 的设置状态栏透明
     * 透明状态栏 添加伪透明状态栏 添加了偏移View后，运行切换状态有点难度 一般用在界面状态栏不变的情况下
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void setTranslucentForImageView(@NonNull Activity activity, boolean hideStatusBarBackground,@IntRange(from = 0, to = 255) int statusBarAlpha,View needOffsetView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
            //removeTranslucentViewIfExist(activity);
            removeFakeStatusBarViewIfExist(activity);//若有伪颜色状态栏 移除
            addTranslucentView(activity, statusBarAlpha);
            setOffsetMarginView(activity,needOffsetView);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
            //removeTranslucentViewIfExist(activity);
            removeFakeStatusBarViewIfExist(activity);//若有伪颜色状态栏 移除
            addTranslucentView(activity, statusBarAlpha);
            setOffsetMarginView(activity,needOffsetView);
        }
    }


    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     * @param activity       fragment 对应的 activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageViewInFragment(Activity activity, boolean hideStatusBarBackground,@IntRange(from = 0, to = 255) int statusBarAlpha,
                                                            View needOffsetView) {
        setTranslucentForImageView(activity,hideStatusBarBackground, statusBarAlpha, needOffsetView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            clearPreviousSetting(activity);
        }
    }


    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }

    /**
     * 添加半透明矩形条 因为一开始就设置了透明 ContentView会充满屏幕 所以 自定义布局要添加到ContentView中 设置偏移
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private static void addTranslucentView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewWithTag(TAG_TRANSLATE_STATUS_BAR_VIEW);
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.getVisibility() == View.GONE) {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
        }
    }

    /**
     * 移除伪透明状态栏 如果要在透明和颜色状态切换 则必须移除 为了一个Activity可以切换状态栏状态
     * @param activity
     */
     static void removeTranslucentViewIfExist(Activity activity) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewWithTag(TAG_TRANSLATE_STATUS_BAR_VIEW);
        if (fakeTranslucentView != null) {
            contentView.removeView(fakeTranslucentView);
        }
    }
    /**
     * 创建半透明矩形 View
     *
     * @param alpha 透明值
     * @return 半透明 View
     */
    private static View createTranslucentStatusBarView(Activity activity, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setTag(TAG_TRANSLATE_STATUS_BAR_VIEW);
        return statusBarView;
    }

    /**
     * 为了Fragment中使用 防止Activity设置了颜色状态栏
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void clearPreviousSetting(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setPadding(0, 0, 0, 0);
        }
    }
    /** ----------------字体颜色-------------**/

    /**
     * Set the status bar to dark.
     * 设置状态栏黑色字体 或白色字体 4.4系统以上才有用
     */
    public static boolean setStatusBarDarkFont(Activity activity, boolean darkFont) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (setMIUIStatusBarFont(activity, darkFont)) {
                setDefaultStatusBarFont(activity, darkFont);
                return true;
            } else if (setMeizuStatusBarFont(activity, darkFont)) {
                setDefaultStatusBarFont(activity, darkFont);
                return true;
            } else {
                return setDefaultStatusBarFont(activity, darkFont);
            }
        }
        else return false;
    }

    //魅族系统改变字体颜色
    private static boolean setMeizuStatusBarFont(Activity activity, boolean darkFont) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkFont) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    //小米系统改变字体颜色 优化
    private  static boolean setMIUIStatusBarFont(Activity activity, boolean dark) {
        Window window = activity.getWindow();
        Class<?> clazz = window.getClass();
        try {
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    //6.0以上改变状态栏字体
    private static boolean setDefaultStatusBarFont(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (dark) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            return true;
        }
        return false;
    }

    /**
     * return statusBar's Height in pixels
     */
    public static int getStatusBarHeight(@NonNull Activity activity) {
        int result = 0;
        int resId = activity.getBaseContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = activity.getBaseContext().getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    //Get alpha color
    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 设置根布局参数 根布局预留状态栏高度
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

}
