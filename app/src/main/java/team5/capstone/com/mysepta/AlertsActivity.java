package team5.capstone.com.mysepta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import team5.capstone.com.mysepta.CallbackProxies.AlertsDescriptionProxy;
import team5.capstone.com.mysepta.CallbackProxies.AlertsProxy;
import team5.capstone.com.mysepta.Fragment.BusAlertsFragment;
import team5.capstone.com.mysepta.Fragment.GenAlertsFragment;
import team5.capstone.com.mysepta.Fragment.RRAlertsFragment;
import team5.capstone.com.mysepta.Fragment.SubAlertsFragment;
import team5.capstone.com.mysepta.Models.AlertsDescriptionModel;
import team5.capstone.com.mysepta.Models.AlertsModel;

/**
 *
 */
public class AlertsActivity extends AppCompatActivity {
    private static final String TAG = "AlertsActivity";

    private Toolbar toolbar;

    public static ArrayList<AlertsModel> allAlertsList;
    public static ArrayList<AlertsModel> generalList;
    public static ArrayList<AlertsModel> rrList;
    public static ArrayList<AlertsModel> subList;
    public static ArrayList<AlertsModel> busList;

    public static ArrayList<AlertsDescriptionModel> alertsDescriptionList;

    private SegmentedGroup dayChoice;
    private RotateLoading mLoadingView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        allAlertsList = new ArrayList<>();
        generalList = new ArrayList<>();
        rrList = new ArrayList<>();
        subList = new ArrayList<>();
        busList = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.alerts_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Alerts");

        mLoadingView = (RotateLoading) findViewById(R.id.rotateloading);
        mLoadingView.start();
        frameLayout = (FrameLayout) findViewById(R.id.alertsPageFragment);
        frameLayout.setVisibility(View.INVISIBLE);

        dayChoice = (SegmentedGroup) findViewById(R.id.segmentedAlert);
        dayChoice.setTintColor(R.color.blue, R.color.black);
        dayChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);

                String text = (String) rb.getText();
                if (text.equalsIgnoreCase(getString(R.string.general))) {
                    Fragment genfrag = new GenAlertsFragment(generalList);
                    loadFragment(R.id.alertsPageFragment, genfrag, false);

                } else if (text.equalsIgnoreCase(getString(R.string.regionalrail))) {
                    RRAlertsFragment rrfrag = new RRAlertsFragment(rrList);
                    loadFragment(R.id.alertsPageFragment, rrfrag, false);

                } else if (text.equalsIgnoreCase(getString(R.string.subway))) {
                    SubAlertsFragment subfrag = new SubAlertsFragment(subList);
                    loadFragment(R.id.alertsPageFragment, subfrag, false);

                } else if (text.equalsIgnoreCase(getString(R.string.bus))) {
                    BusAlertsFragment busFrag = new BusAlertsFragment(busList);
                    loadFragment(R.id.alertsPageFragment, busFrag, false);
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
    public void loadFragment(int paneId, Fragment fragment, boolean placeOnBackStack){
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
                allAlertsList = (ArrayList<AlertsModel>) o;
                 /*Sort Alerts JSON into General, Regional Rail, and Subway lists*/
                for  (int i=0; i < allAlertsList.size(); i++){
                    AlertsModel model = allAlertsList.get(i);
                    Log.d(TAG, "Alert "+i+": \n   "+model.toString());

                    if (allAlertsList.get(i).isGeneral()){
                        Log.d(TAG, "General desc:"+allAlertsList.get(i).getDescription());
                        if(!model.isAlertDeleteable()){
                            generalList.add(allAlertsList.get(i));
                        }else{
                            AlertsModel genModel = allAlertsList.get(i);
                            genModel.setDescription(Html.fromHtml("<h3>There are no general Alerts at this time.</h3"));
                            generalList.add(genModel);
                        }

                    }else if(allAlertsList.get(i).isRegionalRail()){
                        if(!model.isAlertDeleteable()) {
                            if(model.getRouteName().equalsIgnoreCase("Chestnut Hill East")){
                                Log.d("debug", model.toString());
                            }
                            rrList.add(allAlertsList.get(i));
                        }

                    }else if(allAlertsList.get(i).isSubway()){
                        if(!model.isAlertDeleteable()){
                            subList.add(allAlertsList.get(i));
                        }

                        Log.d(TAG, "Subway size:" + subList.size());

                    }else if(allAlertsList.get(i).isBus()){
                        if(!model.isAlertDeleteable()){
                            AlertsModel modelChange = allAlertsList.get(i);
                            modelChange.setRouteName("Bus Line - "+modelChange.getRouteName());
                            busList.add(modelChange);
                        }

                        Log.d(TAG, "Subway size:" + subList.size());

                    }
                }
                RadioButton b = (RadioButton) findViewById(R.id.generalAlertButton);
                b.setChecked(true);
                getAlertsDescriptionData();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        AlertsProxy alertsViews = new AlertsProxy();
        alertsViews.getAlertsView(callback);
    }

    private void getAlertsDescriptionData() {
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                alertsDescriptionList = (ArrayList<AlertsDescriptionModel>) o;

                for(Iterator<AlertsModel> it = allAlertsList.iterator(); it.hasNext(); ){
                    AlertsModel alert = it.next();
                    for(AlertsDescriptionModel alertDescription: alertsDescriptionList){
                        if(alert.getRouteId().equalsIgnoreCase(alertDescription.getRouteId())){
                            if (alert.getIsAlert().equalsIgnoreCase("Y")){
                                String strippedText = alertDescription.getCurrentMessage().replaceAll("\\t", " ");
                                alert.setDescription(Html.fromHtml(strippedText));
                            }else if(alert.getIsDetour().equalsIgnoreCase("Y")){
                                String strippedText = alertDescription.getDetourMessage();
                                alert.setDescription(Html.fromHtml(strippedText));
                            }else if(alert.getIsAdvisory().equalsIgnoreCase("yes")){
                                String strippedText = alertDescription.getAdvisoryMessage().replaceAll("\\t", " ");
                                alert.setDescription(Html.fromHtml(strippedText));
                            }else if(alert.getIsSnow().equalsIgnoreCase("y")){
                                String strippedText = alertDescription.getCurrentMessage();
                                alert.setDescription(Html.fromHtml(strippedText));
                            }else if(alert.getIsSuspended().equalsIgnoreCase("y")){
                                String strippedText = alertDescription.getCurrentMessage();
                                alert.setDescription(Html.fromHtml(strippedText));
                            }else{
                                String strippedText = alertDescription.getCurrentMessage();
                                alert.setDescription(Html.fromHtml(strippedText));
                            }
                        }

                    }
                }
                mLoadingView.stop();
                crossFadeViews();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Debug", "fail");
            }
        };

        AlertsDescriptionProxy alertsViews = new AlertsDescriptionProxy();
        alertsViews.getAlertsDescriptionView("all", callback);
    }

    private void crossFadeViews() {
        mLoadingView.animate()
                .alpha(0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.VISIBLE);
                    }
                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}