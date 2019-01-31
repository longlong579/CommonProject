package self.xhl.com.common.baseui.mvp.fragment

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.timmy.tdialog.TDialog
import self.xhl.com.common.baseui.baseFragment.ToolbarFragment
import self.xhl.com.common.baseui.mvp.presenter.BaseContract
import self.xhl.com.common.dialog.dialogfragment.LoadingDialog

/**
 * @author xhl
 * @desc  基本的带网络请求的Fragment toolBar可有可无 默认无
 * 2018/7/25 17:02
 */
public abstract class PresenterFragment<Presenter : BaseContract.Presenter> : ToolbarFragment(),BaseContract.View<Presenter>
{
    protected var mPresenter: Presenter? = null
    protected var mLoadingDialog: TDialog? = null
    var tv_load_dialog: TextView?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // 在界面onAttach之后就触发初始化Presenter
        initPresenter()
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract fun initPresenter(): Presenter

    override fun showError(str: Int) {
        // 显示错误, 优先使用占位布局
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerError(str)
        } else {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
        }
    }


    override fun showError(str: String) {
        // 显示错误, 优先使用占位布局
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerError(str)
        } else {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
        }
    }


    override fun showLoading()
    {
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerLoading()
        }
        else
            showDialogLoading(null,false)
    }

    override fun hideLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerOk()
        }
        else
            hideDialogLoading()
    }

    override fun setPresenter(presenter: Presenter?) {
        // View中赋值Presenter
        mPresenter = presenter
    }


    override fun hideDialogLoading() {
        val dialog = mLoadingDialog
        if (dialog != null) {
            dialog.dismiss()
            mLoadingDialog = null
        }
    }

    override fun showDialogLoading(msg: String?,cancelAble:Boolean?) {
        var mdialog: TDialog? = mLoadingDialog
        if (mdialog == null) {
            mdialog = LoadingDialog.instance(_mActivity,msg)
            mLoadingDialog = mdialog
            if(cancelAble!=null)
                mLoadingDialog?.isCancelable=cancelAble
        }
        mdialog.show()
    }


   override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null)
            mPresenter!!.destroy()
    }
}