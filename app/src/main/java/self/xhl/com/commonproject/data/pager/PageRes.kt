package self.xhl.com.commonproject.data.pager

/**
 * Created by Administrator on 2018\9\9 0009.
 */
class PageRes<T>
{
    var records: T? = null
    var current: Int = 0
    var pages: Int = 0
    var total: Int = 0
    var size: Int = 0
}