package team5.capstone.com.mysepta;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.mockito.Mockito;

import java.util.ArrayList;

import team5.capstone.com.mysepta.Fragment.RailScheduleFragment;
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

        verify(fragment,times(1)).setupRailAdapter(Mockito.any((Class<ArrayList<NextToArriveRailModel>>)(Class)ArrayList.class),anyString(),anyString());
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
}
