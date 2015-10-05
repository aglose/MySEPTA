package team5.capstone.com.mysepta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.RailScheduleAdapter;
import team5.capstone.com.mysepta.Adapters.RailToFromViewAdapter;
import team5.capstone.com.mysepta.DropDownView.RailChooser;
import team5.capstone.com.mysepta.DropDownView.RailChooserChild;
import team5.capstone.com.mysepta.Fragment.ToFromFragment;
import team5.capstone.com.mysepta.Models.RailLocationData;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class RailActivity extends AppCompatActivity implements ToFromFragment.OnFragmentInteractionListener{

    /*RecyclerView fromRail;
    RecyclerView toRail;
    RecyclerView scheduleView;
    VerticalRecyclerViewFastScroller fromScroll;
    VerticalRecyclerViewFastScroller toScroll;
    RailToFromViewAdapter fromRailChoices;
    RailToFromViewAdapter toRailChoices;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail);

        Fragment toFromFrag = new ToFromFragment();
//        loadFragment(R.id.tofromfrag, toFromFrag, false);

        Fragment scheduleFrag = new RailScheduleFragment();
  //      loadFragment(R.id.schedulefrag, scheduleFrag, false);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        fragmentTransaction.add(R.id.tofromfrag,toFromFrag);

//        fragmentTransaction.commit();
  //      fragmentManager.executePendingTransactions();

        fragmentTransaction.add(R.id.schedulefrag,scheduleFrag);

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();



        /*scheduleView = (RecyclerView) findViewById(R.id.railSchedule);
        scheduleView.setLayoutManager(new LinearLayoutManager(this));

        fromRail = (RecyclerView) findViewById(R.id.fromRail);
        fromRail.setLayoutManager(new LinearLayoutManager(this));


        //toRail = (RecyclerView) findViewById(R.id.toRail);
        //toRail.setLayoutManager(new LinearLayoutManager(this));
        fromScroll = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller_from);
        toScroll = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller_to);

        fromScroll.setRecyclerView(fromRail);
        toScroll.setRecyclerView(toRail);

        fromRail.setOnScrollListener(fromScroll.getOnScrollListener());
        toRail.setOnScrollListener(toScroll.getOnScrollListener());

        fromRailChoices = new RailToFromViewAdapter(this,getRails());
        fromRailChoices.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        fromRailChoices.setParentClickableViewAnimationDefaultDuration();
        fromRailChoices.setParentAndIconExpandOnClick(true);

        fromRail.setAdapter(fromRailChoices);


        toRailChoices = new RailToFromViewAdapter(this,getRails());
        toRailChoices.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        toRailChoices.setParentClickableViewAnimationDefaultDuration();
        toRailChoices.setParentAndIconExpandOnClick(true);

        toRail.setAdapter(toRailChoices);

        ArrayList<RailLocationData> rails = new ArrayList<>();
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",776,"12:00 PM"));
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",777,"12:05 PM"));
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",778,"12:10 PM"));
        rails.add(new RailLocationData("WTR","West Trenton","Philmont",779,"12:15 PM"));

        fromRail.swapAdapter(new RailScheduleAdapter(rails, this),true);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<ParentObject> getRails(){
        ArrayList<ParentObject> rails = new ArrayList<>();

        RailChooser from = new RailChooser("@string/from_rail_text");
        RailChooser to = new RailChooser("@string/to_rail_text");

        ArrayList<Object> subRails = new ArrayList<>();
        subRails.add(new RailChooserChild("30th Street"));
        subRails.add(new RailChooserChild("Temple University"));
        from.setChildObjectList(subRails);
        to.setChildObjectList(subRails);
        rails.add(from);
        rails.add(to);

        return rails;
    }

    public void loadFragment(int paneId,Fragment fragment,boolean placeOnBackStack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(paneId, fragment);

        if(placeOnBackStack){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
