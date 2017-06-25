package lvge.com.myapp.modules.shop_management;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lvge.com.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotAuthenticationFragment extends Fragment implements View.OnClickListener {

    private View view;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;

    public NotAuthenticationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_manage_not_authentication, container, false);
        ImageView img_business_licence = (ImageView) view.findViewById(R.id.img_business_licence);
        img_business_licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });

        ImageView img_identity_card_positive = (ImageView) view.findViewById(R.id.img_identity_card_positive);
        img_identity_card_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });

        ImageView img_identity_card_negative = (ImageView) view.findViewById(R.id.img_identity_card_negative);
        img_identity_card_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });

        return view;
    }

    public void show(View view) {
        dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_menu_shop_manage_img_dialog, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.from_phone_photo);
        takePhoto = (TextView) inflate.findViewById(R.id.cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from_phone_photo:
                Toast.makeText(getActivity(), "点击了从相册选择", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
        }
        dialog.dismiss();
    }
}
