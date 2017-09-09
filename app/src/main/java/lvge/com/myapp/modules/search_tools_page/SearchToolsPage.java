
package lvge.com.myapp.modules.search_tools_page;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lvge.com.myapp.R;
import lvge.com.myapp.util.L;

/**
 * Created by zhang on 2017/8/19.
 */

public class SearchToolsPage extends AppCompatActivity {

    private TextView cancel;

    private final static int RESULT_OK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tools_page);

        cancel = (TextView) findViewById(R.id.search_tools_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final EditText et_search = (EditText) findViewById(R.id.et_common_search);

        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    String query = et_search.getText().toString();
                    Intent intent = new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("SearchResult", query);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                return false;
            }
        });


        //final Intent intent = getIntent();
      /*  final AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.search_edit_frame);
        final SearchView search_view = (SearchView)findViewById(R.id.search_view_bar);
        search_view.setQueryHint("请输入车架号、用户名或设备号码搜索");

        SearchView.SearchAutoComplete  mEdit = (SearchView.SearchAutoComplete) search_view.findViewById(R.id.search_src_text);
        mEdit.setTextSize(12);

        Drawable drawable = getResources().getDrawable(R.drawable.edit_text_shape);
        mEdit.setBackground(drawable);
        search_view.setIconified(false);
        search_view.setIconifiedByDefault(true);
        search_view.setSubmitButtonEnabled(true);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                L.d(query);
                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("SearchResult", query);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
//                TabLayout.Tab tab = tabLayout.getTabAt(0);
//                if(tab != null){
//                    tab.select();
//                }
//                search(query,view,"1");
//                query = query;
//                search_show = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
    }
}
