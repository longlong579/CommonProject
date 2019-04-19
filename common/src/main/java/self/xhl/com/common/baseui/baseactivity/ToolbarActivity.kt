package self.xhl.com.common.baseui.baseactivity

import android.support.annotation.*
import android.support.v7.widget.Toolbar
import com.xhl.statusbarcompatutil.StatusBarCompat
import self.xhl.com.common.toolbardeleget.ToolBarDelege

/**
 * @author xhl
 * @desc
 * 2019/4/18 11:04
 */

/**
 * @author xhl
 * @desc 约定ToolBar的id为：commonToolbar
 * 中间标题id为：toolbar_center_title
 * 解决ToolBar传递问题 简化Menue事件
 * 2018/7/27 17:21
 */
public abstract class ToolbarActivity : BaseActivity() {
    lateinit var toolBarDeleget: ToolBarDelege

    /**
     * 初始化控件调用之前
     */
    abstract fun initToolBarPre()

    /**
     * ToolBar透明等设置 默认是透明
     */
    open fun initTool()
    {
        StatusBarCompat.translucentStatusBar(this,true)
        StatusBarCompat.setOffsetPaddingView(this,getToorBar())
        StatusBarCompat.setStatusBarDarkFont(this,true)
    }

    @CallSuper
    override fun initWidget() {
        toolBarDeleget=ToolBarDelege(this)
        initToolBarPre()
        toolBarDeleget.initToolbar(window.decorView.findViewById(android.R.id.content))
        initTool()
    }

    fun getToorBar(): Toolbar? {
        return  toolBarDeleget.getToorBar()
    }
    //toolBar返回按钮  可以复写满足自己的需求
    open fun initToolbarNav(toolbar: Toolbar) {
        toolBarDeleget.initToolbarNav(toolbar)
    }

    // 复写 menue事件 满足自己的需求
    open fun onMenuClick(menuId: Int) {
    }

    inner class ToolBarBuild
    {

        //默认可以返回
        fun setEnableBack(isEnableBack: Boolean): ToolBarBuild {
            toolBarDeleget.setEnableBack(isEnableBack)
            return this
        }

        //默认有toolBar
        fun setHasToolBar(flaHasBar: Boolean): ToolBarBuild {
            toolBarDeleget.setHasToolBar(flaHasBar)
            return this
        }

        //默认标题居中
        fun setShowCenterTitle(showCentTitle: Boolean): ToolBarBuild {
            toolBarDeleget.setShowCenterTitle(showCentTitle)
            return this
        }

        //默认toolBar名称 可动态改变
        fun setToolBarTitle(toolbarName: String): ToolBarBuild {
            toolBarDeleget.setToolBarTitle(toolbarName)
            return this
        }

        //默认Title颜色 可动态改变
        fun setToolBarTitleColorRes(@ColorRes toolBarTitleColor: Int): ToolBarBuild {
            toolBarDeleget.setToolBarTitleColorRes(toolBarTitleColor)
            return this
        }


        //默认用系统颜色 状态栏背景色 可动态改变
        fun setToolbarBackGround(@ColorInt  color: Int): ToolBarBuild {
            toolBarDeleget.setToolbarBackGround(color)
            return this
        }


        //默认的返回图标 可动态改变
        fun setToolbarBackDrawable(@DrawableRes toolbarBackDrawableRes: Int): ToolBarBuild {
            toolBarDeleget.setToolbarBackDrawable(toolbarBackDrawableRes)
            return this
        }


        // munuId>0有menue
        fun setMenuId(@LayoutRes menuId: Int): ToolBarBuild //返回menuId 默认无menue
        {
            toolBarDeleget.setMenuId(menuId)
            return this
        }
    }

}