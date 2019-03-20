package com.xhl.gdlocation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* @作者 xhl
*
* @创建时间 2019/3/18 10:14
*/

public class GdLocationMode {
   public static final int Battery_Saving=0;
   public static final int Device_Sensors=1;
   public static final int Height_Accuracy=2;


    @IntDef({Battery_Saving, Device_Sensors, Height_Accuracy})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LocationMode {

    }
}
