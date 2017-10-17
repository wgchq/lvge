package lvge.com.myapp.manager;

import java.util.List;

import lvge.com.myapp.model.order.LogisticCompanyModel;
import lvge.com.myapp.util.GsonUtil;
import lvge.com.myapp.util.SharedPreferencesUtil;

/**
 * Created by android on 2017/10/17.
 */

public class CacheManager {
    private static final String KEY_LOGISTIC = "LOgistic";
    public static void saveLogisticCompany(String json){
        SharedPreferencesUtil.getInstance().putString(KEY_LOGISTIC,json);
    }

    public static List<LogisticCompanyModel> getLogisticCompany(){
        String s = SharedPreferencesUtil.getInstance().getString(KEY_LOGISTIC);
        if (s == null)
            return null;
        else {
            List<LogisticCompanyModel> logisticCompanyModels = GsonUtil.parseString2List(s, LogisticCompanyModel.class);
            return logisticCompanyModels;
        }
    }
}
