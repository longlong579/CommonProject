package com.xhl.base.Utils;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.xhl.base.Utils.interfaces.OnCountDownFinishListener;
import com.xhl.base.Utils.interfaces.OnCountDownListener;


/**
 * @author xhl
 * 倒计时操作类
 * @desc 2019/4/4 17:03
 */
public class CustomDownTimeUtil {
    /**
     * 倒计时
     *
     * @param textView 控件
     * @param waitTime 倒计时总时长
     * @param interval 倒计时的间隔时间
     * @param hint     倒计时完毕时显示的文字
     * 记得OnDestroy时 timer.cancel()
     */
    public static android.os.CountDownTimer countDown(final TextView textView, long waitTime, long interval, final String hint) {
        return countDown(textView,waitTime,interval,hint,null,null);
    }

    public static android.os.CountDownTimer countDown(final TextView textView, long waitTime, long interval, final String hint, final OnCountDownListener onCountDownListener, final OnCountDownFinishListener onCountDownFinishListener) {
        textView.setEnabled(false);
        android.os.CountDownTimer timer = new android.os.CountDownTimer(waitTime, interval) {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(String.format("剩下 %d S", millisUntilFinished / 1000));
                if(onCountDownListener!=null)
                {
                    onCountDownListener.onCountDown(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setText(hint);
                if(onCountDownFinishListener!=null)
                {
                    onCountDownFinishListener.onCountDownFinish();
                }
            }
        };
        timer.start();
        return timer;
    }

}
