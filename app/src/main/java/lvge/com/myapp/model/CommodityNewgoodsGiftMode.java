package lvge.com.myapp.model;

import java.util.List;

import lvge.com.myapp.model.OperationResult;

/**
 * Created by mac on 2017/10/14.
 */

public class CommodityNewgoodsGiftMode {
    private OperationResult operationResult;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    private String pageResult;

    public String getPageResult() {
        return pageResult;
    }

    public void setPageResult(String pageResult) {
        this.pageResult = pageResult;
    }

    private MarketEntity marketEntity;

    public MarketEntity getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(MarketEntity marketEntity) {
        this.marketEntity = marketEntity;
    }

    public class MarketEntity{
        private int pageIndex;

        private List<EntityList> entityList;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public List<EntityList> getEntityList() {
            return entityList;
        }

        public void setEntityList(List<EntityList> entityList) {
            this.entityList = entityList;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        private int totalPage;
        private int pageSize;
        private int totalCount;
        private String object;

        public class EntityList{
            public int getGiftID() {
                return giftID;
            }

            public void setGiftID(int giftID) {
                this.giftID = giftID;
            }


            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGiftDesc() {
                return giftDesc;
            }

            public void setGiftDesc(String giftDesc) {
                this.giftDesc = giftDesc;
            }

            private int giftID;
            private int price;
            private int count;
            private String name;
            private String giftDesc;

        }
    }
}
