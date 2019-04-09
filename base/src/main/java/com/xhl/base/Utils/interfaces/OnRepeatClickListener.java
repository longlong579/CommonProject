package com.xhl.base.Utils.interfaces;

import android.view.View;

import com.blankj.utilcode.util.Utils;

import static com.xhl.base.Utils.Utils.isFastClick;


/**
 *
 * @author Vondear
 * @date 2017/7/24
 * 重复点击的监听器
 */

public abstract class OnRepeatClickListener implements View.OnClickListener {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private final int MIN_CLICK_DELAY_TIME = 1000;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        if (!isFastClick(MIN_CLICK_DELAY_TIME)) {
            onSingleClick(v);
        }
    }
}
