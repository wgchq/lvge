package lvge.com.myapp.model;

/**
 * Created by JGG on 2017-10-15.
 */

public class WinXinModel {
    OperationResult operationResult;

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

    public WinXinMessage getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(WinXinMessage marketEntity) {
        this.marketEntity = marketEntity;
    }

    String pageResult;
    WinXinMessage  marketEntity;

}
