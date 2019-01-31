package self.xhl.com.commonproject.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import self.xhl.com.commonproject.R


/**
 * @author xhl
 * @desc
 * 2019/1/28 17:59
 */
class AdapterPart(layoutId: Int, categoryData: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutId, categoryData)
{
    override fun convert(helper: BaseViewHolder?, item: String?) {
      helper?.setText(R.id.textPart,item)
    }
}