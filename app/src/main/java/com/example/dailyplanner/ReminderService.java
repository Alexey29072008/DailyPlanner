
package com.example.dailyplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class ReminderService {
    public static void setReminder(Context context, Event event) {
        if (event.reminderTime <= 0) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("event_title", event.title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, event.title.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar reminderTime = Calendar.getInstance();
        reminderTime.setTimeInMillis(event.startTime);
        reminderTime.add(Calendar.MINUTE, -event.reminderTime);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                reminderTime.getTimeInMillis(), pendingIntent);
    }
}
