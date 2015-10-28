package team5.capstone.com.mysepta.DropDownView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import team5.capstone.com.mysepta.R;

/**
 * Create parent view holder for expandable list
 * Created by Kevin on 9/23/15.
 */
public class StationChooserParentViewHolder extends ParentViewHolder{

        public TextView directionTextView;
        public ImageButton mParentDropDownArrow;

    /**
     * Constructor
     * @param itemView current item view
     */
        public StationChooserParentViewHolder(View itemView) {
            super(itemView);

            directionTextView = (TextView) itemView.findViewById(R.id.parent_list_item_rail_title_text_view);
            mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }
}
