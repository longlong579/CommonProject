package self.xhl.com.commonproject.fragmentlazyload

import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_lazyload_main.*
import self.xhl.com.common.baseui.baseactivity.ToolbarActivity
import self.xhl.com.common.ktextension.singclick
import self.xhl.com.commonproject.R

class LazyloadMainActivity : ToolbarActivity() {


    override fun getContentLayoutId(): Int {
       return R.layout.activity_lazyload_main
    }

    override fun initToolBarPre() {
        ToolBarBuild().setToolBarTitle("懒加载测试")
    }

    override fun initWidget() {
        super.initWidget()
        txt1.singclick {
            ActivityUtils.startActivity(AddModuleLazyLoadActivity::class.java)
        }
        txt2.singclick {
            ActivityUtils.startActivity(ViewPageModuleLazyLoadActivity::class.java)
        }
    }
    override fun initData() {

    }

}
