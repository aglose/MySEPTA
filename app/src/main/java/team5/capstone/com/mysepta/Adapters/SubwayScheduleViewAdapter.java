package team5.capstone.com.mysepta.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;

/**
 * Adapter for subway schedule view.
 * Created by tiestodoe on 10/1/15.
 */
public class SubwayScheduleViewAdapter extends RecyclerView.Adapter<SubwayScheduleViewAdapter.SubwayScheduleHolder> {
    private ArrayList<SubwayScheduleItemModel> arrivals;


    /**
     * Constructor
     * @param arrivals list of arrival times for subway
     */
    public SubwayScheduleViewAdapter(ArrayList<SubwayScheduleItemModel> arrivals){
        this.arrivals = arrivals;
    }

    /**
     * Create view holder
     * @param parent parent view group
     * @param viewType view type
     * @return view holder
     */
    @Override
    public SubwayScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subway_schedule_item, parent, false);
        return new SubwayScheduleHolder(view);
    }

    /**
     * Initialize text in viewholder.
     * @param holder view holder
     * @param position current item position
     */
    @Override
    public void onBindViewHolder(SubwayScheduleHolder holder, int position) {
        holder.scheduleText.setText(arrivals.get(position).getFormattedTimeStr());
    }

    /**
     * Get item count.
     * @return size of arrivals array
     */
    @Override
    public int getItemCount() {
        return arrivals.size();
    }

    /**
     * Subway schedule view holder
     */
    public class SubwayScheduleHolder extends RecyclerView.ViewHolder{
        TextView scheduleText;
        /**
         * Constructor
         * @param itemView current item view
         */
        public SubwayScheduleHolder(View itemView) {
            super(itemView);
            scheduleText = (TextView) itemView.findViewById(R.id.scheduleText);
        }
    }
}
