package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import team5.capstone.com.mysepta.Models.SubwayScheduleItemModel;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SubwayActivity;

/**
 * Adapter for subway schedule view.
 * Created by tiestodoe on 10/1/15.
 */
public class SubwayScheduleViewAdapter extends RecyclerView.Adapter<SubwayScheduleViewAdapter.SubwayScheduleHolder> {
    private static final String TAG = "SubwayScheduleViewAdapter";
    private ArrayList<SubwayScheduleItemModel> arrivals;
    private Context context;

    /**
     * Constructor
     * @param arrivals list of arrival times for subway
     */
    public SubwayScheduleViewAdapter(ArrayList<SubwayScheduleItemModel> arrivals, Context context){
        this.arrivals = arrivals;
        this.context = context;
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
        SimpleDateFormat arrivalTimeFormat = new SimpleDateFormat("hh:mm aa");

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
            Log.d(TAG, "Position: "+position+"\nDifference min: "+diffMinutes+" hour: "+diffHours+"\nCurrent time: " + currentTime + "\nArrival time: " + arrivals.get(position).getFormattedTimeStr());

            if(diffMinutes < 0 && diffHours < 0){
                Log.d(TAG, "negative");
                holder.arriveByText.setText("Left "+String.valueOf( Math.abs(diffMinutes)+" min(s) ago"));
                holder.timeTillText.setText("");
            }else{
                holder.arriveByText.setText("Arrives in ");
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

        String line = arrivals.get(0).getLine();
        holder.lineText.setText(line);

        if(line.equalsIgnoreCase("BSL")){
            holder.circleView.setBackground(context.getDrawable(R.drawable.circle_bsl));
        }

        holder.setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Set and alarm", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void clearData() {
        int size = this.arrivals.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.arrivals.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void setList(ArrayList arrivals){
        this.arrivals = arrivals;
        this.notifyItemRangeInserted(0, arrivals.size()-1);
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
        TextView lineText;
        View circleView;
        ImageButton setAlarmButton;

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
            lineText = (TextView) itemView.findViewById(R.id.lineText);
            circleView = itemView.findViewById(R.id.circleView);
            setAlarmButton = (ImageButton) itemView.findViewById(R.id.setAlarmButton);
        }
    }
}
