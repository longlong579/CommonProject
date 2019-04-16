package self.xhl.com.common.baseui.baseactivity

import android.content.Context
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import self.xhl.com.common.R

/**
 * @author xhl
 * @desc 约定ToolBar的id为：commonToolbar
 * 中间标题id为：toolbar_center_title
 * 解决ToolBar传递问题 简化Menue事件
 * 2018/7/27 17:21
 */
public abstract class ToolbarActivity : BaseActivity() {
    private var mToolbar: Toolbar? = null
    private var centerTitle:TextView?=null
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

    /**
     * 初始化控件调用之前
     */
    abstract fun initToolBarPre()


    override fun initWidget() {
        initToolBarPre()
        initToolbar(window.decorView.findViewById(android.R.id.content))
    }


    private fun initToolbar(root: View) {
        var toolbar:View?=null
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
                        centerTitle?.setTextColor(ContextCompat.getColor(this, titleColorRes))
                }
                else -> {
                    centerTitle?.visibility = View.GONE
                    toolbar.title = toolbarNmae
                    if (titleColorRes != -1)
                        toolbar.setTitleTextColor(ContextCompat.getColor(this, titleColorRes))
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

    inner class ToolBarBuild(var context:Context)
    {
        val mContext=context


        //默认可以返回
        fun setEnableBack(isEnableBack: Boolean): ToolBarBuild {
            enableBack = isEnableBack
            return this
        }

        //默认有toolBar
        fun setHasToolBar(flaHasBar: Boolean): ToolBarBuild {
            hasBar = flaHasBar
            return this
        }

        //默认标题居中
        fun setShowCenterTitle(showCentTitle: Boolean): ToolBarBuild {
            showCenterTitle = showCentTitle
            return this
        }

        //默认toolBar名称 可动态改变
        fun setToolBarTitle(toolbarName: String): ToolBarBuild {
            mToolbar?.let {
                it.title=toolbarName
            }
            centerTitle?.let {
                it.text=toolbarName
            }
            toolbarNmae = toolbarName
            return this
        }

        //默认Title颜色 可动态改变
        fun setToolBarTitleColorRes(@ColorRes toolBarTitleColor: Int): ToolBarBuild {
            titleColorRes = toolBarTitleColor
            centerTitle?.setTextColor(ContextCompat.getColor(mContext, titleColorRes))
            mToolbar?.setTitleTextColor(ContextCompat.getColor(mContext, titleColorRes))
            return this
        }


        //默认用系统颜色 状态栏背景色 可动态改变
        fun setToolbarBackGround(@ColorInt  color: Int): ToolBarBuild {
            if (color != -1) {
//                mToolbar?.setBackgroundResource(color)
                toolbarBackColor=color
                mToolbar?.setBackgroundColor(color)
            }
            return this
        }


        //默认的返回图标 可动态改变
        fun setToolbarBackDrawable(@DrawableRes toolbarBackDrawableRes: Int): ToolBarBuild {
            toolBarBackDrawableRes = toolbarBackDrawableRes
            mToolbar?.setNavigationIcon(toolBarBackDrawableRes)
            return this
        }


        // munuId>0有menue
        fun setMenuId(@LayoutRes menuId: Int): ToolBarBuild //返回menuId 默认无menue
        {
            munuId = menuId
            return this
        }
    }

    //toolBar返回按钮  可以复写满足自己的需求
    open fun initToolbarNav(toolbar: Toolbar) {
        toolbar.setNavigationIcon(toolBarBackDrawableRes)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    // 复写 menue事件 满足自己的需求
    open fun onMenuClick(menuId: Int) {
    }

}