package com.xhl.scanner.qrcode.zxingscaner

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_scanner.*
import org.greenrobot.eventbus.EventBus
import com.xhl.scanner.R
import com.xhl.scanner.qrcode.ScannerResultEvent
import com.xhl.scanner.qrcode.core.QRCodeView

/**
 * @作者 xhl
 * 注意：权限交由外部处理 未授权则不许进入
 * 原先在启动时做了权限判断 ，但出现第一次权限判断后mSurfaceCreated = false的情况 导致扫码不能用
 * 原因暂未找到，原先想反射设置mSurfaceCreated，保险起见用透明Activity做了权限判断
 * @创建时间 2019/1/23 17:36
 */
class ScannerActivity : AppCompatActivity(), QRCodeView.Delegate {
    private val TAG = ScannerActivity::class.java.simpleName
    private val REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666
    private var mFlashing = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var tipDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)//无标题
        getSupportActionBar()?.hide()//隐藏ActionBar 防止系统设置了有ActionBar的主题 而此处未设置主题时，主题覆盖
        setTransparentStatusBar(this)//状态栏导航栏透明
        setContentView(R.layout.activity_scanner)
        zxingview.setDelegate(this)
        permissionAllAllow()//不在此调用  mSurfaceCreated = false;开始进来则为false
      //  zxingview.stopSpotAndHiddenRect() // 停止识别，并且隐藏扫描框
        btnPic.setOnClickListener {
            //系统相册
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY)
        }

        btnLight.setOnClickListener {
            if (mFlashing) {
                mFlashing = false
                // 开闪光灯
                zxingview.openFlashlight()
            } else {
                mFlashing = true
                // 关闪光灯
                zxingview.closeFlashlight()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }


    private fun setTransparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)  //透明状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) //透明导航栏
        }
    }

    override fun onStart() {
        super.onStart()
     //   goRequestPermission(permissions)
        zxingview.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        //        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        zxingview.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        zxingview.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        tipDialog?.let {
            it.cancel()
            tipDialog = null
        }
        zxingview.onDestroy() // 销毁二维码扫描控件
        super.onDestroy()
    }

    private fun vibrate() {
        val vibrator = getSystemService(Activity.VIBRATOR_SERVICE) as Vibrator
                ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, 0x8F))
        } else {
            vibrator.vibrate(200)
        }
    }

    //注意 用kotlin 时Intent? 可空 否则 进入系统相册不选择直接返回会崩溃
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        zxingview.startSpotAndShowRect() // 显示扫描框，并开始识别

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY && data != null) {
            val imagePath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData())

            // 本来就用到 QRCodeView 时可直接调 QRCodeView 的方法，走通用的回调
            zxingview.decodeQRCode(imagePath)

            /*
            没有用到 QRCodeView 时可以调用 QRCodeDecoder 的 syncDecodeQRCode 方法

            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github
            .com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
            //            new AsyncTask<Void, Void, String>() {
            //                @Override
            //                protected String doInBackground(Void... params) {
            //                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
            //                }
            //
            //                @Override
            //                protected void onPostExecute(String result) {
            //                    if (TextUtils.isEmpty(result)) {
            //                        Toast.makeText(TestScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
            //                    } else {
            //                        Toast.makeText(TestScanActivity.this, result, Toast.LENGTH_SHORT).show();
            //                    }
            //                }
            //            }.execute();
        }
    }


//    //--------------------------------------权限-------------------------------
//
//    /**
//     * 显示提示"跳转到应用权限设置界面"的dialog
//     *
//     * @param permission 具体的某个权限，用于展示dialog的内容文字。
//     */
//    private var strBtnCancel = "取消"
//    private var strMsg: String? = null
//    private fun showTipDialog(permission: String) {
//        strBtnCancel = "确定"
//        strMsg = "相应功能需要本权限,您确定不授权?"
//
//        tipDialog = AlertDialog.Builder(this)
//                .setTitle(PermissionUtil.getPermissionTip(permission))
//                .setMessage(strMsg)
//                .setNegativeButton(strBtnCancel, { dialog, which ->
//                    tipDialog?.cancel()
//                    onReject()
//                }).setPositiveButton("去授权", { dialog, which ->
//            dialog?.cancel()
//            goRequestPermission(permissions)
//        })
//                .setCancelable(false)
//                .create()
//
//        tipDialog?.show()
//    }
//
//    private fun showRationaleTipDialog(permission: String) {
//        strBtnCancel = "取消"
//        tipDialog = AlertDialog.Builder(this)
//                .setTitle(PermissionUtil.getPermissionTip(permission))
//                .setMessage("你已拒绝过本权限的请求并选择“不再询问”，本次需要到“设置”页面重新授权。")
//                .setNegativeButton(strBtnCancel, { dialog, which ->
//                    tipDialog?.cancel()
//                    onReject()
//                }).setPositiveButton("授权", DialogInterface.OnClickListener { dialog, which ->
//            tipDialog?.cancel()
//            toAppDetailSetting()
//        })
//                .setCancelable(false)
//                .create()
//
//        tipDialog?.show()
//    }
//
    private fun permissionAllAllow() {
        zxingview.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        //        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        zxingview.startSpotAndShowRect() // 显示扫描框，并开始识别
    }
//
//    private fun onReject() {
//        finish()
//    }
//
//    /**
//     * 跳转系统的App应用详情页
//     */
//    protected fun toAppDetailSetting() {
//        val localIntent = Intent()
//        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
//        localIntent.data = Uri.fromParts("package", packageName, null)
//        startActivity(localIntent)
//    }
//
//    //权限授权
//    private fun goRequestPermission(permissions: Array<String>) {
//        ActivityCompat.requestPermissions(this, permissions, permissions.size)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        // 循环判断权限，只要有一个拒绝了，则回调onReject()。 全部允许时才回调onAllow()
//        for (i in grantResults.indices) {
//            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {// 拒绝权限
//                // 对于 ActivityCompat.shouldShowRequestPermissionRationale
//                // 1：用户拒绝了该权限，没有勾选"不再提醒"，此方法将返回true。
//                // 2：用户拒绝了该权限，有勾选"不再提醒"，此方法将返回 false。
//                // 3：如果用户同意了权限，此方法返回false
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                    // 拒绝选了"不再提醒"，一般提示跳转到权限设置页面
//                    showRationaleTipDialog(permissions[i])
//                } else {
//                    showTipDialog(permissions[i])
//
//                }
//                return
//            }
//        }
//        permissionAllAllow()
//    }

    //----------------------------------zxing回调-------------------------------------
    override fun onScanQRCodeSuccess(result: String?) {
        vibrate()
        EventBus.getDefault().post(ScannerResultEvent(result))
        finish()
       // zxingview.startSpot() // 开始识别
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = zxingview.getScanBoxView().getTipText()
        val ambientBrightnessTip = "\n环境过暗，请打开闪光灯"
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip)
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                zxingview.getScanBoxView().setTipText(tipText)
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
    }
}
