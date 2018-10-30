package self.xhl.com.common.baseui.baseActivity

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
 * @desc
 * 2018/7/27 17:21
 */
public abstract class ToolbarActivity : BaseActivity() {
    private var mToolbar: Toolbar? = null
    private var hasBar: Boolean = true
    private var showCenterTitle=true
    private var toolbarNmae="ToolBar"
    private var enableBack=true
    private var toolBarBackDrawableRes=R.drawable.abc_ic_ab_back_material

    @MenuRes
    private var  munuId=0
    @ColorRes
    private var titleColorRes=0

     override fun initWidget() {
        super.initWidget()
        initToolbar(window.decorView.findViewById(android.R.id.content))
    }

    fun build():ToolbarActivity
    {
        return this
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

    private fun initToolbar(root: View) {
        if (hasBar) {
            val toolbar = root.findViewById<View>(R.id.toolbar)
            when (toolbar) {
                null -> throw RuntimeException("该布局不包含toolBar,请添加toolBar或复写hasToolBar")
                else -> toolbar as Toolbar
            }
            this.mToolbar = toolbar
            val centerTitle = toolbar.findViewById<View>(R.id.toolbar_center_title) as TextView
            when {
                showCenterTitle -> {
                    toolbar.title = ""
                    centerTitle.text = toolbarNmae
                    centerTitle.visibility = View.VISIBLE
                    //setToolBarTitleColor(titleColorRes)
                    centerTitle.setTextColor(ContextCompat.getColor(this,titleColorRes))
                }
                else -> {
                    centerTitle.visibility = View.GONE
                    toolbar.title = toolbarNmae
                    toolbar.setTitleTextColor(ContextCompat.getColor(this,titleColorRes))
                }
            }
            if (enableBack) {
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
    fun setShowCenterTitle(showCenterTitle:Boolean): ToolbarActivity {
        this.showCenterTitle=showCenterTitle
        return this
    }

    //默认toolBar名称
     fun setToolBarTitle(toolbarName: String): ToolbarActivity {
        this.toolbarNmae=toolbarName
        return this
    }

    //默认Title颜色
    fun setToolBarTitleColorRes(@ColorRes toolBarTitleColor: Int): ToolbarActivity {
        this.titleColorRes=toolBarTitleColor
        if(mToolbar!=null)
        {

        }
        return this
    }

    //默认可以返回
     fun setEnableBack(isEnableBack:Boolean): ToolbarActivity {
        enableBack=isEnableBack
        return this
    }


    //默认用系统颜色 状态栏背景色
     fun setToolbarBackGround(@DrawableRes resId: Int = 0):ToolbarActivity {
        if (resId != 0)
            mToolbar?.setBackgroundResource(resId)
        return this
    }


    //默认的返回图标
     fun setToolbarBackDrawable(@DrawableRes toolbarBackDrawableRes: Int): ToolbarActivity {
        this.toolBarBackDrawableRes=toolbarBackDrawableRes
        return this
    }



    // munuId>0有menue
    fun setMenuId(@LayoutRes menuId: Int): ToolbarActivity //返回menuId 默认无menue
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