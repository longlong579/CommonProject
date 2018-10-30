package self.xhl.com.commonproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xhl.statusbarcompatutil.StatusBarCompat
import self.xhl.com.common.baseui.baseActivity.PermissionBaseActivity

class MainActivity : PermissionBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarCompat.translucentStatusBar(this,true)
        StatusBarCompat.setStatusBarDarkFont(this,true)
        //StatusBarCompat.setStatusBarColor(this,resources.getColor(R.color.accent_material_dark))

    }

    override fun getContentLayoutId(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return R.layout.activity_main
    }

    override fun initToolBar() {
        super.initToolBar()
        build().setHasToolBar(true)
                .showCenterTitle(true)

    }
    override fun initWidget() {
        super.initWidget()
    }
}
