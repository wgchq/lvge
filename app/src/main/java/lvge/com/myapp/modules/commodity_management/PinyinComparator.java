package lvge.com.myapp.modules.commodity_management;

import java.util.Comparator;

/**
 * Created by mac on 2017/10/3.
 */

public class PinyinComparator implements Comparator<CarBean> {
    public int compare(CarBean lhs, CarBean rsh){
        return sort(lhs,rsh);
    }

    private int sort(CarBean lhs, CarBean rsh){
        // 获取ascii值
        int lhs_ascii = lhs.getFirstPinYin().toUpperCase().charAt(0);
        int rhs_ascii = rsh.getFirstPinYin().toUpperCase().charAt(0);
        // 判断若不是字母，则排在字母之后
        if (lhs_ascii < 65 || lhs_ascii > 90)
            return 1;
        else if (rhs_ascii < 65 || rhs_ascii > 90)
            return -1;
        else
            return lhs.getPinYin().compareTo(rsh.getPinYin());

    }
}
