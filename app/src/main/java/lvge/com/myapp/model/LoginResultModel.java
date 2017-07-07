package lvge.com.myapp.model;

import java.security.PublicKey;

/**
 * Created by JGG on 2017/7/8.
 */

public class LoginResultModel {

    private OperationResult operationResult;
    private String pageResult;
    private String marketEntity;


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


    public String getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(String marketEntity) {
        this.marketEntity = marketEntity;
    }


}
