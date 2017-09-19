package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/9/19.
 */

public class MessageResultMode {
    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    private OperationResult operationResult;

    public PageResult getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
    }

    private PageResult pageResult;

    public class PageResult{
        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        private int totalCount;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        private int pageSize;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        private int totalPage;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        private int pageIndex;

        private List<EntityList> entityList;

        public List<EntityList> getEntityList() {
            return entityList;
        }

        public void setEntityList(List<EntityList> entityList) {
            this.entityList = entityList;
        }
    }
    public String getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(String marketEntity) {
        this.marketEntity = marketEntity;
    }

    private String marketEntity;
}
