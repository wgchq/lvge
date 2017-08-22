package lvge.com.myapp.modules.customer_management;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import lvge.com.myapp.R;
import lvge.com.myapp.model.ClientResultModel;
import okhttp3.Call;

/**
 * Created by JGG on 2017/7/12.
 */

public class ClientsAdapter extends BaseAdapter {
    private Context mContext;

    public ClientsAdapter(Context context) {
        this.mContext = context;
    }

    public ClientResultModel getClients() {
        return clients;
    }

    public void setClients(ClientResultModel clients) {
        this.clients = clients;
    }

    private ClientResultModel clients;

    @Override
    public int getCount() {
        return this.clients.getPageResult().getEntityList().size();
    }

    @Override
    public Object getItem(int position) {
        return clients.getPageResult().getEntityList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return clients.getPageResult().getEntityList().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.clients_customers_item, null);
        }
        if (clients != null) {

            TextView phone = (TextView) convertView.findViewById(R.id.customer_phone);
            phone.setText(clients.getPageResult().getEntityList().get(position).getPhone());

            final TextView car_no = (TextView) convertView.findViewById(R.id.customer_car_no);
            String str_car_no = "车架号：" + clients.getPageResult().getEntityList().get(position).getVin();
            car_no.setText(str_car_no);
            Drawable drawable = mContext.getResources().getDrawable(SwitchCarLog(clients.getPageResult().getEntityList().get(position).getBrandID()));
           /* Float width = convertView.getResources().getDimension(R.dimen.x34);
            Integer int_width = Integer.parseInt( width.toString());*/
            drawable.setBounds(0,0,34,34);
            car_no.setCompoundDrawables(drawable,null,null,null);


            final ImageView headimge = (ImageView) convertView.findViewById(R.id.sale_consultant_iamage);

            if (!clients.getPageResult().getEntityList().get(position).getHeadImg().equals("")) {

                final int int_position = position;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpUtils.get()
                                .url(clients.getPageResult().getEntityList().get(int_position).getHeadImg())
                                .build()
                                .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                                .execute(new BitmapCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int i) {

                                    }

                                    @Override
                                    public void onResponse(Bitmap bitmap, int i) {
                                        headimge.setImageBitmap(bitmap);
                                    }
                                });
                    }
                }).start();


            }
            TextView length = (TextView) convertView.findViewById(R.id.customer_listview_length);
            String str_length = clients.getPageResult().getEntityList().get(position).getMileAge()+"";
            length.setText(str_length);
            Drawable leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer);
            TextView type = (TextView) convertView.findViewById(R.id.customer_listview_type);
            if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("1")) {
                type.setText("已绑定硬件");
                leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer);
            } else if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("0")){
                type.setText("未绑定硬件");
                leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer_not_on_line);
            }
            else if (clients.getPageResult().getEntityList().get(position).getHasTerminalID().equals("2")){
                type.setText("离线/欠费");
                leftDrawable = mContext.getResources().getDrawable(R.mipmap.client_kilometer_not_on_line);
            }
            leftDrawable.setBounds(0,0,leftDrawable.getIntrinsicWidth(),leftDrawable.getIntrinsicHeight());
            length.setCompoundDrawables(leftDrawable,null,null,null);

        }

        return convertView;
    }

    private int SwitchCarLog(String carID){
        int carLogID = R.mipmap.car_logo_default;
        switch (carID){
            case "1":
               carLogID = R.mipmap.a1;
                break;
            case "2":
                carLogID = R.mipmap.a2;
                break;
            case "3":
                carLogID = R.mipmap.a3;
                break;
            case "4":
                carLogID = R.mipmap.a4;
                break;
            case "5":
                carLogID = R.mipmap.a5;
                break;
            case "6":
                carLogID = R.mipmap.a6;
                break;
            case "7":
                carLogID = R.mipmap.a7;
                break;
            case "8":
                carLogID = R.mipmap.a8;
                break;
            case "9":
                carLogID = R.mipmap.a9;
                break;
            case "10":
                carLogID = R.mipmap.a10;
                break;
            case "11":
                carLogID = R.mipmap.a11;
                break;
            case "12":
                carLogID = R.mipmap.a12;
                break;
            case "13":
                carLogID = R.mipmap.a13;
                break;
            case "14":
                carLogID = R.mipmap.a14;
                break;
            case "15":
                carLogID = R.mipmap.a15;
                break;
            case "16":
                carLogID = R.mipmap.a16;
                break;
            case "17":
                carLogID = R.mipmap.a17;
                break;
            case "18":
                carLogID = R.mipmap.a18;
                break;
            case "19":
                carLogID = R.mipmap.a19;
                break;
            case "20":
                carLogID = R.mipmap.a20;
                break;
            case "21":
                carLogID = R.mipmap.a21;
                break;
            case "22":
                carLogID = R.mipmap.a22;
                break;
            case "23":
                carLogID = R.mipmap.a23;
                break;
            case "24":
                carLogID = R.mipmap.a24;
                break;
            case "25":
                carLogID = R.mipmap.a25;
                break;
            case "26":
                carLogID = R.mipmap.a26;
                break;
            case "27":
                carLogID = R.mipmap.a27;
                break;
            case "28":
                carLogID = R.mipmap.a28;
                break;
            case "29":
                carLogID = R.mipmap.a29;
                break;
            case "30":
                carLogID = R.mipmap.a30;
                break;
            case "31":
                carLogID = R.mipmap.a31;
                break;
            case "32":
                carLogID = R.mipmap.a32;
                break;
            case "33":
                carLogID = R.mipmap.a33;
                break;
            case "34":
                carLogID = R.mipmap.a34;
                break;
            case "35":
                carLogID = R.mipmap.a35;
                break;
            case "36":
                carLogID = R.mipmap.a36;
                break;
            case "37":
                carLogID = R.mipmap.a37;
                break;
            case "38":
                carLogID = R.mipmap.a38;
                break;
            case "39":
                carLogID = R.mipmap.a39;
                break;
            case "40":
                carLogID = R.mipmap.a40;
                break;
            case "41":
                carLogID = R.mipmap.a41;
                break;
            case "42":
                carLogID = R.mipmap.a42;
                break;
            case "43":
                carLogID = R.mipmap.a43;
                break;
            case "44":
                carLogID = R.mipmap.a44;
                break;
            case "45":
                carLogID = R.mipmap.a45;
                break;
            case "46":
                carLogID = R.mipmap.a46;
                break;
            case "47":
                carLogID = R.mipmap.a47;
                break;
            case "48":
                carLogID = R.mipmap.a48;
                break;
            case "49":
                carLogID = R.mipmap.a49;
                break;
            case "50":
                carLogID = R.mipmap.a50;
                break;
            case "51":
                carLogID = R.mipmap.a51;
                break;
            case "52":
                carLogID = R.mipmap.a52;
                break;
            case "53":
                carLogID = R.mipmap.a53;
                break;
            case "54":
                carLogID = R.mipmap.a54;
                break;
            case "55":
                carLogID = R.mipmap.a55;
                break;
            case "56":
                carLogID = R.mipmap.a56;
                break;
            case "57":
                carLogID = R.mipmap.a57;
                break;
            case "58":
                carLogID = R.mipmap.a58;
                break;
            case "59":
                carLogID = R.mipmap.a59;
                break;
            case "60":
                carLogID = R.mipmap.a60;
                break;
            case "61":
                carLogID = R.mipmap.a61;
                break;
            case "62":
                carLogID = R.mipmap.a62;
                break;
            case "63":
                carLogID = R.mipmap.a63;
                break;
            case "64":
                carLogID = R.mipmap.a64;
                break;
            case "65":
                carLogID = R.mipmap.a65;
                break;
            case "66":
                carLogID = R.mipmap.a66;
                break;
            case "67":
                carLogID = R.mipmap.a67;
                break;
            case "68":
                carLogID = R.mipmap.a68;
                break;
            case "69":
                carLogID = R.mipmap.a69;
                break;
            case "70":
                carLogID = R.mipmap.a70;
                break;
            case "71":
                carLogID = R.mipmap.a71;
                break;
            case "72":
                carLogID = R.mipmap.a72;
                break;
            case "73":
                carLogID = R.mipmap.a73;
                break;
            case "74":
                carLogID = R.mipmap.a74;
                break;
            case "75":
                carLogID = R.mipmap.a75;
                break;
            case "76":
                carLogID = R.mipmap.a76;
                break;
            case "77":
                carLogID = R.mipmap.a77;
                break;
            case "78":
                carLogID = R.mipmap.a78;
                break;
            case "79":
                carLogID = R.mipmap.a79;
                break;
            case "80":
                carLogID = R.mipmap.a80;
                break;
            case "81":
                carLogID = R.mipmap.a81;
                break;
            case "82":
                carLogID = R.mipmap.a82;
                break;
            case "83":
                carLogID = R.mipmap.a83;
                break;
            case "84":
                carLogID = R.mipmap.a84;
                break;
            case "85":
                carLogID = R.mipmap.a85;
                break;
            case "86":
                carLogID = R.mipmap.a86;
                break;
            case "87":
                carLogID = R.mipmap.a87;
                break;
            case "88":
                carLogID = R.mipmap.a88;
                break;
            case "89":
                carLogID = R.mipmap.a89;
                break;
            case "90":
                carLogID = R.mipmap.a90;
                break;
            case "91":
                carLogID = R.mipmap.a91;
                break;
            case "92":
                carLogID = R.mipmap.a92;
                break;
            case "93":
                carLogID = R.mipmap.a93;
                break;
            case "94":
                carLogID = R.mipmap.a94;
                break;
            case "95":
                carLogID = R.mipmap.a95;
                break;
            case "96":
                carLogID = R.mipmap.a96;
                break;
            case "97":
                carLogID = R.mipmap.a97;
                break;
            case "98":
                carLogID = R.mipmap.a98;
                break;
            case "99":
                carLogID = R.mipmap.a99;
                break;
            case "100":
                carLogID = R.mipmap.a100;
                break;
            case "101":
                carLogID = R.mipmap.a101;
                break;
            case "102":
                carLogID = R.mipmap.a102;
                break;
            case "103":
                carLogID = R.mipmap.a103;
                break;
            case "104":
                carLogID = R.mipmap.a104;
                break;
            case "105":
                carLogID = R.mipmap.a105;
                break;
            case "106":
                carLogID = R.mipmap.a106;
                break;
            case "107":
                carLogID = R.mipmap.a107;
                break;
            case "108":
                carLogID = R.mipmap.a108;
                break;
            case "109":
                carLogID = R.mipmap.a109;
                break;
            case "110":
                carLogID = R.mipmap.a110;
                break;
            case "111":
                carLogID = R.mipmap.a111;
                break;
            case "112":
                carLogID = R.mipmap.a112;
                break;
            case "113":
                carLogID = R.mipmap.a113;
                break;
            case "114":
                carLogID = R.mipmap.a114;
                break;
            case "115":
                carLogID = R.mipmap.a115;
                break;
            case "116":
                carLogID = R.mipmap.a116;
                break;
            case "117":
                carLogID = R.mipmap.a117;
                break;
            case "118":
                carLogID = R.mipmap.a118;
                break;
            case "119":
                carLogID = R.mipmap.a119;
                break;
            case "120":
                carLogID = R.mipmap.a120;
                break;
            case "121":
                carLogID = R.mipmap.a121;
                break;
            case "122":
                carLogID = R.mipmap.a122;
                break;
            case "123":
                carLogID = R.mipmap.a123;
                break;
            case "124":
                carLogID = R.mipmap.a124;
                break;
            case "125":
                carLogID = R.mipmap.a125;
                break;
            case "126":
                carLogID = R.mipmap.a126;
                break;
            case "127":
                carLogID = R.mipmap.a127;
                break;
            case "128":
                carLogID = R.mipmap.a128;
                break;
            case "132":
                carLogID = R.mipmap.a132;
                break;
            case "134":
                carLogID = R.mipmap.a134;
                break;
            case "135":
                carLogID = R.mipmap.a135;
                break;
            case "136":
                carLogID = R.mipmap.a136;
                break;
            case "137":
                carLogID = R.mipmap.a137;
                break;
            case "138":
                carLogID = R.mipmap.a138;
                break;
            case "139":
                carLogID = R.mipmap.a139;
                break;
            case "140":
                carLogID = R.mipmap.a140;
                break;
            case "141":
                carLogID = R.mipmap.a141;
                break;
            case "142":
                carLogID = R.mipmap.a142;
                break;
            case "143":
                carLogID = R.mipmap.a143;
                break;
            case "144":
                carLogID = R.mipmap.a144;
                break;
            case "145":
                carLogID = R.mipmap.a145;
                break;
            case "146":
                carLogID = R.mipmap.a146;
                break;
            case "147":
                carLogID = R.mipmap.a147;
                break;
            case "148":
                carLogID = R.mipmap.a148;
                break;
            case "149":
                carLogID = R.mipmap.a149;
                break;
            case "150":
                carLogID = R.mipmap.a150;
                break;
            case "151":
                carLogID = R.mipmap.a151;
                break;
            case "152":
                carLogID = R.mipmap.a152;
                break;
            case "153":
                carLogID = R.mipmap.a153;
                break;
            case "154":
                carLogID = R.mipmap.a154;
                break;
            case "155":
                carLogID = R.mipmap.a155;
                break;
            default:
                carLogID = R.mipmap.car_logo_default;
                break;
        }

        return carLogID;
    }

}
