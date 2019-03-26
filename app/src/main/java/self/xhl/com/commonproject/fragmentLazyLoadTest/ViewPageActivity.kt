package self.xhl.com.commonproject.fragmentLazyLoadTest

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
        viewPage.offscreenPageLimit=2//设置Fragment只初始化一次
        viewPage.adapter=adapter
    }
}
