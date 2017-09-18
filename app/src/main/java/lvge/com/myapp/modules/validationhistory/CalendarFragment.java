package lvge.com.myapp.modules.validationhistory;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import lvge.com.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    private View view;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(view);
        return view;

    }

    private void init(View view) {
        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //如果是用的v4的包，则用getActivity().getSuppoutFragmentManager();
                FragmentManager fm = getActivity().getFragmentManager();
                //注意v4包的配套使用
                CalendarSearchFragment fragment = new CalendarSearchFragment();
                String year = date.getYear()+"";
                String month = (date.getMonth()+1)<=9?"0"+(date.getMonth()+1):(date.getMonth()+1)+"";
                String day = date.getDay()<=9?"0"+date.getDay():date.getDay()+"";
                String selectDate = year+"-"+month+"-"+day;
                fragment.setDate(selectDate);
                fragment.setYear(year);
                fragment.setMonth(month);
                fragment.setDay(day);
                fm.beginTransaction().replace(R.id.validation_history_search_layout_content,fragment).commit();
            }
        });
    }

}
