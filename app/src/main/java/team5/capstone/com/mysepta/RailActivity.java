package team5.capstone.com.mysepta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.RailToFromViewAdapter;
import team5.capstone.com.mysepta.DropDownView.RailChooser;
import team5.capstone.com.mysepta.DropDownView.RailChooserChild;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class RailActivity extends AppCompatActivity {

    RecyclerView fromRail;
    RecyclerView toRail;
    VerticalRecyclerViewFastScroller fromScroll;
    VerticalRecyclerViewFastScroller toScroll;
    RailToFromViewAdapter fromRailChoices;
    RailToFromViewAdapter toRailChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail);

        fromRail = (RecyclerView) findViewById(R.id.fromRail);
        fromRail.setLayoutManager(new LinearLayoutManager(this));
        //toRail = (RecyclerView) findViewById(R.id.toRail);
        //toRail.setLayoutManager(new LinearLayoutManager(this));
        /*fromScroll = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller_from);
        toScroll = (VerticalRecyclerViewFastScroller) findViewById(R.id.fast_scroller_to);

        fromScroll.setRecyclerView(fromRail);
        toScroll.setRecyclerView(toRail);

        fromRail.setOnScrollListener(fromScroll.getOnScrollListener());
        toRail.setOnScrollListener(toScroll.getOnScrollListener());*/

        fromRailChoices = new RailToFromViewAdapter(this,getRails());
        fromRailChoices.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        fromRailChoices.setParentClickableViewAnimationDefaultDuration();
        fromRailChoices.setParentAndIconExpandOnClick(true);

        fromRail.setAdapter(fromRailChoices);


       /* toRailChoices = new RailToFromViewAdapter(this,getRails());
        toRailChoices.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        toRailChoices.setParentClickableViewAnimationDefaultDuration();
        toRailChoices.setParentAndIconExpandOnClick(true);

        toRail.setAdapter(toRailChoices);*/

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

        RailChooser from = new RailChooser("@String/from_rail_text");
        RailChooser to = new RailChooser("@String/to_rail_text");

        ArrayList<Object> subRails = new ArrayList<>();
        subRails.add(new RailChooserChild("30th Street"));
        subRails.add(new RailChooserChild("Temple University"));
        from.setChildObjectList(subRails);
        to.setChildObjectList(subRails);
        rails.add(from);
        rails.add(to);

        return rails;
    }
}
