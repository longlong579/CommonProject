package self.xhl.com.commonproject

import com.xhl.statusbarcompatutil.StatusBarCompat
import self.xhl.com.common.baseui.baseActivity.PermissionBaseActivity
import self.xhl.com.common.utils.PermissionUtil


//为了其他项目也能用 故不依赖Common
class MainActivity : PermissionBaseActivity() {


    override fun getContentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initToolBarPre() {
        super.initToolBarPre()
        StatusBarCompat.translucentStatusBar(this,true)
        StatusBarCompat.setStatusBarDarkFont(this,true)
        build().setHasToolBar(false)
                .setShowCenterTitle(true)
                .setToolBarTitle("我是测试")
                .setToolBarTitleColorRes(R.color.red_100)
                .setEnableBack(true)
    }

    override fun initWidget() {
        super.initWidget()
        checkForcePermissions(object :OnPermissionResultListener{

            override fun onAllow() {
                //toast("授权成功")
            }

            override fun onReject() {
                //toast("授权失败")
            }
        },PermissionUtil.PERMISSION_READ_CONTACTS,PermissionUtil.PERMISSION_ACCESS_FINE_LOCATION)

         if(findFragment(LoginFragment::class.java)==null)
         {
             loadRootFragment(R.id.fl_container,LoginFragment.newInstance())
         }
        closeAndroidPDialog()
    }

    /**
     * 解决AndroidP 打包勾选Debug时 弹出“Detectedproblems with API compatibility”问题
     * 原因：Debug调用了隐藏的API
     */
    private fun closeAndroidPDialog() {
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
