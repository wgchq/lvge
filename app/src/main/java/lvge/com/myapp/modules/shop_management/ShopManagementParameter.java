package lvge.com.myapp.modules.shop_management;

/**
 * Created by cnhao on 2017/8/26.
 */

public class ShopManagementParameter {
    //authentic's meaning
    public final static String AUTHENTIC_NOAPPLY = "0";
    public final static String AUTHENTIC_APPLYING = "1";
    public final static String AUTHENTIC_PASSING="2";
    public final static String AUTHENTIC_NOPASS="3";

    public final static int AUTHENTIC_NOTAUTHENTIC_PAGE = 1001;
    public final static int AUTHENTIC_HASCOMMIT_PAGE = 1002;
    public final static int AUTHENTIC_COMMITTING_PAGE = 1003;
    public final static int AUTHENTIC_HASPASS_PAGE = 1004;
    public final static int AUTHENTIC_NOTPASS_PAGE = 1005;



    //openService's meaning
    public final static String OPENSERVICE_NOAPPLY = "0";
    public final static String OPENSERVICE_APPLYING = "1";
    public final static String OPENSERVICE_PASSING = "2";

    //noExpense's meaning
    public final static String NOEXPENSE_NOSUPPORT = "0";
    public final static String NOEXPENSE_SUPPORT = "1";


    //IMG_TYPE
    public final static String SHOPIMG_PAGE = "1";
    public final static String SHOPIMG_IDENTITY = "0";
    public final static String SHOPIMG_DEVICE = "2";
    public final static String SHOPIMG_EXTRA = "3";

    //IMG_FILETYPE
    public final static String SHOPIMG_MAINPAGE = "10";
    public final static String SHOPIMG_ENVPAGE = "11";
    public final static String SHOPIMG_OTHERPAGE = "12";

    public final static String SHOPIMG_BUSINESS_LICENSE = "01";
    public final static String SHOPIMG_IDENTITY_CARD_POSITIVE = "02";
    public final static String SHOPIMG_IDENTITY_CARD_NEGATIVE = "03";
}
