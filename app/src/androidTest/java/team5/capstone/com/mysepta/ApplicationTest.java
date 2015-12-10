package team5.capstone.com.mysepta;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

//import com.google.android.testing.mocking.AndroidMock;

import team5.capstone.com.mysepta.Adapters.RailExpandableAdapter;
import team5.capstone.com.mysepta.Adapters.RailItineraryViewAdapter;
import team5.capstone.com.mysepta.Adapters.RailScheduleAdapter;
import team5.capstone.com.mysepta.Adapters.RailStaticAdapter;
import team5.capstone.com.mysepta.Adapters.SubwayItineraryViewAdapter;
import team5.capstone.com.mysepta.Managers.FavoritesManager;
import team5.capstone.com.mysepta.Models.RailLocationData;
import team5.capstone.com.mysepta.Models.StaticRailModel;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @SmallTest
    public void testRailExpandableAdapter(){
        ArrayList<String> rails = new ArrayList<>();
        rails.add("Start");
        rails.add("End");

        HashMap<String,ArrayList<String>> railChoices = new HashMap<>();
        railChoices.put("Start",rails);
        railChoices.put("End", rails);

        RailExpandableAdapter railExpandableAdapter = new RailExpandableAdapter(getContext(),rails,railChoices);

        //Test initial setup
        assertEquals(railExpandableAdapter.getGroupCount(), 2);
        assertTrue(railExpandableAdapter.isChildSelectable(0,0));
        assertFalse(railExpandableAdapter.hasStableIds());
        assertEquals(railExpandableAdapter.getChildId(0, 0), 0);
        assertEquals(railExpandableAdapter.getChildId(0, 1), 1);
        assertEquals(railExpandableAdapter.getGroupId(0), 0);
        assertEquals((String) railExpandableAdapter.getChild(0, 0), "Start");
        assertEquals((String)railExpandableAdapter.getGroup(0),"Start");
    }

    @SmallTest
    public void testRailExpandableAdapterUpdate(){
        ArrayList<String> rails = new ArrayList<>();
        rails.add("Start");
        rails.add("End");

        HashMap<String,ArrayList<String>> railChoices = new HashMap<>();
        railChoices.put("Start", rails);
        railChoices.put("End", rails);

        RailExpandableAdapter railExpandableAdapter = new RailExpandableAdapter(getContext(),rails,railChoices);

        railExpandableAdapter.updateParent(0, "Hello");
        assertEquals((String)railExpandableAdapter.getGroup(0),"Hello");
    }

    @SmallTest
    public void testRailScheduleAdapter(){
        String start = "start";
        String end = "end";

        ArrayList<RailLocationData> rails = new ArrayList<>();
        rails.add(new RailLocationData("WTR","West Trenton Line","Philmont","438","12:00 PM",false));
        rails.add(new RailLocationData("WTR", "West Trenton Line", "Betharyes", "438", "12:30 PM", false));

        RailScheduleAdapter adapter = new RailScheduleAdapter(rails,getContext(),start,end);

        assertEquals(adapter.getItemCount(),2);
    }

    @SmallTest
    public void testRailStaticAdapter(){
        ArrayList<StaticRailModel> rails = new ArrayList<>();
        rails.add(new StaticRailModel("1234","12:30 PM","12:45 PM"));
        rails.add(new StaticRailModel("1234","1:00 PM","1:45 PM"));
        rails.add(new StaticRailModel("1234", "2:30 PM", "2:45 PM"));
        rails.add(new StaticRailModel("1234", "8:30 AM", "9:45 AM"));

        RailStaticAdapter adapter = new RailStaticAdapter(rails);

        assertEquals(adapter.getItemCount(),5);
    }

    @SmallTest
    public void testRailStaticAdapterSort(){
        ArrayList<StaticRailModel> rails = new ArrayList<>();
        rails.add(new StaticRailModel("12","12:30 PM","12:45 PM"));
        rails.add(new StaticRailModel("123", "1:00 PM", "1:45 PM"));
        rails.add(new StaticRailModel("1234", "2:30 PM", "2:45 PM"));
        rails.add(new StaticRailModel("1", "8:30 AM", "9:45 AM"));

        RailStaticAdapter adapter = new RailStaticAdapter(rails);

        rails = adapter.sortArray(rails);
        assertEquals(rails.get(0).getTrainNumber(),"1");
    }

    @SmallTest
    public void testRailItineraryViewAdapter(){
        RailItineraryViewAdapter adapter = new RailItineraryViewAdapter(0,0,getContext());

        assertEquals(adapter.getItemCount(),17);
        assertEquals(adapter.getItemViewType(5),2);
    }

    @SmallTest
    public void testFavoritesManager(){
        FavoritesManager favorites = FavoritesManager.getInstance();

        assertNotNull(favorites);
    }

    @SmallTest
    public void testSubwayItineraryAdapter(){

        SubwayItineraryViewAdapter adapter = new SubwayItineraryViewAdapter(getContext(),null);


        assertEquals(adapter.getItemCount(), 0);
    }

    @SmallTest
    public void testSubwayLocationAdapter(){
    }

    @SmallTest
    public void testSubwayScheduleAdapter(){

    }


}