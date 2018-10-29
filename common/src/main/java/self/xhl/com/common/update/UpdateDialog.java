package self.xhl.com.common.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;


import java.io.File;

import self.xhl.com.common.R;
import self.xhl.com.common.update.download.DownLoadUtil;

/**
 * @author bingo
 * @version 1.0.0
 */

public class UpdateDialog {
    //普通升级
    public final static int COMMON_UPGRADE = 1;
    //强制升级
    public final static int FORCED_UPGRADE = 2;

    private String downloadUrl;

    private String newVersion;

    private NotificationManager manager;

    private Notification notif;

    private boolean isShow = false;

    private Activity mActivity;

    private Class<?> mClazz;


    private Button mConfirmDownloadBtn;

    public UpdateDialog(int iconRes, String appName, Class clazz) {
        this.mIconRes = iconRes;
        this.mAppName = appName;
        this.mClazz = clazz;
    }

    public static UpdateDialog getInstance(int iconRes, String appName, Class clazz) {
        return new UpdateDialog(iconRes, appName, clazz);
    }

    @DrawableRes
    private int mIconRes;

    private String mAppName;

    public void show(Activity act, String tip, String downloadUrl, String newVersion, final int mode) {
        mActivity = act;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("软件升级").setMessage(tip).setPositiveButton("现在升级", null);

        final boolean[] _needForceUpdate = {false};
        switch (mode) {
            case COMMON_UPGRADE:
                builder.setCancelable(true);
                builder.setNegativeButton("以后再说", null);
                break;
            case FORCED_UPGRADE:
                _needForceUpdate[0] = true;
                builder.setCancelable(false);
                break;
            default:
        }
        final AlertDialog _dialog = builder.create();
        _dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mConfirmDownloadBtn = _dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                mConfirmDownloadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!_needForceUpdate[0]) {
                            _dialog.dismiss();
                        }
                        downloadApkAndInitNotif();
                    }
                });
            }
        });
        _dialog.show();
        this.newVersion = newVersion;
        this.downloadUrl = downloadUrl;
    }

    private void downloadApkAndInitNotif() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mActivity, "正在下载中", Toast.LENGTH_SHORT).show();
            if (!isShow) {
                initNotification();
                downloadAPK();
            } else {

            }
            isShow = true;
            changeConfirmDownloadBtnStatus(isShow);
        } else {
            Toast.makeText(mActivity, "sd卡不可用,下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void downloadApkAndInitNotify(String downloadUrl,Activity activity ,String newVersion) {
        mActivity = activity ;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mActivity, "正在下载中", Toast.LENGTH_SHORT).show();
            this.downloadUrl = downloadUrl;
            this.newVersion = newVersion ;
            if (!isShow) {
                initNotification();
                NoneDialogDownloadAPK();
            } else {
            }
            isShow = true;
        } else {
            Toast.makeText(mActivity, "sd卡不可用,下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化Notification
     */
    private void initNotification() {
        // 点击通知栏后打开的activity
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mActivity);
        notif = builder.setSmallIcon(mIconRes)
                // .setLargeIcon(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher))
                .setTicker("下载通知")
                .setContent(new RemoteViews(mActivity.getPackageName(), R.layout.notif_update_content_layout))
                .setContentIntent(getPendingIntent()).build();
        manager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
//        notif = new Notification();
//        notif.icon = R.drawable.ic_launcher;
//        notif.tickerText = "下载通知";
//        // 通知栏显示所用到的布局文件
//        notif.contentView = new RemoteViews(mActivity.getPackageName(), R.layout.notif_content_layout);
//        notif.contentIntent = pIntent;
        manager.notify(0, notif);
    }

    public PendingIntent getPendingIntent() {
        Intent intent = new Intent(mActivity, mClazz);
        PendingIntent pIntent = PendingIntent.getActivity(mActivity, 0, intent, 0);
        return pIntent;
    }

    public void instalAPK(String apkFile) {
        File file = new File(apkFile);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mActivity.startActivity(intent);
    }

    public void NoneDialogDownloadAPK() {
        DownLoadUtil.downloadAPK(downloadUrl, new DownLoadUtil.DownLoadListener() {

            @Override
            public void downloadFailed() {
                manager.cancel(0);
                isShow = false;
                Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void downloadComplete(long fileSize, long downLoadFileSize, String apkFile) {
                manager.cancel(0);
                isShow = false;
                instalAPK(apkFile);
                //Log.i("info", "downloadComplete");
            }

            @Override
            public void download(long fileSize, long downLoadFileSize) {
                notif.contentView.setTextViewText(R.id.content_view_text1,
                        "典典养车" + newVersion + "下载中 " + (downLoadFileSize * 100 / fileSize) + "%");
                notif.contentView.setImageViewResource(R.id.content_view_image, mIconRes);
                notif.contentView.setProgressBar(R.id.content_view_progress,
                        100, (int) (downLoadFileSize * 100 / fileSize), false);
                manager.notify(0, notif);
            }
        });    }


    public void downloadAPK() {
        DownLoadUtil.downloadAPK(downloadUrl, new DownLoadUtil.DownLoadListener() {

            @Override
            public void downloadFailed() {
                manager.cancel(0);
                isShow = false;
                changeConfirmDownloadBtnStatus(isShow);
                Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void downloadComplete(long fileSize, long downLoadFileSize, String apkFile) {
                manager.cancel(0);
                isShow = false;
                changeConfirmDownloadBtnStatus(isShow);
                instalAPK(apkFile);
                //Log.i("info", "downloadComplete");
            }

            @Override
            public void download(long fileSize, long downLoadFileSize) {
                notif.contentView.setTextViewText(R.id.content_view_text1,
                        mAppName + " " + newVersion + "下载中 " + (downLoadFileSize * 100 / fileSize) + "%");
                notif.contentView.setProgressBar(R.id.content_view_progress,
                        100, (int) (downLoadFileSize * 100 / fileSize), false);
                notif.contentView.setImageViewResource(R.id.content_view_image, mIconRes);
                manager.notify(0, notif);
                //Log.i("info", "download" + downLoadFileSize + "-" + fileSize);
            }
        });    }


    private void changeConfirmDownloadBtnStatus(boolean pStatus) {
        if (!pStatus) {
            mConfirmDownloadBtn.setTextColor(Color.parseColor("#118c8a"));
            mConfirmDownloadBtn.setEnabled(true);
            mConfirmDownloadBtn.setText("现在升级");
        } else {
            mConfirmDownloadBtn.setTextColor(Color.parseColor("#bee3db"));
            mConfirmDownloadBtn.setEnabled(false);
            mConfirmDownloadBtn.setText("正在下载中");
        }
    }

}