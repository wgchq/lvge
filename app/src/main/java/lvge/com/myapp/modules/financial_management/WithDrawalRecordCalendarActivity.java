package lvge.com.myapp.modules.financial_management;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;

import lvge.com.myapp.R;

public class WithDrawalRecordCalendarActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_drawal_record_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.financial_management_with_drawal_record_calendar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mContext = this;
        init();
    }

    private void init() {
        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        Date now = new Date();

        calendarView.setSelectedDate(now);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();

                String year = date.getYear() + "";
                String month = (date.getMonth() + 1) <= 9 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1) + "";
                String day = date.getDay() <= 9 ? "0" + date.getDay() : date.getDay() + "";
                String selectDate = year + "-" + month + "-" + day;

                Intent intent = new Intent(mContext, WithDrawalRecordActivity.class);
                setResult(0, intent);
                finish();
            }
        });
    }

}
