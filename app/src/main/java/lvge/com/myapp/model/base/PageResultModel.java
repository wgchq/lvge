package lvge.com.myapp.model.base;

import android.widget.Toast;

import java.util.List;

import lvge.com.myapp.util.AppUtil;

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
    public List<Integer> object;
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

    public void setFirstPage() {
        pageIndex = 0;
    }

    public int getShouldLoadPageIndex() {
        return pageIndex + 1;
    }

    /**
     * 添加加载的数据进来，已经自动处理刷新哈下拉更新的问题
     * @param paginationX
     */
    public void loadRecords(PageResultModel<T> paginationX) {
        loadRecords(false,paginationX);
    }
    private int loadPageIndex=0;
    /**
     * 刷新，或者重新加载数据的时候，需要把列表返回top
     * 也就是，加载的是第一页，就返回top
     * @return
     */
    public boolean isNeedToBackTop(){
        return 1==pageIndex&&loadPageIndex==1;
    }
    /**
     *
     * @param formFirst 插入到已存在的数据的最前面：true
     * @param paginationX
     */
    public void loadRecords(Boolean formFirst,PageResultModel<T> paginationX) {
        totalCount=paginationX.getTotalCount();
        loadPageIndex=paginationX.pageIndex;
        if (!isEmpty(paginationX)) {
            //如果加载的是第一页的数据，就是刷新，会清空原来的，再添加新数据
            if (1 == paginationX.getPageIndex()) {
                entityList.clear();
            }
            //直接把数据添加进去
            if(formFirst){
                entityList.addAll(0,paginationX.getEntityList());
            }else {
                entityList.addAll(paginationX.getEntityList());
            }
            //更新页码为当前页码
            pageIndex=paginationX.getPageIndex();
        }else{
            //新加载的数据为空，说明：1、下拉加载，没有更多数据了；2、刷新的时候，原来的数据被删除了
            //这里的判断方法是，在刷新的时候，会调用setFirstPage(),所以，pageIndex＝＝0；
            if(0==pageIndex){
                //刷新没有数据
                entityList.clear();
            } else{
                //加载更多没有数据
                Toast.makeText(AppUtil.getAppContext(),"没有数据了",Toast.LENGTH_LONG).show();
            }
        }
    }
    /**
     * 判断是否为空,一般用在LoadLayout判断有没有数据的时候
     * @return
     */
    public boolean isEmpty(){
        return entityList==null||0==entityList.size();
    }

    public static boolean isEmpty(PageResultModel pagination) {
        if (pagination == null
                || pagination.getEntityList() == null
                || "".equals(pagination)
                || "{}".equals(pagination)
                || "[]".equals(pagination)
                || pagination.getEntityList().size() == 0) {
            return true;
        }

        return false;
    }
}
