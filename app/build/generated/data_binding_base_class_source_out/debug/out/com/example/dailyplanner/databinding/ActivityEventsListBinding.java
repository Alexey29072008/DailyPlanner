// Generated by view binder compiler. Do not edit!
package com.example.dailyplanner.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dailyplanner.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEventsListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView dateTitle;

  @NonNull
  public final ListView eventListView;

  private ActivityEventsListBinding(@NonNull LinearLayout rootView, @NonNull TextView dateTitle,
      @NonNull ListView eventListView) {
    this.rootView = rootView;
    this.dateTitle = dateTitle;
    this.eventListView = eventListView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEventsListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEventsListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_events_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEventsListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dateTitle;
      TextView dateTitle = ViewBindings.findChildViewById(rootView, id);
      if (dateTitle == null) {
        break missingId;
      }

      id = R.id.eventListView;
      ListView eventListView = ViewBindings.findChildViewById(rootView, id);
      if (eventListView == null) {
        break missingId;
      }

      return new ActivityEventsListBinding((LinearLayout) rootView, dateTitle, eventListView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
