package self.xhl.com.common.baseuiFragmention.baseFragment

import android.widget.Toast
import self.xhl.com.common.R

/**
 * @author xhl
 * @desc  需要按键回退的Fragment继承此类 onBackPressedSupport返回true拦截按键返回事件自己处理
 * 类似微信的四个主界面 不管在哪个界面 按键返回退出程序
 * 2018/7/23 17:28
 */
abstract class BaseMainBackFragment : ToolbarFragment()
{
    /**
     * 处理回退事件
     *
     * @return
     */
    // 再点一次退出程序时间设置
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0
    override fun onBackPressedSupport(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) run { popChild() }
        else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish()
            } else {
                TOUCH_TIME = System.currentTimeMillis()
                Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show()
            }
        }
        return true//消费事件 不传递给mainActivity
    }
}