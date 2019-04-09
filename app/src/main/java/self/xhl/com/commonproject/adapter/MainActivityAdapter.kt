package self.xhl.com.commonproject.adapter

import android.support.v4.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import self.xhl.com.commonproject.MainhomeActivity
import self.xhl.com.commonproject.R


/**
 * @author xhl
 * @desc
 * 2019/1/28 17:59
 */
class MainActivityAdapter(layoutId: Int, activityData: List<MainhomeActivity.ItemBean>) : BaseQuickAdapter<MainhomeActivity.ItemBean, BaseViewHolder>(layoutId, activityData)
{
    override fun convert(helper: BaseViewHolder?, item: MainhomeActivity.ItemBean) {
        helper?.apply {
            setImageDrawable(R.id.imageView,ContextCompat.getDrawable(mContext,item.imageId))
            setText(R.id.tv_name,item.title)
        }
    }
}