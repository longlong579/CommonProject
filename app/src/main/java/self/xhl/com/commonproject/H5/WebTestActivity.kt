package self.xhl.com.commonproject.H5

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.xhl.base.Utils.UniversalItemDecoration
import com.xhl.statusbarcompatutil.StatusBarCompat
import kotlinx.android.synthetic.main.activity_web_test.*
import self.xhl.com.commonproject.R
import self.xhl.com.commonproject.adapter.WebActivityAdapter

class WebTestActivity : AppCompatActivity() {

    var datas= mutableListOf<String>("EasyWebActivity",
            "AgentWebFragment使用",
            "JsAgentWebFragment使用",
            "与ToolBar联动")
    val mAdapter: WebActivityAdapter by lazy {
        WebActivityAdapter(R.layout.item_part,datas).apply {
            setOnItemClickListener { adapter, view, position ->
               when(position)
               {
                   0->
                   {
                       var intent= Intent(this@WebTestActivity,EasyWebActivity::class.java)
                       startActivity(intent)
                   }
                   1->
                   {
                       var intent=Intent(this@WebTestActivity, CommonActivity::class.java)
                               .putExtra(CommonActivity.TYPE_KEY, 5)
                       startActivity(intent)
                   }
                   2->
                   {
                       var intent=Intent(this@WebTestActivity, CommonActivity::class.java)
                               .putExtra(CommonActivity.TYPE_KEY, 4)
                       startActivity(intent)
                   }
               }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_test)
        initToolBar()
        initView()

    }
    private fun initToolBar()
    {
        toolbar.apply {
            setTitleTextColor(Color.WHITE)
            setTitle("")
            toolbar_title.setText("AgentWeb 使用指南")
            setSupportActionBar(this)
            if (supportActionBar != null) {
                // Enable the Up button
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
            setNavigationOnClickListener(View.OnClickListener { this@WebTestActivity.finish() })
        }
        StatusBarCompat.translucentStatusBar(this,true)
        StatusBarCompat.setStatusBarDarkFont(this,true)
        StatusBarCompat.setOffsetPaddingView(this@WebTestActivity,toolbar)
    }

    fun initView()
    {
        recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            addItemDecoration(object : UniversalItemDecoration()
            {
                override fun getItemOffsets(position: Int): Decoration {
                    var decoration =  ColorDecoration()
                    //你的逻辑设置分割线
                    decoration.top= SizeUtils.dp2px(5f)
                    decoration.decorationColor= Color.TRANSPARENT //分割线颜色
                    return decoration

                }
            })
            adapter=mAdapter
        }
    }

}
