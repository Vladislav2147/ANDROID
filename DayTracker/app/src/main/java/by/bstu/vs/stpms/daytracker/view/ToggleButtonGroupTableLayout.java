package by.bstu.vs.stpms.daytracker.view;
/**
 *
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * @author diego
 *
 */
public class ToggleButtonGroupTableLayout extends TableLayout  implements OnClickListener {

    private static final String TAG = "ToggleButtonGroupTableLayout";
    private RadioButton activeRadioButton;

    /**
     * @param context
     */
    public ToggleButtonGroupTableLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public ToggleButtonGroupTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onClick(View v) {
        final RadioButton rb = (RadioButton) v;
        if ( activeRadioButton != null ) {
            activeRadioButton.setChecked(false);
        }
        rb.setChecked(true);
        activeRadioButton = rb;
    }

    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setChildrenOnClickListener((TableRow)child);
    }


    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        setChildrenOnClickListener((TableRow)child);
    }


    private void setChildrenOnClickListener(TableRow tr) {
        final int c = tr.getChildCount();
        for (int i=0; i < c; i++) {
            final View v = tr.getChildAt(i);
            if (v instanceof RadioButton) {
                v.setOnClickListener(this);
                if (((RadioButton) v).isChecked())
                    activeRadioButton = (RadioButton) v;
            }
        }

    }

    public int getCheckedRadioButtonId() {
        if ( activeRadioButton != null ) {
            return activeRadioButton.getId();
        }

        return -1;
    }
}