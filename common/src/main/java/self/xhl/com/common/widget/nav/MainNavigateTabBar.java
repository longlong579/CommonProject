package self.xhl.com.common.widget.nav;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import self.xhl.com.common.R;

public class MainNavigateTabBar extends LinearLayout implements View.OnClickListener {

    private static final String KEY_CURRENT_TAG = "com.startsmake.template.currentTag";

    private List<ViewHolder> mViewHolderList;
    private OnTabSelectedListener mTabSelectListener;
    private FragmentActivity mFragmentActivity;
    private String mCurrentTag;
    private String mRestoreTag;
    /*主内容显示区域View的id*/
    private int mMainContentLayoutId;
    /*选中的Tab文字颜色*/
    private ColorStateList mSelectedTextColor;
    /*正常的Tab文字颜色*/
    private ColorStateList mNormalTextColor;
    /*Tab文字的颜色*/
    private float mTabTextSize;
    /*默认选中的tab index*/
    private int mDefaultSelectedTab = 0;

    private int mCurrentSelectedTab;

    public MainNavigateTabBar(Context context) {
        this(context, null);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainNavigateTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainNavigateTabBar, 0, 0);

        ColorStateList tabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabTextColor);
        ColorStateList selectedTabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabSelectedTextColor);

        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.MainNavigateTabBar_navigateTabTextSize, 0);
        mMainContentLayoutId = typedArray.getResourceId(R.styleable.MainNavigateTabBar_containerId, 0);

        mNormalTextColor = (tabTextColor != null ? tabTextColor : context.getResources().getColorStateList(R.color.tab_text_normal));


        if (selectedTabTextColor != null) {
            mSelectedTextColor = selectedTabTextColor;
        } else {
            ThemeUtils.checkAppCompatTheme(context);
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            mSelectedTextColor = context.getResources().getColorStateList(typedValue.resourceId);
        }

        mViewHolderList = new ArrayList<>();
    }


    public void addTab(Class frameLayoutClass, TabParam tabParam) {
        int defaultLayout = R.layout.comui_tab_view;
//        if (tabParam.tabViewResId > 0) {
//            defaultLayout = tabParam.tabViewResId;
//        }
        if (TextUtils.isEmpty(tabParam.title)) {
            tabParam.title = getContext().getString(tabParam.titleStringRes);
        }

        View view = LayoutInflater.from(getContext()).inflate(defaultLayout, null);
        view.setFocusable(true);

        ViewHolder holder = new ViewHolder();

        holder.tabIndex = mViewHolderList.size();

        holder.fragmentClass = frameLayoutClass;
        holder.tag = tabParam.title;
        holder.pageParam = tabParam;

        holder.view = view;
        holder.tabIcon = (ImageView) view.findViewById(R.id.tab_icon);
        holder.tabTitle = ((TextView) view.findViewById(R.id.tab_title));

        holder.badgeView = new QBadgeView(view.getContext());

        if (TextUtils.isEmpty(tabParam.title)) {
            holder.tabTitle.setVisibility(View.INVISIBLE);
        } else {
            holder.tabTitle.setText(tabParam.title);
        }

        if (mTabTextSize != 0) {
            holder.tabTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        }
        if (mNormalTextColor != null) {
            holder.tabTitle.setTextColor(mNormalTextColor);
        }

        if (tabParam.backgroundColor > 0) {
            view.setBackgroundResource(tabParam.backgroundColor);
        }

        if (tabParam.iconResId > 0) {
            holder.tabIcon.setImageResource(tabParam.iconResId);
        } else {
            holder.tabIcon.setVisibility(View.INVISIBLE);
        }

        if (tabParam.iconResId > 0 && tabParam.iconSelectedResId > 0) {
            view.setTag(holder);
            view.setOnClickListener(this);
            mViewHolderList.add(holder);
        }

        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mMainContentLayoutId == 0) {
            throw new RuntimeException("mFrameLayoutId Cannot be 0");
        }
        if (mViewHolderList.size() == 0) {
            throw new RuntimeException("mViewHolderList.size Cannot be 0, Please call addTab()");
        }
        if (!(getContext() instanceof FragmentActivity)) {
            throw new RuntimeException("parent activity must is extends FragmentActivity");
        }
        mFragmentActivity = (FragmentActivity) getContext();

        ViewHolder defaultHolder = null;

        hideAllFragment();
        if (!TextUtils.isEmpty(mRestoreTag)) {
            for (ViewHolder holder : mViewHolderList) {
                if (TextUtils.equals(mRestoreTag, holder.tag)) {
                    defaultHolder = holder;
                    mRestoreTag = null;
                    break;
                }
            }
        } else {
            defaultHolder = mViewHolderList.get(mDefaultSelectedTab);
        }

        showFragment(defaultHolder);
    }

    @Override
    public void onClick(View v) {
        Object object = v.getTag();
        if (object != null && object instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) v.getTag();
            showFragment(holder);
            if (mTabSelectListener != null) {
                mTabSelectListener.onTabSelected(holder);
            }
        }
    }

    /**
     * 显示 holder 对应的 fragment
     *
     * @param holder
     */
    private void showFragment(ViewHolder holder) {
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();
        if (isFragmentShown(transaction, holder.tag)) {
            return;
        }

        setCurrSelectedTabByTag(holder.tag);

        hideBadgeViewNumber(holder.badgeView);

        Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(holder.tag);
        if (fragment == null) {
            fragment = getFragmentInstance(holder.tag);
            transaction.add(mMainContentLayoutId, fragment, holder.tag);
        } else {
            transaction.show(fragment);
        }
        transaction.commitNowAllowingStateLoss();
        mCurrentSelectedTab = holder.tabIndex;
    }

    /*消除角标数字*/
    private void hideBadgeViewNumber(QBadgeView badgeView) {
        if (badgeView.getTargetView() == null || badgeView.getBadgeNumber() == 0) {
            return;
        } else {
            badgeView.setBadgeNumber(0);
        }
    }

    private boolean isFragmentShown(FragmentTransaction transaction, String newTag) {
        if (TextUtils.equals(newTag, mCurrentTag)) {
            return true;
        }

        if (TextUtils.isEmpty(mCurrentTag)) {
            return false;
        }

        Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(mCurrentTag);
        if (fragment != null && !fragment.isHidden()) {
            transaction.hide(fragment);
        }

        return false;
    }

    /*设置当前选中tab的图片和文字颜色*/
    private void setCurrSelectedTabByTag(String tag) {
        if (TextUtils.equals(mCurrentTag, tag)) {
            return;
        }
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(mCurrentTag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconResId);
                holder.tabTitle.setTextColor(mNormalTextColor);
            } else if (TextUtils.equals(tag, holder.tag)) {
                holder.tabIcon.setImageResource(holder.pageParam.iconSelectedResId);
                holder.tabTitle.setTextColor(mSelectedTextColor);
            }
        }
        mCurrentTag = tag;
    }


    private Fragment getFragmentInstance(String tag) {
        Fragment fragment = null;
        for (ViewHolder holder : mViewHolderList) {
            if (TextUtils.equals(tag, holder.tag)) {
                try {
                    fragment = (Fragment) Class.forName(holder.fragmentClass.getName()).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return fragment;
    }

    private void hideAllFragment() {
        if (mViewHolderList == null || mViewHolderList.size() == 0) {
            return;
        }
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();

        for (ViewHolder holder : mViewHolderList) {
            Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(holder.tag);
            if (fragment != null && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
        transaction.commitNowAllowingStateLoss();
    }

    public void setSelectedTabTextColor(ColorStateList selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public void setSelectedTabTextColor(int color) {
        mSelectedTextColor = ColorStateList.valueOf(color);
    }

    public void setTabTextColor(ColorStateList color) {
        mNormalTextColor = color;
    }

    public void setTabTextColor(int color) {
        mNormalTextColor = ColorStateList.valueOf(color);
    }

    public void setFrameLayoutId(int frameLayoutId) {
        mMainContentLayoutId = frameLayoutId;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRestoreTag = savedInstanceState.getString(KEY_CURRENT_TAG);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CURRENT_TAG, mCurrentTag);
    }

    public void setTabSelectListener(OnTabSelectedListener tabSelectListener) {
        mTabSelectListener = tabSelectListener;
    }

    public void setDefaultSelectedTab(int index) {
        if (index >= 0 && index < mViewHolderList.size()) {
            mDefaultSelectedTab = index;
        }
    }

    public int getCurrentSelectedTab() {
        return mCurrentSelectedTab;
    }

    public void setCurrentSelectedTab(int index) {
        if (index >= 0 && index < mViewHolderList.size()) {
            ViewHolder holder = mViewHolderList.get(index);
            showFragment(holder);
        }
    }

    /**
     * 隐藏消息数量提示
     *
     * @param index 底部导航的位置
     * @param count 消息数量 count >0 显示,count = 0 隐藏；count < 0 显示 dot
     */

    public void disPlayBadgeCount(int index, int count) {
        if (index >= 0 && index < mViewHolderList.size()) {
            ViewHolder holder = mViewHolderList.get(index);

            if (holder.badgeView.getTargetView() == null) {
                holder.badgeView.bindTarget(holder.view);
                holder.badgeView.setBadgeBackgroundColor(holder.view.getContext().getResources().getColor(R.color.red_100));
                holder.badgeView.setBadgePadding(5, true);
                holder.badgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
                holder.badgeView.setGravityOffset(10, 6, true);
            }
            holder.badgeView.setBadgeNumber(count);
        }
    }

    /**
     * 隐藏消息数量提示
     *
     * @param index 底部导航的位置
     * @param count 消息数量 count = 0 消失
     */

    public void disMissBadgeCount(int index, int count) {
        if (index >= 0 && index < mViewHolderList.size()) {
            ViewHolder holder = mViewHolderList.get(index);
            holder.badgeView.setBadgeNumber(count);
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(ViewHolder holder);
    }

    public static class ViewHolder {
        public View view;
        public QBadgeView badgeView;
        public String tag;
        public TabParam pageParam;
        public ImageView tabIcon;
        public TextView tabTitle;
        public Class fragmentClass;
        public int tabIndex;
    }

    public static class TabParam {
        public int backgroundColor = android.R.color.white;
        public int iconResId;
        public int iconSelectedResId;
        public int titleStringRes;
        //        public int tabViewResId;
        public String title;

        public TabParam(int iconResId, int iconSelectedResId, String title) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }

        public TabParam(int iconResId, int iconSelectedResId, int titleStringRes) {
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }

        public TabParam(int backgroundColor, int iconResId, int iconSelectedResId, int titleStringRes) {
            this.backgroundColor = backgroundColor;
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.titleStringRes = titleStringRes;
        }

        public TabParam(int backgroundColor, int iconResId, int iconSelectedResId, String title) {
            this.backgroundColor = backgroundColor;
            this.iconResId = iconResId;
            this.iconSelectedResId = iconSelectedResId;
            this.title = title;
        }
    }

}
