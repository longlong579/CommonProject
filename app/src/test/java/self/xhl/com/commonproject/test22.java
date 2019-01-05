package self.xhl.com.commonproject;

import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;


/**
 * @author xhl
 * @desc 2018/12/22 13:46
 */
public class test22 {
    @Test
    public void textRxjava(){

       Observable observable= Observable.create(new MySub());
        final String Tag="RXJava ";
       observable.subscribe(new Consumer<String>() {
           @Override
           public void accept(String o) throws Exception {
               Log.e(Tag,o);
           }
       }, new Consumer<Throwable>() {
           @Override
           public void accept(Throwable throwable) throws Exception {
               Log.e(Tag,throwable.getMessage());
           }
       });

    }

    class MySub implements ObservableOnSubscribe
    {

        @Override
        public void subscribe(final ObservableEmitter emitter) throws Exception {

        }
    }

}
