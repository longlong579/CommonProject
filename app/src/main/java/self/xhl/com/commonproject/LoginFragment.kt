package self.xhl.com.commonproject

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.blankj.utilcode.util.ToastUtils
//import com.xhl.gdlocation.GDLocationClient
//import com.xhl.gdlocation.GdLocationMode
//import com.xhl.gdlocation.GdLocationResult
//import com.xhl.gdlocation.IGdLocationListener
import com.xhl.statusbarcompatutil.StatusBarCompat
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.xhl.scanner.qrcode.ScannerResultEvent
import com.xhl.scanner.qrcode.zxingscaner.ZingScannerActivity
import self.xhl.com.common.baseui.baseFragment.ToolbarFragment
import self.xhl.com.commonproject.kotlinextension.singleToast
//import com.mob.wrappers.ShareSDKWrapper.share
//import cn.sharesdk.onekeyshare.OnekeyShare
//import com.mob.tools.gui.BitmapProcessor.stop
import com.tongji.braindata.data.DataManager
import com.tongji.braindata.data.entity.BaseBean
import io.reactivex.android.schedulers.AndroidSchedulers
import self.xhl.com.rx.subscriber.RxCompatException
import self.xhl.com.rx.subscriber.RxCompatOnCompletedSubscriber


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
        DataManager.getAboutUs()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate{
                }
                .subscribe(object : RxCompatOnCompletedSubscriber<BaseBean<String?>>(){
                    override fun onNextCompat(data: BaseBean<String?>?) {
                        var x=0
                    }

                    override fun onErrorCompat(e: RxCompatException?) {
                        var x=0
                    }
                })

//        btnBack2Login.setOnClickListener {
//            //            //singleToast("wsce")
////            //imageLoad(context!!, path1, imageView, RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
////            imageLoadCircle(context!!, path2, imageView)
////            imageLoadRoundedCorners(context!!, path2, imageView, 20, RoundedCornersTransformation.CornerType.BOTTOM)
////            getToorBar()?.title = "ww"
////            //imageLoadWithProgress(context!!,"http://ww1.sinaimg.cn/large/85cccab3gw1etdkmwyrtsg20dw07hx33.jpg",imageView,progressView)
//
//            // d=QrUtil.createAndShow4ColorsQRCode("wfsafsa",null,imageView,null)
//            // QrUtil.builder("wrew").into4Color(imageView)
//            //QrUtil.builder("wwr").intoBar(imageView)
////            val intent = Intent(context, Main3Activity::class.java)
////            startActivity(intent)
////            var s = SelectImageTypeFragment.newInstance("", "")
////            s.showFragment(fragmentManager, "fsa")
//
//            /**********扫码***************/
////            val intent = Intent(context, ZingScannerActivity::class.java)
////            startActivity(intent)
//
//            showShare()
//
////            (_mActivity as PermissionBaseActivity).checkForcePermissions(object :PermissionBaseActivity.OnPermissionResultListener
////            {
////                override fun onAllow() {
////                    getloc()
////                }
////
////                override fun onReject() {
////                    ToastUtils.showLong("权限问题")
////                }
////            },Manifest.permission.ACCESS_FINE_LOCATION)
//
//        }




//        GDLocationClient.newBuilder(_mActivity)
//                .onceLocation(false)
//                .build().locate(object :IGdLocationListener
//        {
//            override fun gdLocationReceive(gdLocationInfo: GdLocationResult?) {
//               var x=0
//                ToastUtils.showLong("定位成功")
//            }
//
//            override fun onFail(errCode: Int, errInfo: String?) {
//                var x=0
//                ToastUtils.showLong("定位失败")
//
//            }
//        })
    }

//    private fun showShare() {
//        val oks = OnekeyShare()
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize()
//        oks.setSilent(true)   //隐藏编辑页面
//        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable-hdpi.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("分享")
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn")
//        // text是分享文本，所有平台都需要这个字段
//        oks.text = "我是分享文本"
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("http://pic9.nipic.com/20100824/2531170_082435310724_2.jpg")//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn")
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本")
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name))
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn")
//
//        // 启动分享GUI
//        oks.show(context)
//    }

    private fun getloc()
    {
        val mlocationClient = AMapLocationClient(_mActivity)
        val option = AMapLocationClientOption()


        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
                .setOnceLocation(false)
                .setInterval(2000)

        mlocationClient.setLocationOption(option)
        mlocationClient.setLocationListener {
            var x = it
            if (it.errorCode == 12) {
                ToastUtils.showLong("定位失败")
            }
            else if(it.errorCode==0)
            {
                ToastUtils.showLong(it.city)
            }

        }
        mlocationClient.startLocation()
    }

    var d: Disposable? = null
    override fun initData() {

    }


    private fun initAdapter() {

    }


    protected var countDownTimer: CountDownTimer = object : CountDownTimer((1000 * 3).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val value = (millisUntilFinished / 1000).toInt().toString()
//            text_tishi.text = getString(R.string.abc_action_bar_home_description, value)
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
        if (d != null && !d?.isDisposed!!) {
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