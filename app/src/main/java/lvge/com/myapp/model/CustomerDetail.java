package lvge.com.myapp.model;

/**
 * Created by JGG on 2017/7/13.
 */

public class CustomerDetail {

    OperationResult operationResult;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public CustomerDetail getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(CustomerDetail marketEntity) {
        this.marketEntity = marketEntity;
    }

    CustomerDetail marketEntity;
}
