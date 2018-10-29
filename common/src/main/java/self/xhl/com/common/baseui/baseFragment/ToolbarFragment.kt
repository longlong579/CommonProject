package self.xhl.com.common.baseui.baseFragment

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import self.xhl.com.common.R

/**
 * @author xhl
 * @desc   fragment的toolBar 不去替换Acticity的ActionBar 所以设置menu不能用系统的OnCreateMenue（）方法
 * 默认不开启ToolBar  不带按返回键处理 交由外界处理
 * 2018/7/24 16:50
 */
public abstract class ToolbarFragment : BaseFragment() {
    protected var toolbar: Toolbar? = null//供外界定制 若要更改toolBar hasToolBar=false 实现自己的initToolBar 或用封装的好的几个方法（常用场景）

    open override fun initWidget(root: View) {
        initToolbar(root)
    }

    private fun initToolbar(root: View) {
        if (hasToolBar()) {
            val toolbar = root.findViewById<View>(R.id.toolbar)
            when (toolbar) {
                null -> throw RuntimeException("该布局不包含toolBar,请添加toolBar或复写hasToolBar")
                else -> toolbar as Toolbar
            }
            this.toolbar = toolbar
            setToolbarBackGround()//设置toolBar的颜色
            val centerTitle = toolbar.findViewById<View>(R.id.toolbar_center_title) as TextView
            when {
                showCenterTitle() -> {
                    toolbar.title = ""
                    centerTitle.text = getTitle()
                    centerTitle.visibility = View.VISIBLE
                    setToolbarTitleColor()
                }
                else -> {
                    centerTitle.visibility = View.GONE
                    toolbar.title = getTitle()
                }
            }
            if (isEnableBack()) {
                initToolbarNav(toolbar)
            }
            initToolbarMenue(toolbar)
        }
    }


    //默认有toolBar
    open fun hasToolBar(): Boolean {
        return true
    }

    //默认标题居中
    open fun showCenterTitle(): Boolean {
        return true
    }

    //默认toolBar名称
    open fun getTitle(): String {
        return "TooblBarTitle"
    }
    //默认用黑色
    private fun setToolbarTitleColor() {
        if(getTitleColor()!=-1) {
            val centerTitle = toolbar?.findViewById<View>(R.id.toolbar_center_title) as TextView
           centerTitle.setTextColor(resources.getColor(getTitleColor()))
        }
    }

    //设置标题颜色
    @ColorRes
    open fun getTitleColor():Int{
      return -1
    }

    //默认可以返回
    open fun isEnableBack(): Boolean {
        return true
    }

    //默认的返回图标
    @DrawableRes
    open fun getToolbarBackDrawable(): Int {
        return R.mipmap.ic_arrow_back_white
    }


    //默认用系统颜色
    protected fun setToolbarBackGround(@DrawableRes resId: Int = 0) {
        if (resId != 0)
            toolbar?.setBackgroundResource(resId)
    }

    //toolBar返回按钮  可以复写满足自己的需求 默认交给MainActivity处理
    open fun initToolbarNav(toolbar: Toolbar) {
        toolbar.setNavigationIcon(getToolbarBackDrawable())
        toolbar.setNavigationOnClickListener {
            _mActivity.onBackPressed()
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
}