package self.xhl.com.common.glide

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import self.xhl.com.common.R
import self.xhl.com.common.glide.progress.GlideApp
import self.xhl.com.common.glide.progress.ProgressManager


/**
 * @author xhl
 * @desc 2019/1/18 17:42
 */
//带进度条的Glide
fun imageLoadWithProgress(context: Context, url: String, imageView: ImageView, progressBar: ProgressBar) {
    ProgressManager.addListener(url) { isComplete, percentage, bytesRead, totalBytes ->
        if (isComplete) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
            progressBar.progress = percentage
        }
    }
    GlideApp.with(context).load(url).into(GlideImageViewTarget(imageView, url))
}

//普通加载
fun imageLoad(context: Context, url: String, imageView: ImageView)
{
    GlideApp.with(context).load(url).into(imageView)
}

//带RequestOptions 的加载
fun imageLoad(context: Context, url: String, imageView: ImageView,option:RequestOptions)
{
    GlideApp.with(context).load(url).placeholder(R.drawable.ic_pic_placehold_default).apply(option).into(imageView)
}

//圆形图片
@JvmOverloads
fun imageLoadCircle(context: Context, url: String, imageView: ImageView,option:RequestOptions=RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
{
    GlideApp.with(context).load(url).placeholder(R.drawable.ic_pic_placehold_default).apply(bitmapTransform(CropCircleTransformation())).apply { option }.into(imageView)
}
//模糊图片  blurRadius;0-25
@JvmOverloads
fun imageLoadBlur(context: Context, url: String, imageView: ImageView,blurRadius:Int=25,option:RequestOptions=RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
{
    GlideApp.with(context).load(url).apply(bitmapTransform(BlurTransformation(blurRadius))).apply(option).into(imageView)
}

//圆角图片
@JvmOverloads
fun imageLoadRoundedCorners(context: Context, url: String, imageView: ImageView,roundRadius:Int,cornerType: RoundedCornersTransformation.CornerType=RoundedCornersTransformation.CornerType.ALL,option:RequestOptions=RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
{
    GlideApp.with(context)
            .load(url)
            .apply(bitmapTransform(RoundedCornersTransformation(roundRadius, 0,
                    cornerType)))
            .apply(option)
            .into(imageView)
}

//-------------------------------清缓存-------------------------------

//清除所有缓存
fun glideClearCache(context: Context)
{
    glidClearMemoryCache(context)
    glidClearFileCache(context)
}

//清理内存缓存 主线程
fun glidClearMemoryCache(context:Context){
    GlideApp.get(context).clearMemory()
}
//清理磁盘缓存 子线程
fun  glidClearFileCache(context:Context){
    Thread(Runnable { GlideApp.get(context).clearDiskCache() }).start()
}

