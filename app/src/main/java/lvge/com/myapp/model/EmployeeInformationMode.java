package lvge.com.myapp.model;

import java.util.List;

/**
 * Created by mac on 2017/9/18.
 */

public class EmployeeInformationMode {
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

    public List<EmployeeInformationList> getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(List<EmployeeInformationList> marketEntity) {
        this.marketEntity = marketEntity;
    }

    private List<EmployeeInformationList> marketEntity;
}
