package self.xhl.com.rx.subscriber;

/**
 * @author bingo
 * @version 1.0.0
 */

//无需修改  复写onCompletedCompat() 避免应用时再去复写
public abstract class RxCompatOnCompletedSubscriber<T> extends RxCompatSubscriber<T> {

    @Override
    public void onCompletedCompat() {
        // no op
    }

}
//使用
/*
                obserable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxCompatOnCompletedSubscriber<T>() {
                    override fun onNextCompat(data: T?) {

                    }

                    override fun onErrorCompat(e: RxCompatException?) {
                   }
                })
 */