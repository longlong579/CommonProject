package searchview.xhl.com.scanner.qrcode.zxingScaner

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View

/**
* @作者 Administrator
*注意 透明Activity需要继承与Activity
* @创建时间 2019/2/26 10:54
*/
class ZingScannerActivity : Activity() {

    private var permissions: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var tipDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(View(this))
        goRequestPermission(permissions)
    }

    //--------------------------------------权限-------------------------------

    /**
     * 显示提示"跳转到应用权限设置界面"的dialog
     *
     * @param permission 具体的某个权限，用于展示dialog的内容文字。
     */
    private var strBtnCancel = "取消"
    private var strMsg: String? = null
    private fun showTipDialog(permission: String) {
        strBtnCancel = "确定"
        strMsg = "相应功能需要本权限,您确定不授权?"

        tipDialog = AlertDialog.Builder(this)
                .setTitle(PermissionUtil.getPermissionTip(permission))
                .setMessage(strMsg)
                .setNegativeButton(strBtnCancel, { dialog, which ->
                    tipDialog?.cancel()
                    onReject()
                }).setPositiveButton("去授权", {dialog, which ->
                    dialog?.cancel()
                    goRequestPermission(permissions)
                })
                .setCancelable(false)
                .create()

        tipDialog?.show()
    }

    private fun showRationaleTipDialog(permission: String) {
        strBtnCancel = "取消"
        tipDialog = AlertDialog.Builder(this)
                .setTitle(PermissionUtil.getPermissionTip(permission))
                .setMessage("你已拒绝过本权限的请求并选择“不再询问”，本次需要到“设置”页面重新授权。")
                .setNegativeButton(strBtnCancel, { dialog, which ->
                    tipDialog?.cancel()
                    onReject()
                }).setPositiveButton("授权", DialogInterface.OnClickListener { dialog, which ->
                    tipDialog?.cancel()
                    toAppDetailSetting()
                })
                .setCancelable(false)
                .create()

        tipDialog?.show()
    }

    private fun permissionAllAllow() {
        val intent = Intent(this, ScannerActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onReject() {
        finish()
    }

    /**
     * 跳转系统的App应用详情页
     */
    protected fun toAppDetailSetting() {
        val localIntent = Intent()
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        localIntent.data = Uri.fromParts("package", packageName, null)
        startActivity(localIntent)
    }

    //权限授权
    private fun goRequestPermission(permissions: Array<String>) {
        ActivityCompat.requestPermissions(this, permissions, permissions.size)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 循环判断权限，只要有一个拒绝了，则回调onReject()。 全部允许时才回调onAllow()
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {// 拒绝权限
                // 对于 ActivityCompat.shouldShowRequestPermissionRationale
                // 1：用户拒绝了该权限，没有勾选"不再提醒"，此方法将返回true。
                // 2：用户拒绝了该权限，有勾选"不再提醒"，此方法将返回 false。
                // 3：如果用户同意了权限，此方法返回false
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    // 拒绝选了"不再提醒"，一般提示跳转到权限设置页面
                    showRationaleTipDialog(permissions[i])
                } else {
                    showTipDialog(permissions[i])

                }
                return
            }
        }
        permissionAllAllow()
    }

    companion object {
       // private const val INQUIRY_ID = "inquiryId"
        @JvmStatic
        fun launch(context: Context) {
            context.startActivity(Intent(context, ZingScannerActivity::class.java).apply {
//                putExtra(INQUIRY_ID, inquiryId)
            })
        }
    }
}
