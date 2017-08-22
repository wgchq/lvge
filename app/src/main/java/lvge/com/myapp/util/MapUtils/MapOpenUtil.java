package lvge.com.myapp.util.MapUtils;

import android.app.admin.SecurityLog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by JGG on 2017-08-22.
 */

public class MapOpenUtil {
    private Double DLAT;
    private Double DLNG;
    private Double SLAT;
    private Double SLNG;
    private String SName;
    private String DName;

    private Context mContext;




    public Double getDLAT() {
        return DLAT;
    }

    public void setDLAT(Double DLAT) {
        this.DLAT = DLAT;
    }

    public Double getDLNG() {
        return DLNG;
    }

    public void setDLNG(Double DLNG) {
        this.DLNG = DLNG;
    }

    public Double getSLAT() {
        return SLAT;
    }

    public void setSLAT(Double SLAT) {
        this.SLAT = SLAT;
    }

    public Double getSLNG() {
        return SLNG;
    }

    public void setSLNG(Double SLNG) {
        this.SLNG = SLNG;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public MapOpenUtil(Context context) {
        mContext = context;
    }

    public void openBaiduMapToGuide() {
        Intent intent = new Intent();
        double[] location = GPSUtil.gcj02_To_Bd09(DLAT, DLNG);
        double[] slocation = GPSUtil.gcj02_To_Bd09(SLAT, SLNG);

        String url = "baidumap://map/direction?" +
                "origin=name:" + SName + "|latlng:" + slocation[0] + "," + slocation[1] +
                "&destination=name:" + DName + "|latlng:" + location[0] + "," + location[1] +
                "&mode=transit&sy=3&index=0&target=1";
        Uri uri = Uri.parse(url);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        mContext.startActivity(intent);
    }

    public void openGaodeMapToGuide() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        String url = "androidamap://route?sourceApplication=amap&slat=" + SLAT + "&slon=" + SLNG+"&sname="+SName
                + "&dlat=" + DLAT + "&dlon=" + DLNG + "&dname=" + DName + "&dev=0&t=1";
        Uri uri = Uri.parse(url);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        mContext.startActivity(intent);
    }


    public void openBrowserToGuide() {
        String url = "http://uri.amap.com/navigation?from=" + SLAT + "," + SLNG + "," +SName+
                "&to=" + DLAT + "," + DLNG + "," + DName + "&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }
}
