package self.xhl.com.commonproject.LocationTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.xhl.gdlocation.GDLocationClient;
import com.xhl.gdlocation.GdLocationResult;
import com.xhl.gdlocation.IGdLocationListener;

import self.xhl.com.commonproject.R;

public class GdLocationActivity extends AppCompatActivity {
    GDLocationClient gdLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gdLocationClient = GDLocationClient.newBuilder(GdLocationActivity.this).onceLocation(false).build();

        gdLocationClient.locate(new IGdLocationListener() {
            @Override
            public void gdLocationReceive(GdLocationResult gdLocationInfo) {
                ToastUtils.showLong("我是第一个定位");
            }

            @Override
            public void onFail(int errCode, String errInfo) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(gdLocationClient!=null)
        {
            gdLocationClient.onDestroy();
            gdLocationClient=null;
        }
    }
}
