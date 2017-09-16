package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/9/16.
 */

public class EmployeeInformationAddPostMode {
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

    private  String pageResult;


    private List<PostListMode> marketEntity;

    public List<PostListMode> getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(List<PostListMode> marketEntity) {
        this.marketEntity = marketEntity;
    }
}
