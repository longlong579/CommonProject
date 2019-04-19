package self.xhl.com.common.baseui.basefragment

import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import android.view.View
import self.xhl.com.common.toolbardeleget.ToolBarDelege

/**
 * @author xhl
 * @desc   fragment的toolBar 不去替换Acticity的ActionBar 所以设置menu不能用系统的OnCreateMenue（）方法
 * 默认不开启ToolBar  不带按返回键处理 交由外界处理
 * 2018/7/24 16:50
 */
public abstract class ToolbarFragment : BaseFragment() {
    lateinit var toolBarDeleget: ToolBarDelege

    /**
     * 初始化控件调用之前
     */
    abstract fun initToolBarPre()

    abstract fun initWidget()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarDeleget=ToolBarDelege(activity!!)
        initToolBarPre()
        initWidget()
        toolBarDeleget.initToolbar(view)
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
        fun setToolbarBackGround(@ColorInt color: Int): ToolBarBuild {
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