package lvge.com.myapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;



import lvge.com.myapp.R;

/**
 * Created by JGG on 2017/5/27.
 */

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> listItems;
    private LayoutInflater listContainer;

    public final class ListItemView {
        public ImageView menuLogo;
        public TextView title;
        public ImageView menu_go;
    }

    public MenuAdapter(Context context, List<Map<String, Object>> listItems) {

        this.context = context;
        this.listContainer = LayoutInflater.from(context);
        this.listItems = listItems;

    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int selectId = position;
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();
            convertView = listContainer.inflate(R.layout.layout_menu_list_item, null);

            listItemView.menuLogo = (ImageView) convertView.findViewById(R.id.menu_icon);
            listItemView.title = (TextView) convertView.findViewById(R.id.menu_text);
            listItemView.menu_go = (ImageView)convertView.findViewById(R.id.menu_go);

            convertView.setTag(listItemView);

        }
        else
        {
            listItemView = (ListItemView)convertView.getTag();
        }
        listItemView.menuLogo.setBackgroundResource((Integer) listItems.get(position).get("menuLogo"));
        listItemView.title.setText((String)listItems.get(position).get("title"));
        int exit = getCount();
        ImageView menu_go =  (ImageView)convertView.findViewById(R.id.menu_go);
        if (exit!=position+1)
        {
            menu_go.setImageResource(R.mipmap.menu_go);
        }
        return convertView;
    }
}
