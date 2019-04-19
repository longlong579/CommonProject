package self.xhl.com.common.toolbardeleget


import android.support.annotation.*
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import self.xhl.com.common.R

/**
 * @author xhl
 * @desc
 * 2019/4/18 11:22
 */
class ToolBarDelege(var activity: FragmentActivity)
{
    val mActivity=activity

    private var mToolbar: Toolbar? = null
    private var centerTitle: TextView?=null
    private var hasBar: Boolean = true
    private var showCenterTitle = true
    private var toolbarNmae = "ToolBar"
    private var enableBack = true
    @ColorInt
    private var  toolbarBackColor=-1
    private var toolBarBackDrawableRes = R.drawable.abc_ic_ab_back_material
    @MenuRes
    private var munuId = 0
    @ColorRes
    private var titleColorRes = -1


    fun initToolbar(root: View) {
        var toolbar: View?=null
        if (hasBar) {
            toolbar = root.findViewById(R.id.commonToolbar)
            when (toolbar) {
                null -> throw RuntimeException("该布局不包含toolBar,请添加toolBar或复写hasToolBar")
                else -> toolbar as Toolbar
            }
            this.mToolbar = toolbar
            centerTitle = toolbar.findViewById<View>(R.id.toolbar_center_title) as TextView
            when {
                showCenterTitle -> {
                    when (centerTitle) {
                        null -> throw RuntimeException("该布局不包含centerTitle,请添加或设置showCenterTitle为false")
                        else -> centerTitle as TextView
                    }
                    toolbar.title = ""
                    centerTitle?.text = toolbarNmae
                    centerTitle?.visibility = View.VISIBLE
                    //setToolBarTitleColor(titleColorRes)
                    if (titleColorRes != -1)
                        centerTitle?.setTextColor(ContextCompat.getColor(mActivity, titleColorRes))
                }
                else -> {
                    centerTitle?.visibility = View.GONE
                    toolbar.title = toolbarNmae
                    if (titleColorRes != -1)
                        toolbar.setTitleTextColor(ContextCompat.getColor(mActivity, titleColorRes))
                }
            }
            if (enableBack) {
                initToolbarNav(toolbar)
            }
            if (toolbarBackColor != -1) {
                mToolbar?.setBackgroundColor(toolbarBackColor)
            }
            initToolbarMenue(toolbar)
        } else {
            if (toolbar != null) {
                toolbar.visibility = View.GONE
            }
        }
    }

    private fun initToolbarMenue(toolbar: Toolbar) {
        val menuId = munuId
        if (menuId != 0) {
            toolbar.inflateMenu(menuId)
            toolbar.setOnMenuItemClickListener { item ->
                //            Log.d("xxx", item.itemId.toString())
                onMenuClick(item.itemId)
                true
            }
        }
    }


    fun getToorBar(): Toolbar? {
        return mToolbar
    }


    //默认可以返回
    fun setEnableBack(isEnableBack: Boolean) {
        enableBack = isEnableBack
    }

    //默认有toolBar
    fun setHasToolBar(flaHasBar: Boolean) {
        hasBar = flaHasBar
    }

    //默认标题居中
    fun setShowCenterTitle(showCentTitle: Boolean) {
        showCenterTitle = showCentTitle
    }

    //默认toolBar名称 可动态改变
    fun setToolBarTitle(toolbarName: String) {
        mToolbar?.let {
            it.title=toolbarName
        }
        centerTitle?.let {
            it.text=toolbarName
        }
        toolbarNmae = toolbarName
    }

    //默认Title颜色 可动态改变
    fun setToolBarTitleColorRes(@ColorRes toolBarTitleColor: Int) {
        titleColorRes = toolBarTitleColor
        centerTitle?.setTextColor(ContextCompat.getColor(mActivity, titleColorRes))
        mToolbar?.setTitleTextColor(ContextCompat.getColor(mActivity, titleColorRes))
    }


    //默认用系统颜色 状态栏背景色 可动态改变
    fun setToolbarBackGround(@ColorInt  color: Int) {
        if (color != -1) {
//                mToolbar?.setBackgroundResource(color)
            toolbarBackColor=color
            mToolbar?.setBackgroundColor(color)
        }
    }


    //默认的返回图标 可动态改变
    fun setToolbarBackDrawable(@DrawableRes toolbarBackDrawableRes: Int) {
        toolBarBackDrawableRes = toolbarBackDrawableRes
        mToolbar?.setNavigationIcon(toolBarBackDrawableRes)
    }


    // munuId>0有menue
    fun setMenuId(@LayoutRes menuId: Int) //返回menuId 默认无menue
    {
        munuId = menuId
    }

    //toolBar返回按钮  可以复写满足自己的需求
     fun initToolbarNav(toolbar: Toolbar) {
        toolbar.setNavigationIcon(toolBarBackDrawableRes)
        toolbar.setNavigationOnClickListener {
            mActivity.onBackPressed()
        }
    }

    // 复写 menue事件 满足自己的需求
     fun onMenuClick(menuId: Int) {
    }
}