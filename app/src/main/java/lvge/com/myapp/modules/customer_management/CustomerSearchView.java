package lvge.com.myapp.modules.customer_management;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import lvge.com.myapp.R;

public class CustomerSearchView extends LinearLayout implements View.OnClickListener {

    private EditText etInput;   //输入框
    private ImageView ivDelete;  //删除键

    private Button btnBack;   //返回建

    private Context mContext;

    private ListView lvTips;

    private ArrayAdapter<String>  mHintAdapter;

    private ArrayAdapter<String> mAutoCompleteAdapter;

    private SearchViewListener mListener;

    public interface SearchViewListener{
        void onRefreshAutoComplete(String text);
        void onSearch(String text);
    }

    public void setSearchViewListener(SearchViewListener listener){
        mListener = listener;
    }

    public CustomerSearchView(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_customer_search_view,this);

        initViews();
    }

    private void initViews(){

        etInput = (EditText)findViewById(R.id.customer_search_input);
        ivDelete = (ImageView)findViewById(R.id.search_custmer);
        btnBack = (Button)findViewById(R.id.search_customer_button);
        lvTips = (ListView)findViewById(R.id.search_custmer_listview);

        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = lvTips.getAdapter().getItem(position).toString();
                etInput.setText(text);
                etInput.setSelection(text.length());

                lvTips.setVisibility(View.GONE);
                notifyStartSearching(text);
            }
        });

        ivDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        etInput.addTextChangedListener(new EditChangedListener());
        etInput.setOnClickListener(this);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    lvTips.setVisibility(GONE);
                    notifyStartSearching(etInput.getText().toString());
                }
                return true;
            }
        });
    }

    //通知监听者，进行搜索操作
    private void notifyStartSearching(String text){
        if(mListener != null){
            mListener.onSearch(etInput.getText().toString());
        }

        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void setTipsHintAdapter(ArrayAdapter<String> adapter){
        this.mHintAdapter = adapter;

        if(lvTips.getAdapter() == null){
            lvTips.setAdapter(mHintAdapter);
        }
    }

    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter){
        this.mAutoCompleteAdapter = adapter;
    }

    private class EditChangedListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!"".equals(s.toString())){
                ivDelete.setVisibility(VISIBLE);
                lvTips.setVisibility(VISIBLE);

                if(mAutoCompleteAdapter != null && lvTips.getAdapter()!= mAutoCompleteAdapter){
                    lvTips.setAdapter(mAutoCompleteAdapter);
                }

                if(mListener!=null){
                    mListener.onRefreshAutoComplete(s + "");
                }else {
                    ivDelete.setVisibility(GONE);
                    if(mHintAdapter != null){
                        lvTips.setAdapter(mHintAdapter);
                    }
                    lvTips.setVisibility(GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.customer_search_input:
                lvTips.setVisibility(VISIBLE);
                break;
            case R.id.search_custmer:
                ivDelete.setVisibility(GONE);
                break;
            case R.id.search_customer_button:
                ((Activity)mContext).finish();
                break;
        }
    }


}
