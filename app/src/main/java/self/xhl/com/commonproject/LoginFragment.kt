package self.xhl.com.commonproject

import android.os.Bundle
import android.os.CountDownTimer
import android.renderscript.RenderScript
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.xhl.statusbarcompatutil.StatusBarCompat
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_login_inform_audit.*
import self.xhl.com.common.baseui.baseFragment.ToolbarFragment
import self.xhl.com.common.glide.*
import self.xhl.com.common.glide.progress.ProgressManager
import self.xhl.com.commonproject.data.pager.PageBean
import self.xhl.com.commonproject.kotlinextension.singleToast


/**
 * @author xhl
 * @desc
 * 2018/7/21 16:10
 */
class LoginFragment : ToolbarFragment() {


    override fun getContentLayoutId(): Int {
        return R.layout.fragment_login_inform_audit
    }

    override fun initArgs(bundle: Bundle?) {
    }

    override fun initToolBarPre() {
        super.initToolBarPre()
        StatusBarCompat.translucentStatusBar(_mActivity, false)
        StatusBarCompat.setStatusBarDarkFont(_mActivity, false)
        build().setHasToolBar(true)
                .setShowCenterTitle(false)
                .setToolBarTitle("我是Frgment测试")
                .setToolBarTitleColorRes(R.color.red_100)
                .setEnableBack(true)
        var p = PageBean
    }

    override fun initWidget(root: View) {

    }

    override fun onFirstInit() {
        //countDownTimer.start()
//        btnBack2Login.setOnClickListener {
//            pop()
//
//        }
//        btnBack2Login.singclick {
//            var v = it
//        }
        var path1 = "http://ww1.sinaimg.cn/large/85cccab3gw1etdkmwyrtsg20dw07hx33.jpg"
        var path2 = "http://img.52z.com/upload/news/20130624/201306241320518553.jpg"
        btnBack2Login.setOnClickListener {
            singleToast("wsce")
            //imageLoad(context!!, path1, imageView, RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
            imageLoadCircle(context!!, path2, imageView)
            imageLoadRoundedCorners(context!!,path2,imageView,20,RoundedCornersTransformation.CornerType.BOTTOM)
            getToorBar()?.title="ww"
            //imageLoadWithProgress(context!!,"http://ww1.sinaimg.cn/large/85cccab3gw1etdkmwyrtsg20dw07hx33.jpg",imageView,progressView)
        }

    }

    override fun initData() {

    }


    private fun initAdapter() {

    }


    protected var countDownTimer: CountDownTimer = object : CountDownTimer((1000 * 3).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val value = (millisUntilFinished / 1000).toInt().toString()
            text_tishi.text = getString(R.string.abc_action_bar_home_description, value)
        }

        override fun onFinish() {
            pop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        countDownTimer.onFinish() 会执行2次
        countDownTimer.cancel()

    }

    companion object {
        fun newInstance(): LoginFragment {
            val fragment = LoginFragment()
            return fragment
        }
    }
}