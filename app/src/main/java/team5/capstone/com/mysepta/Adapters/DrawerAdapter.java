package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import team5.capstone.com.mysepta.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private String mNavTitles[];
    private int mIcons[];

    private String name;
    private int profile;
    private String email;
    Context context;


    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;
        Context context;

        public ViewHolder(View itemView, int ViewType, Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            context = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            if(ViewType == TYPE_CELL) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                RippleDrawable newImage = new RippleDrawable(
                        new ColorStateList(
                                new int[][]{
                                        new int[]{android.R.attr.state_pressed},
                                        new int[]{}
                                },
                                new int[] {
                                        ContextCompat.getColor(context, R.color.headerBlue),
                                        ContextCompat.getColor(context, R.color.headerBlue),
                                }),
                        null,
                        null);
                textView.setBackground(newImage);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            }else{
                Name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = (ImageView) itemView.findViewById(R.id.circleView);
                Holderid = 0;
            }
        }

        @Override
        public void onClick(View v){
            Toast.makeText(context, "The Item Clicked is: " +getPosition(), Toast.LENGTH_SHORT).show();
            //openActivity(getPosition());
        }
    }



    /**
     * Constructor
     * @param Titles
     * @param Icons
     * @param Name
     * @param Email
     * @param Profile
     */
    public DrawerAdapter(String Titles[], int Icons[], String Name, String Email, int Profile, Context passedContext){

        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.context = passedContext;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_CELL) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType, context);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType, context);
            return vhHeader;
        }
        return null;
    }




    /**
     * Initialize view holder
     * @param holder current view holder
     * @param position current item position
     */
    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position -1]);
        }else{
            holder.profile.setImageResource(profile);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_CELL;
    }

    /**
     *
     * @param position
     * @return
     */
    private boolean isPositionHeader(int position) {
        return position == 0;
    }



}