package self.xhl.com.common.listener;

import android.view.View;

import java.util.Calendar;

/**
 * @author bingo
 * @version 1.0.0
 */

/*
    防止按钮重复点击  用此类替换OnClickListener 另一种防止重复点击 用ButtonUtils类（点击方法中处理）
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

     abstract void onNoDoubleClick(View v);

}