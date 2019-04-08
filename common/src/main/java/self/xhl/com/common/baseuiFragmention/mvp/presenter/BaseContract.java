package self.xhl.com.common.baseuiFragmention.mvp.presenter;

import android.support.annotation.StringRes;

/**
 * MVP模式中公共的基本契约
 * @author bingo
 * @version 1.0.0
 */

public interface BaseContract {
    // 基本的界面职责
    interface View<T extends Presenter> {
        // 公共的：显示一个字符串错误
        void showError(@StringRes int str);

        void showError(String str);//供present中调用 view.showError(str);

        // 公共的：显示进度条 以下的实现是为了在Present中调用
        void showLoading();

        void showDialogLoading(String msg,Boolean cancelAble);

        void hideLoading();

        void hideDialogLoading();

        // 支持设置一个Presenter
        void setPresenter(T presenter);
    }

    // 基本的Presenter职责
    interface Presenter {
        // 共用的开始触发
        void start();//->对应view的showLoading view的showLoading又在具体View中实现有空布局可优先使用空布局的loading

        void stop();

        void startWithDialogLoading(String msg,Boolean cancelAble);//->对应view的showDialogLoading()

        void stopDialogLoading();

        boolean canLoadMore();

        // 共用的销毁触发
        void destroy();
    }
}
