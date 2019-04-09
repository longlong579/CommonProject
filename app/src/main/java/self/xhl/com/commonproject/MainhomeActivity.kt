package self.xhl.com.commonproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.xhl.base.Utils.UniversalItemDecoration
import com.xhl.scanner.qrcode.ScannerResultEvent
import com.xhl.scanner.qrcode.zxingscaner.ZingScannerActivity
import com.xhl.statusbarcompatutil.StatusBarCompat
import kotlinx.android.synthetic.main.activity_mainhome.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import self.xhl.com.commonproject.adapter.MainActivityAdapter


class MainhomeActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_mainhome)
        EventBus.getDefault().register(this)
        initToolBar()
        initdata()
        initView()
    }

    private fun initToolBar()
    {
        StatusBarCompat.translucentStatusBar(this,true)
        StatusBarCompat.setStatusBarDarkFont(this,true)
    }
    fun initdata()
    {
        dataList.add(ItemBean(R.drawable.circle_dynamic_generation_code,"二维码", ZingScannerActivity::class.java))
        dataList.add(ItemBean(R.drawable.circle_gps_icon,"高德定位",GdLocationActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
        dataList.add(ItemBean(R.drawable.ic_pic_placehold_default,"自定义View",SlefViewTestActivity::class.java))
    }

    fun initView()
    {
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


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    fun onDataSynEvent(event: ScannerResultEvent) {
       ToastUtils.showShort("扫描结果:"+event.data)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
