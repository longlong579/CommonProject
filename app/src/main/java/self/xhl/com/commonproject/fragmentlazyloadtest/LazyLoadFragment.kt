package self.xhl.com.commonproject.fragmentlazyloadtest

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger
import self.xhl.com.commonproject.R
import self.xhl.com.commonproject.kotlinextension.singleToast
import self.xhl.com.common.baseui.baseFragment.BaseFragment


class LazyLoadFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null


    //注意：shouldLazyLoad/fragmentHidden 不要在异常时存储与处理 每次后台内存异常时，让其默认初始化
    //Activity add模式下
    private var shouldLazyLoad = true//是否需要去延时加载
    private var fragmentHidden: Boolean = true//add模式下Fragment的显影


    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mParam1 = getString(ARG_PARAM1)
            mParam2 = getString(ARG_PARAM2)
        }
        // initVariable()
        Logger.d(this.javaClass.name + mParam1 + "：onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Logger.d(mParam1 + "：onCreateView")

        if (!isReuseView || rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        }
        if (isFragmentVisibleInVP && isFirstVisible)//ViewPagem模式下 只有当VIewPageAdapter 调用了setUserVisibleHint（）才有效
        {
            isFirstVisible = false
            Logger.d(mParam1 + "：懒加载加载")
            ToastUtils.showLong("加载懒加载")
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        textF.text = mParam1
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            //throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
        Logger.d(this.javaClass.name + "：onAttach")

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        Logger.d(this.javaClass.name + mParam1 + "：onDetach")

    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): LazyLoadFragment {
            val fragment = LazyLoadFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        fragmentHidden = hidden
        if (!hidden) {
            delayInit()
            fragmentShow()//delayInit 和fragment不要同时用
        }
        Logger.d(this.javaClass.name + mParam1 + "  :" + hidden)
    }

    override fun onResume() {
        super.onResume()
        Logger.d(this.javaClass.name + mParam1 + "  :onResume")
        if (!fragmentHidden && !shouldLazyLoad)//add模式下页面每次显示加载
        {
            fragmentShow()
        }
    }


    private fun delayInit() {

        if (!shouldLazyLoad) {
            return
        }
        Logger.d(this.javaClass.name + mParam1 + "  :开始加载")
        shouldLazyLoad = false
        firstDelayInit()
        singleToast("第一次加载数据：" + mParam1)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(this.javaClass.name + mParam1 + "：onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        initVariable()
        Logger.d(this.javaClass.name + mParam1 + "：OnDestroy")
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
        Logger.d(this.javaClass.name + "：setUserVisibleHint")
        isFragmentVisibleInVP = isVisibleToUser//可见性
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return
        }
        if (isFirstVisible && isVisibleToUser) {
            firstDelayInit()
            isFirstVisible = false
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true)
            fragmentShow()
            isFragmentVisibleInVP = true
            return
        }
        if (isFragmentVisibleInVP) {
            isFragmentVisibleInVP = false
            onFragmentVisibleChange(false)
        }
    }


    private fun initVariable() {
        isFirstVisible = true//第一次可见
        isFragmentVisibleInVP = false//Fragment可见标志
        rootView = null
        isReuseView = true
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
        Logger.d(this.javaClass.name + mParam1 + "：状态：" + isVisible)
    }




    protected fun isFragmentVisibleInVP(): Boolean {

        return isFragmentVisibleInVP
    }

    /** 初始化延时加载 add模式下只加载一次
     * 在ViewPager模式下 fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    open fun firstDelayInit() {

    }

    //每次显示加载
    open fun fragmentShow() {
        Logger.d(this.javaClass.name + mParam1 + "  :显示加载")
    }

}
