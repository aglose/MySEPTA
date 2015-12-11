package team5.capstone.com.mysepta;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.Fragment.RailScheduleFragment;
import team5.capstone.com.mysepta.Models.AlertsDescriptionModel;
import team5.capstone.com.mysepta.Models.AlertsModel;
import team5.capstone.com.mysepta.Models.NextToArriveRailModel;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Kevin on 12/10/15.
 */
public class MockTests extends InstrumentationTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    @SmallTest
    public void testRailNextToArriveService(){
        RailScheduleFragment fragment = Mockito.mock(RailScheduleFragment.class);

        fragment.getNextTrainData("Philmont", "Temple University", "5");

        ArrayList<NextToArriveRailModel> rails = new ArrayList<>();

        verify(fragment,times(1)).setupRailAdapter(Mockito.any((Class<ArrayList<NextToArriveRailModel>>) (Class) ArrayList.class), anyString(), anyString());
    }

    @SmallTest
    public void testAllRailsNextToArriveService(){
        RailScheduleFragment fragment = Mockito.mock(RailScheduleFragment.class);

        fragment.getNextTrainData("Philmont", "Temple University", "5");

        String[] stations = getInstrumentation().getContext().getResources().getStringArray(R.array.station_names);
        for(String start:stations) {
            for(String end:stations) {
                fragment.getNextTrainData(start,end,"5");
                verify(fragment, times(1)).setupRailAdapter(Mockito.any((Class<ArrayList<NextToArriveRailModel>>)(Class)ArrayList.class), anyString(), anyString());
            }
        }
    }

    /*
    @SmallTest
    public void testAlertsDescriptionService(){
        AlertsActivity alerts = Mockito.mock(AlertsActivity.class);

        alerts.getAlertsDescriptionData();

        ArrayList<AlertsDescriptionModel> alertsList = new ArrayList<>();

        verify(alerts,times(1)).setupAlertsAdapter(Mockito.any((Class<ArrayList<AlertsDescriptionModel>>) (Class) ArrayList.class), anyString(), anyString());
    }

    @SmallTest
    public void testAlertsFragment() throws Exception {
        RailScheduleFragment fragment = Mockito.mock(RailScheduleFragment.class);


        ArrayList<AlertsModel> aList = new ArrayList<>();
        aList.add(new AlertsModel("3","West Trenton","Regional Rail","Y","N","N","N","N","Dec 11 2015 1:00pm"));
        AlertsAdapter alertsAdapter = new AlertsAdapter(aList, getContext());


        alertsRecyclerView.setAdapter(alertsAdapter);

        assertNotNull(aList);
        assertNotNull(alertsAdapter);

    }

    @SmallTest
    public void testBusAlertsFragment() throws Exception {

        ArrayList<AlertsDescriptionModel> busList = new ArrayList<>();
        busList.add(new AlertsDescriptionModel("2", " : ", " : ", "the line will run 5-15 minutes behind"));

        BusAlertsFragment(busList);
        alertsAdapter = new AlertsAdapter(busList, getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        assertNotNull(busList);
        assertNotNull(alertsAdapter);
        assertNotNull(alertsRecyclerView);
        assertEquals(subList.get(0).getRouteName(), "2");

    }

    @SmallTest
    public void testRRAlertsFragment() throws Exception {

        ArrayList<AlertsDescriptionModel> rrList = new ArrayList<>();
        busList.add(new AlertsDescriptionModel("Trenton", " : ", " : ", "the line will run 5-15 minutes behind"));

        RRAlertsFragment(rrList);
        alertsAdapter = new AlertsAdapter(rrList, getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        assertNotNull(rrList);
        assertNotNull(alertsAdapter);
        assertNotNull(alertsRecyclerView);
        assertNotNUll(subList.get(0).getRouteId());

    }

    @SmallTest
    public void testSubAlertsFragment() throws Exception {

        ArrayList<AlertsDescriptionModel> subList = new ArrayList<>();
        busList.add(new AlertsDescriptionModel("Market Frankford", " : ", " : ", "the line will run 5-15 minutes behind"));

        SubAlertsFragment(subList);
        alertsAdapter = new AlertsAdapter(subList, getContext());
        alertsRecyclerView.setAdapter(alertsAdapter);

        assertNotNull(subList);
        assertNotNull(alertsAdapter);
        assertNotNull(alertsRecyclerView);
    }
    */
}
