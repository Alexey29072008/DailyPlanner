package com.example.dailyplanner;

import java.util.ArrayList;
import java.util.Iterator;

public class EventCleaner {

    public static void cleanOldEvents(ArrayList<Event> events) {
        long now = System.currentTimeMillis();
        long oneMonthAgo = now - (30L * 24 * 60 * 60 * 1000); // 30 дней назад в миллисекундах

        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if (e.startTime < oneMonthAgo) {
                iterator.remove();
            }
        }
    }
}
