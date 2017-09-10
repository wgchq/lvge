package lvge.com.myapp.modules.shop_management;

import java.util.List;

import lvge.com.myapp.model.OperationResult;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivityGson;

/**
 * Created by cnhao on 2017/8/25.
 */

public class NotAuthenticationGson {
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
        private String companyName;
        public void setCompanyName(String areaID){
            this.companyName = companyName;
        }
        public String getCompanyName(){
            return companyName;
        }

        private String checkStatus;
        public void setCheckStatus(String areaID){
            this.checkStatus = checkStatus;
        }
        public String getCheckStatus(){
            return checkStatus;
        }

        private List<ShopPic> sellerImgs;
        public void setSellerImgs(List<ShopPic> sellerImgs){
            this.sellerImgs = sellerImgs;
        }
        public List<ShopPic> getSellerImgs(){
            return sellerImgs;
        }
    }

    class ShopPic {
        private String imgPath="";
        public String getImgPath() {
            return imgPath;
        }
        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        private String type="";

        public String getEntityType() {
            return entityType;
        }
        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }
        private String entityType="";


        public String getPictureId() {
            return pictureId;
        }
        public void setPictureId(String entityType) {
            this.pictureId = pictureId;
        }
        private String pictureId="";

    }
}
