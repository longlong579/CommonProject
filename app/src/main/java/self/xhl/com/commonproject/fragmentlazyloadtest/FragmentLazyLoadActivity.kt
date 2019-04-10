package self.xhl.com.commonproject.fragmentlazyloadtest


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.xhl.gdlocation.GDLocationClient
import kotlinx.android.synthetic.main.activity_main2.*
import self.xhl.com.common.baseui.baseActivity.PermissionBaseActivity
import self.xhl.com.commonproject.MainActivity
import self.xhl.com.commonproject.R

/**
 * Activity+Fragment 懒加载 异常退出保存数据测试 add模式
 */
class FragmentLazyLoadActivity : PermissionBaseActivity() {

    private lateinit var mFragmention: FragmentManager
    private val FRAGMENT_KEY = "fragment_key"
    private var currentFragmentIndex: Int = -1

    private val F1Tag = "one"
    private val F2Tag = "two"
    private val F3Tag = "three"

    private var f1: LazyLoadFragment? = null
    private var f2: LazyLoadFragment? = null
    private var f3: LazyLoadFragment? = null
    private var fragmentList: ArrayList<Fragment> = arrayListOf()

    override fun getContentLayoutId(): Int {
     return  R.layout.activity_main2
    }

    //此模式适合activity+Fragment
    //hide 和 show才能触发onHidden add不能触发
    //所以要想做延迟加载 在onHidden里面处理的话 需要初始化的时候就把所有的fragment都add
    //但是 add了还不行  必须 commitAllowingStateLoss 一次，否则 如果放在同一个commitAllowingStateLoss执行
    //那么 onCreate 之后就会立马执行onHidden而此时还咩有onCreateView所以会报空指针
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)

        if (savedInstanceState != null) {
            savedInstanceState.apply {
                reStoreSaveInstance(savedInstanceState)//重新获取Fragment
            }
        } else {
            mFragmention = supportFragmentManager
            val mTra = mFragmention.beginTransaction()
            //第一步：先将Fragment add到FragmentTransaction
            if (f1 == null) {
                f1 = LazyLoadFragment.newInstance("第1111111个", "")
                mTra.add(R.id.fragmentContent, f1!!, F1Tag)
                fragmentList.add(f1 ?: return)//多线程可能把f1置空 但空时,提前退出，不加入list
            }

            if (f2 == null) {
                f2 = LazyLoadFragment.newInstance("第22222个", "")
                mTra.add(R.id.fragmentContent, f2!!, F2Tag)

                fragmentList.add(f2 ?: return)
            }
            if (f3 == null) {
                f3 = LazyLoadFragment.newInstance("第333333个", "")
                mTra.add(R.id.fragmentContent, f3!!, F3Tag)
                fragmentList.add(f3 ?: return)
            }
            mTra.commitNowAllowingStateLoss()//先提交一次 将Fragment都Add

        }
        //第2步 先显示一次 不用currentFragmentIndex，直接用第一个位置
        changeFragment(0)//异常退出时显示到正确位置

        bt1.setOnClickListener {
            changeFragment(0)
        }
        bt2.setOnClickListener {
            changeFragment(1)
        }
        bt3.setOnClickListener {
            changeFragment(2)
        }
        bt4.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        bt8.setOnClickListener {
            startActivity(Intent(this, ViewPageActivity::class.java))
        }
    }


    //通过标志改变Fragment
    private fun changeFragment(index: Int) {
        //在本界面则不替换
        if(currentFragmentIndex!=index) {
            currentFragmentIndex = index
            val mTra = supportFragmentManager.beginTransaction()
            hideAllFragment(mTra)
            mTra.show(fragmentList[index])
            mTra.commitAllowingStateLoss()
        }
    }

    fun hideAllFragment(mTra: FragmentTransaction) {
        for (f in fragmentList) {
            mTra.hide(f)
        }
    }

    //异常退出恢复
    fun reStoreSaveInstance(inState: Bundle) {
        currentFragmentIndex = inState.getInt(FRAGMENT_KEY)
        supportFragmentManager.apply {
            f1 = findFragmentByTag(F1Tag) as LazyLoadFragment
            f2 = findFragmentByTag(F2Tag) as LazyLoadFragment
            f3 = findFragmentByTag(F3Tag) as LazyLoadFragment
            fragmentList.add(f1 ?: return)
            fragmentList.add(f2 ?: return)
            fragmentList.add(f3 ?: return)
        }
    }

    /*保存状态 防止Fragment重影 销毁时 FragmentTransaction保存有Fragment,再次进来不处理直接重新创建
    *的话其实有多个Fragment
    */
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(FRAGMENT_KEY, currentFragmentIndex)
        super.onSaveInstanceState(outState)
    }

//    //注意 保存状态不是这个函数！！！
//    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//    }

    override fun onDestroy() {
        super.onDestroy()
    }
}