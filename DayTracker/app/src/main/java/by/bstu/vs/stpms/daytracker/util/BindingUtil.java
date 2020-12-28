package by.bstu.vs.stpms.daytracker.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import by.bstu.vs.stpms.daytracker.R;
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType;

public class BindingUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy");

    @BindingAdapter("android:text")
    public static void setCalendar(TextView textView, Calendar calendar) {
        textView.setText(formatter.format(calendar.getTime()));
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, BusinessType type){
        int imageID = 0;
        switch (type) {
            case CHILL:
                imageID = R.drawable.business_chill;
                break;
            case WORK:
                imageID = R.drawable.business_work;
                break;
            case STUDYING:
                imageID = R.drawable.business_studying;
                break;
            case ROAD:
                imageID = R.drawable.business_road;
                break;
            case LUNCH:
                imageID = R.drawable.business_lunch;
                break;
            case SLEEP:
                imageID = R.drawable.business_sleep;
                break;
        }

        imageView.setImageResource(imageID);
    }
}
