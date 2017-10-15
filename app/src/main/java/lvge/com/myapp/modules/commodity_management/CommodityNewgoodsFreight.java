package lvge.com.myapp.modules.commodity_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;

public class CommodityNewgoodsFreight extends AppCompatActivity implements View.OnClickListener{

    private TextView commodity_newgoods_freight_finish;
    private EditText commodity_newgoods_freight_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_newgoods_freight);

        commodity_newgoods_freight_finish = (TextView)findViewById(R.id.commodity_newgoods_freight_finish);
        commodity_newgoods_freight_finish.setOnClickListener(this);
        commodity_newgoods_freight_edittext = (EditText)findViewById(R.id.commodity_newgoods_freight_edittext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commodity_newgoods_freight_finish:
                Intent intent = new Intent();
                intent.putExtra("inputfreight",commodity_newgoods_freight_edittext.getText());
                setResult(18,intent);
                finish();
                break;
            default:
                break;
        }
    }
}
