package self.xhl.com.common.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author xhl
 * @desc
 * 2018/9/5 13:25
 */
class ViewPageAdapter(fr:FragmentManager) :FragmentPagerAdapter(fr)
{
     private var fragments: ArrayList<Fragment> = arrayListOf()
     private var titles: ArrayList<String> = arrayListOf()


    override fun getCount(): Int {
       return  fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    //TabLayout的标题
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }
}