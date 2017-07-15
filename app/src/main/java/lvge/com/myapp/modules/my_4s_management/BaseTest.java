package lvge.com.myapp.modules.my_4s_management;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;


/**
 * Created by mac on 2017/7/15.
 */

public class BaseTest {

    public String imgPut(String path, List<String> filePaths, Map<String, Object> map) throws Exception {
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符

        // 发送POST请求
        URL url = new URL(path);
        //String sa = String.valueOf(new CookieJarImpl(new MemoryCookieStore()));
      //  CookieStore store = OkHttpUtils.getInstance().getOkHttpClient().
      ///  List<Cookie> URIS = cookieJar.;
        CookieJarImpl cookieJarImpl = (CookieJarImpl)OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        CookieStore store =cookieJarImpl.getCookieStore();        // 得到所有的 URI
        List<Cookie> uris = store.getCookies();
        String responsea = uris.toString();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Cookie", responsea);
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        OutputStream out = new DataOutputStream(conn.getOutputStream());

        // *****************************其他参数的设置*********************************
        if (map != null) {
            Iterator iter = map.entrySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String inputName = (String) entry.getKey();
                String inputValue = (String) entry.getValue();
                if (inputValue == null) {
                    continue;
                }
                sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                sb.append(inputValue);
            }

            out.write(sb.toString().getBytes());
        }
        // *****************************其他参数的设置
        // end*********************************

        // ************************文件和图片的设置*****************************
        if (ListUtil.hasValue(filePaths)) {
            for (String filePath : filePaths) {
                String filename = filePath.substring(filePath.lastIndexOf("/") + 1);

                StringBuffer strBuf = new StringBuffer();
                strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                // strBuf.append("Content-Disposition: form-data; name='file" +
                // index + "'; filename=" + filename + "\r\n");
                strBuf.append("Content-Disposition: form-data; name=\"" + filename + "\"; filename=\"" + filename + "\"\r\n");
                strBuf.append("Content-Type:multipart/form-data" + "\r\n\r\n");

                out.write(strBuf.toString().getBytes());

                InputStream is = new FileInputStream(filePath);
                DataInputStream i = new DataInputStream(is);

                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = i.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                is.close();
            }
        }
        // ************************文件和图片的设置 end*****************************

        byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
        out.write(endData);

        out.flush();
        out.close();

        InputStream ins = null;
        try {
            ins = conn.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int in;
            while ((in = ins.read()) != -1) {
                baos.write(in);
            }
            String str = baos.toString();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                ins.close();
            }
        }
        return null;
    }

    public static HttpCookie getcookies(){

        HttpCookie res = null;
        // 使用 Cookie 的时候：
        // 取出 CookieStore
        CookieJarImpl cookieJarImpl = (CookieJarImpl)OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        CookieStore store =cookieJarImpl.getCookieStore();        // 得到所有的 URI
        List<Cookie> uris = store.getCookies();
        /**
        for (URI ur : uris) {
            // 筛选需要的 URI
            // 得到属于这个 URI 的所有 Cookie
            List<HttpCookie> cookies = store.get(ur);
            for (HttpCookie coo : cookies) {
                res = coo;
            }
        }
         **/
        return res;
    }


}
