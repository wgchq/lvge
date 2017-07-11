package lvge.com.myapp.modules.my_4s_management;

import android.app.Application;

import java.util.List;

import lvge.com.myapp.model.OperationResult;

/**
 * Created by mac on 2017/7/11.
 */

public class My4sManagementActivityGson {

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

    public My4sManagementActivityGson.marketEntity getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(My4sManagementActivityGson.marketEntity marketEntity) {
        this.marketEntity = marketEntity;
    }

    private marketEntity marketEntity;

    public class marketEntity{
        private String id;
        private String serverPhone;
        private String sellerID;
        private String assistPhone;
        private String address;
        private String notifyDangerPhone;
        private String levelCode;
        private String lng;
        private String photo;
        private String lat;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }



        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }


        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLevelCode() {
            return levelCode;
        }

        public void setLevelCode(String levelCode) {
            this.levelCode = levelCode;
        }



        public String getNotifyDangerPhone() {
            return notifyDangerPhone;
        }

        public void setNotifyDangerPhone(String notifyDangerPhone) {
            this.notifyDangerPhone = notifyDangerPhone;
        }


        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAssistPhone() {
            return assistPhone;
        }

        public void setAssistPhone(String assistPhone) {
            this.assistPhone = assistPhone;
        }



        public marketEntity(String assistPhone) {
            this.assistPhone = assistPhone;
        }

        public String getSellerID() {
            return sellerID;
        }

        public void setSellerID(String sellerID) {
            this.sellerID = sellerID;
        }


        public String getServerPhone() {
            return serverPhone;
        }
        public void setServerPhone(String serverPhone) {
            this.serverPhone = serverPhone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



    }

}

