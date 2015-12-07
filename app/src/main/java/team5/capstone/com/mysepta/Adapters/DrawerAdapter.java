package team5.capstone.com.mysepta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;

import io.smooch.ui.ConversationActivity;
import team5.capstone.com.mysepta.AlertsActivity;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SettingsActivity;
import team5.capstone.com.mysepta.TwitterActivity;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private static ArrayList<String> navIcons;
    private static final int ICONS[] = {R.drawable.ic_alerts,R.drawable.ic_settings,R.drawable.ic_help, R.drawable.ic_twitter};
    private static String NAME = "Username";
    private static String EMAIL = "username@gmail.com";
    private static int PROFILE = R.drawable.profile;

    private String name;
    private int profile;
    private String email;
    private Context context;
    private GoogleLoginInterface loginInterface;

    public interface GoogleLoginInterface{
        void login();
    }

    /**
     * Constructor
     * @param passedContext
     * @param loginInterface
     */
    public DrawerAdapter(ArrayList<String> navIcons, Context passedContext, GoogleLoginInterface loginInterface){
        this.navIcons = navIcons;
        this.context = passedContext;
        this.loginInterface = loginInterface;
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
            SignInButton signInButton = (SignInButton)  v.findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_WIDE);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginInterface.login();
                }
            });
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
        if(holder.holderID == 1) {
            holder.textView.setText(navIcons.get(position - 1));
            if(position <= 4){
                holder.imageView.setImageResource(ICONS[position - 1]);
            }

        }else{
            holder.profile.setImageResource(profile);
            holder.name.setText(name);
            holder.email.setText(email);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderID;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;
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
                holderID = 1;
            }else{
                name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = (ImageView) itemView.findViewById(R.id.circleView);
                holderID = 0;
            }
        }

        @Override
        public void onClick(View v) {
            if (getPosition()==1){

                Intent a = new Intent(context, AlertsActivity.class);
                context.startActivity(a);
            }else if (getPosition()==2){

                Intent s = new Intent(context, SettingsActivity.class);
                context.startActivity(s);
            }else if (getPosition()==3) {
                ConversationActivity.show(context);
            }else if (getPosition()==4) {

                Intent t = new Intent(context, TwitterActivity.class);
                context.startActivity(t);
            }else if(getPosition()==5){
                //logout
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return navIcons.size()+1;
    }

    public void setList(ArrayList navIcons){
        this.navIcons = navIcons;
        this.notifyItemInserted(navIcons.size()+1);
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