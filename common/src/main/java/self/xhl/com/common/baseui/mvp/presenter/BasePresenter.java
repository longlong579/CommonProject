package self.xhl.com.common.baseui.mvp.presenter;

/**
 * @author bingo
 * @version 1.0.0
 */

public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    protected int currentPage;
    protected int totalPage;

    private T mView;

    public BasePresenter(T view) {
        setView(view);
    }

    /**
     * 设置一个View，子类可以复写
     */
    @SuppressWarnings("unchecked")
    protected void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     *
     * @return View
     */
    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        // 开始的时候进行Loading调用
        T view = mView;
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void stop() {
        // 开始的时候进行Loading调用
        T view = mView;
        if (view != null) {
            view.hideLoading();
        }
    }

    @Override
    public void startWithDialogLoading(String msg,Boolean cancelAble) {
        T view = mView;
        if (view != null) {
            view.showDialogLoading(msg,cancelAble);
        }
    }

    @Override
    public void stopDialogLoading() {
        T view = mView;
        if (view != null) {
            view.hideDialogLoading();
        }
    }

    @Override
    public boolean canLoadMore() {
        return currentPage < totalPage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void destroy() {
        T view = mView;
        mView = null;
        if (view != null) {
            // 把Presenter设置为NULL
            view.setPresenter(null);
        }
    }
}
