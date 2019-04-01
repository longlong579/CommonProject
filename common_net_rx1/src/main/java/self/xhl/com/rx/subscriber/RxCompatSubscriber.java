package self.xhl.com.rx.subscriber;

import android.util.Log;

import com.eightbitlab.rxbus.Bus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import self.xhl.com.net.exception.BizException;
import self.xhl.com.net.exception.TickOutException;
import self.xhl.com.net.exception.TokenFailException;
import self.xhl.com.net.netutil.ExceptionUtil;
import self.xhl.com.rx.event.TickOutEvent;
import self.xhl.com.rx.event.TokenFailEvent;

/**
 * @author bingo xhl
 * @version 1.0.0
 */
//按需修改（注意踢下线的判断 code值 以及踢下线的事件注册）自定义Subscriber 异常统一处理   比如添加Token过期处理
public abstract class RxCompatSubscriber<T> implements Observer<T> {
    public static final String RXCOMPAT_SUBSCRIBER_TAG = RxCompatSubscriber.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public final void onError(Throwable e) {
        RxCompatException rxCompatException;
        if (e instanceof TickOutException) {
            // TODO: 16/01/2018 踢下线逻辑
//            EventBus.getDefault().post(new KickoutEvent());
            Bus.INSTANCE.send(new TickOutEvent());
            rxCompatException = new RxCompatException(e, ((TickOutException) e).getDetailMsg());
        }
       else if(e instanceof TokenFailException)
        {   // TODO:  Token失效
            Bus.INSTANCE.send(new TokenFailEvent());
            rxCompatException = new RxCompatException(e, ((TokenFailException) e).getDetailMsg());
        }
        else if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            rxCompatException = new RxCompatException(bizException.getErrCode(), bizException.getMessage(), e.getLocalizedMessage());
        } else {
            if (ExceptionUtil.isNetworkTimeout(e)) {
                rxCompatException = new RxCompatException(RxCompatException.CODE_NETWORK_TIMEOUT, RxCompatException.ERROR_NETWORK_TIMEOUT, e, e.getLocalizedMessage());
            } else if (ExceptionUtil.isNetworkUnavailable(e)) {
                rxCompatException = new RxCompatException(RxCompatException.CODE_NETWORK_UNAVAIABLE, RxCompatException.ERROR_NETWORK_UNAVAIABLE, e, e.getLocalizedMessage());
            } else if (ExceptionUtil.isServerBusy(e)) {
                rxCompatException = new RxCompatException(RxCompatException.CODE_SERVER_BUSY, RxCompatException.ERROR_SERVER_BUSY, e, e.getLocalizedMessage());
            }  else {
                // default error
                rxCompatException = new RxCompatException(e, e.getLocalizedMessage());
            }
        }
        try {
            onErrorCompat(rxCompatException);
        } catch (Throwable throwable) {
        }

        Log.e(RXCOMPAT_SUBSCRIBER_TAG, rxCompatException.getMessage()); // 单元测试失败依据，勿删除！！

        Log.e(RXCOMPAT_SUBSCRIBER_TAG, String.valueOf(rxCompatException.getCause()));
    }

    @Override
    public final void onNext(T t) {
        try {
            onNextCompat(t);
        } catch (Throwable throwable) {
            Log.e(RXCOMPAT_SUBSCRIBER_TAG, throwable.getLocalizedMessage());
        }
    }

    @Override
    public final void onComplete() {
        try {
            onCompletedCompat();
        } catch (Throwable throwable) {
            Log.e(RXCOMPAT_SUBSCRIBER_TAG, throwable.getLocalizedMessage());
        }
    }

    public abstract void onNextCompat(T data);

    public abstract void onErrorCompat(RxCompatException e);

    public abstract void onCompletedCompat();

}
