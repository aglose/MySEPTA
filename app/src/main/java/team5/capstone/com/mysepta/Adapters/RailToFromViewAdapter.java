package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.DropDownView.RailChooser;
import team5.capstone.com.mysepta.DropDownView.RailChooserChild;
import team5.capstone.com.mysepta.DropDownView.StationChooserChildViewHolder;
import team5.capstone.com.mysepta.DropDownView.StationChooserParentViewHolder;

/**
 * Created by Kevin on 9/23/15.
 */
public class RailToFromViewAdapter extends ExpandableRecyclerAdapter<StationChooserParentViewHolder,StationChooserChildViewHolder>{
    LayoutInflater mInflater;
    onChildClickedListener mListener;

    public RailToFromViewAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
        //mListener = (onChildClickedListener) context;
    }

    @Override
    public StationChooserParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.choose_station_parent, viewGroup, false);
        return new StationChooserParentViewHolder(view);
    }

    @Override
    public StationChooserChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.choose_station_child, viewGroup, false);
        return new StationChooserChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(StationChooserParentViewHolder stationChooserParentViewHolder, int i, Object parentObject) {
        RailChooser parent = (RailChooser) parentObject;
        stationChooserParentViewHolder.directionTextView.setText(parent.getTitle());
        stationChooserParentViewHolder.directionTextView.setTag(R.string.loc_tag,i);
        stationChooserParentViewHolder.directionTextView.setTag(R.string.name_tag,parent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(final StationChooserChildViewHolder stationChooserChildViewHolder,final int i, final Object childObject) {
        final RailChooserChild child = (RailChooserChild) childObject;
        stationChooserChildViewHolder.stationText.setText(child.getStationName());
        stationChooserChildViewHolder.stationText.setTag(R.string.loc_tag,i);
        stationChooserChildViewHolder.stationText.setTag(R.string.name_tag,child.getStationName());

        /*stationChooserChildViewHolder.stationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.childClicked((String) child.getStationName(), i);
            }
        });*/
    }

    public interface onChildClickedListener{
        public void childClicked(String childText,int position);
    }

}
