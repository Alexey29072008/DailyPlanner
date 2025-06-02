package com.example.dailyplanner;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

public class DragDropCalendarView extends View {

    private Paint linePaint, textPaint, eventPaint, eventTextPaint;
    private float cellHeight;
    private int totalRows = 24 * 4;

    private ArrayList<Event> events = new ArrayList<>();
    private Event selectedEvent = null;
    private Calendar currentDate = Calendar.getInstance();

    public DragDropCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(2);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        eventPaint = new Paint();

        eventTextPaint = new Paint();
        eventTextPaint.setColor(Color.BLACK);
        eventTextPaint.setTextSize(24);
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cellHeight = getHeight() / (float) totalRows;

        for (int i = 0; i < totalRows; i++) {
            float y = i * cellHeight;
            canvas.drawLine(0, y, getWidth(), y, linePaint);
        }

        for (Event e : events) {
            if (isSameDay(e.startTime, currentDate)) {
                int startMin = getMinutesFromTime(e.startTime);
                float top = (startMin / 15f) * cellHeight;
                float bottom = top + (e.duration / 15f) * cellHeight;

                if (e.startTime > System.currentTimeMillis()) {
                    eventPaint.setColor(Color.argb(64, 204, 229, 255)); // #CCE5FF @ 25%
                } else {
                    eventPaint.setColor(Color.argb(64, 153, 255, 153)); // #99FF99 @ 25%
                }

                canvas.drawRect(0, top, getWidth(), bottom, eventPaint);
                canvas.drawText(e.title, 10, top + 30, eventTextPaint);
            }
        }
    }

    private int getMinutesFromTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
    }

    private boolean isSameDay(long timeMillis, Calendar calendar) {
        Calendar eventCal = Calendar.getInstance();
        eventCal.setTimeInMillis(timeMillis);
        return eventCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && eventCal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP && selectedEvent != null) {
            float y = event.getY();
            int newStartMin = (int) (y / cellHeight) * 15;

            Calendar newStart = (Calendar) currentDate.clone();
            newStart.set(Calendar.HOUR_OF_DAY, newStartMin / 60);
            newStart.set(Calendar.MINUTE, newStartMin % 60);
            selectedEvent.startTime = newStart.getTimeInMillis();

            EventStorage.saveEvents(getContext(), events);
            selectedEvent = null;
            invalidate();
        }
        return true;
    }

    public void startDragEvent(Event event) {
        selectedEvent = event;
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(this);
        startDragAndDrop(data, shadowBuilder, null, 0);
    }
}
