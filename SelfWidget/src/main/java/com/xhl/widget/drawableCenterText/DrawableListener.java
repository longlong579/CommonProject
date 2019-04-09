package com.xhl.widget.drawableCenterText;

/**
 * @author xhl
 * @desc 2019/1/19 13:31
 */

import android.view.View;

/**
 * Drawable 监听回调接口
 *
 * Created by yinw on 2016-12-19.
 */

public class DrawableListener {


    public interface DrawableRightListener{

        void drawableRightListener(View view);

    }

    public interface DrawableLeftListener{

        void drawableLeftListener(View view);

    }


    public interface DrawableTopListener{

        void drawableTopListener(View view);

    }


    public interface DrawableBottomListener{

        void drawableBottomListener(View view);

    }

}
