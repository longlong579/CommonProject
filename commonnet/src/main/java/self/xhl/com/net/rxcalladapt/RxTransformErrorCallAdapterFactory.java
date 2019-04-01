package self.xhl.com.net.rxcalladapt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * @author xhl
 * @version 1.0.0
 */
//无需修改  observable捕获异常 交给onError
public final class RxTransformErrorCallAdapterFactory extends CallAdapter.Factory{
    private final RxJava2CallAdapterFactory original;
    private RxTransformErrorCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    private RxTransformErrorCallAdapterFactory(Scheduler scheduler) {
        original = RxJava2CallAdapterFactory.createWithScheduler(scheduler);
    }

    static CallAdapter.Factory create() {
        return new RxTransformErrorCallAdapterFactory();
    }

    public static CallAdapter.Factory createWithScheduler(Scheduler scheduler) {
        return new RxTransformErrorCallAdapterFactory(scheduler);
    }

    @Override
    public CallAdapter<?,?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?,?> adapter = original.get(returnType, annotations, retrofit);
        if(adapter == null) return null;

        return new RxCallAdapterWrapper(adapter);
    }

    private static class RxCallAdapterWrapper implements CallAdapter {
        private final CallAdapter<?,?> wrapped;

        RxCallAdapterWrapper(CallAdapter<?,?> wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public Object adapt(Call call) {
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, Observable>() {
                @Override
                public Observable apply(Throwable throwable) throws Exception {
                    return Observable.error(transformException(throwable));
                }
            });
        }


        private Throwable transformException(Throwable throwable) {
            return throwable;
        }
    }
}