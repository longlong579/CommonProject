package self.xhl.com.commonproject

import com.xhl.statusbarcompatutil.StatusBarCompat
import org.jetbrains.anko.toast
import self.xhl.com.common.baseui.baseActivity.PermissionBaseActivity
import self.xhl.com.common.utils.PermissionUtil

class MainActivity : PermissionBaseActivity() {


    override fun getContentLayoutId(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return R.layout.activity_main
    }

    override fun initToolBarPre() {
        super.initToolBarPre()
        StatusBarCompat.translucentStatusBar(this,true)
        StatusBarCompat.setStatusBarDarkFont(this,true)
        build().setHasToolBar(true)
                .setShowCenterTitle(true)
                .setToolBarTitle("我是测试")
                .setToolBarTitleColorRes(R.color.red_100)
                .setEnableBack(true)
    }

    override fun initWidget() {
        super.initWidget()
        checkForcePermissions(object :OnPermissionResultListener{

            override fun onAllow() {
                toast("授权成功")
            }

            override fun onReject() {
                toast("授权失败")
            }
        },PermissionUtil.PERMISSION_CAMERA,PermissionUtil.PERMISSION_READ_CONTACTS,PermissionUtil.PERMISSION_READ_EXTERNAL_STORAGE)
    }

}
