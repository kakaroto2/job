package com.yoloboo.controller.BaseBean;

/**
 * Created by huhaosumail on 16/5/9.
 */
public class PagingBean {
    private int pageIndex = 1;

    //默认数15
    private int pageSize = 15;

    public int getPageIndex()
    {
        return (pageIndex == 1) ? 0 : (pageIndex -1) * getPageSize();
    }

    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
}
