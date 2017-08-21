package lvge.com.myapp.model;

/**
 * Created by JGG on 2017-08-22.
 */

public class ClientDetailSosModel {
    private OperationResult operationResult;
    private String pageResult;
    private SosInfor marketEntity;


    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public String getPageResult() {
        return pageResult;
    }

    public void setPageResult(String pageResult) {
        this.pageResult = pageResult;
    }

    public SosInfor getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(SosInfor marketEntity) {
        this.marketEntity = marketEntity;
    }


}


