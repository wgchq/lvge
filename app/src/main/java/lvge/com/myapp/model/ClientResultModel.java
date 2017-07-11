package lvge.com.myapp.model;

import java.util.PriorityQueue;

/**
 * Created by JGG on 2017/7/12.
 */

public class ClientResultModel {
    private OperationResult operationResult;
    private PageResult<ClientViewModel> pageResult;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public PageResult<ClientViewModel> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<ClientViewModel> pageResult) {
        this.pageResult = pageResult;
    }

    public String getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(String marketEntity) {
        this.marketEntity = marketEntity;
    }

    private String marketEntity;


}
