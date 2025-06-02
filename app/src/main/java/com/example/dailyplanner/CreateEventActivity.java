package com.example.dailyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEventActivity extends AppCompatActivity {

    private EditText titleInput, durationInput, reminderInput;
    private Button saveButton, dateButton, timeButton;
    private Calendar selectedDateTime;
    private Event editingEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        titleInput = findViewById(R.id.eventTitleInput);
        durationInput = findViewById(R.id.durationInput);
        reminderInput = findViewById(R.id.reminderInput);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        saveButton = findViewById(R.id.saveEventButton);


        selectedDateTime = Calendar.getInstance();

        Intent intent = getIntent();
        if (intent.hasExtra("event")) {
            editingEvent = (Event) intent.getSerializableExtra("event");
            if (editingEvent != null) {
                titleInput.setText(editingEvent.title);
                durationInput.setText(String.valueOf(editingEvent.duration));
                reminderInput.setText(String.valueOf(editingEvent.reminderTime));
                selectedDateTime.setTimeInMillis(editingEvent.startTime);
                updateDateButton();
                updateTimeButton();
            }
        }

        dateButton.setOnClickListener(v -> {
            new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        selectedDateTime.set(Calendar.YEAR, year);
                        selectedDateTime.set(Calendar.MONTH, month);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateButton();
                    },
                    selectedDateTime.get(Calendar.YEAR),
                    selectedDateTime.get(Calendar.MONTH),
                    selectedDateTime.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        timeButton.setOnClickListener(v -> {
            new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        updateTimeButton();
                    },
                    selectedDateTime.get(Calendar.HOUR_OF_DAY),
                    selectedDateTime.get(Calendar.MINUTE),
                    true
            ).show();
        });

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            int duration = Integer.parseInt(durationInput.getText().toString());
            int reminder = Integer.parseInt(reminderInput.getText().toString());

            Event event = new Event(title, selectedDateTime.getTimeInMillis(), duration, reminder);

            List<Event> allEvents = EventStorage.loadEvents(this);
            if (editingEvent != null) {
                allEvents.removeIf(e -> e.startTime == editingEvent.startTime && e.title.equals(editingEvent.title));
            }
            allEvents.add(event);
            EventStorage.saveEvents(this, new ArrayList<>(allEvents));
            finish();
        });
    }

    private void updateDateButton() {
        int year = selectedDateTime.get(Calendar.YEAR);
        int month = selectedDateTime.get(Calendar.MONTH) + 1;
        int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);
        dateButton.setText(String.format("%02d.%02d.%d", day, month, year));
    }

    private void updateTimeButton() {
        int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDateTime.get(Calendar.MINUTE);
        timeButton.setText(String.format("%02d:%02d", hour, minute));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}