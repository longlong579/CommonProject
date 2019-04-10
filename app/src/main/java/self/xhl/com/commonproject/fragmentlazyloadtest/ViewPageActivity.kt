package self.xhl.com.commonproject.fragmentlazyloadtest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_view_page.*

import java.util.ArrayList

import self.xhl.com.common.viewpage.ViewPageAdapter
import self.xhl.com.commonproject.R

class ViewPageActivity : AppCompatActivity() {
    internal var listFraget = ArrayList<Fragment>()
    internal var titles = ArrayList<String>()
    internal var adapter: ViewPageAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page)
        initView()

    }

    fun initView()
    {
        listFraget.add(LazyLoadFragment.newInstance("A1", "A1"))
        listFraget.add(LazyLoadFragment.newInstance("B1", "B1"))
        listFraget.add(LazyLoadFragment.newInstance("C1", "C1"))
        titles.add("1")
        titles.add("2")
        adapter = ViewPageAdapter(supportFragmentManager, listFraget, titles)
        viewPage.offscreenPageLimit=1//设置Fragment只初始化一次
        viewPage.adapter=adapter
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
