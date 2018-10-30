package self.xhl.com.common.baseui.baseActivity

import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import self.xhl.com.common.R

/**
 * @author xhl
 * @desc
 * 2018/7/27 17:21
 */
public abstract class ToolbarActivity : BaseActivity() {
    private var mToolbar: Toolbar? = null
    private var hasBar: Boolean = true
    private var showCenterTitle=true

     override fun initWidget() {
        super.initWidget()
        initToolbar(window.decorView.findViewById(android.R.id.content))
    }

    private fun initToolbar(root: View) {
        if (hasBar) {
            val toolbar = root.findViewById<View>(R.id.toolbar)
            when (toolbar) {
                null -> throw RuntimeException("该布局不包含toolBar,请添加toolBar或复写hasToolBar")
                else -> toolbar as Toolbar
            }
            this.mToolbar = toolbar
            setToolbarBackGround()//设置toolBar的颜色
            val centerTitle = toolbar.findViewById<View>(R.id.toolbar_center_title) as TextView
            when {
                showCenterTitle -> {
                    toolbar.title = ""
                    centerTitle.text = getToolBarTitle()
                    centerTitle.visibility = View.VISIBLE
                }
                else -> {
                    centerTitle.visibility = View.GONE
                    toolbar.title = getToolBarTitle()
                }
            }
            if (isEnableBack()) {
                initToolbarNav(toolbar)
            }
            initToolbarMenue(toolbar)
        }
    }


     fun getToorBar(): Toolbar? {
        return mToolbar
    }

    //默认有toolBar
     fun setHasToolBar(flaHasBar: Boolean) :ToolbarActivity{
        hasBar=flaHasBar
        return this
    }

    //默认标题居中
    fun showCenterTitle(showCenterTitle:Boolean): ToolbarActivity {
        this.showCenterTitle=showCenterTitle
        return this
    }

    //默认toolBar名称
    open fun getToolBarTitle(): String {
        return "TooblBarTitle"
    }

    //默认可以返回
    open fun isEnableBack(): Boolean {
        return true
    }

    //默认的返回图标
    @DrawableRes
    open fun getToolbarBackDrawable(): Int {
        return R.drawable.abc_ic_ab_back_material
    }

    //默认用系统颜色
    protected fun setToolbarBackGround(@DrawableRes resId: Int = 0) {
        if (resId != 0)
            mToolbar?.setBackgroundResource(resId)
    }

    //toolBar返回按钮  可以复写满足自己的需求
    open fun initToolbarNav(toolbar: Toolbar) {
        toolbar.setNavigationIcon(getToolbarBackDrawable())
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    //复写 >0有menue
    open fun getMenuId(): Int //返回menuId 默认无menue
    {
        return 0
    }

    // 复写 menue事件 满足自己的需求
    open fun onMenuClick(menuId: Int) {
    }

    private fun initToolbarMenue(toolbar: Toolbar) {
        val menuId = getMenuId()
        if (menuId != 0) {
            toolbar.inflateMenu(menuId)
            toolbar.setOnMenuItemClickListener { item ->
                //            Log.d("xxx", item.itemId.toString())
                onMenuClick(item.itemId)
                true
            }
        }
    }

    fun build():ToolbarActivity
    {
        return this
    }
}