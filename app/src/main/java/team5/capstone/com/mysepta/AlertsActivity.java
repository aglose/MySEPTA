package team5.capstone.com.mysepta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.TabActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
import team5.capstone.com.mysepta.CallbackProxies.AlertsProxy;
import team5.capstone.com.mysepta.Fragment.AlertsFragment;
import team5.capstone.com.mysepta.Fragment.GenAlertsFragment;
import team5.capstone.com.mysepta.Fragment.RRAlertsFragment;
import team5.capstone.com.mysepta.Fragment.SubAlertsFragment;
import team5.capstone.com.mysepta.Models.AlertsModel;

/**
 *
 */
public class AlertsActivity extends AppCompatActivity {
    private static final String TAG = "AlertsActivity";

    private Toolbar toolbar;

    public static ArrayList<AlertsModel> aList;
    public static ArrayList<AlertsModel> generalList;
    public static ArrayList<AlertsModel> rrList;
    public static ArrayList<AlertsModel> subList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        aList = new ArrayList<>();
        generalList = new ArrayList<>();
        rrList = new ArrayList<>();
        subList = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.alerts_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Alerts");

         /* Alert Fragments
        AlertsFragment afrag = new AlertsFragment();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.Alertspage, afrag);
        transaction.commit();
*/
       // GenAlertsFragment genfrag = new GenAlertsFragment();
        RRAlertsFragment rrfrag = new RRAlertsFragment();
        SubAlertsFragment subfrag = new SubAlertsFragment();

        SegmentedGroup dayChoice = (SegmentedGroup) findViewById(R.id.segmentedAlert);
        dayChoice.setTintColor(R.color.blue, R.color.black);
        dayChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);

                String text = (String) rb.getText();
                if (text.equalsIgnoreCase(getString(R.string.general))) {
                    Log.d("tag","isclickedgeneral: "+generalList.get(0).getLastUpdate());
                    Fragment genfrag = new GenAlertsFragment(generalList);
                    loadFragment(R.id.Alertspage, genfrag, false);

                } else if (text.equalsIgnoreCase(getString(R.string.regionalrail))) {
                    RRAlertsFragment rrfrag = new RRAlertsFragment();
                    loadFragment(R.id.Alertspage, rrfrag, false);

                } else if (text.equalsIgnoreCase(getString(R.string.subway))) {
                    SubAlertsFragment subfrag = new SubAlertsFragment();
                    loadFragment(R.id.Alertspage, subfrag, false);

                }
            }
        });

        getAlertsData();



    }

    /**
     *
     * @param paneId
     * @param fragment
     * @param placeOnBackStack
     */
    public void loadFragment(int paneId,Fragment fragment,boolean placeOnBackStack){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        fragmentTransaction.replace(paneId, fragment);

        if(placeOnBackStack){
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public void getAlertsData(){
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                aList = (ArrayList<AlertsModel>) o;
                 /*Sort Alerts JSON into General, Regional Rail, and Subway lists*/
                for  (int i=0; i < aList.size(); i++){
                    if (aList.get(i).isGeneral()){
                        generalList.add(aList.get(i));

                    }else if(aList.get(i).isRegionalRail()){
                        rrList.add(aList.get(i));

                    }else if(aList.get(i).isSubway()){
                        subList.add(aList.get(i));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        AlertsProxy alertsViews = new AlertsProxy();
        alertsViews.getAlertsView(callback);
    }


}