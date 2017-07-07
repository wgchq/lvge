package lvge.com.myapp.model;

/**
 * Created by JGG on 2017/7/8.
 */

public class OperationResult {
    private int resultCode;
    private String resultMsg;

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
