
package com.example.dailyplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends BaseAdapter {

    private final Context context;
    private final List<Event> events;
    private final EventActionListener listener;

    public EventAdapter(Context context, List<Event> events, EventActionListener listener) {
        this.context = context;
        this.events = events;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        }

        TextView titleText = convertView.findViewById(R.id.eventTitle);
        Event event = events.get(position);
        titleText.setText(event.title);

        convertView.setOnLongClickListener(v -> {
            v.setOnCreateContextMenuListener((menu, v1, menuInfo) -> {
                menu.setHeaderTitle("Выберите действие");
                menu.add("Изменить").setOnMenuItemClickListener(item -> {
                    listener.onEdit(event);
                    return true;
                });
                menu.add("Удалить").setOnMenuItemClickListener(item -> {
                    new AlertDialog.Builder(context)
                            .setTitle("Удалить событие")
                            .setMessage("Вы уверены, что хотите удалить событие \"" + event.title + "\"?")
                            .setPositiveButton("Удалить", (dialog, which) -> {
                                events.remove(position);
                                EventStorage.saveEvents(context, new ArrayList<>(events));
                                notifyDataSetChanged();
                            })
                            .setNegativeButton("Отмена", null)
                            .show();
                    return true;
                });

            });
            return false;
        });

        return convertView;
    }

    public interface EventActionListener {
        void onEdit(Event event);
    }
}
