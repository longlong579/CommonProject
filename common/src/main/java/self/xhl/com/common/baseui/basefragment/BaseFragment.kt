package self.xhl.com.common.baseui.basefragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import self.xhl.com.common.baseuifragmention.baseFragment.BaseFragment
import self.xhl.com.common.widget.emptyview.PlaceHolderView


abstract class BaseFragment : Fragment() {

    protected var mPlaceHolderView: PlaceHolderView? = null//空布局

    //注意：shouldLazyLoad/fragmentHidden 不要在异常时存储与处理 每次后台内存异常时，让其默认初始化
    //Activity add模式下
    private var shouldLazyLoad = true//是否需要去延时加载
    private var fragmentHidden: Boolean = true//add模式下Fragment的显影

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // 初始化参数
        initArgs(arguments)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (!isReuseView || rootView == null) {
            val layId = getContentLayoutId()
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            rootView = inflater.inflate(layId, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFragmentVisibleInVP && isFirstVisible)//ViewPagem模式下 只有当VIewPageAdapter 调用了setUserVisibleHint（）才有效
        {
            onFirstInit()
            fragmentShow()
            isFirstVisible = false
        }
    }

//    companion object {
//
//        private val ARG_PARAM1 = "param1"
//        private val ARG_PARAM2 = "param2"
//
//        fun newInstance(param1: String?, param2: String?): BaseFragment {
//            val fragment = BaseFragment()
//            val args = Bundle()
//            args.putString(ARG_PARAM1, param1)
//            args.putString(ARG_PARAM2, param2)
//            fragment.arguments = args
//            return fragment
//        }
//    }

    //只有在add模式下有用
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        fragmentHidden = hidden
        if (!hidden) {
            delayInit()
            fragmentShow()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!fragmentHidden && !shouldLazyLoad || isFragmentVisibleInVP)//add模式下页面每次显示加载
        {
            fragmentShow()
        }
    }


    //add模式下延时加载
    private fun delayInit() {

        if (!shouldLazyLoad) {
            return
        }
        shouldLazyLoad = false
        onFirstInit()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        if(!isReuseView)
        {
            initVariable()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        initVariable()
    }


    //-------------------------------------FragmentViewPage中懒加载----------------------------------------------
    //ViewPage模式下
    private val TAG = BaseFragment::class.java.simpleName
    private var isFragmentVisibleInVP: Boolean = false//是否可见(在ViewPage中)
    private var isReuseView: Boolean = true//是否View复用 默认复用
    private var isFirstVisible: Boolean = true//是否第一次可见
    private var rootView: View? = null

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isFragmentVisibleInVP) {
            isFragmentVisibleInVP = false
            onFragmentVisibleChange(false)
        }
        isFragmentVisibleInVP = isVisibleToUser//可见性
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return
        }
        if (isFirstVisible && isVisibleToUser) {
            onFirstInit()
            isFirstVisible = false
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true)
            fragmentShow()
            isFragmentVisibleInVP = true
            return
        }

    }


    private fun initVariable() {
        isFirstVisible = true//第一次可见
        isFragmentVisibleInVP = false//Fragment可见标志
        rootView = null
        isReuseView = true
    }


    //-------------------------------对外开放-----------------------------------
    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
    fun setPlaceHolderView(placeHolderView: PlaceHolderView) {
        this.mPlaceHolderView = placeHolderView
    }



    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected fun reuseView(isReuse: Boolean) {
        isReuseView = isReuse
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     * false 可见  -> 不可见
     */
    protected fun onFragmentVisibleChange(isVisible: Boolean) {
       // Logger.d( mParam1 + "：状态 " + isVisible)
    }

    protected fun isFragmentVisibleInVP(): Boolean {
        return isFragmentVisibleInVP
    }

    //得到当前界面的资源文件Id @return 资源文件Id
    @LayoutRes
    protected abstract fun getContentLayoutId(): Int

    //初始化相关参数
    abstract fun initArgs(bundle: Bundle?)


    /** 初始化延时加载 add模式下只加载一次
     * 在ViewPager模式下 fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    open fun onFirstInit() {
    }

    //每次显示加载 第一次加载时会显示2次 以后正常
    open fun fragmentShow() {
    }

}
