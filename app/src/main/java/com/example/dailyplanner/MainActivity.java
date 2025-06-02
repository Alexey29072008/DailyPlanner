package com.example.dailyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Button addEventButton;
    private Button openEventsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        addEventButton = findViewById(R.id.addEventButton);
        openEventsButton = findViewById(R.id.openEventsButton);

        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivity(intent);
        });

        openEventsButton.setOnClickListener(v -> {
            CalendarDay selectedDay = calendarView.getSelectedDate();
            if (selectedDay != null) {
                long selectedMillis = selectedDay.getDate().getTime();
                Intent intent = new Intent(MainActivity.this, EventsListActivity.class);
                intent.putExtra("selectedDate", selectedMillis);
                startActivity(intent);
            }
        });

        highlightEventDates();
    }

    private void highlightEventDates() {
        ArrayList<Event> events = EventStorage.loadEvents(this);
        HashSet<CalendarDay> daysWithEvents = new HashSet<>();
        for (Event e : events) {
            daysWithEvents.add(CalendarDay.from(new Date(e.startTime)));
        }
        calendarView.addDecorator(new EventDayDecorator(daysWithEvents));
    }
}