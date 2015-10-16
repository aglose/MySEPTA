package team5.capstone.com.mysepta.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToFromFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ToFromFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView toFromRec;
    private RailToFromViewAdapter fromRailChoices;
    private View view;

    public ToFromFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_to_from, container, false);
        toFromRec = (RecyclerView) view.findViewById(R.id.fromRail);
        toFromRec.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fromRailChoices = new RailToFromViewAdapter(view.getContext(),getRails());
        fromRailChoices.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        fromRailChoices.setParentClickableViewAnimationDefaultDuration();
        fromRailChoices.setParentAndIconExpandOnClick(true);

        toFromRec.setAdapter(fromRailChoices);

        // Inflate the layout for this fragment
        return view;
    }


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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public ArrayList<ParentObject> getRails(){
        ArrayList<ParentObject> rails = new ArrayList<>();

        RailChooser from = new RailChooser(view.getResources().getString(R.string.from_rail_text));
        RailChooser to = new RailChooser(view.getResources().getString(R.string.to_rail_text));

        ArrayList<Object> subRails = new ArrayList<>();
        subRails.add(new RailChooserChild("30th Street"));
        subRails.add(new RailChooserChild("Temple University"));
        from.setChildObjectList(subRails);
        to.setChildObjectList(subRails);
        rails.add(from);
        rails.add(to);

        return rails;
    }

}
