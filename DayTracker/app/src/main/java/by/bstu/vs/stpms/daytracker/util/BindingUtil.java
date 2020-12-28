package by.bstu.vs.stpms.daytracker.util;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BindingUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    @BindingAdapter("android:text")
    public static void setCalendar(TextView textView, Calendar calendar) {
        textView.setText(formatter.format(calendar.getTime()));
    }
}
