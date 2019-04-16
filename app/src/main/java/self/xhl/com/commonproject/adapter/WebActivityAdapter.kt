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
class WebActivityAdapter(layoutId: Int, activityData: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutId, activityData)
{
    override fun convert(helper: BaseViewHolder?, item: String) {
        helper?.apply {
            setText(R.id.textPart,item)
        }
    }
}