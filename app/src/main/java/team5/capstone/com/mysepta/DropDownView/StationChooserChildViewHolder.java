package team5.capstone.com.mysepta.DropDownView;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import team5.capstone.com.mysepta.R;

/**
 * Created by Kevin on 9/23/15.
 */
public class StationChooserChildViewHolder extends ChildViewHolder {

    public TextView stationText;

    public StationChooserChildViewHolder(View itemView) {
        super(itemView);

        stationText = (TextView) itemView.findViewById(R.id.childTextView);
    }
}
