package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import team5.capstone.com.mysepta.Models.NextToArriveRailModel;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.R;

/**
 * Adapter to define functionality of rail schedule fragment.
 * Created by Kevin on 9/30/15.
 */
public class RailScheduleAdapter extends RecyclerView.Adapter {
    private static final String TAG = "RailScheduleAdapter";

    private Context context;
    private ArrayList<RailLocationData> rails;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_CONNECTION = 2;

    /**
     * Constructor
     * @param rails List of rail data
     * @param context activity
     */
    public RailScheduleAdapter(Context context, ArrayList<RailLocationData> rails){
        this.rails = rails;
        this.context = context;
    }


    /**
     * Check type of item view.
     * @param position location of view
     * @return TYPE_HEADER if in position 0, otherwise TYPE_CELL
     */
    @Override
    public int getItemViewType(int position) {

        if(rails.get(position).isConnection()){
            return TYPE_CONNECTION;
        }
        else{
            return TYPE_CELL;
        }
    }

    /**
     * Create viewholder
     * @param parent parent viewgroup
     * @param viewType type of item view
     * @return rail schedule holder for view
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch(viewType) {
            case TYPE_HEADER:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_rail_item, parent, false);
                return new RailScheduleHolder(view);
            }
            case TYPE_CONNECTION:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rail_conn_list_item_card, parent, false);
                return new RailConnectionScheduleHolder(view);
            }
            case TYPE_CELL:{
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rail_list_item_card, parent, false);
                return new RailScheduleHolder(view);
            }


        }

        return null;

    }

    /**
     * Initialize view parameters and bind them.
     * @param holder holder for view
     * @param position position of view
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(getItemViewType(position)) {
            case TYPE_HEADER:{
                //((RailScheduleHolder)holder).arrivalStation.setText(end);
                //((RailScheduleHolder)holder).departureStation.setText(start);
                break;
            }
            case TYPE_CELL:{
                RailLocationData temp = rails.get(position);
                /*if(position<rails.size()-1){
                    RailLocationData temp2 = rails.get(position+1);
                    if(temp2.isConnection()){
                        ((RailScheduleHolder)holder).conImage.setVisibility(View.VISIBLE);
                    }
                }*/

                SimpleDateFormat originalTimeFormat = new SimpleDateFormat("hh:mma");
                SimpleDateFormat newTimeFormat = new SimpleDateFormat("hh:mm a");

                ((RailScheduleHolder) holder).cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                ((RailScheduleHolder)holder).railLineText.setText(temp.getRailName()+" Line");
                ((RailScheduleHolder)holder).railAcronym.setText(temp.getRailAcr());
                ((RailScheduleHolder)holder).trainDestinationLabel.setText("Going to " + temp.getStation());
                ((RailScheduleHolder)holder).trainNo.setText(temp.getRailNumber());
                String newTimeFormatString = "";
                try {
                    Date oldFormatDate = originalTimeFormat.parse(temp.getTime().trim());
                    newTimeFormatString = newTimeFormat.format(oldFormatDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(newTimeFormatString.charAt(0) == '0'){
                    newTimeFormatString = newTimeFormatString.substring(1, newTimeFormatString.length());
                }
                if(temp.getDelay().equalsIgnoreCase("On Time")){
                    ((RailScheduleHolder)holder).originalTimeLabel.setVisibility(View.INVISIBLE);
                    ((RailScheduleHolder)holder).originalTime.setVisibility(View.INVISIBLE);
                    ((RailScheduleHolder)holder).delayText.setVisibility(View.INVISIBLE);
                    ((RailScheduleHolder)holder).delayLabel.setText(temp.getDelay());
                    ((RailScheduleHolder)holder).timeText.setText(newTimeFormatString);
                }else{
                    ((RailScheduleHolder)holder).delayLabel.setText("Delayed - ");
                    ((RailScheduleHolder)holder).delayText.setVisibility(View.VISIBLE);
                    ((RailScheduleHolder)holder).delayText.setText(temp.getDelay());
                    ((RailScheduleHolder)holder).originalTimeLabel.setVisibility(View.VISIBLE);
                    ((RailScheduleHolder)holder).originalTime.setVisibility(View.VISIBLE);
                    ((RailScheduleHolder)holder).originalTime.setText(newTimeFormatString);
                    try{
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(newTimeFormat.parse(newTimeFormatString));
                        Log.d(TAG, "Added time: "+temp.getTime().trim());
                        String additionalTime = "";
                        for(int i=0; i<temp.getDelay().length();i++){
                            if(Character.isDigit(temp.getDelay().charAt(i))){
                                additionalTime += temp.getDelay().charAt(i);
                            }else{
                                break;
                            }
                        }
                        int additionalTimeInt = Integer.parseInt(additionalTime);
                        Log.d(TAG, "Orig time: "+String.valueOf(additionalTimeInt));
                        cal.add(Calendar.MINUTE, additionalTimeInt);
                        String newTime = newTimeFormat.format(cal.getTime());
                        if(newTime.charAt(0) == '0'){
                            newTime = newTime.substring(1, newTime.length());
                        }
                        Log.d(TAG, "Final time: "+newTime);
                        ((RailScheduleHolder)holder).timeText.setText(newTime);
                    }catch(Exception e){
                        Log.d(TAG, e.toString());
                    }
                }
                if(temp.isConnection() == false){
                    ((RailScheduleHolder)holder).directRoute.setText("Yes");
                }else{
                    ((RailScheduleHolder)holder).directRoute.setText("No");
                }

                break;
            }
            case TYPE_CONNECTION:{
                RailLocationData temp = rails.get(position);
                ((RailConnectionScheduleHolder) holder).cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                ((RailConnectionScheduleHolder) holder).railText.setText(temp.getRailName());
                ((RailConnectionScheduleHolder)holder).railAcr.setText(temp.getRailAcr());
                ((RailConnectionScheduleHolder)holder).timeText.setText(temp.getTime());
                ((RailConnectionScheduleHolder)holder).trainText.setText("#" + temp.getRailNumber() + " to " + temp.getStation());
                break;
            }

        }
    }

    /**
     * Return number of items.
     * @return number of rails + headers
     */
    @Override
    public int getItemCount() {
        return rails.size();
    }

    /**
     * Creates holder for views to make item views easily retrievable.
     */
    public class RailScheduleHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView railLineText;
        TextView trainDestinationLabel;
        TextView railAcronym;
        TextView timeText;
        TextView originalTimeLabel;
        TextView originalTime;
        TextView directRoute;
        TextView trainNo;
        TextView delayLabel;
        TextView delayText;


        /**
         * Constructor
         * @param itemView current item
         */
        public RailScheduleHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.rail_item_card);
            railLineText = (TextView) itemView.findViewById(R.id.trainLineLabel);
            trainDestinationLabel = (TextView) itemView.findViewById(R.id.trainDestinationLabel);
            railAcronym = (TextView) itemView.findViewById(R.id.lineAcronym);
            timeText = (TextView) itemView.findViewById(R.id.arrivalTime);
            originalTimeLabel = (TextView) itemView.findViewById(R.id.originalTimeLabel);
            originalTime = (TextView) itemView.findViewById(R.id.originalTime);
            directRoute = (TextView) itemView.findViewById(R.id.directRoute);
            trainNo = (TextView) itemView.findViewById(R.id.trainNo);
            delayLabel = (TextView) itemView.findViewById(R.id.delay);
            delayText = (TextView) itemView.findViewById(R.id.delayText);
        }
    }

    /**
     * Creates holder for views to make item views easily retrievable.
     */
    public class RailConnectionScheduleHolder extends RecyclerView.ViewHolder{
        TextView railText;
        TextView railAcr;
        TextView trainText;
        TextView timeText;
        CardView cardView;

        /**
         * Constructor
         * @param itemView current item
         */
        public RailConnectionScheduleHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.rail_conn_item_card);
            railText = (TextView) itemView.findViewById(R.id.trainLabelConn);
            railAcr = (TextView) itemView.findViewById(R.id.lineNameConn);
            trainText = (TextView) itemView.findViewById(R.id.trainInfoConn);
            timeText = (TextView) itemView.findViewById(R.id.arrivalTimeConn);
        }
    }
}
