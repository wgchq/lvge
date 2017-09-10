package lvge.com.myapp.modules.shop_management;

import java.util.List;

import lvge.com.myapp.model.OperationResult;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivityGson;

/**
 * Created by cnhao on 2017/8/25.
 */

public class ShopManagementBackAddressGson {
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

    public MarketEntity getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(MarketEntity marketEntity) {
        this.marketEntity = marketEntity;
    }

    private MarketEntity marketEntity;

    public class MarketEntity{
        private String receiver;
        public void setReceiver(String receiver){
            this.receiver = receiver;
        }
        public String getReceiver(){
            return receiver;
        }

        private String detailAddress;
        public void setDetailAddress(String detailAddress){
            this.detailAddress = detailAddress;
        }
        public String getDetailAddress(){
            return detailAddress;
        }

        private String postCode;
        public void setPostCode(String postCode){
            this.postCode = postCode;
        }
        public String getPostCode(){
            return postCode;
        }

        private String mobile;
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return mobile;
        }
    }

}
