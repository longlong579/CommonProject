package self.xhl.com.common.baseui.baseFragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment
import self.xhl.com.common.widget.emptyview.PlaceHolderView

/**
 * @author xhl
 * @desc
 * 2018/7/24 09:13
 */
public abstract class BaseFragment : SupportFragment() {
    protected var mRoot: View? = null
    protected var mPlaceHolderView: PlaceHolderView? = null//空布局
    protected var mIsFirstInitData = true   // 标示是否第一次初始化数据

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // 初始化参数
        initArgs(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRoot == null) {
            val layId = getContentLayoutId()
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            val root = inflater.inflate(layId, container, false)
            mRoot = root
//               initWidget(root)
        } else {
            if (mRoot!!.parent != null) {
                // 把当前Root从其父控件中移除
                (mRoot!!.parent as ViewGroup).removeView(mRoot)
            }
        }
        return mRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mIsFirstInitData) {
            // 触发一次以后就不会触发
            mIsFirstInitData = false
            // 触发
            //firstInit()
        } else {
            onSecondInit()//暂时未用到
        }
    }


    //初始化相关参数
    abstract fun initArgs(bundle: Bundle?)

    //得到当前界面的资源文件Id @return 资源文件Id
    @LayoutRes
    protected abstract fun getContentLayoutId(): Int


    //初始化ToolBar
    abstract fun initToolBar(root: View)

    //初始化控件
    abstract fun initWidget(root: View)

    //延迟加载
    abstract fun onFirstInit()

    open fun onSecondInit() {

    }

    //初始化数据
    abstract fun initData()

    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    fun setPlaceHolderView(placeHolderView: PlaceHolderView) {
        this.mPlaceHolderView = placeHolderView
    }

    //延迟加载
    private fun firstInit() {
        mRoot?.let {
            initToolBar(it)
            initWidget(it)//初始化toolbar等
            onFirstInit()//初始化控件  其实都可以在onFirstInit或initWidget()中延时加载 分开是为了代码清晰些
            initData()// 当View创建完成后初始化数据
        }

    }

    /*
    该函数系统不会自动调用（即单纯的Fragment显示 隐藏不能用该方法） 而在ViewPage PagerFragmentAdapter中会被自动调用
    *  判断界面切换显示时加载，但界面由隐藏—》显示时不会调用， 一般在if (isAdded && isVisibleToUser){
           refresh()
       }中刷新界面
    */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }


    /*------------------------------------以下不常用---------------------------------*/

    /*
    * 若需求是每次显示都要刷新，比如界面不可见-》可见就要刷新用这个 注意第一次显示时在onLazyInitView之前
    */
    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    /*
    * 延迟加载 界面创建并显示后加载 不建议用此函数
    * 如果要用这个来延迟加载 则offscreenPageLimit=fragment个数，否则onDestroyView之后 View会重新创建而其不执行
    */
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        firstInit()
    }
}