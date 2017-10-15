package lvge.com.myapp.modules.commodity_management;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;

public class CommodityCommonProblem extends AppCompatActivity {

    private ListView commodity_common_problem;
    private TextView commodity_common_add;
    private CommodityCommonProblemAdapter mAdapter;
    private List<ItemBean> mData;
    private int expandPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_common_problem);

        commodity_common_problem = (ListView)findViewById(R.id.commodity_common_problem);
        commodity_common_add = (TextView)findViewById(R.id.commodity_common_add);
        mData = new ArrayList<ItemBean>();
        mAdapter = new CommodityCommonProblemAdapter(this,mData);
        commodity_common_problem.setAdapter(mAdapter);

        commodity_common_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.add(new ItemBean());
                mAdapter.notifyDataSetChanged();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.commodity_common_problem_toobar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ArrayList<String> quesetion = new ArrayList<String>();
                ArrayList<String> answer = new ArrayList<String>();
                for (int i=0;i<mData.size();i++){
                    quesetion.add(mData.get(i).getQuestion());
                    answer.add(mData.get(i).getAnswer());
                }
                intent.putStringArrayListExtra("question",quesetion);
                intent.putStringArrayListExtra("answer",answer);
                setResult(15,intent);
                finish();
            }
        });
    }

    private class CommodityCommonProblemAdapter extends BaseAdapter{

        private Context context;
        private LayoutInflater inflater;
        public List<ItemBean> itemBeanList;

        public CommodityCommonProblemAdapter(Context context,List<ItemBean> mData){
            super();
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.itemBeanList = mData;
        }

        @Override
        public int getCount() {
            return itemBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class onImageViewClickListener implements View.OnClickListener{

            private int position;
            public onImageViewClickListener(int position){
                super();
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                if(expandPosition == position){
                    expandPosition = -1;
                }else {
                    expandPosition = position;
                }
                itemBeanList.remove(position);
                notifyDataSetChanged();
            }
        }

        private class ViewHolder{
            private TextView commodity_newgoods_textview2;
            private TextView commodity_newgoods_textview3;
            private EditText commodity_newgoods_commoditySellingprice;
            private EditText commodity_newgoods_commodityquestion;
            private ImageView commodity_common_delete;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.commodity_common_problem_item,null);
                holder = new ViewHolder();

                holder.commodity_newgoods_textview2 = (TextView)convertView.findViewById(R.id.commodity_newgoods_textview2);
                holder.commodity_newgoods_textview3 = (TextView)convertView.findViewById(R.id.commodity_newgoods_textview3);
                holder.commodity_newgoods_commoditySellingprice = (EditText) convertView.findViewById(R.id.commodity_newgoods_commoditySellingprice);
                holder.commodity_newgoods_commodityquestion = (EditText)convertView.findViewById(R.id.commodity_newgoods_commodityquestion);
                holder.commodity_common_delete = (ImageView)convertView.findViewById(R.id.commodity_common_delete);
                holder.commodity_common_delete.setOnClickListener(new onImageViewClickListener(position));
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            final ItemBean itemBean = itemBeanList.get(position);
            if(holder.commodity_newgoods_commoditySellingprice.getTag() instanceof TextWatcher){
                holder.commodity_newgoods_commoditySellingprice.removeTextChangedListener((TextWatcher)holder.commodity_newgoods_commoditySellingprice.getTag());
            }
            if(holder.commodity_newgoods_commodityquestion.getTag() instanceof TextWatcher){
                holder.commodity_newgoods_commodityquestion.removeTextChangedListener((TextWatcher)holder.commodity_newgoods_commodityquestion.getTag());
            }

            holder.commodity_newgoods_commoditySellingprice.setText(itemBean.getQuestion());
            holder.commodity_newgoods_commodityquestion.setText(itemBean.getAnswer());

            holder.commodity_newgoods_textview2.setText(String.valueOf(position + 1));

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(TextUtils.isEmpty(s)){
                        itemBean.setQuestion("");
                    }else {
                        itemBean.setQuestion(s.toString());
                    }
                }
            };

            TextWatcher watcher1 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(TextUtils.isEmpty(s)){
                        itemBean.setAnswer("");
                    }else {
                        itemBean.setAnswer(s.toString());
                    }
                }
            };
            holder.commodity_newgoods_commodityquestion.addTextChangedListener(watcher);
            holder.commodity_newgoods_commodityquestion.setTag(watcher);
            holder.commodity_newgoods_commoditySellingprice.addTextChangedListener(watcher1);
            holder.commodity_newgoods_commoditySellingprice.setTag(watcher1);

            return convertView;
        }
    }

    public class ItemBean{
        private String question;
        private String answer;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
