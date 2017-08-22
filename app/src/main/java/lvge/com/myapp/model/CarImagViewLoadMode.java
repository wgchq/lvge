package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/8/22.
 */

public class CarImagViewLoadMode {
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


    public List<ImageViewLoad> getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(List<ImageViewLoad> marketEntity) {
        this.marketEntity = marketEntity;
    }

    private List<ImageViewLoad> marketEntity;

    public class ImageViewLoad{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        public byte[] getImgBinaryData() {
            return imgBinaryData;
        }

        public void setImgBinaryData(byte[] imgBinaryData) {
            this.imgBinaryData = imgBinaryData;
        }

        private byte[] imgBinaryData;
    }
}
