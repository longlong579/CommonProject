package self.xhl.com.commonproject

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.xhl.statusbarcompatutil.StatusBarCompat
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login_inform_audit.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import searchview.xhl.com.scanner.qrcode.ScannerResultEvent
import searchview.xhl.com.scanner.qrcode.qrUtil.QrUtil
import searchview.xhl.com.scanner.qrcode.zxing.QRCodeEncoder
import self.xhl.com.common.baseui.baseFragment.ToolbarFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initArgs(bundle: Bundle?) {
    }

    override fun initToolBarPre() {
        super.initToolBarPre()
        build().setHasToolBar(true)
                .setShowCenterTitle(false)
                .setToolBarTitle("我是Frgment测试")
                .setToolBarTitleColorRes(R.color.red_100)
                .setEnableBack(true)
    }

    override fun initWidget(root: View) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            _mActivity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)  //透明状态栏
//            _mActivity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) //透明导航栏
//        }
        StatusBarCompat.setOffsetPaddingView(_mActivity, getToorBar())
        StatusBarCompat.translucentStatusBar(_mActivity)
        StatusBarCompat.setStatusBarDarkFont(_mActivity, false)
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
//            //singleToast("wsce")
//            //imageLoad(context!!, path1, imageView, RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
//            imageLoadCircle(context!!, path2, imageView)
//            imageLoadRoundedCorners(context!!, path2, imageView, 20, RoundedCornersTransformation.CornerType.BOTTOM)
//            getToorBar()?.title = "ww"
//            //imageLoadWithProgress(context!!,"http://ww1.sinaimg.cn/large/85cccab3gw1etdkmwyrtsg20dw07hx33.jpg",imageView,progressView)
//            val intent = Intent(context, ScannerActivity::class.java)
//            startActivity(intent)
           // d=QrUtil.createAndShow4ColorsQRCode("wfsafsa",null,imageView,null)
           // QrUtil.builder("wrew").into4Color(imageView)
            QrUtil.builder("wwr").intoBar(imageView)

        }
    }

   var d: Disposable?=null
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: ScannerResultEvent) {
        singleToast(event.data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        countDownTimer.onFinish() 会执行2次
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        if(d!=null && !d?.isDisposed!!)
        {
//            d?.dispose()
//            d=null
        }
    }

    companion object {
        fun newInstance(): LoginFragment {
            val fragment = LoginFragment()
            return fragment
        }
    }
}