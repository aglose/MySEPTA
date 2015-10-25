package team5.capstone.com.mysepta.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import team5.capstone.com.mysepta.R;

/**
 * Created by Kevin on 10/24/15.
 */
public class RailExpandableAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String,ArrayList<String>> railChoices;
    private ArrayList<String> rails;
    private ArrayList<String> tags;
    private LayoutInflater inflater;

    public RailExpandableAdapter(Activity context, ArrayList<String> rails, Map<String,ArrayList<String>> railChoices){
        this.context = context;
        this.rails = rails;
        this.railChoices = railChoices;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tags = new ArrayList<>(rails);
    }

    @Override
    public int getGroupCount() {
        return tags.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return railChoices.get(tags.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return rails.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return railChoices.get(tags.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateParent(int groupPosition,String newValue){
        rails.set(groupPosition,newValue);
    }
}
