package lvge.com.myapp.modules.ValidationHistory;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import lvge.com.myapp.R;

public class HistoryValidationSearchActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getFragmentManager();
    private CalendarFragment calendarFragment;
    private ImageView history_validation_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_validation_calendar_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_validation_history);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();

        ImageView history_validation_calendar = (ImageView) findViewById(R.id.history_validation_calendar);
        history_validation_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalenda();
            }
        });
        history_validation_search = (ImageView) findViewById(R.id.history_validation_search);
        history_validation_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryValidationSearchActivity.this, SearchValidationHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showCalenda() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (calendarFragment == null) {
            calendarFragment = new CalendarFragment();
        }
        transaction.replace(R.id.validation_history_search_layout_content, calendarFragment);
        transaction.commit();
    }

    private void init() {
        Intent intent = getIntent();
        String iscalendar = intent.getStringExtra("calendar");
        if (iscalendar.equals("true")) {
            showCalenda();
        }
    }
}
