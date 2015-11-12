package team5.capstone.com.mysepta.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance();
        String currentTime = timeFormat.format(c.getTime());
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = timeFormat.parse(arrivals.get(position).getFormattedTimeStr());
            d2 = timeFormat.parse(currentTime);
            long difference = d1.getTime() - d2.getTime();
            Date diff = new Date(difference);
            String diffTime = timeFormat.format(diff.getTime());
            holder.timeTillText.setText(diffTime);
        } catch (Exception e) {
            e.printStackTrace();
            holder.timeTillText.setText("error");
        }
        holder.scheduleText.setText(arrivals.get(position).getFormattedTimeStr());
        holder.arrivalID.setText(String.valueOf(position));

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
        TextView arrivalID;
        TextView timeTillText;

        /**
         * Constructor
         * @param itemView current item view
         */
        public SubwayScheduleHolder(View itemView) {
            super(itemView);
            scheduleText = (TextView) itemView.findViewById(R.id.scheduleText);
            arrivalID = (TextView) itemView.findViewById(R.id.arrivalNumber);
            timeTillText = (TextView) itemView.findViewById(R.id.timeTillText);
        }
    }
}
