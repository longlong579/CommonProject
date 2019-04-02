package self.xhl.com.commonproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main3.*
import self.xhl.com.common.ktextension.toast
import self.xhl.com.commonproject.adapter.AdapterPart

import java.util.ArrayList
import java.util.Arrays

class Main3Activity : AppCompatActivity() {
     var list: List<String> = arrayListOf()
     var listDetail: List<String> = arrayListOf("AA","BB","CC","DD","EE","FF")
    var adapterPart:AdapterPart?=null
    var adapterDetail:AdapterPart?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val title = arrayOf("AA","BB","CC","DD","EE","FF")
        list = Arrays.asList(*title)
        initView()
        F.Kt{
          say()
        }
    }

    fun say()
    {
        toast("fsaf")
    }

    private fun initView() {
        recyclerViewPart.apply {
           val linearLayoutManager= LinearLayoutManager(this@Main3Activity)
            setLayoutManager(linearLayoutManager)
            adapterPart= AdapterPart(R.layout.item_part,list)
            addOnScrollListener(object :RecyclerView.OnScrollListener()
            {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val view=recyclerView.getChildAt(0)
                    val position=  recyclerView.getChildAdapterPosition(view)

                    if(view?.top==0)
                    {
                        Log.e("ReccleScoll",position.toString())
                        Toast.makeText(this@Main3Activity,position.toString(),Toast.LENGTH_SHORT).show()
                    }
                }


                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })

            recyclerViewPart.adapter=adapterPart
        }

    }

}
