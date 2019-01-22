package self.xhl.com.common.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import self.xhl.com.common.glide.progress.OnProgressListener;
import self.xhl.com.common.glide.progress.ProgressManager;

/**
 * @author xhl
 * @desc 2019/1/18 17:36
 * 若使用进度条 则into()传入GlideImageViewTarget 监听加载开始与失败
 */
public class GlideImageViewTarget extends DrawableImageViewTarget {
    private String Url;
    GlideImageViewTarget(ImageView view,String url) {
        super(view);
        this.Url=url;
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(Url);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(Url);
        }
        super.onLoadFailed(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        OnProgressListener onProgressListener = ProgressManager.getProgressListener(Url);
        if (onProgressListener != null) {
            onProgressListener.onProgress(true, 100, 0, 0);
            ProgressManager.removeListener(Url);
        }
        super.onResourceReady(resource, transition);
    }
}