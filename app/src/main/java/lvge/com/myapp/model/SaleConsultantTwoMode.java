package lvge.com.myapp.model;

/**
 * Created by mac on 2017/7/20.
 */

public class SaleConsultantTwoMode {
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

    public SaleConsultantTwomarketEntity getSaleConsultantTwomarketEntity() {
        return saleConsultantTwomarketEntity;
    }

    public void setSaleConsultantTwomarketEntity(SaleConsultantTwomarketEntity saleConsultantTwomarketEntity) {
        this.saleConsultantTwomarketEntity = saleConsultantTwomarketEntity;
    }

    private SaleConsultantTwomarketEntity saleConsultantTwomarketEntity;
}
