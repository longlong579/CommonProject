package self.xhl.com.common.baseui.baseActivity;

/**
 * @author xhl
 * @desc 2018/10/24 17:46
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.View;

import self.xhl.com.common.R;
import self.xhl.com.common.utils.PermissionUtil;

public abstract class PermissionBaseActivity extends ToolbarActivity {
    protected final String TAG = getClass().getSimpleName().replace("Activity", "Act");
    private boolean flagIsForcePermission = false;//是否是强制权限
    protected AlertDialog tipDialog;
    private SparseArray<OnPermissionResultListener> listenerMap = new SparseArray<>();
    private String[] permissions;
    private int size;
    /**
     * 权限请求结果监听者
     */
    public interface OnPermissionResultListener {

        /**
         * 权限被允许
         */
        void onAllow();

        /**
         * 权限被拒绝
         */
        void onReject();
    }

    /**
     * 镜像权限申请
     *
     * @param onPermissionResultListener 申请权限结果回调
     */
    public void checkPermissions(OnPermissionResultListener onPermissionResultListener,final String ...permissions) {
        if (Build.VERSION.SDK_INT < 23 || permissions.length == 0) {// android6.0已下不需要申请，直接为"同意"
            if (onPermissionResultListener != null)
                onPermissionResultListener.onAllow();
        } else {
//            size = listenerMap.size();
//            this.permissions=permissions;
//            if (onPermissionResultListener != null) {
//                listenerMap.put(size, onPermissionResultListener);
//            }
//            ActivityCompat.requestPermissions(this, permissions, size);
            goRequestPermission(permissions,onPermissionResultListener);
        }
    }

    private void goRequestPermission(final String[] permissions,OnPermissionResultListener onPermissionResultListener)
    {
        size = listenerMap.size();
        this.permissions=permissions;
        if (onPermissionResultListener != null) {
            listenerMap.put(size, onPermissionResultListener);
        }
        ActivityCompat.requestPermissions(this, permissions, size);
    }
    /**
     * 强制权限申请
     *
     * @param onPermissionResultListener 申请权限结果回调
     */
    public void checkForcePermissions( OnPermissionResultListener onPermissionResultListener,final String ...permissions) {
        flagIsForcePermission = true;
        checkPermissions(onPermissionResultListener,permissions);
    }

    /**
     * 显示提示"跳转到应用权限设置界面"的dialog
     *
     * @param permission 具体的某个权限，用于展示dialog的内容文字。
     */
    private String strBtnCancel = "取消";
    private String strMsg;
    private void showTipDialog(String permission, final OnPermissionResultListener onPermissionResultListener) {
        if (!flagIsForcePermission) {
            strBtnCancel = "确定";
            strMsg="相应功能需要本权限,您确定不授权?";
        }
        else {
            strBtnCancel = "退出应用";
            strMsg="程序相应功能必须需要本权限，否则不能允许";
        }

        tipDialog = new AlertDialog.Builder(this)
                .setTitle(PermissionUtil.Companion.getPermissionTip(permission))
                .setMessage(strMsg)
                .setNegativeButton(strBtnCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipDialog.cancel();
                        onPermissionResultListener.onReject();
                    }
                }).setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipDialog.cancel();
                        goRequestPermission(permissions, onPermissionResultListener);
                    }
                })
                .setCancelable(false)
                .create();

        tipDialog.show();
    }

    private void showRationaleTipDialog(String permission, final OnPermissionResultListener onPermissionResultListener) {
        if (!flagIsForcePermission)
            strBtnCancel = "取消";
        else
            strBtnCancel = "退出应用";
        tipDialog = new AlertDialog.Builder(this)
                .setTitle(PermissionUtil.Companion.getPermissionTip(permission))
                .setMessage("你已拒绝过本权限的请求并选择“不再询问”，本次需要到“设置”页面重新授权。")
                .setNegativeButton(strBtnCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipDialog.cancel();
                        onPermissionResultListener.onReject();
                    }
                }).setPositiveButton("授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipDialog.cancel();
                        toAppDetailSetting();
                    }
                })
                .setCancelable(false)
                .create();

        tipDialog.show();
    }
    /**
     * 跳转系统的App应用详情页
     */
    protected void toAppDetailSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(localIntent);
    }

    @Override
    protected void onDestroy() {
        if (tipDialog != null) {
            tipDialog.cancel();
            tipDialog = null;
        }
        listenerMap.clear();
        listenerMap = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OnPermissionResultListener onPermissionResultListener = listenerMap.get(requestCode);

        if (onPermissionResultListener != null) {
            listenerMap.remove(requestCode);
            // 循环判断权限，只要有一个拒绝了，则回调onReject()。 全部允许时才回调onAllow()
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {// 拒绝权限
                    // 对于 ActivityCompat.shouldShowRequestPermissionRationale
                    // 1：用户拒绝了该权限，没有勾选"不再提醒"，此方法将返回true。
                    // 2：用户拒绝了该权限，有勾选"不再提醒"，此方法将返回 false。
                    // 3：如果用户同意了权限，此方法返回false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        // 拒绝选了"不再提醒"，一般提示跳转到权限设置页面
                        showRationaleTipDialog(permissions[i], onPermissionResultListener);
                    } else {
                        showTipDialog(permissions[i], onPermissionResultListener);
                        //onPermissionResultListener.onReject();
                    }
                    return;
                }
            }
            onPermissionResultListener.onAllow();
        }
    }
}
