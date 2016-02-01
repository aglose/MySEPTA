package team5.capstone.com.mysepta.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

import team5.capstone.com.mysepta.Activities.SubwayActivity;

/**
 * Create dialog to allow date to be chosen.
 * Created by Andrew on 10/18/2015.
 */
public class SubwayTimePickerFragment extends DialogFragment{
    /**
     * Constructor
     * @param savedInstanceState saved state on close
     * @return Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), (SubwayActivity)getActivity(), hour, minute, false);
    }
}
