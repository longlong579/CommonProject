package self.xhl.com.commonproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.widget.GridLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.xhl.base.Utils.UniversalItemDecoration
import com.xhl.scanner.qrcode.ScannerResultEvent
import com.xhl.scanner.qrcode.zxingscaner.ZingScannerActivity
import kotlinx.android.synthetic.main.activity_mainhome.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import self.xhl.com.common.baseui.baseactivity.ToolbarActivity
import self.xhl.com.commonproject.H5.EasyWebActivity
import self.xhl.com.commonproject.H5.WebTestActivity
import self.xhl.com.commonproject.adapter.MainActivityAdapter
import self.xhl.com.commonproject.fragmentlazyload.LazyloadMainActivity
import self.xhl.com.commonproject.location_map.GdLocationActivity


class MainhomeActivity : ToolbarActivity() {

    data class ItemBean(@DrawableRes var  imageId: Int, var title:String, var activityClass: Class<*>)
    var dataList= mutableListOf<ItemBean>()
    val mAdapter:MainActivityAdapter by lazy {
        MainActivityAdapter(R.layout.item_activity,dataList).apply {
            setOnItemClickListener { adapter, view, position ->
                var intent=Intent(baseContext,dataList.get(position).activityClass)
                startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }
    override fun getContentLayoutId(): Int {
        return R.layout.activity_mainhome
    }

    override fun initToolBarPre() {
        ToolBarBuild().setShowCenterTitle(false).setToolBarTitle("自己的封装框架").setToolBarTitleColorRes(R.color.colorWhite)
                .setToolbarBackGround(Color.argb(127, 255, 0, 255))
    }

    override fun initWidget() {
        super.initWidget()
        recyclerView.apply {
            layoutManager=GridLayoutManager(context,3)
            addItemDecoration(object :UniversalItemDecoration()
            {
                override fun getItemOffsets(position: Int): Decoration {
                    var decoration =  ColorDecoration()
                    //你的逻辑设置分割线

                    decoration.right=SizeUtils.dp2px(5f) // 右分割
                    decoration.left=SizeUtils.dp2px(5f)  //左分割
                    decoration.top=SizeUtils.dp2px(5f)
                    decoration.decorationColor=Color.TRANSPARENT //分割线颜色
                    return decoration

                }
            })
            adapter=mAdapter
        }
    }

    override fun initData() {
        dataList.add(ItemBean(R.drawable.circle_dynamic_generation_code,"二维码", ZingScannerActivity::class.java))
        dataList.add(ItemBean(R.drawable.circle_gps_icon,"高德定位", GdLocationActivity::class.java))
        dataList.add(ItemBean(R.drawable.circle_captcha,"Fragment懒加载", LazyloadMainActivity::class.java))
        dataList.add(ItemBean(R.drawable.circle_clound,"WebActivity",WebTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"EasyWebActivity",EasyWebActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
    }




    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    fun onDataSynEvent(event: ScannerResultEvent) {
       ToastUtils.showShort("扫描结果:"+event.data)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
