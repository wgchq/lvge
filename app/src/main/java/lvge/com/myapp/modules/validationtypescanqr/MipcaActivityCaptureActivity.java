package lvge.com.myapp.modules.validationtypescanqr;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import lvge.com.myapp.MainPageActivity;
import lvge.com.myapp.R;

public class MipcaActivityCaptureActivity extends Activity implements DecoratedBarcodeView.TorchListener {
    private CaptureManager captureManager;
    private DecoratedBarcodeView mDBV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mipca_capture);

        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
        mDBV.setTorchListener(this);

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();

        LinearLayout lly_manual_input_qr_code = (LinearLayout) findViewById(R.id.lly_manual_input_qr_code);
        if (lly_manual_input_qr_code != null) {
            lly_manual_input_qr_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  MipcaActivityCaptureActivity.this.finish();
                    Intent intent = new Intent(MipcaActivityCaptureActivity.this, MainPageActivity.class);
                    setResult(2,intent);

                    finish();
                    // startActivity(intent);
                }
            });
        }

        TextView backBut = (TextView) findViewById(R.id.main_person_id);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MipcaActivityCaptureActivity.this.finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }


}
