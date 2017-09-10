package lvge.com.myapp.modules.shop_management;

import java.util.List;

import lvge.com.myapp.model.OperationResult;
import lvge.com.myapp.modules.my_4s_management.My4sManagementActivityGson;

/**
 * Created by cnhao on 2017/8/25.
 */

public class ShopManagementActivityGson {
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
        private String areaID;
        public void setAreaID(String areaID){
            this.areaID = areaID;
        }
        public String getAreaID(){
            return areaID;
        }

        private String sellerID;
        public void setSellerID(String sellerID){
            this.sellerID = sellerID;
        }
        public String getSellerID(){
            return sellerID;
        }

        private int authentic;
        public void setAuthentic(int authentic){
            this.authentic = authentic;
        }
        public int getAuthentic(){
            return authentic;
        }

        private String address;
        public void setAddress(String address){
            this.address = address;
        }
        public String getAddress(){
            return address;
        }

        private String name;
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }

        private String openService;
        public void setOpenService(String openService){
            this.openService = openService;
        }
        public String getOpenService(){
            return openService;
        }

        private String lng;
        public void setLng(String lng){
            this.lng = lng;
        }
        public String getLng(){
            return lng;
        }

        private String telephone;
        public void setTelephone(String telephone){
            this.telephone = telephone;
        }
        public String getTelephone(){
            return telephone;
        }

        private String lat;
        public void setLat(String lat){
            this.lat = lat;
        }
        public String getLat(){
            return lat;
        }

        private String mobile;
        public void setMobile(String lat){
            this.mobile = mobile;
        }
        public String getMobile(){
            return mobile;
        }

        private String noExpense;
        public void setNoExpense(String noExpense){this.noExpense = noExpense;}
        public String getNoExpense(){return noExpense;}

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
