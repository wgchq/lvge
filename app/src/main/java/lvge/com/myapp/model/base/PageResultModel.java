package lvge.com.myapp.model.base;

import java.util.List;

public class PageResultModel<T> {

    /**
     * pageSize : 10
     * totalPage : 100
     * pageIndex : 1
     * totalCount : 1000
     * entityList : []
     */

    private int pageSize;
    private int totalPage;
    private int pageIndex;
    private int totalCount;
    private List<T> entityList;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }
}
