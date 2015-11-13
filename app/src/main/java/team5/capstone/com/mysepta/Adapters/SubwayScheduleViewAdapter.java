package team5.capstone.com.mysepta.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private static final String TAG = "SubwayScheduleViewAdapter";
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
        SimpleDateFormat arrivalTimeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat differenceInTimeFormat = new SimpleDateFormat("hh:mm");

        Calendar c = Calendar.getInstance();
        String currentTime = arrivalTimeFormat.format(c.getTime());
        Date arrivalDate = null;
        Date currentDate = null;
        try {
            arrivalDate = arrivalTimeFormat.parse(arrivals.get(position).getFormattedTimeStr());
            currentDate = arrivalTimeFormat.parse(currentTime);
            long difference = arrivalDate.getTime() - currentDate.getTime();

            long diffMinutes = difference / (60 * 1000) % 60;
            long diffHours = difference / (60 * 60 * 1000) % 24;
            Log.d(TAG, "Current time: " + currentTime + "\nArrival time: " + arrivals.get(position).getFormattedTimeStr());

            if(difference < 0){
                Log.d(TAG, "negative");
                holder.arriveByText.setText("Left "+String.valueOf( Math.abs(diffMinutes)+" min(s) ago"));
                holder.timeTillText.setText("");
            }else{
                if(diffHours > 1 && diffMinutes > 1){
                    holder.timeTillText.setText(" " + String.valueOf(diffHours) + " hrs " + String.valueOf(diffMinutes) + " mins");
                }else if(diffHours > 1 && diffMinutes == 1){
                    holder.timeTillText.setText(" " + String.valueOf(diffHours) + " hrs " + String.valueOf(diffMinutes) + " min");
                }else if(diffHours == 1 && diffMinutes == 1){
                    holder.timeTillText.setText(" " + String.valueOf(diffHours) + " hr " + String.valueOf(diffMinutes) + " min");
                }else if(diffHours == 1 && diffMinutes > 1){
                    holder.timeTillText.setText(" " + String.valueOf(diffHours) + " hr " + String.valueOf(diffMinutes) + " mins");
                }else if(diffHours == 0 && diffMinutes == 1){
                    holder.timeTillText.setText(" " + String.valueOf(diffMinutes) + " min");
                }else if(diffHours == 0 && diffMinutes > 1){
                    holder.timeTillText.setText(" " + String.valueOf(diffMinutes) + " mins");
                }else{
                    holder.timeTillText.setText(" 0 min");
                }
            }

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
        TextView arriveByText;

        /**
         * Constructor
         * @param itemView current item view
         */
        public SubwayScheduleHolder(View itemView) {
            super(itemView);
            scheduleText = (TextView) itemView.findViewById(R.id.scheduleText);
            arrivalID = (TextView) itemView.findViewById(R.id.arrivalNumber);
            timeTillText = (TextView) itemView.findViewById(R.id.timeTillText);
            arriveByText = (TextView) itemView.findViewById(R.id.arriveByText);
        }
    }
}
