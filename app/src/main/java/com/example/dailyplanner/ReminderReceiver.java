
package com.example.dailyplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("EVENT_TITLE");
        Toast.makeText(context, "Напоминание: " + title, Toast.LENGTH_LONG).show();
    }
}
