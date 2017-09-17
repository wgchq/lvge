package lvge.com.myapp.model;

/**
 * Created by JGG on 2017-09-17.
 */

public class HistoryValidationListEntity {

    private OperationResult operationResult;
    private PageResult<HistoryValidationModel> pageResult;
    private String marketEntity = null;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }

    public PageResult<HistoryValidationModel> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult<HistoryValidationModel> pageResult) {
        this.pageResult = pageResult;
    }

    public String getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(String marketEntity) {
        this.marketEntity = marketEntity;
    }
}
