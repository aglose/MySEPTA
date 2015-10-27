package team5.capstone.com.mysepta.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.RailToFromViewAdapter;
import team5.capstone.com.mysepta.DropDownView.RailChooser;
import team5.capstone.com.mysepta.DropDownView.RailChooserChild;
import team5.capstone.com.mysepta.R;


/**
 * This fragment is currently not in use.  This fragment generates an expandable list view and
 * populates it.  It then waits for the view to be clicked and generates the clicked values.
 */
public class ToFromFragment extends Fragment implements RailToFromViewAdapter.onChildClickedListener{

    private OnFragmentInteractionListener mListener;
    private RecyclerView toFromRec;
    private RailToFromViewAdapter fromRailChoices;
    private View view;
    private String startStation,endStation;
    private int size;

    /**
     * Empty constructor.
     */
    public ToFromFragment() {
        // Required empty public constructor
    }


    /**
     * Create initial expandable list view.  Initialize and get layout items. Initialize expandable
     * adapter.  Set listener for children.
     * @param inflater inflator for activity this belongs to
     * @param container group this belongs to
     * @param savedInstanceState saved values for orientation change
     * @return ToFromFragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_to_from, container, false);

        startStation = endStation = null;

        toFromRec = (RecyclerView) view.findViewById(R.id.fromRail);
        toFromRec.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fromRailChoices = new RailToFromViewAdapter(view.getContext(),getRails());
        fromRailChoices.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        fromRailChoices.setParentClickableViewAnimationDefaultDuration();
        fromRailChoices.setParentAndIconExpandOnClick(true);

        toFromRec.setAdapter(fromRailChoices);
        size = toFromRec.getChildCount();

        toFromRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loc = (int) v.getTag(R.string.loc_tag);
                String name = (String) v.getTag(R.string.name_tag);

                if(size/2 > loc){
                    startStation = name;
                    Log.d("Start Rail",startStation);
                }
                else{
                    endStation = name;
                    Log.d("End Rail",endStation);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Initialize fragment listener and
     * @param activity activity to which this fragment belongs.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Detach fragment.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Update child text.
     * @param childText new text
     * @param position child position
     */
    @Override
    public void childClicked(String childText, int position) {
        int temp = toFromRec.getChildCount();
        if(temp/2 > position) {
            startStation = childText;
            Log.d("Start Rail",startStation);
        }
        else {
            endStation = childText;
            Log.d("End Rail",endStation);
        }

    }

    /**
     * No current function.  Would be used to setup interaction with this fragment.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * Generate list of valid rails
     * @return ArrayList of parent objects and their children.
     */
    public ArrayList<ParentObject> getRails(){
        ArrayList<ParentObject> rails = new ArrayList<>();

        RailChooser from = new RailChooser(view.getResources().getString(R.string.from_rail_text));
        RailChooser to = new RailChooser(view.getResources().getString(R.string.to_rail_text));

        String[] stationNames = view.getResources().getStringArray(R.array.station_names);

        ArrayList<Object> subRails = new ArrayList<>();

        for(String name:stationNames){
            subRails.add(new RailChooserChild(name));
        }
        //subRails.add(new RailChooserChild("30th Street"));
        //subRails.add(new RailChooserChild("Temple University"));

        from.setChildObjectList(subRails);
        to.setChildObjectList(subRails);
        rails.add(from);
        rails.add(to);

        return rails;
    }

}
