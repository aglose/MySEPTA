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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;

import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team5.capstone.com.mysepta.Adapters.AlertsAdapter;
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

        String[] alrtMenu = getResources().getStringArray(R.array.Alerts_Menu);
        ListView lv = (ListView)findViewById(R.id.alertsMenu);
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.alert_list_item, alrtMenu);
        lv.setAdapter(adapter);


        /* Alert Fragments*/
        AlertsFragment afrag = new AlertsFragment();
        final GenAlertsFragment genfrag = new GenAlertsFragment();
        final RRAlertsFragment rrfrag = new RRAlertsFragment();
        final SubAlertsFragment subfrag = new SubAlertsFragment();


        /* Fragment Manager*/
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //transaction.add(R.id.fragmentAlerts, afrag);
        //transaction.commit();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                /* Loads respective fragments*/
                if (position == 0) {
                    loadFragment(R.id.fragAlerts, genfrag, false);
                    getSupportActionBar().setTitle("General Alerts");

                } else if (position == 1) {
                    loadFragment(R.id.fragAlerts, rrfrag, false);
                    getSupportActionBar().setTitle("Regional Rail Alerts");

                } else if (position == 2) {
                    loadFragment(R.id.fragAlerts, subfrag, false);
                    getSupportActionBar().setTitle("Subway Alerts");

                }
                //Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

            }
        });


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
}