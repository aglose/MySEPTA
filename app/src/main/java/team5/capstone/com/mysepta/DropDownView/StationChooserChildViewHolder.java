package team5.capstone.com.mysepta.DropDownView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import team5.capstone.com.mysepta.R;

/**
 * Create holder for child view of expandable list
 * Created by Kevin on 9/23/15.
 */
public class StationChooserChildViewHolder extends ChildViewHolder {

    public TextView stationText;

    /**
     * Constructor
     * @param itemView current item view
     */
    public StationChooserChildViewHolder(View itemView) {
        super(itemView);

        stationText = (TextView) itemView.findViewById(R.id.childTextView);
    }
}
