package lvge.com.myapp.model;

import java.util.List;

import lvge.com.myapp.modules.my_4s_management.My4sManagementActivityGson;

/**
 * Created by mac on 2017/7/12.
 */

public class SalesConsultantGson {

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    private OperationResult operationResult;

    public String getPageResult() {
        return pageResult;
    }

    public void setPageResult(String pageResult) {
        this.pageResult = pageResult;
    }

    private String pageResult;


    private List<marketEntity>  marketEntity;

    public List<SalesConsultantGson.marketEntity> getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(List<SalesConsultantGson.marketEntity> marketEntity) {
        this.marketEntity = marketEntity;
    }

    public  class marketEntity{
        private String id;
        private String phone;
        private String headImg;
        private String sellerID;
        private String memo;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getSellerID() {
            return sellerID;
        }

        public void setSellerID(String sellerID) {
            this.sellerID = sellerID;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
