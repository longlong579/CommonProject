package self.xhl.com.commonproject.data.pager;


/**
 * @author bingo
 * @version 1.0.0
 */

public class PageHelper {
    private int mCurrentPage = 0;

    public PageBean getPageParam() {
        if (mCurrentPage < 0) mCurrentPage = 0;
        PageBean pageBean = new PageBean(++mCurrentPage);
        return pageBean;
    }

    public PageBean getPageParam(int pageSize) {
        if (mCurrentPage < 0) mCurrentPage = 0;
        PageBean pageBean = new PageBean(++mCurrentPage);
        pageBean.setSize(pageSize);
        return pageBean;
    }

    public PageBean getPageBean() {
        if (mCurrentPage < 0) mCurrentPage = 0;
        PageBean pageBean = new PageBean(++mCurrentPage);
        return pageBean;
    }

    public void decreasePage() {
        mCurrentPage--;
    }

    public void resetPage() {
        mCurrentPage = 0;
    }


}
