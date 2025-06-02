package com.example.dailyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventsListActivity extends AppCompatActivity {

    private ListView listView;
    private TextView dateTitle;
    private EventAdapter adapter;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        listView = findViewById(R.id.eventListView);
        dateTitle = findViewById(R.id.dateTitle);

        long dateMillis = getIntent().getLongExtra("selectedDate", -1);
        if (dateMillis == -1) {
            finish();
            return;
        }

        selectedDate = Calendar.getInstance();
        selectedDate.setTimeInMillis(dateMillis);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        dateTitle.setText(sdf.format(selectedDate.getTime()));

        List<Event> allEvents = EventStorage.loadEvents(this);
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : allEvents) {
            Calendar eventDate = Calendar.getInstance();
            eventDate.setTimeInMillis(event.startTime);
            if (isSameDay(eventDate, selectedDate)) {
                filteredEvents.add(event);
            }
        }

        adapter = new EventAdapter(this, filteredEvents, new EventAdapter.EventActionListener() {
            @Override
            public void onEdit(Event event) {
                Intent intent = new Intent(EventsListActivity.this, CreateEventActivity.class);
                intent.putExtra("eventToEdit", event);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}