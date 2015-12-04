package team5.capstone.com.mysepta.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import team5.capstone.com.mysepta.R;

/**
 * Creates adapter to create functionality of RailExpandableView.
 * Created by Kevin on 10/24/15.
 */
public class RailExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String,ArrayList<String>> railChoices;
    private ArrayList<String> rails;
    private ArrayList<String> tags;
    private LayoutInflater inflater;

    /**
     * Constructor
     * @param context activity
     * @param rails List of parent values
     * @param railChoices Map with parent value keys to List of children
     */
    public RailExpandableAdapter(Context context, ArrayList<String> rails, Map<String,ArrayList<String>> railChoices){
        this.context = context;
        this.rails = rails;
        this.railChoices = railChoices;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(this.railChoices == null){
            this.railChoices = new HashMap<>();
        }
        if(this.rails == null){
            this.rails = new ArrayList<>();
        }
        this.tags = new ArrayList<>(rails);
    }

    /**
     * Retrieve count of parents.
     * @return Number of parents
     */
    @Override
    public int getGroupCount() {
        return tags.size();
    }

    /**
     * Return children count under parent at groupPosition.
     * @param groupPosition parent position
     * @return child count under parent
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return railChoices.get(tags.get(groupPosition)).size();
    }

    /**
     * Retrieves parent value (is String but returns object).
     * @param groupPosition parent position
     * @return Object parent value (is String)
     */
    @Override
    public Object getGroup(int groupPosition) {
        return rails.get(groupPosition);
    }

    /**
     * Retrieves child value (is String but returns object).
     * @param groupPosition parent location
     * @param childPosition child location
     * @return Object child value (is String)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return railChoices.get(tags.get(groupPosition)).get(childPosition);
    }

    /**
     * Return parent id.
     * @param groupPosition parent location
     * @return parentID = parent location
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns child id.
     * @param groupPosition parent position
     * @param childPosition child position
     * @return childID = child position
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Check if ids are stable
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Initialize parent view.
     * @param groupPosition parent view position
     * @param isExpanded true if parent expanded, else false
     * @param convertView parent view
     * @param parent group parent is a part of
     * @return initialized parent view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String railDirection = (String) getGroup(groupPosition);
        if(convertView == null){

            convertView = inflater.inflate(R.layout.choose_station_parent2,null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.parent_list_item_rail_title_text_view2);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(railDirection);
        return convertView;
    }

    /**
     * Initialize child view at childPosition under groupPosition.
     * @param groupPosition parent position
     * @param childPosition child position
     * @param isLastChild is last child in parent
     * @param convertView child view
     * @param parent parent viewgroup
     * @return initialized child view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String stopName = (String) getChild(groupPosition, childPosition);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.choose_station_child,null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.childTextView);
        item.setText(stopName);

        return convertView;
    }

    /**
     * Checks if child can be selected.
     * @param groupPosition parent position
     * @param childPosition child position
     * @return true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateParent(int groupPosition,String newValue){
        try{
            rails.get(groupPosition);
            rails.set(groupPosition,newValue);
        }catch (ArrayIndexOutOfBoundsException e){
            Log.d("Invalid groupPosition: ",e.toString());
        }
    }
}
