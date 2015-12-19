package team5.capstone.com.mysepta.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;

import io.smooch.ui.ConversationActivity;
import team5.capstone.com.mysepta.AlertsActivity;
import team5.capstone.com.mysepta.R;
import team5.capstone.com.mysepta.SettingsActivity;
import team5.capstone.com.mysepta.TwitterActivity;

/**
 * Adapter to define view functionality of drawer fragment.
 * Created by Matt
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private static ArrayList<String> navTextList;
    private static final int ICONS[] = {R.drawable.ic_alerts, R.drawable.ic_help, R.drawable.ic_twitter, R.drawable.ic_settings};
    private static String NAME = "Username";
    private static String EMAIL = "username@gmail.com";
    private static int PROFILE = R.drawable.profile;

    private String name;
    private int profile;
    private String email;
    private Context context;
    private GoogleLoginInterface loginInterface;
    private Button tweetButton;

    public interface GoogleLoginInterface{
        void login();
        void logout();
    }

    /**
     * Constructor
     * @param passedContext activity
     * @param loginInterface google login functionality
     */
    public DrawerAdapter(ArrayList<String> navTextList, Context passedContext, GoogleLoginInterface loginInterface){
        this.navTextList = navTextList;
        this.context = passedContext;
        this.loginInterface = loginInterface;
    }



    /**
     * Creates drawer viewholder
     * @param parent parent view group
     * @param viewType type of item view
     * @return drawer holder for view
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
            tweetButton = (Button)  v.findViewById(R.id.tweet_button);

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
            holder.textView.setText(navTextList.get(position - 1));
            if(position <= 4){
                holder.imageView.setImageResource(ICONS[position - 1]);
                if(position == 3){
                    holder.tweetButton.setVisibility(View.VISIBLE);
                }
            }

        }else{
            holder.profile.setImageResource(profile);
            holder.name.setText(name);
            holder.email.setText(email);
        }
    }

    /**
     * Creates holder for views to make item views easily retrievable.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderID;

        View rowBackground;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView name;
        TextView email;
        Context context;
        Button tweetButton;

        public ViewHolder(View itemView, int ViewType, Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            context = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            if(ViewType == TYPE_CELL) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                rowBackground = itemView.findViewById(R.id.row_background);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
                    rowBackground.setBackground(newImage);
                }

                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                tweetButton = (Button)  itemView.findViewById(R.id.tweet_button);
                tweetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TweetComposer.Builder builder = new TweetComposer.Builder(context)
                                .text("Hey @SEPTA I noticed....");
                        builder.show();
                    }
                });
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
                ConversationActivity.show(context);
            }else if (getPosition()==3) {
                Intent t = new Intent(context, TwitterActivity.class);
                context.startActivity(t);
            }else if (getPosition()==4) {
                Intent s = new Intent(context, SettingsActivity.class);
                context.startActivity(s);
            }else if(getPosition()==5){
                //logout
                loginInterface.logout();

            }
        }

    }

    /**
     * Retrieves amount of
     * @return size of itemlist
     */
    @Override
    public int getItemCount() {
        return navTextList.size()+1;
    }

    public void setList(ArrayList navIcons, boolean remove){
        this.navTextList = navIcons;
        if(remove){
            this.notifyItemRemoved(5);
        }else{
            this.notifyItemInserted(navIcons.size()+1);
        }

    }
    /**
     *
     * @param position location of view
     * @return TYPE_HEADER if in position 0, otherwise TYPE_CELL
     */
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_CELL;
    }

    /**
     *
     * @param position location of view
     * @return true if item is header
     */
    private boolean isPositionHeader(int position) {
        return position == 0;
    }



}