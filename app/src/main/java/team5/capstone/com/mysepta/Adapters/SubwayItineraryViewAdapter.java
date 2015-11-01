package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import team5.capstone.com.mysepta.Fragment.SubwayItineraryViewFragment;
import team5.capstone.com.mysepta.MapsActivity;
import team5.capstone.com.mysepta.R;

/**
 * Adapter for the inital subway itinerary
 * Created by Andrew on 9/19/2015.
 */
public class SubwayItineraryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    Context context;
    SubwayItineraryViewFragment.SubwayChangeFragmentListener mSubwayFragmentListener;
    /**
     * Constructor
     * @param context activity
     */
    public SubwayItineraryViewAdapter(Context context, SubwayItineraryViewFragment.SubwayChangeFragmentListener mSubwayFragmentListener) {
        this.context = context;
        this.mSubwayFragmentListener = mSubwayFragmentListener;
    }

    /**
     * Return view type
     * @param position item position
     * @return TYPE_HEADER if position 0, otherwise TYPE_CELL
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    /**
     * Get item count
     * @return size of 2
     */
    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * Create view holder and set onclicklistener
     * @param parent parent viewgroup
     * @param viewType type of item view
     * @return view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.subway_itinerary_header, parent, false);
                CardView header = (CardView) view.findViewById(R.id.subwayItineraryHeaderCard);
                header.setCardBackgroundColor(ContextCompat.getColor(context, R.color.headerBlue));
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.subway_itinerary_card, parent, false);
                Button mflButton = (Button) view.findViewById(R.id.mflButton);
                mflButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSubwayFragmentListener.onItinerarySelection("MFL");
                    }
                });

                Button bslButton = (Button) view.findViewById(R.id.bslButton);
                bslButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                SubwayScheduleManager.createJOUSPObject(); THIS IS USED FOR TESTING
                        mSubwayFragmentListener.onItinerarySelection("BSL");
                    }
                });
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }


    /**
     * Initialize view holder
     * @param holder current view holder
     * @param position current item position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
        }
    }
}