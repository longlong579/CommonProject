package self.xhl.com.commonproject

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.fragment_login_inform_audit.*

import self.xhl.com.common.baseui.baseFragment.ToolbarFragment
import self.xhl.com.commonproject.R

/**
 * @author xhl
 * @desc
 * 2018/7/21 16:10
 */
class LoginFragment :ToolbarFragment() {


    override fun getContentLayoutId(): Int {
        return R.layout.fragment_login_inform_audit
    }

    override fun initArgs(bundle: Bundle?) {
    }

    override fun initToolBarPre() {
        super.initToolBarPre()
        build().setHasToolBar(true)
                .setShowCenterTitle(true)
                .setToolBarTitle("我是Frgment测试")
                .setToolBarTitleColorRes(R.color.red_100)
                .setEnableBack(true)
    }

    override fun initWidget(root: View) {

    }

    override fun onFirstInit() {
        //countDownTimer.start()
        btnBack2Login.setOnClickListener {
            pop()
        }
    }

    override fun initData() {
    }


    private fun initAdapter() {

    }



    protected var countDownTimer: CountDownTimer = object : CountDownTimer((1000 * 3).toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val value = (millisUntilFinished / 1000).toInt().toString()
            text_tishi.text=getString(R.string.abc_action_bar_home_description, value)
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
        fun newInstance(): LoginFragment{
            val fragment = LoginFragment()
            return fragment
        }
    }
}