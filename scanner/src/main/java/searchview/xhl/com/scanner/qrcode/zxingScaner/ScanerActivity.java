package searchview.xhl.com.scanner.qrcode.zxingScaner;

import com.xhl.statusbarcompatutil.StatusBarCompat;

import self.xhl.com.common.baseui.baseActivity.PermissionBaseActivity;

public class ScanerActivity extends PermissionBaseActivity {
    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public void initToolBarPre() {
        super.initToolBarPre();
        StatusBarCompat.translucentStatusBar(this,true);
        StatusBarCompat.setStatusBarDarkFont(this,true);
        build().setHasToolBar(false);
//                .setShowCenterTitle(true)
//                .setToolBarTitle("我是测试")
//                .setToolBarTitleColorRes(R.color.red_100)
//                .setEnableBack(true)
    }
}
