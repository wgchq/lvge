package lvge.com.myapp.model.base;

public class BaseResponse<T> {

    /**
     * operationResult : {"resultMsg":"","resultCode":0}
     * pageResult : null
     * marketEntity : {"id":8382,"phone":"13623475329","mileage":1054.6,"vin":"LGBH52E05HY290637","name":"13623475329","imei":"864270031097961","lng":112.59842502170139,"bName":"日产 轩逸 轩逸 2016款 1.6XV CVT智享版","platenumber":"13623475329","lat":37.815747884114586}
     */

    private OperationResultBean operationResult;
    private T pageResult;
    private T marketEntity;

    public boolean isSuccess(){
        return operationResult.getResultCode() == 0;
    }

    public OperationResultBean getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResultBean operationResult) {
        this.operationResult = operationResult;
    }

    public T getPageResult() {
        return pageResult;
    }

    public void setPageResult(T pageResult) {
        this.pageResult = pageResult;
    }

    public T getMarketEntity() {
        return marketEntity;
    }

    public void setMarketEntity(T marketEntity) {
        this.marketEntity = marketEntity;
    }

    public static class OperationResultBean {
        /**
         * resultMsg :
         * resultCode : 0
         */

        private String resultMsg;
        private int resultCode;

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
    }
}
