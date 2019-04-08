package self.xhl.com.commonproject;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.xhl.gdlocation.GDLocationClient;
import com.xhl.gdlocation.GdLocationResult;
import com.xhl.gdlocation.IGdLocationListener;
import com.xhl.statusbarcompatutil.StatusBarCompat;

import self.xhl.com.common.baseuiFragmention.baseActivity.PermissionBaseActivity;

/**
 *  * 高德定位 注意：在主项目AndroidMainifest中添加： 服务 和 key:（去申请）
 *  * <service android:name="com.amap.api.location.APSService" />
 *  * <meta-data
 *  * android:name="com.amap.api.v2.apikey"
 *  * android:value="4e66291e968acaa610d413af80d5a36f"/>
 */
public class GdLocationActivity extends PermissionBaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this,true);
        StatusBarCompat.setStatusBarDarkFont(this,true);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        checkForcePermissions(new OnPermissionResultListener() {
                                  @Override
                                  public void onAllow() {
                                      GDLocationClient.newBuilder(GdLocationActivity.this).build()
                                              .locate(new IGdLocationListener() {
                                                  @Override
                                                  public void gdLocationReceive(GdLocationResult gdLocationInfo) {
                                                      ToastUtils.showLong("定位成功"+gdLocationInfo.getPoiName());
                                                  }

                                                  @Override
                                                  public void onFail(int errCode, String errInfo) {

                                                  }
                                              });
                                  }

                                  @Override
                                  public void onReject() {

                                  }
                              }
                , Manifest.permission.ACCESS_FINE_LOCATION);
    }

}
