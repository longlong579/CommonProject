package self.xhl.com.commonproject;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ToastUtils;

import self.xhl.com.common.baseui.baseActivity.PermissionBaseActivity;


public class Main4Activity extends PermissionBaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main4);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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
                                      AMapLocationClient mlocationClient =new  AMapLocationClient(Main4Activity.this);
                                      AMapLocationClientOption option = new AMapLocationClientOption();


                                      option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
                                              .setOnceLocation(false)
                                              .setInterval(2000);

                                      mlocationClient.setLocationOption(option);
                                      mlocationClient.setLocationListener(new AMapLocationListener() {
                                          @Override
                                          public void onLocationChanged(AMapLocation aMapLocation) {
                                              if(aMapLocation.getErrorCode()==12)
                                              {
                                                  ToastUtils.showLong("定位失败 权限问题");
                                              }
                                              else if(aMapLocation.getErrorCode()==0)
                                              {
                                                  ToastUtils.showLong(aMapLocation.getCity());
                                              }
                                          }
                                      });
                                      mlocationClient.startLocation();
                                  }

                                  @Override
                                  public void onReject() {

                                  }
                              }
                , Manifest.permission.ACCESS_FINE_LOCATION);
    }

}
