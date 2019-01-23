package self.xhl.com.common.baseui.baseActivity

import android.os.Bundle
import android.view.Window
import me.yokeyword.fragmentation.SupportActivity
import self.xhl.com.common.widget.emptyview.PlaceHolderView


/**
 * @author xhl
 * @desc
 * 2018/7/27 16:13
 * 沉浸式状态栏 配置在 shouldDisableLightStatusBarMode
 */
public abstract class BaseActivity:SupportActivity()
{
    protected var mPlaceHolderView: PlaceHolderView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)//隐藏标题栏
        // 在界面未初始化之前调用的初始化窗口
        initWidows()

        if (initArgs(intent.extras)) {
            // 得到界面Id并设置到Activity界面中
            val layId = getContentLayoutId()
            if (layId != KEY_NOT_USE) {
                setContentView(layId)
            }

            initBefore()
            initToolBarPre()
            initWidget()
            initData()
        } else {
            finish()
        }
    }

    /**
     * 初始化窗口
     */
    open fun initWidows() {
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回True，错误返回False
     */
    open fun initArgs(bundle: Bundle?): Boolean {
        return true
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    protected abstract fun getContentLayoutId(): Int

    /**
     * 初始化控件调用之前
     */
    open fun initBefore() {

    }
    /**
     * 初始化控件调用之前
     */
    open fun initToolBarPre() {

    }
    /**
     * 初始化控件
     */
    open fun initWidget() {

    }

    /**
     * 初始化数据
     */
    open fun initData() {

    }


    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    protected fun setPlaceHolderView(placeHolderView: PlaceHolderView) {
        this.mPlaceHolderView = placeHolderView
    }


    companion object {
         val KEY_NOT_USE = -1
    }
}