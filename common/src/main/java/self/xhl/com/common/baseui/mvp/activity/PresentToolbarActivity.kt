package self.xhl.com.common.baseui.mvp.activity

import android.widget.TextView
import android.widget.Toast
import com.timmy.tdialog.TDialog
import self.xhl.com.common.baseuiFragmention.baseActivity.PermissionBaseActivity
import self.xhl.com.common.baseuiFragmention.mvp.presenter.BaseContract
import self.xhl.com.common.dialog.dialogfragment.LoadingDialog

/**
 * @author xhl
 * @desc
 * 2018/7/27 17:27
 */

 abstract class PresentToolbarActivity<Presenter : BaseContract.Presenter> : PermissionBaseActivity(), BaseContract.View<Presenter> {
    protected var mPresenter: Presenter? = null
    protected var mLoadingDialog: TDialog? = null

    override fun initBefore() {
        super.initBefore()
        // 初始化Presenter
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
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }


    override fun showError(str: String) {
        // 显示错误, 优先使用占位布局
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerError(str)
        } else {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }


    override fun showLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerLoading()
        } else
            showDialogLoading(null,true)
    }

    override fun hideLoading() {
        if (mPlaceHolderView != null) {
            mPlaceHolderView!!.triggerOk()
        } else
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

    var tv_load_dialog:TextView?=null
    override fun showDialogLoading(msg: String?,cancelAble:Boolean?) {
        var dialog: TDialog? = mLoadingDialog
        if (dialog == null) {
            dialog = LoadingDialog.instance(this, msg)
            mLoadingDialog = dialog
            if(cancelAble!=null)
                mLoadingDialog?.isCancelable = cancelAble
            dialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null)
            mPresenter!!.destroy()
    }
}