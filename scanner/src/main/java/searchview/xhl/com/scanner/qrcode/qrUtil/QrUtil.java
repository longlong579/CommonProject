package searchview.xhl.com.scanner.qrcode.qrUtil;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import searchview.xhl.com.scanner.qrcode.zxing.QRCodeEncoder;

/**
 * @author xhl
 * 二维码 条形码生成工具
 * @desc 2019/1/24 14:50
 */
public class QrUtil {

//------------------------------------在主线程直接用-----------------------------------------

    /**
     * 获取建造者
     * qr
     *
     * @param text 样式字符串文本
     */
    public static QrUtil.Builder builder(@NonNull String text) {
        return new QrUtil.Builder(text);
    }

    public static class Builder {

        private int backgroundColor = 0xffffffff;

        private int codeColor = 0xff000000;

        private int width = 160; //dp 二维码 值越大 越清晰

        private Bitmap logo = null;

        private String content;

        private int[] colors = {Color.GREEN, 0x99FF4081, Color.BLUE, Color.CYAN};

        private int barWidth = 160;//一维码宽度  值越大 越清晰 也别太大，占内存
        private int barHeigh = 70;//一维码高度
        private int barTextSize = 0;//0时 底下无文字 单位px

        public Builder backColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder codeColor(int codeColor) {
            this.codeColor = codeColor;
            return this;
        }

        public Builder codeColors(int[] codeColors) {
            this.colors = codeColors;
            return this;
        }

        public Builder codeSide(int width) {
            this.width = width;
            return this;
        }

        public Builder codeLogo(Bitmap logo) {
            this.logo = logo;
            return this;
        }


        public Builder(@NonNull String text) {
            this.content = text;
        }

        //一维码
        public Builder codeBarWidth(int width) {
            this.barWidth = width;
            return this;
        }

        public Builder codeBarHeigh(int heigh) {
            this.barHeigh = heigh;
            return this;
        }

        public Builder codeTextSize(int textSize) {
            this.barTextSize = textSize;
            return this;
        }

        //默认白底黑色
        public Bitmap into(ImageView imageView) {
            int sizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, imageView.getResources().getDisplayMetrics());
            Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, sizePx, Color.BLACK, logo);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }

        //4种颜色
        public Bitmap into4Color(ImageView imageView) {
            int sizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, imageView.getResources().getDisplayMetrics());
            Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, sizePx, sizePx, colors, logo);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }

        //一维码
        public Bitmap intoBar(ImageView imageView) {
            int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWidth, imageView.getResources().getDisplayMetrics());
            int heighPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barHeigh, imageView.getResources().getDisplayMetrics());
            Bitmap bitmap = QRCodeEncoder.syncEncodeBarcode(content, barWidth, barHeigh, barTextSize);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }
    }


    //------------------------------------在子线程用-----------------------------------------

    static class f implements LifecycleOwner {
        public f() {
            super();
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return null;
        }
    }

    //白底黑色的二维码  返回的Disposable 在onDestroy中销毁 防内存溢出 实际使用时生成还是比较快的，一般不会出现内存泄漏
    public static Disposable createAndShowQRCode(String qrContent, final ImageView imageView, final Bitmap logo) {
        Disposable disposable = Observable.just(qrContent)

                .flatMap(new Function<String, ObservableSource<Bitmap>>() {
                    @Override
                    public ObservableSource<Bitmap> apply(String s) throws Exception {
                        int[] colors = {Color.GREEN, 0x99FF4081, Color.BLUE, Color.CYAN};
                        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, imageView.getResources().getDisplayMetrics());
                        Bitmap bitmap = null;
                        bitmap = QRCodeEncoder.syncEncodeQRCode(s, size, Color.BLACK, Color.WHITE, logo);
                        return Observable.just(bitmap);
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        imageView.setImageBitmap(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        return disposable;
    }

    //4种前景色的二维码  返回的Disposable 在onDestroy中销毁 防内存溢出
    public static Disposable createAndShow4ColorsQRCode(String qrUrl, final int[] foregroundColors, final ImageView imageView, final Bitmap logo) {
        Disposable disposable = Observable.just(qrUrl)
                .flatMap(new Function<String, ObservableSource<Bitmap>>() {
                    @Override
                    public ObservableSource<Bitmap> apply(String s) throws Exception {
                        int[] colors = {Color.GREEN, 0x99FF4081, Color.BLUE, Color.CYAN};
                        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, imageView.getResources().getDisplayMetrics());
                        Bitmap bitmap = null;
                        if (foregroundColors == null) {
                            bitmap = QRCodeEncoder.syncEncodeQRCode(s, size, size, colors, logo);
                        } else {
                            bitmap = QRCodeEncoder.syncEncodeQRCode(s, size, size, foregroundColors, logo);
                        }
                        return Observable.just(bitmap);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        imageView.setImageBitmap(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        return disposable;
    }
}
