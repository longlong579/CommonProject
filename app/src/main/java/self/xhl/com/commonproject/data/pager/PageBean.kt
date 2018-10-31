package self.xhl.com.commonproject.data.pager

/**
 * 分页请求参数
 *
 * @author bingo
 * @version 1.0.0
 */

class PageBean {

    var size: Int = 0
    var current: Int = 0


    constructor(size: Int, current: Int) {
        this.size = size
        this.current = current
    }

    constructor(current: Int) {
        this.current = current
        this.size = DEFAULT_SIZE
    }

    companion object {
        private val DEFAULT_SIZE = 10
    }
}
