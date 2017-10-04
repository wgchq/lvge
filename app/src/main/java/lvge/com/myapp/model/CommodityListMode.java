package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/9/26.
 */

public class CommodityListMode {
    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    private OperationResult operationResult;


    private PageResultA pageResult;

    private String marketEntity;

    public String getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(String marketEntity) {
        this.marketEntity = marketEntity;
    }

    public PageResultA getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResultA pageResult) {
        this.pageResult = pageResult;
    }

    public class PageResultA{

       private List<CommodityListEntitylist> entityList;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int pageIndex;

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

        public List<CommodityListEntitylist> getEntityList() {
            return entityList;
        }

        public void setEntityList(List<CommodityListEntitylist> entityList) {
            this.entityList = entityList;
        }
    }
}
