package self.xhl.com.commonproject.fragmentlazyload

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_blank.*
import self.xhl.com.common.baseui.basefragment.BaseFragment
import self.xhl.com.commonproject.R

/**
* @作者 xhl
*
* @创建时间 2019/4/18 16:40
*/
class LazyLoadFragment : BaseFragment() {

    var parms1:String?=null
    var parms2:String?=null

    override fun initArgs(bundle: Bundle?) {
        parms1=bundle?.getString(Key1)
        parms2=bundle?.getString(Key2)
    }

    override fun getContentLayoutId(): Int {
        return R.layout.fragment_blank
    }

    override fun onFirstInit() {
        super.onFirstInit()
        textF.text=parms1
        ToastUtils.showShort(parms1+": 第一次加载")
    }

    companion object {
        private val Key1="ParmsKey1"
        private val Key2="ParmsKey2"
        @JvmStatic
        fun newInstance(parm1:String, parm2:String): LazyLoadFragment {
            val fragment = LazyLoadFragment()
            val bundle=Bundle()
            bundle.putString(Key1,parm1)
            bundle.putString(Key2,parm2)
            fragment.arguments=bundle
            return fragment
        }
    }
}
