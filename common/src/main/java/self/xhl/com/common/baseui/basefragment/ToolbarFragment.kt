package self.xhl.com.common.baseui.basefragment

import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.v4.content.ContextCompat
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
    private var mToolbar: Toolbar? = null//供外界定制 若要更改toolBar hasToolBar=false 实现自己的initToolBar 或用封装的好的几个方法（常用场景）
    private var hasBar: Boolean = true
    private var showCenterTitle=true
    private var toolbarNmae="ToolBar"
    private var enableBack=true
    private var toolBarBackDrawableRes=R.drawable.abc_ic_ab_back_material
    @MenuRes
    private var  munuId=0
    @ColorRes
    private var titleColorRes=0

    /**
     * 初始化控件调用之前
     */
    open fun initToolBarPre() {

    }

    fun getToorBar(): Toolbar? {
        return mToolbar
    }

    fun build(): ToolbarFragment
    {
        return this
    }

    //toolBar返回按钮  可以复写满足自己的需求 默认交给MainActivity处理
    open fun initToolbarNav(toolbar: Toolbar) {
        toolbar.setNavigationIcon(toolBarBackDrawableRes)
        toolbar.setNavigationOnClickListener {
           activity?.onBackPressed()
        }
    }

    // 复写 menue事件 满足自己的需求
    open fun onMenuClick(menuId: Int) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar(view)
    }

    open fun initToolBar(root: View) {
        initToolBarPre()
        initToolbarHere(root)
    }

    private fun initToolbarHere(root: View) {
        val toolbar = root.findViewById<View>(R.id.toolbar)
        if (hasBar) {
            when (toolbar) {
                null -> throw RuntimeException("该布局不包含toolBar,请添加toolBar或复写hasToolBar")
                else -> toolbar as Toolbar
            }
            this.mToolbar = toolbar
            val centerTitle = toolbar.findViewById<View>(R.id.toolbar_center_title) as TextView
            when {
                showCenterTitle-> {
                    toolbar.title = ""
                    centerTitle.text = toolbarNmae
                    centerTitle.visibility = View.VISIBLE
                    if(titleColorRes!=-1)
                        context?.let {
                            centerTitle.setTextColor(ContextCompat.getColor(it,titleColorRes))
                        }

                }
                else -> {
                    centerTitle.visibility = View.GONE
                    toolbar.title = toolbarNmae
                    if(titleColorRes!=-1)
                        context?.let {
                            toolbar.setTitleTextColor(ContextCompat.getColor(it,titleColorRes))
                        }

                }
            }
            if (enableBack) {
                initToolbarNav(toolbar)
            }
            initToolbarMenue(toolbar)
        }
        else
        {
            if(toolbar!=null)
            {
                toolbar.visibility=View.GONE
            }
        }
    }


    //默认有toolBar
    fun setHasToolBar(flaHasBar: Boolean) :ToolbarFragment{
        hasBar=flaHasBar
        return this
    }

    //默认标题居中
    fun setShowCenterTitle(showCenterTitle:Boolean): ToolbarFragment {
        this.showCenterTitle=showCenterTitle
        return this
    }

    //默认toolBar名称
    fun setToolBarTitle(toolbarName: String): ToolbarFragment {
        this.toolbarNmae=toolbarName
        return this
    }

    //默认Title颜色
    fun setToolBarTitleColorRes(@ColorRes toolBarTitleColor: Int): ToolbarFragment {
        this.titleColorRes=toolBarTitleColor
        return this
    }

    //默认可以返回
    fun setEnableBack(isEnableBack:Boolean): ToolbarFragment {
        enableBack=isEnableBack
        return this
    }


    //默认用系统颜色 状态栏背景色
    fun setToolbarBackGround(@DrawableRes resId: Int = 0):ToolbarFragment {
        if (resId != 0)
            mToolbar?.setBackgroundResource(resId)
        return this
    }


    //默认的返回图标
    fun setToolbarBackDrawable(@DrawableRes toolbarBackDrawableRes: Int): ToolbarFragment {
        this.toolBarBackDrawableRes=toolbarBackDrawableRes
        return this
    }



    // munuId>0有menue
    fun setMenuId(@LayoutRes menuId: Int): ToolbarFragment //返回menuId 默认无menue
    {
        this.munuId=menuId
        return this
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
}