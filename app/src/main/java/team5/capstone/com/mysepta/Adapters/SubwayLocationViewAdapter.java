package team5.capstone.com.mysepta.Adapters;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import team5.capstone.com.mysepta.MainActivity;
import team5.capstone.com.mysepta.Models.SubwayLocationData;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SubwayActivity;

/**
 * Adapter for subway location view fragment.
 * Created by Andrew on 9/20/2015.
 */
public class SubwayLocationViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = "SubwayLocationViewAdapter";

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    private MainActivity context;
    private String[] listLocations;
    private SubwayLocationData subwayLocationData;
    private String line;
    private SubwayItineraryViewAdapter.ChangeItineraryListener mChangeItineraryListener;

    /**
     * Constructor
     * @param line name of subway line
     * @param context activity
     * @param subwayLocationData stop data
     */
    public SubwayLocationViewAdapter(String line, MainActivity context, SubwayLocationData subwayLocationData, SubwayItineraryViewAdapter.ChangeItineraryListener mChangeItineraryListener){
        if(line.equalsIgnoreCase("MFL")){
            listLocations = context.getResources().getStringArray(R.array.market_frankford_line_sorted);
        }else if(line.equalsIgnoreCase("BSL")){
            listLocations = context.getResources().getStringArray(R.array.broad_street_line_sorted);
        }else{
            Log.d(TAG, "Messed up mere");
        }
        this.line = line;
        this.subwayLocationData = subwayLocationData;
        this.context = context;
        this.mChangeItineraryListener = mChangeItineraryListener;
    }

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
     * Inflate view holder.
     * @param parent parent view group
     * @param viewType view type
     * @return subway location holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subway_location_header, parent, false);
            return new SubwayLocationHeaderHolder(view){};
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subway_location_card, parent, false);

            return new SubwayLocationItemHolder(view);
        }
    }

    /**
     * Initialize and bind view holder.  Starts subway activity and passes data.
     * @param holder current item holder
     * @param position current item position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        boolean imageItem = false;
        if(getItemViewType(position) == TYPE_HEADER){
            ((SubwayLocationHeaderHolder)holder).header.setCardBackgroundColor(ContextCompat.getColor(context, R.color.headerBlue));
            ((SubwayLocationHeaderHolder)holder).headerText.setGravity(Gravity.CENTER);
            ((SubwayLocationHeaderHolder)holder).backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Pressed");
                    mChangeItineraryListener.itineraryBack();
                }
            });
        }else{
            final String locationName = listLocations[position-1];

            ((SubwayLocationItemHolder)holder).lineConnectingRoutes.bringToFront();
            ((SubwayLocationItemHolder)holder).roundedImage.bringToFront();
            if(locationName.equalsIgnoreCase("Fern Rock Transportation Center") || locationName.equalsIgnoreCase("AT&T") ||
                    locationName.equalsIgnoreCase("City Hall") || locationName.equalsIgnoreCase("69th Street Transportation Center") ||
                    locationName.equalsIgnoreCase("15th St") || locationName.equalsIgnoreCase("Frankford Transportation Center")){
                imageItem = true;
                if(locationName.equalsIgnoreCase("Fern Rock Transportation Center") || locationName.equalsIgnoreCase("69th Street Transportation Center")){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((SubwayLocationItemHolder)holder).lineConnectingRoutes.getLayoutParams();
                    params.setMargins(params.leftMargin, 8, 0, 0); //substitute parameters for left, top, right, bottom
                    ((SubwayLocationItemHolder)holder).lineConnectingRoutes.setLayoutParams(params);
                }else if(locationName.equalsIgnoreCase("AT&T") || locationName.equalsIgnoreCase("Frankford Transportation Center")){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((SubwayLocationItemHolder)holder).lineConnectingRoutes.getLayoutParams();
                    params.setMargins(params.leftMargin, 0, 0, 8); //substitute parameters for left, top, right, bottom
                    ((SubwayLocationItemHolder)holder).lineConnectingRoutes.setLayoutParams(params);
                }
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes2.setVisibility(View.INVISIBLE);
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes.bringToFront();
                ((SubwayLocationItemHolder)holder).roundedImage.bringToFront();
                ((SubwayLocationItemHolder)holder).roundedImage.setVisibility(View.VISIBLE);
                ((SubwayLocationItemHolder)holder).roundedImage.setImageResource(R.drawable.fern_rock_bsl_map);
            }else{
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes2.setVisibility(View.VISIBLE);
                ((SubwayLocationItemHolder)holder).roundedImage.setVisibility(View.INVISIBLE);
            }

            int circleCount = 0;
            if(line.equalsIgnoreCase("BSL")){
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes.setBackgroundColor(ContextCompat.getColor(context, R.color.broadStreetOrange));
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes2.setBackgroundColor(ContextCompat.getColor(context, R.color.broadStreetOrange));
                ((SubwayLocationItemHolder)holder).roundedImage.setBorderColor(ContextCompat.getColor(context, R.color.broadStreetOrange));
                String trainTypes = SubwayLocationData.getTypesTrains(locationName);

                for(int i=0; i<3; i++){
                    if(trainTypes.charAt(i) == '1'){
                        circleCount++;
                        switch(circleCount){
                            case 1:
                                ((SubwayLocationItemHolder)holder).circleView1.setVisibility(View.VISIBLE);
                                switch(i){
                                    case 0:
                                        ((SubwayLocationItemHolder)holder).circleView1.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_express_sub));
                                        break;
                                    case 1:
                                        ((SubwayLocationItemHolder)holder).circleView1.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_spur_sub));
                                        break;
                                    case 2:
                                        ((SubwayLocationItemHolder)holder).circleView1.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_special_sub));
                                        break;
                                }
                                break;
                            case 2:
                                ((SubwayLocationItemHolder)holder).circleView2.setVisibility(View.VISIBLE);
                                switch(i){
                                    case 0:
                                        ((SubwayLocationItemHolder)holder).circleView2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_express_sub));
                                        break;
                                    case 1:
                                        ((SubwayLocationItemHolder)holder).circleView2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_spur_sub));
                                        break;
                                    case 2:
                                        ((SubwayLocationItemHolder)holder).circleView2.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_special_sub));
                                        break;
                                }
                                break;
                            case 3:
                                ((SubwayLocationItemHolder)holder).circleView3.setVisibility(View.VISIBLE);
                                switch(i){
                                    case 0:
                                        ((SubwayLocationItemHolder)holder).circleView3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_express_sub));
                                        break;
                                    case 1:
                                        ((SubwayLocationItemHolder)holder).circleView3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_spur_sub));
                                        break;
                                    case 2:
                                        ((SubwayLocationItemHolder)holder).circleView3.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_special_sub));
                                        break;
                                }
                                break;
                        }
                    }
                }
                switch(circleCount){
                    case 0:
                        ((SubwayLocationItemHolder)holder).circleView1.setVisibility(View.INVISIBLE);
                        ((SubwayLocationItemHolder)holder).circleView2.setVisibility(View.INVISIBLE);
                        ((SubwayLocationItemHolder)holder).circleView3.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        ((SubwayLocationItemHolder)holder).circleView2.setVisibility(View.INVISIBLE);
                        ((SubwayLocationItemHolder)holder).circleView3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        ((SubwayLocationItemHolder)holder).circleView3.setVisibility(View.INVISIBLE);
                        break;
                }

            }else{
                ((SubwayLocationItemHolder) holder).circleView1.setVisibility(View.INVISIBLE);
                ((SubwayLocationItemHolder) holder).circleView2.setVisibility(View.INVISIBLE);
                ((SubwayLocationItemHolder) holder).circleView3.setVisibility(View.INVISIBLE);
                ((SubwayLocationItemHolder) holder).roundedImage.setBorderColor(ContextCompat.getColor(context, R.color.marketFrankfordBlue));
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes.setBackgroundColor(ContextCompat.getColor(context, R.color.marketFrankfordBlue));
                ((SubwayLocationItemHolder)holder).lineConnectingRoutes2.setBackgroundColor(ContextCompat.getColor(context, R.color.marketFrankfordBlue));
            }

            Log.d(TAG, String.valueOf(position)+" "+locationName);
            ((SubwayLocationItemHolder)holder).locationText.setText(locationName);
            final boolean finalImageItem = imageItem;
            ((SubwayLocationItemHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stopID = 0;
                    String direction = "";
                    if (line.equalsIgnoreCase("BSL")) {
//                        stopID = subwayLocationData.getStopId(locationName, "North");
                        direction = "NORTH";
                    } else {
//                        stopID = subwayLocationData.getStopId(locationName, "East");
                        direction = "EAST";
                    }
                    Intent startSubwayActivity = new Intent(context, SubwayActivity.class);


                    Bundle bundle = new Bundle();
                    bundle.putInt(context.getString(R.string.STOP_ID_KEY), stopID);
                    bundle.putString(context.getString(R.string.DIRECTION_KEY), direction);
                    bundle.putString(context.getString(R.string.LINE_KEY), line);
                    bundle.putString(context.getString(R.string.LOCATION_KEY), locationName);
                    startSubwayActivity.putExtras(bundle);
                    context.startActivity(startSubwayActivity);

                }
            });
        }

    }

    /**
     * Get number of items.
     * @return number of listLocations and header
     */
    @Override
    public int getItemCount() {
        return listLocations.length+1;
    }

    /**
     * Holder for subway locations
     */
    public class SubwayLocationItemHolder extends RecyclerView.ViewHolder{
        LinearLayout circleViews;
        TextView locationText;
        View lineConnectingRoutes;
        View lineConnectingRoutes2;
        View circleView1;
        View circleView2;
        View circleView3;
        RoundedImageView roundedImage;
        CardView cardView;

        /**
         * Constructor
         * @param itemView item view
         */
        public SubwayLocationItemHolder(View itemView) {
            super(itemView);
            circleViews = (LinearLayout) itemView.findViewById(R.id.circleViews);
            cardView = (CardView) itemView.findViewById(R.id.subway_location_item_card);
            locationText = (TextView) itemView.findViewById(R.id.locationText);
            roundedImage = (RoundedImageView) itemView.findViewById(R.id.roundedImage);
            lineConnectingRoutes = itemView.findViewById(R.id.lineConnectingRoutes);
            lineConnectingRoutes2 = itemView.findViewById(R.id.lineConnectingRoutes2);
            circleView1 = itemView.findViewById(R.id.circleView1);
            circleView2 = itemView.findViewById(R.id.circleView2);
            circleView3 = itemView.findViewById(R.id.circleView3);
        }
    }

    public class SubwayLocationHeaderHolder extends RecyclerView.ViewHolder{
        CardView header;
        TextView headerText;
        View backButton;

        /**
         * Constructor
         * @param itemView item view
         */
        public SubwayLocationHeaderHolder(View itemView) {
            super(itemView);
            header = (CardView) itemView.findViewById(R.id.subwayLocationHeader);
            headerText = (TextView) itemView.findViewById(R.id.header_title);
            backButton = itemView.findViewById(R.id.backButton);
        }
    }
}
