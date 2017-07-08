package lvge.com.myapp.modules.my_4s_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import lvge.com.myapp.R;

public class SalesConsultant extends AppCompatActivity {

    private SalesConsultantListview customListView;

    private SalesConsutantListViewAdapter mAdapter;

    private List<SalesConsutantListViewData> contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_consultant);


        customListView = (SalesConsultantListview)findViewById(R.id.sales_consultant_listview);
        customListView.setOnDeleteListener(new SalesConsultantListview.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                contentList.remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {    //点击listView事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        contentList = getListItem();
        mAdapter = new SalesConsutantListViewAdapter(this,contentList);
        customListView.setAdapter(mAdapter);
    }

    private List<SalesConsutantListViewData> getListItem(){

        return null;
    }

}
