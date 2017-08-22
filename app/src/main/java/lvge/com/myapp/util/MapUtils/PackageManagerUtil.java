package lvge.com.myapp.util.MapUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JGG on 2017-08-22.
 */

public class PackageManagerUtil {

    public PackageManagerUtil(Context context) {
        mContext = context;
        mPackageManager = mContext.getPackageManager();
    }

    private static PackageManager mPackageManager = null;
    private static Context mContext = null;
    private static List<String> mPackageNames = new ArrayList<>();
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";


    private static void initPackageManager() {

        List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                mPackageNames.add(packageInfos.get(i).packageName);
            }
        }
    }

    public static boolean haveGaodeMap() {
        initPackageManager();
        return mPackageNames.contains(GAODE_PACKAGE_NAME);
    }

    public static boolean haveBaiduMap() {
        initPackageManager();
        return mPackageNames.contains(BAIDU_PACKAGE_NAME);
    }
}
