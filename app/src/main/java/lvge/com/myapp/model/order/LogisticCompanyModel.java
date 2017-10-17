package lvge.com.myapp.model.order;

import java.io.Serializable;

/**
 * Created by android on 2017/10/17.
 */

public class LogisticCompanyModel implements Serializable {

    /**
     * logisticID : 82706
     * companyName : RnMQ1lBRA6
     * companyCode : udoL7iySC9
     */

    private int logisticID;
    private String companyName;
    private String companyCode;

    public int getLogisticID() {
        return logisticID;
    }

    public void setLogisticID(int logisticID) {
        this.logisticID = logisticID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
