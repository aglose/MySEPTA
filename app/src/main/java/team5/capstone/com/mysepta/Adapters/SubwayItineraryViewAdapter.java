package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team5.capstone.com.mysepta.Fragment.SubwayItineraryViewFragment;
import team5.capstone.com.mysepta.Managers.SubwayScheduleManager;
import team5.capstone.com.mysepta.R;

/**
 * Adapter for subway section of home rail screen. Dictates how subway itinerary fragment acts.
 * Created by Andrew on 9/20/2015.
 */
public class SubwayItineraryViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    SubwayItineraryViewFragment.SubwayChangeFragmentListener mSubwayFragmentListener;

    /**
     * Constructor
     * @param context activity
     * @param mSubwayFragmentListener listener for button clicks
     */
    public SubwayItineraryViewAdapter(Context context, SubwayItineraryViewFragment.SubwayChangeFragmentListener mSubwayFragmentListener) {
        this.context = context;
        this.mSubwayFragmentListener = mSubwayFragmentListener;
    }

    /**
     * Create recycler view holder for subway fragment. Set button click listeners.
     * @param parent parent viewgroup
     * @param viewType type of view
     * @return recycler view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subway_itinerary_card, parent, false);

        Button mflButton = (Button) view.findViewById(R.id.mflButton);
        mflButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View namebar = view.findViewById(R.id.subway_itinerary_card);
                ((ViewGroup) namebar.getParent()).removeView(namebar);
                mSubwayFragmentListener.onItinerarySelection("MFL");
            }
        });

        Button bslButton = (Button) view.findViewById(R.id.bslButton);
        bslButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View namebar = view.findViewById(R.id.subway_itinerary_card);
//                SubwayScheduleManager.createJOUSPObject(); THIS IS USED FOR TESTING
                ((ViewGroup) namebar.getParent()).removeView(namebar);
                mSubwayFragmentListener.onItinerarySelection("BSL");
            }
        });
        return new RecyclerView.ViewHolder(view) {
        };
    }

    /**
     * Empty method, needed for override
     * @param holder recycler view holder
     * @param position item position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * Returns item count.
     * @return 1
     */
    @Override
    public int getItemCount() {
        return 1;
    }
}
