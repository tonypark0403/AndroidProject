package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ca.seneca.map524.smartsecretary.R;

/**
 * Created by Wonho on 11/5/2016.
 */
public class TimetableFragment extends Fragment implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {

    private final static String TAG = "TimetableFragment";

    // compile 'com.github.alamkanak:android-week-view:1.2.6'
    private WeekView mWeekView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView)rootView.findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);

        // set the week mode, and start time 8:00am
        mWeekView.setNumberOfVisibleDays(7);
        mWeekView.goToHour(8);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));

        return rootView;
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate) {
                    weekday = String.valueOf(weekday.charAt(0));
                }
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour == 12 ? hour + " PM" : (hour - 12) + " PM") : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    protected String getEventTitle(String code, Calendar startTime, Calendar endTime, String room) {
        return String.format("%s\n%02d:%02d~%02d:%02d\n%s",
                code, startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE),
                endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE), room);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    // text code
    public class Subject {
        String code;
        String room;
        int day;
        int startHour;
        int startMin;
        int endHour;
        int endMin;
        int color;
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // set monday of current week
        Calendar monday = Calendar.getInstance();
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mWeekView.goToDate(monday);

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        // test code, will be changed using database
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        Subject subject = new Subject();
        subject.code = "MAP524";
        subject.room = "S2169";
        subject.day = Calendar.TUESDAY;
        subject.startHour = 9;
        subject.startMin = 50;
        subject.endHour = 11;
        subject.endMin = 35;
        subject.color = R.color.event_color_01;
        subjects.add(subject);

        subject = new Subject();        
        subject.code = "MAP524";
        subject.room = "T3074";
        subject.day = Calendar.THURSDAY;
        subject.startHour = 15;
        subject.startMin = 20;
        subject.endHour = 17;
        subject.endMin = 5;
        subject.color = R.color.event_color_01;
        subjects.add(subject);

        subject = new Subject();
        subject.code = "SYS466";
        subject.room = "S2174";
        subject.day = Calendar.MONDAY;
        subject.startHour = 11;
        subject.startMin = 40;
        subject.endHour = 13;
        subject.endMin = 25;
        subject.color = R.color.event_color_02;
        subjects.add(subject);

        subject = new Subject();
        subject.code = "SYS466";
        subject.room = "T3076";
        subject.day = Calendar.WEDNESDAY;
        subject.startHour = 11;
        subject.startMin = 40;
        subject.endHour = 13;
        subject.endMin = 25;
        subject.color = R.color.event_color_02;
        subjects.add(subject);

        subject = new Subject();
        subject.code = "INT422";
        subject.room = "S2123";
        subject.day = Calendar.TUESDAY;
        subject.startHour = 13;
        subject.startMin = 30;
        subject.endHour = 15;
        subject.endMin = 15;
        subject.color = R.color.event_color_04;
        subjects.add(subject);

        subject = new Subject();
        subject.code = "INT422";
        subject.room = "T3073";
        subject.day = Calendar.THURSDAY;
        subject.startHour = 13;
        subject.startMin = 30;
        subject.endHour = 15;
        subject.endMin = 15;
        subject.color = R.color.event_color_04;
        subjects.add(subject);

        subject = new Subject();
        subject.code = "GAM537";
        subject.room = "T2108";
        subject.day = Calendar.MONDAY;
        subject.startHour = 15;
        subject.startMin = 20;
        subject.endHour = 17;
        subject.endMin = 5;
        subject.color = R.color.event_color_03;
        subjects.add(subject);

        subject = new Subject();
        subject.code = "GAM537";
        subject.room = "T2108";
        subject.day = Calendar.TUESDAY;
        subject.startHour = 15;
        subject.startMin = 20;
        subject.endHour = 17;
        subject.endMin = 5;
        subject.color = R.color.event_color_03;
        subjects.add(subject);
        
        

        // add classes
        Calendar startTime;
        Calendar endTime;
        WeekViewEvent event;
        for (int i = 0; i < subjects.size(); i++) {
            startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_WEEK, subjects.get(i).day);
            startTime.set(Calendar.HOUR_OF_DAY, subjects.get(i).startHour);
            startTime.set(Calendar.MINUTE, subjects.get(i).startMin);
            startTime.set(Calendar.MONTH, newMonth - 1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, subjects.get(i).endHour);
            endTime.set(Calendar.MINUTE, subjects.get(i).endMin);
            endTime.set(Calendar.MONTH, newMonth - 1);
            event = new WeekViewEvent(0, getEventTitle(subjects.get(i).code, startTime, endTime, subjects.get(i).room), startTime, endTime);
            event.setColor(getResources().getColor(subjects.get(i).color));
            events.add(event);
        }

        return events;
    }
}
