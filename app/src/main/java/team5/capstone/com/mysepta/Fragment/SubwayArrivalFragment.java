package team5.capstone.com.mysepta.Fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.SubwayScheduleViewAdapter;
import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;

/**
 * Created by Andrew on 12/5/2015.
 */
public class SubwayArrivalFragment extends Fragment{
    private static final String TAG = "SubwayArrivalFragment";

    private RecyclerView subwayArrivalRecyclerView;
    private View view;
    private SubwayScheduleViewAdapter subwayScheduleViewAdapter;
    private TextView directionText;
    private String direction;
    private ArrayList arrivalsList;
    private TextView directionPreText;

    /**
     * onCreate
     * @param savedInstanceState saved state on close
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        direction = getArguments() != null ? getArguments().getString(getString(R.string.SUBWAY_DIRECTION_KEY)) : "";
        String json = getArguments() != null ? getArguments().getString(getString(R.string.SUBWAY_ARRIVALS_LIST)) : "";
        Type type = new TypeToken<List<SubwayScheduleItemModel>>(){}.getType();
        arrivalsList = new Gson().fromJson(json, type);
        Log.d(TAG, direction);
    }

    /**
     * Create fragment view
     * @param inflater layout inflater
     * @param container parent container
     * @param savedInstanceState saved state on close
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subway_arrivals, container, false);
    }

    /**
     * initialize view.
     * @param view current fragment view
     * @param savedInstanceState saved state on close
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        directionText = (TextView) view.findViewById(R.id.direction);
        directionPreText = (TextView) view.findViewById(R.id.directionPreText);

        directionText.setText(direction+"BOUND");
        directionPreText.setText("Direction: ");
        subwayArrivalRecyclerView = (RecyclerView) view.findViewById(R.id.subwayScheduleView);
        subwayArrivalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        subwayScheduleViewAdapter = new SubwayScheduleViewAdapter(arrivalsList, getActivity());
        subwayArrivalRecyclerView.setAdapter(subwayScheduleViewAdapter);
    }

    /*Switch the list by its direction*/
    public void switchArrivalsList(ArrayList<SubwayScheduleItemModel> arrivalsList){
        subwayScheduleViewAdapter.clearData();
        subwayScheduleViewAdapter.setList(arrivalsList);
    }

    /*Animate flipping direciton for subway schedules*/
    public void changeDirectionText(final String text){
        ObjectAnimator animateDirection = ObjectAnimator.ofFloat(directionText, "rotationX", 0.0f, 360f);
        animateDirection.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                directionText.setText(text + "BOUND");
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animateDirection.setDuration(800);
        animateDirection.setInterpolator(new AccelerateDecelerateInterpolator());
        animateDirection.start();
    }
}
